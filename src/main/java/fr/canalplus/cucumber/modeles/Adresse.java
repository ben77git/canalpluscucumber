package fr.canalplus.cucumber.modeles;

public class Adresse {

	private String active, pays, canal, condition;

	public Adresse(String active, String pays, String canal, String condition) {
		super();
		this.active = active;
		this.pays = pays;
		this.canal = canal;
		this.condition = condition;
	}
	
	public Adresse(String active, String pays) {
		this(active, pays, null, null);
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

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
