package com.tiendacarta.vehiculo.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*aqui vamos a realizar el salida  este DTO no lleva las valadaciones automaticas que usamos en spring boot */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoDTO {
    /*SOLO VAMOS A usar estos datos del modelo original  */
    private Long id;
    private String matricula;
    private String marca;
    private String color;
    private String version;
    private Integer ano;
    private String tipovehiculo;
    private LocalDate fechaingreso;
    /*para el client  */


    
}
