package argus.menu.controlador;

import argus.menu.modelo.Materia;
import argus.menu.servicios.MateriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu-app") //http://localhost:8080/menu-app
@CrossOrigin(value = "http://localhost:4200") //puerto angular
public class MateriaControlador {

    private static final Logger logger = LoggerFactory.getLogger(MateriaControlador.class);
    @Autowired
    private MateriaServicio materiaService;

    @GetMapping("/materias") //   http://localhost:8080/menu-app/materias
    public List<Materia> obtenerMateria(){
        List<Materia> materias = this.materiaService.listarMaterias();
        logger.info("Materias obtenidas: ");
        materias.forEach(materia -> logger.info(materia.toString()));

        return materias;
    }
}
