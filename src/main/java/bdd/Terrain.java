package bdd;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Terrain implements Serializable {
	private static final long serialVersionUID = 90710992421249315L;

	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String nom;
	
	@OneToMany(mappedBy = "terrain")
	private Set<Affrontement> matchs;
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String newNom) {
		nom = newNom;
	}
	
	@Override
	public String toString() {
		return nom;
	}
}
