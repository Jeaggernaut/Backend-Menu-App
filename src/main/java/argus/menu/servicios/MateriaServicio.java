package argus.menu.servicios;

import argus.menu.modelo.Materia;
import argus.menu.repositorio.MenuRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServicio implements IMateriaServicio{

    @Autowired
    private MenuRepositorio menuRepositorio;

    @Override
    public List<Materia> listarMaterias() {
        return this.menuRepositorio.findAll();
    }

    @Override
    public Materia buscarMateriaPorId(Integer idMateria) {
        Materia materia = this.menuRepositorio.findById(idMateria).orElse(null);

        return materia;
    }

    @Override
    public void guardarMateria(Materia materia) {
        this.menuRepositorio.save(materia);

    }

    @Override
    public void eliminarMateriaPorId(Integer idMateria) {
        this.menuRepositorio.deleteById(idMateria);
    }
}
