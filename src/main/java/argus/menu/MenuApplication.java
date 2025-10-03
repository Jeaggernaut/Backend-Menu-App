package argus.menu;

import argus.menu.modelo.Materia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MenuApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuApplication.class, args);

        Materia materia = new Materia();
        materia.setMateria("Mate");
        materia.setProyecto("Proyecto1");
        materia.setMiembros(4);

        System.out.println(materia);
	}

}
