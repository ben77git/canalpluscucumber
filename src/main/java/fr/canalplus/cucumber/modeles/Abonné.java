package fr.canalplus.cucumber.modeles;

import java.util.ArrayList;
import java.util.List;

public class Abonné {
	private String login;
	
	private List<Contrat> contrats;
	private List<Mouvement> mouvements;
	

	public Abonné(String login, Adresse adresse) {
		this.login = login;
		contrats = new ArrayList<>();
		contrats.add(new Contrat("principal", adresse));
		mouvements = new ArrayList<>();
		mouvements.add(new Mouvement("création", adresse));
	}
	
	public boolean modifieAdresse(String active, String pays, String canal, String condition) {
		Adresse adresseCourante = contrats.get(0).getAdresse();
		// si la nouvelle adresse est identique a l'ancienne
		if (adresseCourante.getActive().equals(active) &&
				adresseCourante.getPays().equals(pays) &&
				adresseCourante.getCanal().equals(canal) &&
				adresseCourante.getCondition().equals(condition)) {
			return false;
		}
		Adresse nouvelleAdresse = new Adresse(active, pays, canal, condition);
		contrats.forEach(x -> x.setAdresse(nouvelleAdresse));
		mouvements.add(new Mouvement("modification", nouvelleAdresse));
		return true;
	}
	
	public boolean modifieAdresse(String canal, String condition) {
		contrats.forEach(x -> {x.getAdresse().setCanal(canal); x.getAdresse().setCondition(condition);});
		mouvements.add(new Mouvement("modification", contrats.get(0).getAdresse()));
		return true;
	}
	
	public void ajouteContrat(String nom) {
		contrats.add(new Contrat(nom, contrats.get(0).getAdresse()));
	}

	public String getLogin() {
		return login;
	}

	public List<Contrat> getContrats() {
		return contrats;
	}
	
	
	public List<Mouvement> getMouvements() {
		return mouvements;
	}
}