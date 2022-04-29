package br.com.spring.ecommerce.service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CuponDTO;
import br.com.spring.ecommerce.model.Cupon;
import br.com.spring.ecommerce.repository.CuponRepository;
import br.com.spring.ecommerce.util.FormatDate;

@Service
public class CuponService {

	@Autowired
	private CuponRepository cuponRepository;
	
	public ResponseEntity<Cupon> insertCupon(CuponDTO dto) throws ParseException
	{
		Cupon cupon = new Cupon();
		
		BeanUtils.copyProperties(dto, cupon);
		
		cupon.setC_register(FormatDate.convertStringToDate(dto.getC_register()));
		cupon.setC_final(FormatDate.convertStringToDate(dto.getC_final()));
		
		cuponRepository.save(cupon);
	
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Transactional
	public ResponseEntity<?> updateCupon(Integer id, CuponDTO dto) throws ParseException
	{
		Optional<Cupon> cupon = cuponRepository.findById(id);
		
		if(cupon.isPresent())
		{
			BeanUtils.copyProperties(dto, cupon.get(), "id", "c_type");
			cupon.get().setC_register(FormatDate.convertStringToDate(dto.getC_register()));
			cupon.get().setC_final(FormatDate.convertStringToDate(dto.getC_final()));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		cuponRepository.save(cupon.get());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	public List<CuponDTO> listAll(){
		List<Cupon> cupons = cuponRepository.findByTypePromo();
		
		return cupons.stream().map(c ->{
			
			CuponDTO dto = new CuponDTO();
			
			BeanUtils.copyProperties(c, dto);
			
			dto.setC_register(FormatDate.convertDateToString(c.getC_register()));
			dto.setC_final(FormatDate.convertDateToString(c.getC_final()));

			return dto;
		}).collect(Collectors.toList());
	}
	
	public List<CuponDTO> listAllChange(){
		List<Cupon> cupons = cuponRepository.findByTypeChange();
		
		return cupons.stream().map(c ->{
			
			CuponDTO dto = new CuponDTO();
			
			BeanUtils.copyProperties(c, dto);
			
			dto.setC_register(FormatDate.convertDateToString(c.getC_register()));
			dto.setC_final(FormatDate.convertDateToString(c.getC_final()));

			return dto;
		}).collect(Collectors.toList());
	}
	
	
	public CuponDTO listOne(Integer id)
	{
		Optional<Cupon> cupon = cuponRepository.findById(id);
		CuponDTO dto = new CuponDTO();
		BeanUtils.copyProperties(cupon.get(), dto);
		dto.setC_register(FormatDate.convertDateToString(cupon.get().getC_register()));
		dto.setC_final(FormatDate.convertDateToString(cupon.get().getC_final()));
		return dto;
	}
	
	public ResponseEntity<CuponDTO> listOneByName(String name)
	{
		Cupon cup = cuponRepository.findByName(name);
		CuponDTO dto = new CuponDTO();
		
		if(Objects.nonNull(cup)) {
			BeanUtils.copyProperties(cup, dto);
			dto.setC_register(FormatDate.convertDateToString(cup.getC_register()));
			dto.setC_final(FormatDate.convertDateToString(cup.getC_final()));
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.ok().build();		
	}
		
	public ResponseEntity<?> deleteCupon(Integer id){
	
		Optional<Cupon> cupon = cuponRepository.findById(id);
		
		if(cupon.isPresent())
		{
			cuponRepository.deleteById(id);
			}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().build();
	}
	
}
