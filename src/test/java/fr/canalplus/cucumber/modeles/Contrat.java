package fr.canalplus.cucumber.modeles;

public class Contrat {
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
