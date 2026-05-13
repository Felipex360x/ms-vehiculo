package com.tiendacarta.vehiculo.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoCreateDTO {

    
    @NotBlank(message = "La matricula es obligatoria")
    @Size(min =6, max =6, message = "La matricula tienen que tener sus 6 caracteres")
    private String matricula;

    @NotBlank(message = "la marca del auto es obligatorio")
    private String marca;

    @NotBlank(message = "inngrese el color del vehiculo")
    private String color;

    @NotNull(message = "ingrese el año del vehiculo")
    private Integer ano;

    @NotBlank(message = "ingrese el tipo de vehiculo")
    private String tipovehiculo;


    @Past(message = "La fecha de ingreso debe ser anterior a hoy")
    private LocalDate fechaingreso;
}
