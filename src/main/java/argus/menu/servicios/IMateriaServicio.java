package argus.menu.servicios;

import argus.menu.modelo.Materia;

import java.util.List;

public interface IMateriaServicio {
    List<Materia> listarMaterias();
    Materia buscarMateriaPorId(Integer idMateria);
    void guardarMateria(Materia materia);
    void eliminarMateriaPorId(Integer idMateria);

}
