package bdd;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = -2186904499547206125L;

	@Id @GeneratedValue
	private int id;
	
	@Column(nullable = false)
	private String nom;

	@Column(nullable = false)
	private String prenom;
	
	@Column(nullable = false, length = 20, unique = true)
	private String login;
	
	@Column(length = 20)
	private String motDePasse;
	
	@Column(unique = true)
	@NotNull
	@Email
	private String eMail;
	
	@OneToMany(mappedBy = "utilisateur")
	private Set<Disponibilite> disponibilites = new HashSet<Disponibilite>();
	
	@ManyToMany(mappedBy = "joueursMatch", fetch = FetchType.EAGER)
	private Set<Affrontement> matchsPrevus = new HashSet<Affrontement>();
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Utilisateur)) {
			return false;
		}
		Utilisateur utilisateurOther = (Utilisateur) other;
		return utilisateurOther.getId() == this.getId();
	}
	public String getNom() {
		return nom;
	}
	
	public void setNom(String newNom) {
		nom = newNom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String newPrenom) {
		prenom = newPrenom;
	}
	
	public boolean verifAuth(String loginEntre, String motDePasseEntre) {
		return (
				login.compareTo(loginEntre) == 0 &&
				motDePasse.compareTo(motDePasseEntre) == 0
				);
	}
	
	public void setLogin(String newLogin) {
		login = newLogin;
	}
	
	public void setMotDePasse(String newMotDePasse) {
		motDePasse = newMotDePasse;
	}

	public void setEmail(String newEmail) {
		eMail = newEmail;
	}
	
	public String getEmail() {
		return eMail;
	}

	public Object getId() {
		return id;
	}
	
	public Set<Affrontement> getMatchsPrevus() {
		return matchsPrevus;
	}
	
	@Override
	public String toString() {
		return nom + " " + prenom;
	}
}
