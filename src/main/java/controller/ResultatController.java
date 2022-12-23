package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bdd.Affrontement;
import bdd.Resultat;
import bdd.Utilisateur;
import repository.AffrontementRepository;
import repository.ResultatRepository;
import repository.UtilisateurRepository;

@Controller
public class ResultatController {

	@Autowired
 	UtilisateurRepository utilisateurRepository;
	@Autowired
	ResultatRepository resultatRepository;
	@Autowired
	AffrontementRepository previsionRepository;
	
	@PostMapping("/resultat")
	public String resultatPost(
			@RequestParam Map<String,String> allRequestParams,
			Model model,
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
		miseAJourScores(utilisateur, allRequestParams);
		return "redirect:resultat";
	}
	
	private boolean miseAJourScores(
			Utilisateur utilisateur,
			Map<String, String> changements
			) {
		boolean changement = false;
		Map<Integer, Resultat> matchsAEnregistrer = new HashMap<Integer, Resultat>();
		for (Entry<String, String> mapentry: changements.entrySet()) {
			if (mapentry.getValue().equals("")) {
				continue;
			}
			String[] infos = mapentry.getKey().split("prev");
			Integer id = Integer.valueOf(infos[1]);
			Affrontement previsionCorrespondante =
					previsionRepository.findById(id).get();
			if (matchsAEnregistrer.containsKey(id)) {
				Resultat resultat = matchsAEnregistrer.get(id);
				if (infos[0].equals("11")) {
					resultat.setPoints1Set1(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("21")) {
					resultat.setPoints2Set1(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("12")) {
					resultat.setPoints1Set2(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("22")) {
					resultat.setPoints2Set2(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("13")) {
					resultat.setPoints1Set3(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("23")) {
					resultat.setPoints2Set3(Integer.valueOf(mapentry.getValue()));
				}
				if (resultat.verifier()) {
					resultatRepository.save(resultat);
					changement = true;
				}
			} else {
				Resultat resultat = new Resultat();
				resultat.setAffrontement(previsionCorrespondante);
				if (infos[0].equals("11")) {
					resultat.setPoints1Set1(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("21")) {
					resultat.setPoints2Set1(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("12")) {
					resultat.setPoints1Set2(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("22")) {
					resultat.setPoints2Set2(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("13")) {
					resultat.setPoints1Set3(Integer.valueOf(mapentry.getValue()));
				}
				if (infos[0].equals("23")) {
					resultat.setPoints2Set3(Integer.valueOf(mapentry.getValue()));
				}
				matchsAEnregistrer.put(id, resultat);
			}
		}
		return changement;
	}

	@GetMapping("/resultat")
	public String resultatGet(
			Model model,
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
		List<Affrontement> listAffrontement =
				previsionRepository.findByJoueursMatch(utilisateur);
		List<Affrontement> listPrevision = new ArrayList<Affrontement>();
		List<Resultat> listResultats = new ArrayList<Resultat>();
		for (Affrontement affrontement: listAffrontement) {
			Resultat resultat = affrontement.getResultat();
			if (resultat == null) {
				listPrevision.add(affrontement);
			} else {
				listResultats.add(resultat);
			}
		}
		model.addAttribute("resultats", listResultats);
		model.addAttribute("previsions", listPrevision);
		return "resultat";
	}

}
