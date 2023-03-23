package com.daniel.AppTareas.controlador;

import com.daniel.AppTareas.modelo.Grupo;
import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.servicio.GrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController {

    @Autowired
    GrupoServicio grupoServicio;

    @GetMapping("/grupos")
    public ResponseEntity<List<Grupo>> getGrupos() {
        List<Grupo> grupos = grupoServicio.getGrupos();
        if (grupos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(grupos, HttpStatus.OK);
        }
    }

    @GetMapping("/tareas/{id}")
    public ResponseEntity<List<Tarea>> getTareasByIdGrupo(@PathVariable("id") Integer id) {
        List<Tarea> tareaList = grupoServicio.getTareasByIdGrupo(id);
        if (tareaList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(tareaList, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Grupo> crearGrupo(@RequestBody Grupo grupo) {
        Grupo grupoTemp = grupoServicio.crearGrupo(grupo);
        return new ResponseEntity<>(grupoTemp, HttpStatus.OK);
    }

    @PatchMapping("/{id}/{nuevoNombre}")
    public ResponseEntity<Grupo> updateNombreGrupoById(@PathVariable("id") Integer id, @PathVariable("nuevoNombre") String nuevoNombre) {
        Grupo grupoTemp = grupoServicio.updateNombreGrupo(id, nuevoNombre);
        return new ResponseEntity<>(grupoTemp, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGrupoById(@PathVariable("id") Integer id){
        grupoServicio.eliminarGrupoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
