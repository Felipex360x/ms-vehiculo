package com.tiendacarta.vehiculo.Repository;
import com.tiendacarta.vehiculo.Model.Auto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class AutoRepositoryTest {

    @Autowired
    private AutoRepository autoRepository;

    @Test
    @DisplayName("save -debe pesrist el Vehiculos y asignar un id generado automaticamnte")
    void debePersistirAutoYAsignarIdGenerado(){
        //Given
        Auto auto = new Auto(
            null, "BBB222", "Hyundai", "Negro", "Base", null, 2022, "Sedan", LocalDate.now()        
        );
        //When
        Auto guardado = autoRepository.save(auto);
        //then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId()>0);
        assertEquals("BBB222",guardado.getMatricula());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los Vehiculos guardados en la BD")
    void debeRetornarTodosLosAuto(){
        //Given
        autoRepository.save(new Auto(
            null, "BBB222", "Hyundai", "Negro", "Base", null, 2022, "Sedan", LocalDate.now() 
        ));
        autoRepository.save(new Auto(
            null, "BBB333", "MG", "Negro", "Base", null, 2022, "4x4", LocalDate.now() 
        ));
        //when
        List<Auto> autos = autoRepository.findAll();
        //then
        assertNotNull(autos);
        assertEquals(2, autos.size());

    }

    
    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeEncontrarAutoPorIdExistente(){
        //Give
        Auto guardado = autoRepository.save(new Auto(
        null, "BBB444", "nizzan", "Negro", "Base", null, 2022, "largo", LocalDate.now() 
        ));
        //when
        Optional<Auto> resultado = autoRepository.findById(guardado.getId());
        //then
        assertTrue(resultado.isPresent());
        assertEquals("BBB444", resultado.get().getMatricula());
    } 

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Auto> resultado = autoRepository.findById(999L);
        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar el Usuario de la base de datos")
    void debeEliminarAutoPorId(){
        //Given
        Auto guardado = autoRepository.save(new Auto(
            null, "BBB444", "nizzan", "Negro", "Base", null, 2022, "largo", LocalDate.now() 
        ));
        Long id = guardado.getId();
        //when
        autoRepository.deleteById(id);
        //then
        assertFalse(autoRepository.findById(id).isPresent());
    }
}