import com.mashape.unirest.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

/*
public class WordsAPIClient {

    public static void main(String[] args) throws Exception {

        String word = Word.word;
        */
/*String apiKey = WordsAPIKey.API_KEY;*//*


        // set up the request
        HttpResponse<String> response = Unirest.get("https://wordsapiv1.p.rapidapi.com/words/" + word + "/definitions")
                */
/*.header("X-RapidAPI-Key", apiKey)*//*

                .header("Accept", "application/json")
                .asString();

        // parse the JSON response
        JSONObject json = new JSONObject(response.getBody());
        JSONArray definitions = json.getJSONArray("definitions");

        // print the first definition
        System.out.println("Definition: " + definitions.getJSONObject(0).getString("definition"));
    }
}*/
