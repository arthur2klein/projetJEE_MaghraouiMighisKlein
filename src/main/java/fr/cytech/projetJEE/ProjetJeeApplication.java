package fr.cytech.projetJEE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import repository.UtilisateurRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"bdd", "controller", "fr.cytech.projetJEE", "repository"})
@EntityScan("bdd")
@EnableJpaRepositories(basePackages="repository")
public class ProjetJeeApplication {

	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetJeeApplication.class, args);
	}
}
