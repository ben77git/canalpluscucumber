package fr.canalplus.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.assertj.core.api.Condition;

import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;
import fr.canalplus.cucumber.modeles.Abonné;
import net.thucydides.core.annotations.Steps;

public class Stepdefs {

	@Steps
	ApplicationAbonné application;

	private String idAbonné;
	private String canal;
	private String condition;

	private Abonné abonnéModifié;

	@Etantdonné("^un abonné avec une adresse principale (.+) en (.+)$")
	public void un_abonné_avec_une_adresse_principale(String active, String pays) throws IOException {
		idAbonné = application.créerNouveauAbonné(active, pays);
		assertThat(idAbonné).isIn("123456789", "123456790");
	}

	@Lorsque("^le conseiller connecté à (.+) modifie l'adresse de l'abonné (.+)$")
	public void le_conseiller_connecté_modifie_l_abonné(String canal, String condition) throws IOException {
		this.canal = canal;
		this.condition = condition;
		abonnéModifié = application.modifieAdresse(idAbonné, canal, condition);
	}

	@Alors("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
	public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() {
		assertThat(abonnéModifié.getContrats()).matches(contrats -> contrats.size() > 0)
				.allMatch(contrat -> contrat.getAdresse().getCanal().equals(canal)
						&& contrat.getAdresse().getCondition().equals(condition));

	}

	@Alors("^un mouvement de modification d'adresse est créé$")
	public void un_mouvement_de_modification_d_adresse_est_créé() {
		assertThat(abonnéModifié.getMouvements())
				.matches(mouvements -> mouvements.size() > 1).areAtLeastOne(new Condition<>(
						mouvement -> "modification".equals(mouvement.getType())
								&& canal.equals(mouvement.getAdresse().getCanal())
								&& condition.equals(mouvement.getAdresse().getCondition()),
						"un mouvement modification"));
	}
}
