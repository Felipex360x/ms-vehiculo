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

import java.util.List;

@RestController
@RequestMapping("/api/v2/vehiculos")
public class AutoController {

    @Autowired
    private  AutoService autoService;

    @GetMapping
    public ResponseEntity<List<AutoDTO>>getAll(){
        return ResponseEntity.ok(autoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(autoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AutoDTO> crear(@Valid @RequestBody AutoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AutoCreateDTO dto) {
        return ResponseEntity.ok(autoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
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