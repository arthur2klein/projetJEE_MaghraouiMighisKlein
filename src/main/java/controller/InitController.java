package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import bdd.Terrain;
import repository.DisponibiliteRepository;
import repository.AffrontementRepository;
import repository.ResultatRepository;
import repository.TerrainRepository;
import repository.UtilisateurRepository;

@Controller
public class InitController {
	
	@Autowired
	DisponibiliteRepository disponibiliteRepository;
	@Autowired
	AffrontementRepository previsionRepository;
	@Autowired
	ResultatRepository resultatRepository;
	@Autowired
	TerrainRepository terrainRepository;
	@Autowired
	UtilisateurRepository utilisateurRepository;	

	@GetMapping("init")
	public String initGet() {
		disponibiliteRepository.deleteAll();
		previsionRepository.deleteAll();
		resultatRepository.deleteAll();
		utilisateurRepository.deleteAll();
		terrainRepository.deleteAll();
		Terrain terrain1 = new Terrain();
		terrain1.setNom("terrain1");
		terrainRepository.save(terrain1);
		Terrain terrain2 = new Terrain();
		terrain2.setNom("terrain2");
		terrainRepository.save(terrain2);
		Terrain terrain3 = new Terrain();
		terrain3.setNom("terrain3");
		terrainRepository.save(terrain3);
		Terrain terrain4 = new Terrain();
		terrain4.setNom("terrain4");
		terrainRepository.save(terrain4);
		return "redirect:index";	
	}
	
	@GetMapping("createTerrain")
	public String createTerrainGet() {
		terrainRepository.deleteAll();
		Terrain terrain1 = new Terrain();
		terrain1.setNom("terrain1");
		terrainRepository.save(terrain1);
		Terrain terrain2 = new Terrain();
		terrain2.setNom("terrain2");
		terrainRepository.save(terrain2);
		Terrain terrain3 = new Terrain();
		terrain3.setNom("terrain3");
		terrainRepository.save(terrain3);
		Terrain terrain4 = new Terrain();
		terrain4.setNom("terrain4");
		terrainRepository.save(terrain4);
		return "redirect:index";
	}
	

}
