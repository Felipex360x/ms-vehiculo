package com.tiendacarta.vehiculo.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*Orden de los datos */
@JsonPropertyOrder({

})
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    
    private Long id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private String version;
    private Integer ano;
    private String TipoVehi;
    private LocalDate fechaingreso;










}
