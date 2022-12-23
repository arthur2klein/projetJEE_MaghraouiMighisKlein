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
public class NouvelUtilisateurController {

	@Autowired
 	UtilisateurRepository utilisateurRepository;
	
	@GetMapping("/nouvelUtilisateur")
	public String showNouvelUtilisateurPage() {
		return "nouvelUtilisateur";
	}
	
	@PostMapping("/nouvelUtilisateur")
	public String login(
			@RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("eMail") String eMail,
			@RequestParam("login") String login,
			@RequestParam("motDePasse") String motDePasse,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession session
			) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setLogin(login);
		if (utilisateurRepository.findByLogin(login) != null) {
			redirectAttributes
			.addFlashAttribute("error", "Ce login existe déjà.");
			return "redirect:nouvelUtilisateur";			
		}
		if (utilisateurRepository.findByeMail(eMail) != null) {
			redirectAttributes
			.addFlashAttribute("error", "Cette addresse mail existe déjà.");
			return "redirect:nouvelUtilisateur";			
		}
		utilisateur.setMotDePasse(motDePasse);
		utilisateur.setEmail(eMail);
		utilisateur = utilisateurRepository.save(utilisateur);
		if (utilisateur.getId() == null) {
			redirectAttributes.addAttribute("error", "Création impossible.");
			return "redirect:nouvelUtilisateur";
		}
		session.setAttribute("Utilisateur", utilisateur);
		return "redirect:index";
	}
}
