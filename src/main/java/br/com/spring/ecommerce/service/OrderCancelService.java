package br.com.spring.ecommerce.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.OrderCancelDTO;
import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.OrderCancel;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.OrderCancelRepository;
import br.com.spring.ecommerce.repository.OrderRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.ChangeStatus;
import br.com.spring.ecommerce.util.FormatDate;

@Service
public class OrderCancelService {

	@Autowired
	private OrderCancelRepository orderCancelRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authService;
	
	public ResponseEntity<?> createCancel(Integer id, OrderCancelDTO dto){
		
		OrderCancel oCancel = new OrderCancel();
		
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		Optional<Order> order = orderRepository.findById(id);
		
		BeanUtils.copyProperties(dto, oCancel, "id", "change_date", "change_reply", "user", "order", "status");
		
		oCancel.setUser(user);
		oCancel.setChange_date(new Date());
		oCancel.setOrder(order.get());
		oCancel.setStatus(ChangeStatus.AGUARDANDO_APROVAÇÃO);
		
		orderCancelRepository.save(oCancel);
		
		return ResponseEntity.ok().build();
		
	}
	
	@Transactional
	public ResponseEntity<?> changeStatus(Integer id, OrderCancelDTO dto)
	{
		Optional<OrderCancel> oChange = orderCancelRepository.findById(id);
		
		oChange.get().setStatus(dto.getStatus());
		
		orderCancelRepository.save(oChange.get());
		
		return ResponseEntity.ok().build();
	}
	
	public List<OrderCancelDTO> listAll(){
		
		List<OrderCancel> oChange = orderCancelRepository.findAll();

		return oChange.stream().map(p -> {
			OrderCancelDTO dto = new OrderCancelDTO();
			
			BeanUtils.copyProperties(p, dto,  "order", "user");
			
			dto.setChange_date(FormatDate.convertDateToString(p.getChange_date()));
			
			return dto;
		}).collect(Collectors.toList());
		
	}
	
	public List<OrderCancelDTO> listAllByUserId(){
		
		User user = userRepository.findOneById(authService.getCurrent().getId());
		List<OrderCancel> oChange = orderCancelRepository.findByUserId(user);

		return oChange.stream().map(p -> {
			OrderCancelDTO dto = new OrderCancelDTO();
			
			BeanUtils.copyProperties(p, dto,  "order", "user");
			
			dto.setChange_date(FormatDate.convertDateToString(p.getChange_date()));
			
			return dto;
		}).collect(Collectors.toList());
		
	}
	
	public ResponseEntity<OrderCancelDTO> listByChangeId(Integer id){
		Optional<OrderCancel> pChange = orderCancelRepository.findById(id);
		OrderCancelDTO dto = new OrderCancelDTO();
		
		BeanUtils.copyProperties(pChange.get(), dto, "order", "user");

		
		dto.setChange_date(FormatDate.convertDateToString(pChange.get().getChange_date()));

		
		return ResponseEntity.ok(dto);
		
	}
	
}
