package com.tiendacarta.vehiculo.Service;

import com.tiendacarta.vehiculo.DTO.AutoCreateDTO;
import com.tiendacarta.vehiculo.DTO.AutoDTO;
import com.tiendacarta.vehiculo.Model.Auto;
import com.tiendacarta.vehiculo.Repository.AutoRepository;
import com.tiendacarta.vehiculo.Exepction.RecursoNoEncontradoException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {

    @Mock
    private AutoRepository autoRepository;

    @InjectMocks
    private AutoService autoService;

    @Test
    @DisplayName("Test Listar Autos - Debería retornar una lista de autos")
    void testListarAutos() {
        List<Auto> autosMock = List.of(
                new Auto(1L, "AAA111", "Toyota", "Rojo", "Full", null, 2023, "Sedan", LocalDate.now()),
                new Auto(2L, "BBB222", "Hyundai", "Negro", "Base", null, 2022, "Sedan", LocalDate.now())
        );

        when(autoRepository.findAll()).thenReturn(autosMock);

        List<AutoDTO> autos = autoService.findAll();

        assertNotNull(autos);
        assertEquals(2, autos.size());
        assertEquals("Toyota", autos.get(0).getMarca());
        assertEquals("Hyundai", autos.get(1).getMarca());

        verify(autoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Listar Autos - Debería retornar lista vacía cuando no hay autos")
    void testListarAutosVacios() {

        when(autoRepository.findAll()).thenReturn(List.of());

        List<AutoDTO> autos = autoService.findAll();

        assertNotNull(autos);
        assertEquals(0, autos.size());

        verify(autoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Buscar Auto por ID - Debería retornar el auto correspondiente")
    void testBuscarAutoPorId() {

        Auto autoMock = new Auto(1L, "AAA111", "Toyota", "Rojo", "Full", null, 2023, "Sedan", LocalDate.now());

        when(autoRepository.findById(1L)).thenReturn(Optional.of(autoMock));

        AutoDTO auto = autoService.findById(1L);

        assertNotNull(auto);
        assertEquals("Toyota", auto.getMarca());

        verify(autoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test Buscar Auto por ID - Debería lanzar excepción cuando no existe")
    void testBuscarAutoPorIdNoEncontrado() {

        when(autoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> autoService.findById(1L));

        verify(autoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test Crear Auto - Debería crear un auto correctamente")
    void testCrearAuto() {

        AutoCreateDTO dto = new AutoCreateDTO(
                "CCC333",
                "Kia",
                "Blanco",
                2024,
                "Hatchback",
                LocalDate.now()
        );

        Auto autoMock = new Auto(
                1L,
                "CCC333",
                "Kia",
                "Blanco",
                "Full",
                null, 2024,
                "Hatchback",
                LocalDate.now()
        );

        when(autoRepository.save(any(Auto.class))).thenReturn(autoMock);

        AutoDTO auto = autoService.crear(dto);

        assertNotNull(auto);
        assertEquals("Kia", auto.getMarca());

        verify(autoRepository, times(1)).save(any(Auto.class));
    }

    @Test
    @DisplayName("Test Eliminar Auto - Debería eliminar el auto correspondiente")
    void testEliminarAuto() {

        when(autoRepository.existsById(1L)).thenReturn(true);

        autoService.eliminar(1L);

        verify(autoRepository, times(1)).existsById(1L);
        verify(autoRepository, times(1)).deleteById(1L);
    }
}