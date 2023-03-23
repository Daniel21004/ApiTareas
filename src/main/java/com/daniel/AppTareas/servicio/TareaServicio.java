package com.daniel.AppTareas.servicio;

import com.daniel.AppTareas.excepciones.TareaNoValidaException;
import com.daniel.AppTareas.modelo.Grupo;
import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.repositorio.TareaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TareaServicio {

    TareaRepository tareaRepository;

    GrupoServicio grupoServicio;

    public TareaServicio(GrupoServicio grupoServicio, TareaRepository tareaRepository) {
        this.grupoServicio = grupoServicio;
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> getTareas() {
        return tareaRepository.findAll();
    }

    public List<Tarea> getTareasByNombre(String nombre) {
        return tareaRepository.findByNombre(nombre);
    }

    public Optional<Tarea> getTareaById(Integer id) {
        return tareaRepository.findById(id);
    }

    public Tarea crearTarea(Tarea tarea) throws TareaNoValidaException{
        if (verificarTarea(tarea)) {
            tarea.setNombre(tarea.getNombre().trim());
            asignarNombreGrupo(tarea);
            return tareaRepository.save(tarea);
        }else{
            throw new TareaNoValidaException("La tarea no es valida", HttpStatus.BAD_REQUEST);
        }
    }

    private Boolean verificarTarea(Tarea tarea) throws TareaNoValidaException {
        if (tarea == null) {
            log.warn("Tarea nula");
            throw new TareaNoValidaException("La tarea es nula", HttpStatus.BAD_REQUEST);
        }
        if (tarea.getNombre().isEmpty()) {
            log.warn("El nombre de la tarea esta vacio");
            throw new TareaNoValidaException("El nombre de la tarea esta vacio", HttpStatus.BAD_REQUEST);
        }
        if (tarea.getNombre().length() > 300) {
            log.warn("El nombre de la tarea supera los 300 caracteres");
            throw new TareaNoValidaException("El nombre de la tarea supera los 300 caracteres", HttpStatus.BAD_REQUEST);
        }
        if (tarea.getGrupo() == null) {
            log.warn("El grupo es nulo");
            throw new TareaNoValidaException("El grupo es nulo", HttpStatus.BAD_REQUEST);
        }
        if (!grupoServicio.existeGrupoById(tarea.getGrupo().getId())) {
            log.warn("El grupo asignado no existe");
            throw new TareaNoValidaException("El grupo asignado no existe", HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    private void asignarNombreGrupo(Tarea tarea) {
        if (grupoServicio.existeGrupoById(tarea.getGrupo().getId())) {
            Optional<Grupo> grupoTemp = grupoServicio.getGrupoById(tarea.getGrupo().getId());
            tarea.getGrupo().setNombre(grupoTemp.get().getNombre());
        }
    }

    public Tarea actualizarTarea(Integer id, Tarea tareaActualizada) throws TareaNoValidaException {
        if (verificarTarea(tareaActualizada)) {
            tareaActualizada.setId(id);
            tareaActualizada.setNombre(tareaActualizada.getNombre().trim());
            tareaActualizada.setEstado(tareaActualizada.getEstado());
            tareaActualizada.setGrupo(tareaActualizada.getGrupo());
            asignarNombreGrupo(tareaActualizada);
            return tareaRepository.save(tareaActualizada);
        }else{
            throw new TareaNoValidaException("La tarea no es valida", HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean eliminarTareaById(Integer id) throws TareaNoValidaException {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id);
            return true;
        } else {
            throw new TareaNoValidaException("La tarea no existe", HttpStatus.NOT_FOUND);
        }
    }
}
