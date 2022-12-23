package bdd;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Affrontement implements Serializable{
	private static final long serialVersionUID = -8006047934054469551L;

	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(mappedBy = "affrontement")
	private Resultat resultat;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "match_utilisateur",
			joinColumns = {@JoinColumn(name = "idAffrontement")},
			inverseJoinColumns = {@JoinColumn(name = "idJoueur")}
	)
	private Set<Utilisateur> joueursMatch = new HashSet<Utilisateur>();
	
	@Column(nullable = false)
	private int jour;
	
	@Column(nullable = false)
	private int heure;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "idTerrain")
	private Terrain terrain;
	
	public int getId() {
		return id;
	}
	
	public Set<Utilisateur> getJoueurs() {
		return joueursMatch;
	}
	
	protected void addJoueur(Utilisateur newUtilisateur) {
		newUtilisateur.getMatchsPrevus().add(this);
		joueursMatch.add(newUtilisateur);
	}
	
	public void setJoueurs(Utilisateur joueur1, Utilisateur joueur2) {
		addJoueur(joueur1);
		addJoueur(joueur2);
	}
	
	public void deleteJoueurs(Utilisateur newUtilisateur2) {
		joueursMatch.clear();
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
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public void setTerrain(Terrain newTerrain) {
		terrain = newTerrain;
	}

	public Resultat getResultat() {
		return resultat;
	}
	
	public Set<Utilisateur> getJoueursMatch() {
		return joueursMatch;
	}
}
