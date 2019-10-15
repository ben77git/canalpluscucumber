package fr.canalplus.cucumber;

import java.util.ArrayList;
import java.util.List;

import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;
import static org.junit.Assert.*;


class Abonné {
	private String login;
	
	private List<Contrat> contrats;
	private List<Mouvement> mouvements;
	

	public Abonné(String login) {
		this.login = login;
		contrats = new ArrayList<>();
		Adresse adressePrincipale = new Adresse("active", "FRANCE", "FACE", "sans date d’effet");
		contrats.add(new Contrat("principal", adressePrincipale));
		mouvements = new ArrayList<>();
		mouvements.add(new Mouvement("création", adressePrincipale));
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

	public static Abonné créerNouveauAbonné(String login) {
		Contrat contratInitial = new Contrat("initial", new Adresse("active", "FRANCE", "FACE", "sans date d’effet"));
		Abonné nouveauAbonné = new Abonné(login);
		nouveauAbonné.getContrats().add(contratInitial);
		return nouveauAbonné;
	}
}

class Contrat {
	private String nom;
	private Adresse adresse;
	
	public Contrat(String nom, Adresse adresse) {
		this.nom = nom;
		this.adresse = adresse;
	}
	
	public String getNom() {
		return nom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
}

class Adresse {

	private String active, pays, canal, condition;

	public Adresse(String active, String pays, String canal, String condition) {
		super();
		this.active = active;
		this.pays = pays;
		this.canal = canal;
		this.condition = condition;
	}

	public String getActive() {
		return active;
	}
	
	public String getPays() {
		return pays;
	}

	public String getCanal() {
		return canal;
	}

	public String getCondition() {
		return condition;
	}
	
}

class Mouvement {
	private String type;
	private Adresse adresse;
	
	public Mouvement(String type, Adresse adresse) {
		super();
		this.type = type;
		this.adresse = adresse;
	}

	public String getType() {
		return type;
	}

	public Adresse getAdresse() {
		return adresse;
	}
	
	
}

public class Stepdefs {
	
	private Abonné unAbonné;
	private String nouveauStatut;
	private String nouveauPays;
	private String nouveauCanal;
	private String nouvelleCondition;
	// private boolean 
	
	@Etantdonné("^un abonné avec une adresse principale (.+) en (.+)$")
	public void un_abonné_avec_une_adresse_principale(String active, String pays) {
		unAbonné = Abonné.créerNouveauAbonné("toto");
		nouveauStatut = active;
		nouveauPays = pays;
	}

	@Lorsque("^le conseiller connecté à (.+) modifie l'adresse de l'abonné (.+)$")
	public void le_conseiller_connecté_modifie_l_abonné(String canal, String condition) {
		nouveauCanal = canal;
		nouvelleCondition = condition;
		unAbonné.modifieAdresse(nouveauStatut, nouveauPays, nouveauCanal, nouvelleCondition);
	}

	@Alors("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
	public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() {
		unAbonné.getContrats().forEach(contrat -> {
			assertEquals(nouveauStatut, contrat.getAdresse().getActive());
			assertEquals(nouveauPays, contrat.getAdresse().getPays());
			assertEquals(nouveauCanal, contrat.getAdresse().getCanal());
			assertEquals(nouvelleCondition, contrat.getAdresse().getCondition());
		});
	}

	@Alors("^un mouvement de modification d'adresse est créé$")
	public void un_mouvement_de_modification_d_adresse_est_créé() {
		assertEquals(2, unAbonné.getMouvements().size());
		assertEquals("modification", unAbonné.getMouvements().get(1).getType());
	}
}
