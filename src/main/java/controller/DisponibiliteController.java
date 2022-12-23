package controller;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bdd.Disponibilite;
import bdd.Affrontement;
import bdd.Terrain;
import bdd.Utilisateur;
import fr.cytech.projetJEE.Outils;
import repository.DisponibiliteRepository;
import repository.AffrontementRepository;
import repository.TerrainRepository;
import repository.UtilisateurRepository;

@Controller
public class DisponibiliteController {

	@Autowired
 	UtilisateurRepository utilisateurRepository;
	@Autowired
	DisponibiliteRepository disponibiliteRepository;
	@Autowired
	AffrontementRepository previsionRepository;
	@Autowired
	TerrainRepository terrainRepository;
	
	@GetMapping("/disponibilite")
	public String disponibiliteGet(
			RedirectAttributes redirectAttributes,
			HttpSession session
			) {
		Utilisateur utilisateur =
				(Utilisateur) session.getAttribute("Utilisateur");
		if (utilisateur == null) {
			redirectAttributes
				.addFlashAttribute("error", "Veuillez vous identifier.");
			return "redirect:identification";
		}
		List<Disponibilite> disponibilites =
				disponibiliteRepository.findByUtilisateur(utilisateur);
		for (Disponibilite disponibilite: disponibilites) {
			redirectAttributes.addFlashAttribute(
						Outils.jourDeSemaine(disponibilite.getJour()) +
						disponibilite.getHeure(), "checked"
						);
		}
		return "disponibilite";
	}
	
	@PostMapping("/disponibilite")
	public String disponibilitePost(
			@RequestParam Map<String,String> allRequestParams,
			RedirectAttributes redirectAttributes,
			HttpSession session
			) {
		Utilisateur utilisateur =
				(Utilisateur) session.getAttribute("Utilisateur");
		if (utilisateur == null) {
			redirectAttributes
				.addFlashAttribute("error", "Veuillez vous identifier.");
			return "redirect:identification";
		}
		for (Disponibilite disponibilite: disponibiliteRepository.
				findByUtilisateur(utilisateur)) {
			disponibiliteRepository.delete(disponibilite);
		}
		BitSet resultat = rechercheMatch(utilisateur, allRequestParams);
		if (resultat.get(1) == true) {
			redirectAttributes
				.addFlashAttribute("success", "Un match a été trouvé.");
			return "redirect:agenda";			
		}
		if (resultat.get(0) == true) {
			redirectAttributes
				.addFlashAttribute("success", "En attente d'autre joueur, ...");
			return "redirect:disponibilite";
		}
		redirectAttributes.
			addFlashAttribute("error", "Pas de créneau possible.");
		return "redirect:disponibilite";
	}

	public BitSet rechercheMatch(
			Utilisateur utilisateur,
			Map<String, String> mapDisponibilite
			) {
		BitSet res = new BitSet(2);
		boolean disponibiliteEnregistree = false;
		boolean matchTrouve = false;
		for (Map.Entry<String, String> mapEntry: mapDisponibilite.entrySet()) {
			String[] infos = ((String)mapEntry.getKey()).split("i");
			Disponibilite disponibilite = new Disponibilite();
			disponibilite.setUtilisateur(utilisateur);
			disponibilite.setJour(Outils.jourVersNombre(infos[0] + "i"));
			disponibilite.setHeure((int)Integer.valueOf(infos[1]));
			if (disponibiliteRepository.
					findByUtilisateurAndJourAndHeure(
							utilisateur,
							disponibilite.getJour(),
							disponibilite.getHeure()
							) != null ||
				previsionRepository.findByJoueursMatchAndJourAndHeure(
						utilisateur,
						disponibilite.getJour(),
						disponibilite.getHeure()
						) != null) {
				continue;
			}
			List<Affrontement> previsionMemeMoment =
					previsionRepository.findByJourAndHeure(
						disponibilite.getJour(),
						disponibilite.getHeure()
						);
			List<Disponibilite> disponibiliteMemeMoment =
					disponibiliteRepository.findByJourAndHeure(
						disponibilite.getJour(),
						disponibilite.getHeure()
						);
			Terrain terrainDisponible =
					findTerrainDisponible(previsionMemeMoment);
			if (terrainDisponible == null) {
				continue;
			}
			boolean matchPossible = false;
			for (Disponibilite matchPotentiel: disponibiliteMemeMoment) {
				Affrontement match = new Affrontement();
				match.setJour(disponibilite.getJour());
				match.setHeure(disponibilite.getHeure());
				match.setTerrain(terrainDisponible);
				match.setJoueurs(matchPotentiel.getUtilisateur(), utilisateur);
				previsionRepository.save(match);
				disponibiliteRepository.delete(matchPotentiel);
				matchPossible = true;
				matchTrouve = true;
				break;
			}
			if (!matchPossible) {
				disponibiliteRepository.save(disponibilite);
				disponibiliteEnregistree = true;
			}
		}
		res.set(0, disponibiliteEnregistree);
		res.set(1, matchTrouve);
		return res;
	}

	private Terrain findTerrainDisponible(List<Affrontement> previsions) {
		int nombreTerrains = (int)terrainRepository.count();
		int nombrePrevision = (int)previsions.size();
		if (nombrePrevision >= nombreTerrains) {
			return null;
		}
		return terrainRepository.findByNom("terrain" + (nombrePrevision + 1));
	}

}
