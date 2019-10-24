package fr.canalplus.cucumber;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;

import cucumber.api.CucumberOptions;
import fr.canalplus.cucumber.modeles.Abonné;
import fr.canalplus.cucumber.modeles.Adresse;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = { "pretty" }, features = "src/test/resources/features", glue = "fr.canalplus.cucumber")
public class RunCucumberTest {

	private static final String APPLICATION_JSON = "application/json";

	private static final Logger log = LoggerFactory.getLogger(RunCucumberTest.class);

	private static WireMockServer wireMockServer;

	private static final Gson GSON = new Gson();
	
	private static final int CODE_STATUT_HTTP_OK = 200;

	@BeforeClass
	public static void initWireMockServer() throws JsonProcessingException {
		log.info("Initialisation du serveur WireMock");

		wireMockServer = new WireMockServer();
		wireMockServer.start();

		// création adresses principales
		Adresse adresseFrance = new Adresse("inactive", "France");
		Adresse adressePologne = new Adresse("active", "Pologne");

		// creation d'un abonné en FRANCE
		Abonné abonneFrance = new Abonné("123456789", adresseFrance);

		stubFor(post(urlEqualTo("/api/abonnes")).withRequestBody(equalToJson(GSON.toJson(adresseFrance)))
				.willReturn(aResponse().withStatus(CODE_STATUT_HTTP_OK).withBody(abonneFrance.getLogin())
						.withHeader("Content-Type", APPLICATION_JSON)));

		// création d'un abonné en POLOGNE
		Abonné abonnePologne = new Abonné("123456790", adressePologne);

		stubFor(post(urlEqualTo("/api/abonnes")).withRequestBody(equalToJson(GSON.toJson(adressePologne)))
				.willReturn(aResponse().withStatus(CODE_STATUT_HTTP_OK).withBody(abonnePologne.getLogin())
						.withHeader("Content-Type", APPLICATION_JSON)));

		// modification d'adresses
		Adresse adresseModifSansDateEffet = new Adresse(null, null, "FACE", "sans date d’effet");
		Adresse adresseModifAvecDateEffet = new Adresse(null, null, "EC", "avec date d’effet");

		// modification de l'adresse de l'abonné pour une condition "sans date
		// d'effet"
		abonneFrance.modifieAdresse("FACE", "sans date d’effet");

		// modification d'adresse
		stubFor(put(urlEqualTo("/api/abonnes/123456789/adresse"))
				.withRequestBody(equalToJson(GSON.toJson(adresseModifSansDateEffet)))
				.willReturn(aResponse().withStatus(CODE_STATUT_HTTP_OK).withBody(GSON.toJson(abonneFrance))
						.withHeader("Content-Type", APPLICATION_JSON)));

		// modification de l'adresse de l'abonné pour une condition "sans date
		// d'effet"
		abonnePologne.modifieAdresse("EC", "avec date d’effet");

		// modification d'adresse
		stubFor(put(urlEqualTo("/api/abonnes/123456790/adresse"))
				.withRequestBody(equalToJson(GSON.toJson(adresseModifAvecDateEffet)))
				.willReturn(aResponse().withStatus(CODE_STATUT_HTTP_OK).withBody(GSON.toJson(abonnePologne))
						.withHeader("Content-Type", APPLICATION_JSON)));

	}

	@AfterClass
	public static void stopWireMockServer() {
		wireMockServer.stop();
		log.info("Arret du serveur WireMock ");
	}

}
