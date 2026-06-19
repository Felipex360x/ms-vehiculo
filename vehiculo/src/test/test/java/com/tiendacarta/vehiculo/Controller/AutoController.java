package com.tiendacarta.vehiculo.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tiendacarta.vehiculo.DTO.AutoDTO;
import com.tiendacarta.vehiculo.Exepction.GlobalExceptionHandler;
import com.tiendacarta.vehiculo.Exepction.RecursoNoEncontradoException;
import com.tiendacarta.vehiculo.Service.AutoService;

@ExtendWith(MockitoExtension.class)
class AutoControllerTest {


@Mock
private AutoService service;

@InjectMocks
private AutoController controller;

private MockMvc mockMvc;

@BeforeEach
void setUp() {
    mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
}
  // ── GET /api/v2/vehiculos──────────────────────────────────────────────────
@Test
@DisplayName("GET /api/v2/vehiculos - debe retornar 200 con la lista de vehículos")
void debeRetornar200CuandoSePidenVehiculos() throws Exception {
    when(service.findAll()).thenReturn(List.of(
            new AutoDTO(
                    1L,
                    "BBB444",
                    "Nissan",
                    "Negro",
                    "Base",
                    2024,
                    "SUV",
                    LocalDate.now()
            ),
            new AutoDTO(
                    2L,
                    "CCC555",
                    "Hyundai",
                    "Blanco",
                    "Premium",
                    2023,
                    "Sedan",
                    LocalDate.now()
            )
    ));

    mockMvc.perform(get("/api/v2/vehiculos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].matricula").value("BBB444"));
}

@Test
@DisplayName("GET /api/v2/vehiculos - debe retornar lista vacía")
void debeRetornar200ConListaVacia() throws Exception {

    when(service.findAll()).thenReturn(List.of());

    mockMvc.perform(get("/api/v2/vehiculos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
}

  // ── GET /api/v2/vehiculos/{id}──────────────────────────────────────────────────
@Test
@DisplayName("GET /api/v2/vehiculos/{id} - debe retornar vehículo existente")
void debeRetornarVehiculoPorId() throws Exception {

    when(service.findById(1L)).thenReturn(
            new AutoDTO(
                    1L,
                    "BBB444",
                    "Nissan",
                    "Negro",
                    "Base",
                    2024,
                    "SUV",
                    LocalDate.now()
            )
    );

    mockMvc.perform(get("/api/v2/vehiculos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.matricula").value("BBB444"));
}

@Test
@DisplayName("GET /api/v2/vehiculos/{id} - debe retornar 404")
void debeRetornar404CuandoVehiculoNoExiste() throws Exception {

    when(service.findById(999L))
            .thenThrow(new RecursoNoEncontradoException("Auto no encontrado: 999"));

    mockMvc.perform(get("/api/v2/vehiculos/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Auto no encontrado: 999"));
}
  // ── Post /api/v2/vehiculos──────────────────────────────────────────────────
@Test
@DisplayName("POST /api/v2/vehiculos - debe crear vehículo")
void debeRetornar201AlCrearVehiculo() throws Exception {

    String json = """
            {
                "matricula":"BBB444",
                "marca":"Nissan",
                "color":"Negro",
                "ano":2024,
                "tipovehiculo":"SUV",
                "fechaingreso":"2024-01-01"
            }
            """;

    when(service.crear(any())).thenReturn(
            new AutoDTO(
                    1L,
                    "BBB444",
                    "Nissan",
                    "Negro",
                    "Base",
                    2024,
                    "SUV",
                    LocalDate.of(2024,1,1)
            )
    );

    mockMvc.perform(post("/api/v2/vehiculos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.matricula").value("BBB444"));
}

@Test
@DisplayName("POST /api/v2/vehiculos - debe retornar 400 cuando matrícula está vacía")
void debeRetornar400CuandoMatriculaEstaVacia() throws Exception {

    String json = """
            {
                "matricula":"",
                "marca":"Nissan",
                "color":"Negro",
                "ano":2024,
                "tipovehiculo":"SUV",
                "fechaingreso":"2024-01-01"
            }
            """;

    mockMvc.perform(post("/api/v2/vehiculos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.matricula").exists());
}

  // ── PUT /api/v2/vehiculos/{id}──────────────────────────────────────────────────
@Test
@DisplayName("PUT /api/v2/vehiculos/{id} - debe actualizar vehículo")
void debeActualizarVehiculo() throws Exception {

    String json = """
            {
                "matricula":"CCC555",
                "marca":"Toyota",
                "color":"Blanco",
                "ano":2025,
                "tipovehiculo":"SUV",
                "fechaingreso":"2024-01-01"
            }
            """;

    when(service.actualizar(anyLong(), any()))
            .thenReturn(
                    new AutoDTO(
                            1L,
                            "CCC555",
                            "Toyota",
                            "Blanco",
                            "Premium",
                            2025,
                            "SUV",
                            LocalDate.of(2024,1,1)
                    )
            );

    mockMvc.perform(put("/api/v2/vehiculos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.matricula").value("CCC555"));
}

@Test
@DisplayName("DELETE /api/v2/vehiculos/{id} - debe eliminar vehículo")
void debeEliminarVehiculo() throws Exception {

    doNothing().when(service).eliminar(1L);

    mockMvc.perform(delete("/api/v2/vehiculos/1"))
            .andExpect(status().isNoContent());
}

// ── DELETE /api/v2/vehiculos/{id} ──────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/v2/vehiculos/{id} - debe eliminar vehiculo existente")
    void debeEliminarAuto() throws Exception {
        doNothing().when(service).eliminar(1L);
        mockMvc.perform(delete("/api/v2/vehiculos/{id}/1")).andExpect(status().isNoContent());
    }

}