package com.desafio.logistiqal.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.logistiqal.model.Producto;


//ProductoRepository extiende CrudRepository para poder incorporar los metodos que nos permiten realizar las queries.

//Segun documentacion,CrudRepository es una "Interface for generic CRUD operations on a repository for a specific type."

@Repository
public interface ProductoRepository extends CrudRepository<Producto,Integer>, PagingAndSortingRepository
<Producto,Integer>
{

//	@Query("select p from Producto p where p.nombre = :nombre")
//	public List<Producto> findByNombre(@Param("nombre") String nombre);

	@Query(value="select p from Producto p where p.nombre like %?1%")
	public Page<Producto> findByNombre(String nombre, Pageable pageable);

}

//like %:username%

//SELECT * FROM mytable
//WHERE column1 LIKE '%word1%'