package bdd;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Disponibilite implements Serializable {
	private static final long serialVersionUID = 4685383626187260071L;

	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idUtilisateur")
	private Utilisateur utilisateur;
	
	@Column(nullable = false)
	private int jour;
	
	@Column(nullable = false)
	private int heure;
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	public void setUtilisateur(Utilisateur newUtilisateur) {
		utilisateur = newUtilisateur;
	}

	public void setJour(int newJour) {
		jour = newJour;
	}

	public void setHeure(int newHeure) {
		heure = newHeure;
	}
	
	public int getJour() {
		return jour;
	}
	
	public int getHeure() {
		return heure;
	}
}
