package com.tiendacarta.vehiculo.Controller;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendacarta.vehiculo.DTO.AutoCreateDTO;
import com.tiendacarta.vehiculo.DTO.AutoDTO;

import com.tiendacarta.vehiculo.Service.AutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Vehículos", description = "Gestión de vehículos")
@RestController
@RequestMapping("/api/v2/vehiculos")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @Operation(
        summary = "Listar vehículos",
        description = "Obtiene todos los vehículos registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<AutoDTO>> getAll() {
        return ResponseEntity.ok(autoService.findAll());
    }

    @Operation(
        summary = "Buscar vehículo por ID",
        description = "Obtiene un vehículo específico por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> getById(
            @Parameter(description = "ID del vehículo")
            @PathVariable Long id) {

        return ResponseEntity.ok(autoService.findById(id));
    }

    @Operation(summary = "Registrar nueva vehículo")
    @ApiResponse(responseCode = "201", description = "vehículo creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping
    public ResponseEntity<AutoDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo vehículo"
            )
            @Valid @RequestBody AutoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autoService.crear(dto));
    }



    @Operation(summary = "Actualizar vehículo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "vehículo no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutoDTO> actualizar(
            @Parameter(description = "ID del vehículo a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del vehículo"
            )
            @Valid @RequestBody AutoCreateDTO dto) {
        return ResponseEntity.ok(autoService.actualizar(id, dto));
    }

    @Operation(
        summary = "Eliminar vehículo",
        description = "Elimina un vehículo por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del vehículo")
            @PathVariable Long id) {

        autoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }



    /*--------------------------------Validacion Manual------------------------------------------- */

    /*Validacion manual con Endpoid */
    @PostMapping("/manual")
    public ResponseEntity<?> crearConValidacionManual(@RequestBody AutoCreateDTO dto){
        List<String> errores = autoService.validarAutoManual(dto);
        if(!errores.isEmpty()){
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        AutoDTO nuevoAuto = autoService.crear(dto);
        return new ResponseEntity<>(nuevoAuto,HttpStatus.CREATED);
    }






}