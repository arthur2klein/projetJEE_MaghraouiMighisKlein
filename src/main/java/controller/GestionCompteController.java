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
import repository.DisponibiliteRepository;
import repository.AffrontementRepository;
import repository.ResultatRepository;
import repository.UtilisateurRepository;

@Controller
public class GestionCompteController {

	@Autowired
 	UtilisateurRepository utilisateurRepository;
	@Autowired
	AffrontementRepository affrontementRepository;
	@Autowired
	ResultatRepository resultatRepository;
	@Autowired
	DisponibiliteRepository disponibiliteRepository;
	
	@GetMapping("/gestionCompte")
	public String gestionCompteGet(
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
		return "gestionCompte";
	}
	
	@PostMapping("/gestionCompte")
	public String gestionComptePost(
			@RequestParam("switch") String action,
			@RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("eMail") String eMail,
			@RequestParam("login") String login,
			@RequestParam("motDePasse") String motDePasse,
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
		if (action.equals("modifier")) {
			Utilisateur memeLogin = utilisateurRepository.findByLogin(login);
			if (memeLogin != null && !memeLogin.equals(utilisateur)) {
				redirectAttributes
					.addFlashAttribute("error", "Ce login existe déjà.");
				return "redirect:gestionCompte";			
			}
			Utilisateur memeEMail = utilisateurRepository.findByeMail(eMail);
			if (memeEMail != null && !memeEMail.equals(utilisateur)) {
				redirectAttributes
				.addFlashAttribute("error", "Cette addresse mail existe déjà.");
				return "redirect:nouvelUtilisateur";			
			}
			if (!nom.equals(""))
				utilisateur.setNom(nom);
			if (!prenom.equals(""))
				utilisateur.setPrenom(prenom);
			if (!login.equals(""))
				utilisateur.setLogin(login);
			if (!motDePasse.equals(""))
				utilisateur.setMotDePasse(motDePasse);
			if (!eMail.equals(""))
				utilisateur.setEmail(eMail);
			utilisateurRepository.save(utilisateur);
			redirectAttributes
				.addFlashAttribute("success", "Changements enregistrés.");
			return "redirect:index";
		}
		if (action.equals("supprimer")) {
			utilisateurRepository.delete(utilisateur);
			session.setAttribute("Utilisateur", null);
			redirectAttributes
				.addFlashAttribute("success", "Compte supprimé.");
			return "redirect:index";
		}
		if (action.equals("deconnexion")) {
			session.setAttribute("Utilisateur", null);
			redirectAttributes
				.addFlashAttribute("success", "Deconnexion réussie.");
			return "redirect:index";
		}
		return "redirect:index";
	}
}
