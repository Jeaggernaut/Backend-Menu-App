package argus.menu.controlador;

import argus.menu.modelo.Materia;
import argus.menu.servicios.MateriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu-app") //http://localhost:8080/menu-app
@CrossOrigin(value = "http://localhost:4200") //puerto angular
public class MateriaControlador {

    private static final Logger logger = LoggerFactory.getLogger(MateriaControlador.class);
    @Autowired
    private MateriaServicio materiaServicio;

    @GetMapping("/materias") //   http://localhost:8080/menu-app/materias?userId=1
    public List<Materia> obtenerMateria(@RequestParam Integer userId){
        List<Materia> materias = this.materiaServicio.listarMateriasPorUsuario(userId);
        logger.info("Materias obtenidas para usuario " + userId + ": ");
        materias.forEach(materia -> logger.info(materia.toString()));

        return materias;
    }

    @PostMapping("/materias") //   http://localhost:8080/menu-app/materias?userId=1
    public Materia agregarMateria(@RequestParam Integer userId, @RequestBody Materia materia){
        materia.setUsuarioId(userId);
        logger.info("Proyecto a agregar para usuario " + userId + ": "+materia);
        return this.materiaServicio.guardarMateria(materia);
    }

    @DeleteMapping("/materias/{idMateria}") //   http://localhost:8080/menu-app/materias/1
    public void eliminarMateria(@PathVariable Integer idMateria){
        logger.info("Eliminando materia con id: " + idMateria);
        this.materiaServicio.eliminarMateriaPorId(idMateria);
    }
}
