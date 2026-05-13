package com.tiendacarta.vehiculo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendacarta.vehiculo.Model.Auto;

@Repository
public interface AutoRepository  extends JpaRepository<Auto,Long>{

    boolean existsByMatriculaIgnoreCase(String matricula);

}
