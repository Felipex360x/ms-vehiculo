package com.tiendacarta.vehiculo.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para crear o actualizar un VEHICULO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoCreateDTO {

    
    @Schema(description = "Matricula de vehiculo", example = "bbb222")
    @NotBlank(message = "La matricula es obligatoria")
    @Size(min =6, max =6, message = "La matricula tienen que tener sus 6 caracteres")
    private String matricula;
    @Schema(description = "Marca de vehiculo", example = "MG")
    @NotBlank(message = "la marca del auto es obligatorio")
    private String marca;
    @Schema(description = "Color de vehiculo", example = "Negro")
    @NotBlank(message = "inngrese el color del vehiculo")
    private String color;
    @Schema(description = "año de vehiculo", example = "2013")
    @NotNull(message = "ingrese el año del vehiculo")
    private Integer ano;
    @Schema(description = "Tipo de vehiculo", example = "4x4")
    @NotBlank(message = "ingrese el tipo de vehiculo")
    private String tipovehiculo;
    @Schema(description = "fecha de ingreso", example = "10-06-2021")
    @Past(message = "La fecha de ingreso debe ser anterior a hoy")
    private LocalDate fechaingreso;
}
