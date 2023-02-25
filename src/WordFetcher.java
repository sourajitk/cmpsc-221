import java.util.ArrayList;

public abstract class WordFetcher extends Letters {
    String letter;
    static String alphabets;
    public WordFetcher(String letter, ArrayList<String> alphabets) {
        super(letter, alphabets);
    }
}
