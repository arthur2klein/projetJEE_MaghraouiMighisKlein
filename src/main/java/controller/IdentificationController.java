package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bdd.Utilisateur;
import repository.UtilisateurRepository;

@Controller
public class IdentificationController {

	@Autowired
 	UtilisateurRepository utilisateurRepository;
	
	@GetMapping("/identification")
	public String authentificationGet() {
		return "identification";
	}
	
	@PostMapping("/identification")
	public String authentificationPost(
			@RequestParam("login") String login,
			@RequestParam("motDePasse") String motDePasse,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession session
			) {
		Utilisateur utilisateur =
				utilisateurRepository
					.findByLoginAndMotDePasse(login, motDePasse);
		if (utilisateur == null) {
			utilisateur =
				utilisateurRepository
					.findByeMailAndMotDePasse(login, motDePasse);
		}
		if (utilisateur == null) {
			redirectAttributes
				.addFlashAttribute("error", "Veuillez vous identifier.");
			return "redirect:identification";
		}
		session.setAttribute("Utilisateur", utilisateur);
		return "redirect:index";
	}

}
