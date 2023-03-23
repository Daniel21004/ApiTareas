package com.daniel.AppTareas.repositorio;

import com.daniel.AppTareas.modelo.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

}
