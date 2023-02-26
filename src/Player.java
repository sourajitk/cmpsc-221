import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private ArrayList<Tile> letters;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    //Getters for name, letters, and score
    public String getName() {
        return name;
    }

    public ArrayList<Tile> getTiles() {
        return letters;
    }

    public int getScore() {
        return score;
    }

    //Methods for adding, removing, and getting amount of letters from the player
    public void addLetter(Tile letter) {
        letters.add(letter);
    }

    public void removeLetter(Tile letter) {
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
