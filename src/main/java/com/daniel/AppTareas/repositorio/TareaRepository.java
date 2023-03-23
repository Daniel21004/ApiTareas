package com.daniel.AppTareas.repositorio;

import com.daniel.AppTareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    public List<Tarea> findByNombre(String nombre);
}
