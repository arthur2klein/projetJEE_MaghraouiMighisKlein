package bdd;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Resultat implements Serializable{
	private static final long serialVersionUID = 1132651853947713327L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	@NotNull
	@JoinColumn(name = "idMatch", referencedColumnName = "id")
	private Affrontement affrontement;
	
	@Column
	private Integer points1Set1;
	
	@Column
	private Integer points1Set2;
	
	@Column
	private Integer points1Set3;
	
	@Column
	private Integer points2Set1;
	
	@Column
	private Integer points2Set2;
	
	@Column
	private Integer points2Set3;
	
	public Set<Utilisateur> getJoueurs() {
		return affrontement.getJoueurs();
	}
	
	public Integer getPoints1Set1() {
		return points1Set1;
	}
	
	public void setPoints1Set1(Integer newPoints1) {
		points1Set1 = newPoints1;
	}
	
	public Integer getPoints2Set1() {
		return points2Set1;
	}
	
	public void setPoints2Set1(Integer newPoints2) {
		points2Set1 = newPoints2;
	}
	
	public Integer getPoints1Set2() {
		return points1Set2;
	}
	
	public void setPoints1Set2(Integer newPoints1) {
		points1Set2 = newPoints1;
	}
	
	public Integer getPoints2Set2() {
		return points2Set2;
	}
	
	public void setPoints2Set2(Integer newPoints2) {
		points2Set2 = newPoints2;
	}
	
	public Integer getPoints1Set3() {
		return points1Set3;
	}
	
	public void setPoints1Set3(Integer newPoints1) {
		points1Set3 = newPoints1;
	}
	
	public Integer getPoints2Set3() {
		return points2Set3;
	}
	
	public void setPoints2Set3(Integer newPoints2) {
		points2Set3 = newPoints2;
	}

	public void setAffrontement(Affrontement affrontement) {
		this.affrontement = affrontement;
	}
	
	public Affrontement getAffrontement() {
		return affrontement;
	}
	
	public boolean verifier() {
		Integer gagnant1 = gagnant(points1Set1, points2Set1);
		Integer gagnant2 = gagnant(points1Set2, points2Set2);
		Integer gagnant3 = gagnant(points1Set3, points2Set3);
		if (
			gagnant1 == 0 ||
			gagnant2 == 0 ||
			(gagnant1 != gagnant2 && gagnant3 == 0)
			) {
			return false;
		}
		return true;
	}
	
	public Integer gagnant(Integer points1, Integer points2) {
		if (points1 == null || points2 == null)
			return 0;
		if (
			(points1 == 6 && points2 < 5) ||
			(points1 > 6 && points2 == points1 - 2)
			) {
			return 1;
		}
		if (
			(points2 == 6 && points1 < 5) ||
			(points2 > 6 && points1 == points2 - 2)
			) {
			return 2;
		}
		return 0;
	}
}
