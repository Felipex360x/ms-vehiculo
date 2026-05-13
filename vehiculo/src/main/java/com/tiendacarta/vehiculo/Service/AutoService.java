package com.tiendacarta.vehiculo.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/*usar estos 2 para que funcine  */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiendacarta.vehiculo.DTO.AutoCreateDTO;
import com.tiendacarta.vehiculo.DTO.AutoDTO;
import com.tiendacarta.vehiculo.Exepction.RecursoNoEncontradoException;

import com.tiendacarta.vehiculo.Model.Auto;
import com.tiendacarta.vehiculo.Repository.AutoRepository;


@Service
public class AutoService {
    
    /*client */
    private static final Logger Log = LoggerFactory.getLogger(AutoService.class);

    @Autowired
    private  AutoRepository autoRepository;

    public List<AutoDTO>findAll(){
        Log.info("consutlando a todos los vehiculos");
        return autoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AutoDTO findById(Long id){
        Log.info("Buscando Vehiculo id={}",id);
        Auto a = autoRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("vehiculo no encontrado:"+id));
        Log.info("vehiculo encotrado: matricula={}",a.getMatricula());
        return toDTO(a);
    }

    public AutoDTO crear(AutoCreateDTO dto){
        Log.info("creando al vehiculo matricula={}",dto.getMatricula());
        Auto a = new Auto();
        a.setMatricula(dto.getMatricula());
        a.setMarca(dto.getMarca());
        a.setColor(dto.getColor());
        a.setAno(dto.getAno());
        a.setTipoVehi(dto.getTipovehiculo());
        a.setFechaingreso(dto.getFechaingreso());
        Auto guardar = autoRepository.save(a);
        Log.info("vehiculo creado id={}",guardar.getId());
        return toDTO(guardar);
    }

    public AutoDTO actualizar(Long id,AutoCreateDTO dto){
        Log.info("actualizando vehiculo id ={}",id);
        Auto a = autoRepository.findById(id) .orElseThrow(()-> new RecursoNoEncontradoException("vehiculo no encotrado:" +id));
        a.setMatricula(dto.getMatricula());
        a.setMarca(dto.getMarca());
        a.setColor(dto.getColor());
        a.setAno(dto.getAno());
        a.setTipoVehi(dto.getTipovehiculo());
        a.setFechaingreso(dto.getFechaingreso());
        return toDTO(autoRepository.save(a));
    }

    public void eliminar(Long id) {
        Log.info("Eliminando Vehiculo id={}", id);
        if (!autoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Vehiculo no encontrado: " + id);
        }
        autoRepository.deleteById(id);
        Log.info("vehiculo id={} eliminado", id);
    }

    private AutoDTO toDTO(Auto a) {
        return new AutoDTO(
            a.getId(),
            a.getMatricula(),
            a.getMarca(),
            a.getColor(),
            a.getVersion(),
            a.getAno(),
            a.getTipoVehi(),
            a.getFechaingreso()
        );
    
    }



/*---------------------------------------------Validacion de Matricula si ya esta registrada------------------------------------------------- */

    /*Metod de validacion si existen ya los datos esto servira para ver si la matricula ya existe  */
    public List<String> validarAutoManual(AutoCreateDTO dto){
        List<String> errores = new ArrayList<>();
        if(autoRepository.existsByMatriculaIgnoreCase(dto.getMatricula())){
            errores.add("la matricula ya esta registrada");
        }
        return errores;
    }






}
