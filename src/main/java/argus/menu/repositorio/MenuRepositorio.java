package argus.menu.repositorio;

import argus.menu.modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuRepositorio extends JpaRepository<Materia, Integer> {
    List<Materia> findByUsuarioId(Integer usuarioId);
}
