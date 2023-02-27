import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordsAPIClient {

    /* TODO: Setup a test key */
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_URL = "https://wordsapiv1.p.mashape.com/words/";

    public static void main(String[] args) throws IOException {
        String word = Word.word;
        /* TODO: Implement a way to check if the word doesn't exist. */
        List<String> definitions = getDefinitions(word);
        for (String definition : definitions) {
            System.out.println(definition);
        }
    }

    public static List<String> getDefinitions(String word) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(API_URL + word + "/definitions");
        httpget.setHeader("X-Mashape-Key", API_KEY);
        httpget.setHeader("Accept", "application/json");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(result);

        JSONArray definitionsArray = json.getJSONArray("definitions");
        List<String> definitions = new ArrayList<>();
        for (int i = 0; i < definitionsArray.length(); i++) {
            JSONObject definitionObject = definitionsArray.getJSONObject(i);
            String definition = definitionObject.getString("definition");
            definitions.add(definition);
        }
        return definitions;
    }
}
