package repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bdd.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
	public Utilisateur findByeMail(String eMail);
	public Utilisateur findByLoginAndMotDePasse(String login, String motDePasse);
	public Utilisateur findByLogin(String login);
	public Utilisateur findByeMailAndMotDePasse(String eMail, String motDePasse);
}
