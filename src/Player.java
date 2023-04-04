import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Tile> tiles;
    private int score;

    public Player(String name) {
        this.name = name;
        this.tiles = new ArrayList<>();
        this.score = 0;
    }

    //Getters for name, letters, and score
    public String getName() {
        return name;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Tile getTile(char letter){
        for (Tile tile : tiles) {
            if (tile.letter == letter) {
                return tile;
            }
        }
        return null;
    }

    public boolean hasLetter(char letter) {
        for (Tile tile : tiles) {
            if (tile.letter == letter) {
                return true;
            }
        }
        return false;
    }

    public int updateScore(char letter, int multiplier) {
        for (Tile tile : tiles) {
            if (tile.letter == letter) {
                score += tile.value*multiplier;
                return score;
            }
        }
        return multiplier;
    }

    public boolean removeTile(char letter) {
        for (Tile tile : tiles) {
            if (tile.letter == letter) {
                tiles.remove(tile);
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    //Methods for adding, removing, and getting amount of letters from the player
    public void drawLetter(Tile letter) {
        tiles.add(letter);
    }

    public void removeLetter(Tile letter) {
        tiles.remove(letter);
    }

    public int getLetterCount() {
        return tiles.size();
    }

    //Method for adding points to player's score
    public void addPoint(int points) {
        score += points;
    }

}
