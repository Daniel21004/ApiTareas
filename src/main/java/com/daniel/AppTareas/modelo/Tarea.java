package com.daniel.AppTareas.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 300)
    private String nombre;
    @ManyToOne() // Muchas tareas pertenecen a un grupo
    @JoinColumn(name = "grupo_id", referencedColumnName = "id", nullable = true)
    // La columna se llamara grupo_id y esta referenciada con el atributo 'id' de la clase grupo, seria la llave foranea
    private Grupo grupo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;
}
