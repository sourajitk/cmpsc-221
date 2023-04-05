import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordsAPIClient {

    public String definition() throws UnirestException {
        String word = Board.getInputText();
        String apiKey = WordsAPIKey.API_KEY;

        // set up the request
        HttpResponse<String> response = Unirest.get("https://wordsapiv1.p.rapidapi.com/words/" + word + "/definitions")
                .header("X-RapidAPI-Key", apiKey)
                .header("Accept", "application/json")
                .asString();

        // parse the JSON response
        JSONObject json = new JSONObject(response.getBody());
        JSONArray definitions = json.getJSONArray("definitions");
        String meaning = "Definition: " + definitions.getJSONObject(0).getString("definition");
        return meaning;
    }
}
