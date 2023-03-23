package repositorios;

import com.daniel.AppTareas.AppTareasApplication;
import com.daniel.AppTareas.modelo.Estado;
import com.daniel.AppTareas.modelo.Grupo;
import com.daniel.AppTareas.modelo.Tarea;
import com.daniel.AppTareas.repositorio.GrupoRepository;
import com.daniel.AppTareas.repositorio.TareaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = AppTareasApplication.class)
public class TareaRepositoryTest {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    private List<Grupo> grupoList;

    private List<Tarea> tareaList;

    @Test
    public void findByNombreTest() {
        grupoList = Arrays.asList(new Grupo(1, "Grupo 1"), new Grupo(2, "Grupo 2"), new Grupo(3, "Grupo 3"));
        tareaList = Arrays.asList(new Tarea(1, "Tarea 1", grupoList.get(0), Estado.En_curso), new Tarea(2, "Tarea 2", grupoList.get(1), Estado.En_curso), new Tarea(3, "Tarea 3", grupoList.get(2), Estado.En_curso));

        grupoRepository.save(grupoList.get(0));
        grupoRepository.save(grupoList.get(1));
        grupoRepository.save(grupoList.get(2));

        tareaRepository.save(tareaList.get(0));
        tareaRepository.save(tareaList.get(1));
        tareaRepository.save(tareaList.get(2));

        List<Tarea> tareaListTemp = tareaRepository.findByNombre("Tarea 1");

        Assertions.assertEquals(1, tareaListTemp.size());
//        Assertions.assertEquals(tareaList.get(0), tareaListTemp.get(0));

        tareaListTemp = tareaRepository.findByNombre("Tarea");

        Assertions.assertEquals(0, tareaListTemp.size());
    }
}
