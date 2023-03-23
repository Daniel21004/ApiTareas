package servicios;

import com.daniel.AppTareas.excepciones.GrupoNoValidoException;
import com.daniel.AppTareas.modelo.Estado;
import com.daniel.AppTareas.modelo.Grupo;
import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.repositorio.GrupoRepository;
import com.daniel.AppTareas.servicio.GrupoServicio;
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


public class GrupoServicioTest {

    @Mock
    private GrupoRepository grupoRepository;
    @InjectMocks
    private GrupoServicio grupoServicio;

    private List<Grupo> grupoList;
    private List<Tarea> tareaList;


    private Grupo grupoTemp;

    @BeforeEach
    public void setup() {
        grupoList = Arrays.asList(new Grupo(1, "Grupo 1"), new Grupo(2, "Grupo 2"), new Grupo(3, "Grupo 3"));
        tareaList = Arrays.asList(new Tarea(1, "Tarea 1", grupoList.get(0), Estado.En_curso), new Tarea(2, "Tarea 2", grupoList.get(1), Estado.En_curso), new Tarea(3, "Tarea 3", grupoList.get(2), Estado.En_curso));
        grupoTemp = new Grupo(1, "grupo 1");
        grupoTemp.setTareaList(tareaList);
        MockitoAnnotations.openMocks(this);
        Mockito.when(grupoRepository.existsById(1)).thenReturn(true);
        Mockito.when(grupoRepository.findAll()).thenReturn(grupoList);
        Mockito.when(grupoRepository.findById(1)).thenReturn(Optional.of(grupoTemp));
        Mockito.when(grupoRepository.save(grupoTemp)).thenReturn(grupoTemp);
    }

    @Test
    public void getGruposTest() {
        List<Grupo> grupoListTemp = grupoServicio.getGrupos();
        Assertions.assertEquals(grupoList, grupoListTemp);
    }

    @Test
    public void getGrupoByIdTest() {
        Assertions.assertEquals(Optional.of(grupoTemp), grupoServicio.getGrupoById(1));
        Assertions.assertTrue(grupoRepository.findById(1).isPresent());
        Assertions.assertFalse(grupoRepository.findById(2).isPresent());
    }

    @Test
    public void getTareasByIdGrupoTest() {
        Assertions.assertArrayEquals(tareaList.toArray(), grupoServicio.getTareasByIdGrupo(1).toArray());
        Assertions.assertThrows(GrupoNoValidoException.class, () -> {
            grupoServicio.getTareasByIdGrupo(2);
        }, "El grupo no existe");
    }

    @Test
    public void existeGrupoByIdTest() {
        Assertions.assertTrue(grupoServicio.existeGrupoById(1));
        Assertions.assertFalse(grupoServicio.existeGrupoById(2));
    }

    @Test
    public void crearGrupoTest() {
        Assertions.assertEquals(grupoTemp, grupoServicio.crearGrupo(grupoTemp));
        Assertions.assertThrows(GrupoNoValidoException.class, ()->{
            grupoServicio.crearGrupo(new Grupo(1, ""));
        }, "El grupo no es valido");
    }

    @Test
    public void verificarGrupoTest() {
        Assertions.assertThrows(GrupoNoValidoException.class, () -> {
            grupoServicio.verificarGrupo(null);
        }, "El grupo es nulo");
        Assertions.assertThrows(GrupoNoValidoException.class, () -> {
            grupoServicio.verificarGrupo(new Grupo(1, ""));
        }, "El nombre del grupo es invalido");
        Assertions.assertThrows(GrupoNoValidoException.class, () -> {
            grupoServicio.verificarGrupo(new Grupo(1, "La música es mi pasión y mi escape de la realidad. xd"));
        }, "El nombre del grupo supera los 50 carácteres máximos");

        Assertions.assertTrue(grupoServicio.verificarGrupo(new Grupo(1, "La música es mi pasión y mi escape de la realidad.")));
    }

    @Test
    public void updateNombreGrupoTest() {
        String nuevoNombre = "nuevoNombreGrupo";

        Assertions.assertThrows(GrupoNoValidoException.class, () -> {
            grupoServicio.updateNombreGrupo(2, "");
        }, "El grupo no existe");

        Assertions.assertEquals(nuevoNombre, grupoServicio.updateNombreGrupo(1, "nuevoNombreGrupo").getNombre());
    }

    @Test
    public void eliminarGrupoTest() {
        Assertions.assertThrows(GrupoNoValidoException.class, () -> {
            grupoServicio.eliminarGrupoById(2);
        }, "El grupo no existe");
        Assertions.assertTrue(grupoServicio.eliminarGrupoById(1));
    }

}
