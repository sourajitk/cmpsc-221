import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Letter> letters;
    private int score;

    public Player(String name) {
        this.name = name;
        this.letters = new ArrayList<>();
        this.score = 0;
    }

    //Getters for name, letters, and score
    public String getName() {
        return name;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public int getScore() {
        return score;
    }

    //Methods for adding, removing, and getting amount letters from the player
    public void addLetter(Letter letter) {
        letters.add(letter);
    }

    public void removeLetter(Letter letter) {
        letters.remove(letter);
    }

    public int getLetterCount() {
        return letters.size();
    }

    //Method for adding points to player's score
    public void addPoint(int points) {
        score += points;
    }

}
