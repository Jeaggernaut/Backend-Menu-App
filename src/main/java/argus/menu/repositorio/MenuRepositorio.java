package argus.menu.repositorio;

import argus.menu.modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepositorio extends JpaRepository<Materia, Integer> {



}
