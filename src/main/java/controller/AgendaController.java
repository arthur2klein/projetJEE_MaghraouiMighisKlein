package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bdd.Affrontement;
import bdd.Utilisateur;
import repository.AffrontementRepository;
import repository.TerrainRepository;
import repository.UtilisateurRepository;

@Controller
public class AgendaController {

	@Autowired
 	UtilisateurRepository utilisateurRepository;
	@Autowired
	AffrontementRepository previsionRepository;
	@Autowired
	TerrainRepository terrainRepository;
	
	@GetMapping("/agenda")
	public String agendaGet(
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
		List<Affrontement> listPrevisions = new ArrayList<Affrontement>();
		for (Affrontement affrontement:
			previsionRepository.findByJoueursMatch(utilisateur)) {
			if (affrontement.getResultat() == null) {
				listPrevisions.add(affrontement);
			}
		}
		model.addAttribute("previsions", listPrevisions);
		return "agenda";
	}
}
