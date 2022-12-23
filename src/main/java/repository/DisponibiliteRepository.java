package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdd.Disponibilite;
import bdd.Utilisateur;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Integer> {
	public List<Disponibilite> findByUtilisateur(Utilisateur utilisateur);

	public Disponibilite findByUtilisateurAndJourAndHeure(Utilisateur utilisateur, int jour, int heure);

	public List<Disponibilite> findByJourAndHeure(int jour, int heure);

}
