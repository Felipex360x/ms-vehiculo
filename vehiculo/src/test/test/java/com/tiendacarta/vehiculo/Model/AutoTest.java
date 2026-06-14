package com.tiendacarta.vehiculo.Model;




import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AutoTest {

    @Test
    @DisplayName("Test constructor vacío de Auto - debería crear instancia no nula")
    void testConstructorVacio() {
        Auto auto = new Auto();
        assertNotNull(auto);
    }

    @Test
    @DisplayName("Test constructor con parámetros de Auto - debería asignar correctamente los valores")
    void testConstructorConParametros() {

        LocalDate fecha = LocalDate.of(2024, 1, 10);

        Auto auto = new Auto(
                1L,
                "ABCD12",
                "Toyota",
                "Corolla",
                "Rojo",
                "Full",
                2023,
                "Sedan",
                fecha
        );

        assertEquals(1L, auto.getId());
        assertEquals("ABCD12", auto.getMatricula());
        assertEquals("Toyota", auto.getMarca());
        assertEquals("Corolla", auto.getModelo());
        assertEquals("Rojo", auto.getColor());
        assertEquals("Full", auto.getVersion());
        assertEquals(2023, auto.getAno());
        assertEquals("Sedan", auto.getTipoVehi());
        assertEquals(fecha, auto.getFechaingreso());
    }

    @Test
    @DisplayName("Test setters de Auto - deberían modificar correctamente los valores")
    void testSetters() {

        Auto auto = new Auto();

        LocalDate fecha = LocalDate.of(2025, 5, 20);

        auto.setId(2L);
        auto.setMatricula("XYZ999");
        auto.setMarca("Hyundai");
        auto.setModelo("Elantra");
        auto.setColor("Negro");
        auto.setVersion("Base");
        auto.setAno(2022);
        auto.setTipoVehi("Hatchback");
        auto.setFechaingreso(fecha);

        assertEquals(2L, auto.getId());
        assertEquals("XYZ999", auto.getMatricula());
        assertEquals("Hyundai", auto.getMarca());
        assertEquals("Elantra", auto.getModelo());
        assertEquals("Negro", auto.getColor());
        assertEquals("Base", auto.getVersion());
        assertEquals(2022, auto.getAno());
        assertEquals("Hatchback", auto.getTipoVehi());
        assertEquals(fecha, auto.getFechaingreso());
    }
}