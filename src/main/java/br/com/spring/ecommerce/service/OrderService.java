package br.com.spring.ecommerce.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AddressDTO;
import br.com.spring.ecommerce.dto.CartProductsDTO;
import br.com.spring.ecommerce.dto.CartTotalPriceDTO;
import br.com.spring.ecommerce.dto.CuponDTO;
import br.com.spring.ecommerce.dto.GraficResultDTO;
import br.com.spring.ecommerce.dto.OrderDTO;
import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.dto.UserDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.Cupon;
import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.AddressRepository;
import br.com.spring.ecommerce.repository.CuponRepository;
import br.com.spring.ecommerce.repository.OrderRepository;
import br.com.spring.ecommerce.repository.ProductsRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.FormatDate;
import br.com.spring.ecommerce.util.FormatPrice;
import br.com.spring.ecommerce.util.PurchaseStatus;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CuponRepository cuponRepository;

	@Autowired
	private ProductsRepository prodRepository;

	@Autowired
	private AuthenticationService authService;

	@Transactional
	public ResponseEntity<?> createOrder(Integer id) {
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Order order = new Order();
		Optional<Address> address = addressRepository.findById(id);

		List<Products> prods = new ArrayList<>();

		prods.addAll(user.getCart().getProduct());

		if (CollectionUtils.isNotEmpty(prods)) {

			order.setStatus(PurchaseStatus.PEDIDO_EFETUADO);
			order.setProducts(prods);
			order.setUser(user);
			order.setOrderDate(new Date());
			order.setAddress(address.get());

			prods.forEach(p -> {
				p.setProd_quantity(p.getProd_quantity() - (Integer) 1);
				if (p.getProd_quantity() == 0) {
					p.setProd_active(0);
					p.setProd_act_reason("FORA DE MERCADO");
				}
				prodRepository.save(p);
			});

			user.getCart().getProduct().clear();

			userRepository.save(user);
			orderRepository.save(order);

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@Transactional
	public ResponseEntity<?> createOrderWith(CuponDTO cDto, Integer id) {
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Order order = new Order();
		Cupon cupon = new Cupon();
		Optional<Address> address = addressRepository.findById(id);

		List<Products> prods = new ArrayList<>();
		List<ProductsDTO> prodsDTO = new ArrayList<>();

		prods.addAll(user.getCart().getProduct());

		prods.forEach(pr -> {
			ProductsDTO dto = new ProductsDTO();
			BeanUtils.copyProperties(pr, dto);
			prodsDTO.add(dto);
		});

		if (CollectionUtils.isNotEmpty(prods)) {
			order.setStatus(PurchaseStatus.PEDIDO_EFETUADO);
			order.setProducts(prods);
			order.setUser(user);
			order.setOrderDate(new Date());
			order.setAddress(address.get());

			prods.forEach(p -> {
				p.setProd_quantity(p.getProd_quantity() - (Integer) 1);
				if (p.getProd_quantity() == 0) {
					p.setProd_active(0);
					p.setProd_act_reason("FORA DE MERCADO");
				}
				prodRepository.save(p);
			});

			BeanUtils.copyProperties(cDto, cupon);

			if (Objects.nonNull(cupon)) {

				if (cupon.getC_type() == 0) {

					cupon.setC_quantity(cupon.getC_quantity() - 1);
					try {
						cupon.setC_register(FormatDate.convertStringToDate(cDto.getC_register()));
						cupon.setC_final(FormatDate.convertStringToDate(cDto.getC_final()));
					} catch (ParseException e) {
						e.printStackTrace();
					}

					order.setCupon(cupon);

					user.getCart().getProduct().clear();

					cuponRepository.save(cupon);
					userRepository.save(user);
					orderRepository.save(order);

					return ResponseEntity.ok().build();

				} else if (cupon.getC_type() == 1) {

					List<CartProductsDTO> cpDto = new ArrayList<>();

					prodsDTO.forEach(p -> {

						List<ProductsDTO> qntd = new ArrayList<>();
						CartProductsDTO dto = new CartProductsDTO();
						qntd = prodsDTO.stream().filter(c -> c.getId().equals(p.getId())).collect(Collectors.toList());
						dto.setQuantity(qntd.size());
						dto.setProductDTO(p);

						if (!cpDto.contains(dto)) {
							cpDto.add(dto);
						}
					});
					
					cpDto.forEach(l -> {

						l.setPrice(FormatPrice.getPrice(l.getProductDTO().getProd_price() * l.getQuantity()));
					});

					CartTotalPriceDTO ctPrice = new CartTotalPriceDTO();

					ctPrice.setCartProducts(cpDto);

					ctPrice.setTotalPrice(FormatPrice.getTotalPrice(cpDto));

					if (ctPrice.getTotalPrice() < cupon.getC_percentage()) {

						cupon.setC_quantity(cupon.getC_quantity() - 1);
						cupon.setUser(user);

						try {
							cupon.setC_register(FormatDate.convertStringToDate(cDto.getC_register()));
							cupon.setC_final(FormatDate.convertStringToDate(cDto.getC_final()));
						} catch (ParseException e) {
							e.printStackTrace();
						}

						Random rand = new Random();
						Integer nome = rand.nextInt(999999);

						Cupon cuponGenerated = new Cupon();
						cuponGenerated.setC_name(nome.toString());
						cuponGenerated.setC_register(new Date());
						cuponGenerated.setC_final(new Date());
						Double val = cupon.getC_percentage().doubleValue() - ctPrice.getTotalPrice();
						cuponGenerated.setC_percentage(val.intValue());
						cuponGenerated.setC_quantity(1);
						cuponGenerated.setC_type(1);
						cuponGenerated.setUser(user);
						cuponRepository.save(cuponGenerated);

						user.getCart().getProduct().clear();

						order.setCupon(cupon);

						cuponRepository.save(cupon);
						userRepository.save(user);
						orderRepository.save(order);

						return ResponseEntity.ok(cuponGenerated.getC_name());

					}
				}
			}
			
			cupon.setC_quantity(cupon.getC_quantity() - 1);
			cupon.setUser(user);

			try {
				cupon.setC_register(FormatDate.convertStringToDate(cDto.getC_register()));
				cupon.setC_final(FormatDate.convertStringToDate(cDto.getC_final()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			user.getCart().getProduct().clear();
			order.setCupon(cupon);

			cuponRepository.save(cupon);
			userRepository.save(user);
			orderRepository.save(order);

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	public List<OrderDTO> listAll() {

		List<Order> order = orderRepository.findAll();

		return order.stream().map(p -> {
			UserDTO user = new UserDTO();
			OrderDTO dto = new OrderDTO();

			List<ProductsDTO> lDto = new ArrayList<>();

			BeanUtils.copyProperties(p.getUser(), user);
			BeanUtils.copyProperties(p, dto);

			p.getProducts().forEach(o -> {
				ProductsDTO pDto = new ProductsDTO();
				BeanUtils.copyProperties(o, pDto);
				lDto.add(pDto);
			});

			dto.setProducts(lDto);

			dto.setUser(user);
			dto.setOrderDate(FormatDate.convertDateToString(p.getOrderDate()));

			return dto;
		}).collect(Collectors.toList());

	}

	public ResponseEntity<?> changeStatus(Integer id, OrderDTO dto) {
		Optional<Order> order = orderRepository.findById(id);

		order.get().setStatus(dto.getStatus());

		orderRepository.save(order.get());

		return ResponseEntity.ok().build();
	}

	public List<OrderDTO> listAllByUserId() {

		User user = userRepository.findOneById(authService.getCurrent().getId());

		List<Order> order = orderRepository.findAllByUserId(user.getId());

		return order.stream().map(o -> {

			OrderDTO dto = new OrderDTO();

			BeanUtils.copyProperties(o, dto);

			dto.setOrderDate(FormatDate.convertDateToString(o.getOrderDate()));

			return dto;
		}).collect(Collectors.toList());
	}

	public ResponseEntity<OrderDTO> findOrderById(Integer id) {

		Order order = orderRepository.findOneById(id);

		if (Objects.nonNull(order.getCupon())) {
			CuponDTO cDto = new CuponDTO();
			AddressDTO aDto = new AddressDTO();
			UserDTO user = new UserDTO();
			OrderDTO dto = new OrderDTO();
			BeanUtils.copyProperties(order.getAddress(), aDto);
			BeanUtils.copyProperties(order.getCupon(), cDto);
			BeanUtils.copyProperties(order.getUser(), user);
			BeanUtils.copyProperties(order, dto);
			dto.setCupon(cDto);
			dto.setAddressDto(aDto);
			dto.setUser(user);
			dto.setOrderDate(FormatDate.convertDateToString(order.getOrderDate()));
			return ResponseEntity.ok(dto);
		}

		if (Objects.nonNull(order)) {
			UserDTO user = new UserDTO();
			AddressDTO aDto = new AddressDTO();
			OrderDTO dto = new OrderDTO();
			BeanUtils.copyProperties(order.getUser(), user);
			BeanUtils.copyProperties(order.getAddress(), aDto);
			BeanUtils.copyProperties(order, dto);
			dto.setUser(user);
			dto.setAddressDto(aDto);
			dto.setOrderDate(FormatDate.convertDateToString(order.getOrderDate()));
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	public CartTotalPriceDTO findAllProductsByCartId(Integer id) {
		Order order = orderRepository.findOneById(id);

		List<CartProductsDTO> cartList = new ArrayList<>();
		List<ProductsDTO> prodList = new ArrayList<>();
		UserDTO uDto = new UserDTO();

		BeanUtils.copyProperties(order.getUser(), uDto, "password");

		order.getProducts().forEach(p -> {

			ProductsDTO prodDTO = new ProductsDTO();
			BeanUtils.copyProperties(p, prodDTO);
			prodList.add(prodDTO);
		});

		prodList.forEach(p -> {

			List<ProductsDTO> qntd = new ArrayList<>();
			CartProductsDTO dto = new CartProductsDTO();
			qntd = prodList.stream().filter(c -> c.getId().equals(p.getId())).collect(Collectors.toList());
			dto.setQuantity(qntd.size());
			dto.setProductDTO(p);

			if (!cartList.contains(dto)) {
				cartList.add(dto);
			}
		});

		cartList.forEach(l -> {

			l.setPrice(FormatPrice.getPrice(l.getProductDTO().getProd_price() * l.getQuantity()));
		});

		CartTotalPriceDTO ctPrice = new CartTotalPriceDTO();

		ctPrice.setCartProducts(cartList);

		ctPrice.setTotalPrice(FormatPrice.getTotalPrice(cartList));

		Integer total = cartList.stream().map(x -> x.getQuantity()).reduce((x, y) -> x + y).orElse(0);

		ctPrice.setTotal(total);

		if (CollectionUtils.isNotEmpty(cartList)) {

			ctPrice.setTotalPrice(FormatPrice.getTotalPrice(cartList));

		} else {

			ctPrice.setTotalPrice(0.00);
		}

		ctPrice.setUser(uDto);
		return ctPrice;
	}

	public ResponseEntity<GraficResultDTO> findQuantityByDatesBetween(Integer id, String ini, String end) {
		
		GraficResultDTO results = orderRepository.findByIdAndDateBetween(id, ini, end);
		
		return ResponseEntity.ok(results);
		
	}
}
