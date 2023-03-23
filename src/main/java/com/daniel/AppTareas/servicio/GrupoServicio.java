package com.daniel.AppTareas.servicio;

import com.daniel.AppTareas.excepciones.GrupoNoValidoException;
import com.daniel.AppTareas.modelo.Grupo;
import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.repositorio.GrupoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GrupoServicio {

    GrupoRepository grupoRepository;

    public GrupoServicio(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public List<Grupo> getGrupos() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> getGrupoById(Integer id) {
        return grupoRepository.findById(id);
    }

    public List<Tarea> getTareasByIdGrupo(Integer id) throws GrupoNoValidoException {
        if (existeGrupoById(id)) {
            return grupoRepository.findById(id).get().getTareaList();
        } else {
            throw new GrupoNoValidoException("El grupo no existe", HttpStatus.NOT_FOUND);
        }
    }

    public Boolean existeGrupoById(Integer id) {
        return grupoRepository.existsById(id);
    }

    public Grupo crearGrupo(Grupo grupo) throws GrupoNoValidoException {
        if (verificarGrupo(grupo)) {
            grupo.setNombre(grupo.getNombre().trim());
            return grupoRepository.save(grupo);
        }else{
           throw new GrupoNoValidoException("El grupo no es valido", HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean verificarGrupo(Grupo grupo) throws GrupoNoValidoException {
        if (grupo == null) {
            log.warn("El grupo es nulo");
            throw new GrupoNoValidoException("El grupo es nulo", HttpStatus.BAD_REQUEST);
        }
        if (grupo.getNombre().isEmpty()) {
            log.warn("El nombre del grupo es invalido");
            throw new GrupoNoValidoException("El nombre del grupo es invalido", HttpStatus.BAD_REQUEST);
        }
        if (grupo.getNombre().length() > 50) {
            log.warn("El nombre del grupo supera los 50 carácteres máximos");
            throw new GrupoNoValidoException("El nombre del grupo supera los 50 carácteres máximos", HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    public Grupo updateNombreGrupo(Integer id, String nuevoNombre) throws GrupoNoValidoException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(id);

        if (grupoOptional.isPresent() && verificarGrupo(grupoOptional.get())) {
            grupoOptional.get().setNombre(nuevoNombre.trim());
            return grupoRepository.save(grupoOptional.get());
        } else {
            throw new GrupoNoValidoException("El grupo no existe", HttpStatus.NOT_FOUND);
        }
    }

    public Boolean eliminarGrupoById(Integer id) throws GrupoNoValidoException {
        if (grupoRepository.existsById(id)) {
            grupoRepository.deleteById(id);
            return true;
        } else {
            throw new GrupoNoValidoException("El grupo no existe", HttpStatus.NOT_FOUND);
        }
    }

}
