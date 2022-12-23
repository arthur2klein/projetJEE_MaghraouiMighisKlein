package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdd.Affrontement;
import bdd.Utilisateur;

@Repository
public interface AffrontementRepository extends JpaRepository<Affrontement, Integer> {
	public List<Affrontement> findByJourAndHeure(int jour, int heure);
	public Affrontement findByJoueursMatchAndJourAndHeure(
			Utilisateur utilisateur,
			int jour,
			int heure
			);
	public List<Affrontement> findByJoueursMatch(Utilisateur utilisateur);
}
