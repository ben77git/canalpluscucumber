package fr.canalplus.cucumber.modeles;

public class Mouvement {
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
