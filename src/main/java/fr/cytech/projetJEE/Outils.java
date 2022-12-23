package fr.cytech.projetJEE;

public class Outils {
	public static String jourDeSemaine(int i) {
		i %=7 ;
		switch(i) {
		case 1:
			return "lundi";
		case 2:
			return "mardi";
		case 3:
			return "mercredi";
		case 4:
			return "jeudi";
		case 5:
			return "vendredi";
		case 6:
			return "samedi";
		default:
			return "dimanche";
		}
	}

	public static int jourVersNombre(String i) {
		switch(i) {
		case "lundi":
			return 1;
		case "mardi":
			return 2;
		case "mercredi":
			return 3;
		case "jeudi":
			return 4;
		case "vendredi":
			return 5;
		case "samedi":
			return 6;
		default:
			return 0;
		}
	}
}
