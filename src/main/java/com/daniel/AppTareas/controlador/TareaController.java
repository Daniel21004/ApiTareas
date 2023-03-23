package com.daniel.AppTareas.controlador;

import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.servicio.TareaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    TareaServicio tareaServicio;

    public TareaController(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    @GetMapping("/tareas")
    public ResponseEntity<List<Tarea>> getTareas() {
        List<Tarea> tareas = tareaServicio.getTareas();
        if (tareas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(tareas, HttpStatus.OK);
        }
    }

    @GetMapping("/hola")
    public ResponseEntity<String> getHola() {

        return new ResponseEntity<>("Hola", HttpStatus.OK);

    }

    @GetMapping("/{nombre}")
    public ResponseEntity<List<Tarea>> getTareasByNombre(@PathVariable("nombre") String nombre) {
        List<Tarea> tareaList = tareaServicio.getTareasByNombre(nombre);
        if (nombre.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (tareaList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(tareaList, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea tareaTemp = tareaServicio.crearTarea(tarea);
        return new ResponseEntity<>(tareaTemp, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTareaById(@PathVariable("id") Integer id, @RequestBody Tarea tarea) {
        Optional<Tarea> tareaTemp = tareaServicio.getTareaById(id);
        if (tareaTemp.isPresent()) {
            return new ResponseEntity<>(tareaServicio.actualizarTarea(tareaTemp.get().getId(), tarea), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTareaById(@PathVariable("id") Integer id) {
        tareaServicio.eliminarTareaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
