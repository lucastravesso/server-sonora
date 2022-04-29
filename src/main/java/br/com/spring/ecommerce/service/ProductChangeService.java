package br.com.spring.ecommerce.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.ProductChangeDTO;
import br.com.spring.ecommerce.model.Cupon;
import br.com.spring.ecommerce.model.ProductChange;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.CuponRepository;
import br.com.spring.ecommerce.repository.ProductChangeRepository;
import br.com.spring.ecommerce.repository.ProductsRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.ChangeStatus;
import br.com.spring.ecommerce.util.FormatDate;

@Service
public class ProductChangeService {

	@Autowired
	private ProductChangeRepository productChangeReposioty;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private CuponRepository cuponRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationService authService;

	public ResponseEntity<?> createChange(Integer id, ProductChangeDTO dto) {
		ProductChange pChange = new ProductChange();
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Optional<Products> product = productsRepository.findById(id);

		BeanUtils.copyProperties(dto, pChange, "id", "change_date", "change_reply", "user", "product", "status");

		pChange.setUser(user);
		pChange.setProduct(product.get());
		pChange.setChange_date(new Date());
		pChange.setStatus(ChangeStatus.AGUARDANDO_APROVAÇÃO);

		productChangeReposioty.save(pChange);
		return ResponseEntity.ok().build();
	}

	@Transactional
	public ResponseEntity<?> changeStatus(Integer id, ProductChangeDTO dto) {
		Optional<ProductChange> pChange = productChangeReposioty.findById(id);

		pChange.get().setStatus(dto.getStatus());

		if (pChange.get().getStatus().equals(ChangeStatus.TROCA_APROVADA)) {

			Random rand = new Random();
			Integer nome = rand.nextInt(9999);
			pChange.get().getProduct().setProd_quantity(pChange.get().getProduct().getProd_quantity() + 1);
			productsRepository.save(pChange.get().getProduct());
			Cupon cupon = new Cupon();
			cupon.setC_name(nome.toString());
			cupon.setC_register(new Date());
			cupon.setC_final(new Date());
			Integer val = pChange.get().getProduct().getProd_price().intValue();
			cupon.setC_percentage(val);
			cupon.setC_quantity(1);
			cupon.setC_type(1);
			cuponRepository.save(cupon);

			pChange.get().setChange_reply(dto.getChange_reply() + " - " + nome);

			productChangeReposioty.save(pChange.get());

			return ResponseEntity.ok().build();

		}

		pChange.get().setChange_reply(dto.getChange_reply());

		productChangeReposioty.save(pChange.get());

		return ResponseEntity.ok().build();
	}

	public List<ProductChangeDTO> listAll() {

		List<ProductChange> pChange = productChangeReposioty.findAll();

		return pChange.stream().map(p -> {
			ProductChangeDTO dto = new ProductChangeDTO();

			BeanUtils.copyProperties(p, dto);

			dto.setChange_date(FormatDate.convertDateToString(p.getChange_date()));

			return dto;
		}).collect(Collectors.toList());

	}

	public List<ProductChangeDTO> listAllByUserId() {

		User user = userRepository.findOneById(authService.getCurrent().getId());
		List<ProductChange> pChange = productChangeReposioty.findByUserId(user);

		return pChange.stream().map(p -> {
			ProductChangeDTO dto = new ProductChangeDTO();

			BeanUtils.copyProperties(p, dto);

			dto.setChange_date(FormatDate.convertDateToString(p.getChange_date()));

			return dto;
		}).collect(Collectors.toList());

	}

	public ResponseEntity<ProductChangeDTO> listByChangeId(Integer id) {
		Optional<ProductChange> pChange = productChangeReposioty.findById(id);
		ProductChangeDTO dto = new ProductChangeDTO();

		BeanUtils.copyProperties(pChange.get(), dto);

		dto.setChange_date(FormatDate.convertDateToString(pChange.get().getChange_date()));

		return ResponseEntity.ok(dto);

	}

}
