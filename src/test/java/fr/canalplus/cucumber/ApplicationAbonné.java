package fr.canalplus.cucumber;

import java.io.IOException;

import fr.canalplus.cucumber.modeles.Abonné;
import fr.canalplus.cucumber.modeles.Adresse;
import gherkin.deps.com.google.gson.Gson;
import net.thucydides.core.annotations.Step;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApplicationAbonné {

	private static final String URL_ABONNES = "http://localhost:8080/api/abonnes";

	private static final Gson GSON = new Gson();

	final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private OkHttpClient client = new OkHttpClient();
	private Response response;

	@Step("On crée un abonné d'adresse principale {0} en {1}")
	public String créerNouveauAbonné(String active, String pays) throws IOException {

		// adresse principale de l'abonné
		Adresse adresse = new Adresse(active, pays);

		RequestBody body = RequestBody.create(JSON, GSON.toJson(adresse));
		Request request = new Request.Builder().url(URL_ABONNES).post(body).build();
		response = client.newCall(request).execute();
		return response.body().string();
	}

	@Step("Le conseiller connecté au canal {1} modifie l'adresse de l'abonné d'identifiant {0} de condition {2}")
	public Abonné modifieAdresse(String idAbonné, String canal, String condition) throws IOException {

		String creermodifierAdresse = URL_ABONNES + "/" + idAbonné + "/adresse";

		Adresse adresseModif = new Adresse(null, null, canal, condition);

		RequestBody body = RequestBody.create(JSON, GSON.toJson(adresseModif));
		Request request = new Request.Builder().url(creermodifierAdresse).put(body).build();
		response = client.newCall(request).execute();
		return GSON.fromJson(response.body().string(), Abonné.class);
	}

}
