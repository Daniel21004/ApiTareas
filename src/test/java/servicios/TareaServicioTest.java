package servicios;


import com.daniel.AppTareas.excepciones.TareaNoValidaException;
import com.daniel.AppTareas.modelo.Estado;
import com.daniel.AppTareas.modelo.Grupo;
import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.repositorio.TareaRepository;
import com.daniel.AppTareas.servicio.GrupoServicio;
import com.daniel.AppTareas.servicio.TareaServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TareaServicioTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private GrupoServicio grupoServicio;

    @InjectMocks
    private TareaServicio tareaServicio;

    private List<Tarea> tareaList;
    private List<Grupo> grupoList;

    @BeforeEach
    public void setup() {
        grupoList = Arrays.asList(new Grupo(1, "Grupo 1"), new Grupo(2, "Grupo 2"), new Grupo(3, "Grupo 3"));
        tareaList = Arrays.asList(new Tarea(1, "Tarea 1", grupoList.get(0), Estado.En_curso), new Tarea(2, "Tarea 2", grupoList.get(1), Estado.En_curso), new Tarea(3, "Tarea 3", grupoList.get(2), Estado.En_curso));
        MockitoAnnotations.openMocks(this);
        Mockito.when(tareaRepository.findAll()).thenReturn(tareaList);
        Mockito.when(tareaRepository.findByNombre("ejemplo")).thenReturn(tareaList);
        Mockito.when(tareaRepository.findById(1)).thenReturn(Optional.of(tareaList.get(0)));
        Mockito.when(tareaRepository.save(tareaList.get(0))).thenReturn(tareaList.get(0));
        Mockito.when(grupoServicio.existeGrupoById(1)).thenReturn(true);
        Mockito.when(grupoServicio.existeGrupoById(2)).thenReturn(false);
        Mockito.when(grupoServicio.existeGrupoById(3)).thenReturn(true);
        Mockito.when(grupoServicio.getGrupoById(1)).thenReturn(Optional.of(grupoList.get(0)));
        Mockito.when(grupoServicio.getGrupoById(3)).thenReturn(Optional.of(grupoList.get(2)));
//        Mockito.when(grupoServicio.getGrupoById())
    }


    @Test
    public void getTareasTest() {
        Assertions.assertArrayEquals(tareaList.toArray(), tareaServicio.getTareas().toArray(new Tarea[0]));
    }

    @Test
    public void getTareasByNombreTest() {
        Assertions.assertEquals(tareaList, tareaServicio.getTareasByNombre("ejemplo"));
    }

    @Test
    public void getTareaByIdTest() {
        Assertions.assertEquals(Optional.of(tareaList.get(0)), tareaServicio.getTareaById(1));
        Assertions.assertTrue(tareaServicio.getTareaById(1).isPresent());
        Assertions.assertFalse(tareaServicio.getTareaById(2).isPresent());
    }

    @Test
    public void crearTareaTest() {
        Assertions.assertNotNull(tareaServicio.crearTarea(tareaList.get(0)));
        Assertions.assertEquals(grupoList.get(0).getNombre(), tareaServicio.crearTarea(tareaList.get(0)).getGrupo().getNombre());
    }


    @Test
    public void verificarTareaTest() {

        Assertions.assertThrows(TareaNoValidaException.class, () -> {
            tareaServicio.crearTarea(null);
        }, "La tarea no es valida");
        Assertions.assertThrows(TareaNoValidaException.class, () -> {
            tareaServicio.crearTarea(null);
        }, "La tarea es nula");

        Assertions.assertThrows(TareaNoValidaException.class, () -> {
            tareaList.get(0).setNombre("");
            tareaServicio.crearTarea(tareaList.get(0));
        }, "El nombre de la tarea esta vacio");

        Assertions.assertThrows(TareaNoValidaException.class, () -> {
            tareaList.get(0).setNombre("No es necesariamente una mala práctica tener métodos privados, estáticos o que" +
                    " no devuelvan nada en Spring. De hecho, es común tener métodos privados en los servicios para " +
                    "encapsular lógica específica del servicio que no se utiliza fuera de él. Los métodos estáticos " +
                    "también sedo se utilicen de manera adecuada. ");
            tareaServicio.crearTarea(tareaList.get(0));
        }, "El nombre de la tarea supera los 300 caracteres");

        Assertions.assertThrows(TareaNoValidaException.class, () -> {
            tareaList.get(0).setGrupo(null);
            tareaServicio.crearTarea(tareaList.get(0));
        }, "El grupo es nulo");

        Assertions.assertThrows(TareaNoValidaException.class, () -> {
            Tarea tareaTemp = new Tarea(1, "tareaTemp", new Grupo(2, "grupoTemp"), Estado.En_curso);
            tareaServicio.crearTarea(tareaTemp);
        }, "El grupo asignado no existe");
    }


    @Test
    public void actualizarTareaTest(){
        Tarea tareaTemp = tareaList.get(0);
        tareaTemp.setNombre("nombre prueba");
        tareaTemp.setEstado(Estado.Terminado);
        tareaTemp.setGrupo(grupoList.get(2));
        Assertions.assertEquals(tareaTemp, tareaServicio.actualizarTarea(1, tareaTemp));
    }

}
