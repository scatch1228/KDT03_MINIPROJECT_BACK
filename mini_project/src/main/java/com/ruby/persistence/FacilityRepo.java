package com.ruby.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ruby.domain.Facility;

public interface FacilityRepo extends JpaRepository<Facility, Integer>{
	//return Lists of facilities
	Page<Facility> findByNameContaining(String name, Pageable paging);
	Page<Facility> findByNameContainingAndCityContaining(String name,String city, Pageable paging);
	Page<Facility> findByNameContainingAndCityContainingAndGugunContaining(String name,String city, String gugun, Pageable paging);
	Page<Facility> findByNameContainingAndCityContainingAndTypeContaining(String name,String city, String type, Pageable paging); 
	Page<Facility> findByNameContainingAndCityContainingAndGugunContainingAndTypeContaining(String name,String city, String gugun, String type, Pageable paging);
	//noname
	Page<Facility> findByCityContaining(String city, Pageable paging);
	Page<Facility> findByCityContainingAndGugunContaining(String city, String gugun, Pageable paging);
	Page<Facility> findByCityContainingAndTypeContaining(String city, String type, Pageable paging); 
	Page<Facility> findByCityContainingAndGugunContainingAndTypeContaining(String city, String gugun, String type, Pageable paging);
	Page<Facility> findByTypeContaining(String type, Pageable paging);
	
	
	//return facility counts with input
	Integer countByCityLike(String city);
	Integer countByCityLikeAndGugunLike(String city, String gugun);
	Integer countByCityLikeAndGugunLikeAndTypeLike(String city, String gugun, String type);
	Integer countByCityLikeAndTypeLike(String city, String type);
	
	//return facility counts without input
	@Query("SELECT COUNT(f) FROM Facility f")
	Integer countAll(); 
	
	@Query("SELECT f.city, COUNT(f) FROM Facility f GROUP BY f.city ORDER BY f.city")
	List<Object[]> countByCity(); 
	//given city, return counts within cities gugun/types
	@Query("SELECT f.gugun, count(f) FROM Facility f GROUP BY f.city, f.gugun HAVING f.city=?1 ORDER BY f.city")
	List<Object[]> countByGugun(String city); 
	
	@Query("SELECT f.type, count(f) FROM Facility f GROUP BY f.city, f.type HAVING f.city=?1 ORDER BY f.city")
	List<Object[]> countByType(String city);

	@Query("SELECT f.type, count(f) FROM Facility f GROUP BY f.city, f.gugun, f.type HAVING f.city=?1 AND f.gugun=?2 ORDER BY f.gugun")
	List<Object[]> countByTypeinGugun(String city, String gugun);
	
	//MySQL Dialect
	/*
	@Query(value = "SELECT count(*) FROM facility WHERE create_date >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 10 YEAR), '%Y%m%d') AND city=?1", 
		       nativeQuery = true)
	Integer countNewInCity(String city);
	@Query(value = "SELECT count(*) FROM facility WHERE create_date > DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 25 YEAR), '%Y%m%d') AND create_date < DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 10 YEAR), '%Y%m%d') AND city=?1", 
			nativeQuery = true)
	Integer countMidInCity(String city);
	@Query(value = "SELECT count(*) FROM facility WHERE create_date <= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 25 YEAR), '%Y%m%d') AND city=?1", 
			nativeQuery = true)
	Integer countOldInCity(String city);
	@Query(value = "SELECT count(*) FROM facility WHERE erdsgn='Y' AND city=?1", 
			nativeQuery = true)
	Integer countERDInCity(String city);
	
	//misc query
	@Query(value = "SELECT count(*) FROM (SELECT DISTINCT gugun FROM facility WHERE city=?1) as count_guguns", 
			nativeQuery = true)
	Integer countGugunsInCity(String city);
	@Query(value = "select round(avg((curdate()-create_date))/10000, 1) from facility where city like ?1", 
			nativeQuery = true)
	Double avgOldInCity(String city);
	*/
	
	// Postgre Dialect
	@Query(value = "SELECT count(*) FROM facility WHERE create_date >= to_char(CURRENT_DATE - INTERVAL '10 years', 'YYYYMMDD') AND city=?1", nativeQuery = true)
	Integer countNewInCity(String city);

	@Query(value = "SELECT count(*) FROM facility WHERE create_date > to_char(CURRENT_DATE - INTERVAL '25 years', 'YYYYMMDD') AND create_date < to_char(CURRENT_DATE - INTERVAL '10 years', 'YYYYMMDD') AND city=?1", nativeQuery = true)
	Integer countMidInCity(String city);

	@Query(value = "SELECT count(*) FROM facility WHERE create_date <= to_char(CURRENT_DATE - INTERVAL '25 years', 'YYYYMMDD') AND city=?1", nativeQuery = true)
	Integer countOldInCity(String city);

	@Query(value = "SELECT count(*) FROM facility WHERE erdsgn='Y' AND city=?1", nativeQuery = true)
	Integer countERDInCity(String city);

	// misc query
	@Query(value = "SELECT count(*) FROM (SELECT DISTINCT gugun FROM facility WHERE city=?1) as count_guguns", nativeQuery = true)
	Integer countGugunsInCity(String city);

	@Query(value = "SELECT round(avg(extract(year from age(CURRENT_DATE, to_date(create_date, 'YYYYMMDD'))))::numeric, 1) FROM facility WHERE city LIKE ?1", nativeQuery = true)
	Double avgOldInCity(String city);
}
