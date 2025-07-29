package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT s FROM Sale s WHERE s.date BETWEEN :min AND :max AND UPPER(s.seller.name) LIKE CONCAT('%', UPPER(:name), '%')")
	Page<Sale> report(LocalDate min, LocalDate max, String name, Pageable pageble);
	
	@Query(
			"SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(sl.name, sum(s.amount)) "
			+ "FROM Sale s JOIN s.seller sl "
			+ "WHERE s.date BETWEEN :min AND :max "
			+ "GROUP BY sl.name")
	List<SellerMinDTO> summary(LocalDate min, LocalDate max);
}
