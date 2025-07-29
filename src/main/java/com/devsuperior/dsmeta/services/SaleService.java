package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> report(String minDate, String maxDate, String name, Pageable pageable) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate max = null;
		LocalDate min = null;
		if(maxDate.isEmpty())
			max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		else 
			max = LocalDate.parse(maxDate, formatter);
		
		if(minDate.isEmpty())
			min = max.minusYears(1L);
		else 
			min = LocalDate.parse(minDate, formatter);
				
		Page<Sale> sales = repository.report(min, max, name, pageable);
		
		return sales.map(x -> new SaleMinDTO(x));		
	}
	
	public List<SellerMinDTO> summary(String minDate, String maxDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate max = null;
		LocalDate min = null;
		if(maxDate.isEmpty())
			max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		else 
			max = LocalDate.parse(maxDate, formatter);
		
		if(minDate.isEmpty())
			min = max.minusYears(1L);
		else 
			min = LocalDate.parse(minDate, formatter);
				
		List<SellerMinDTO> sellers = repository.summary(min, max);
		
		return sellers;		
	}
}
