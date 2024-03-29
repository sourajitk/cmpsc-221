/**
 * Tile management class.
 */

public class Tile {

    char letter;
    int value;

    @Override
    public String toString() {
        return letter +
                "," + value;
    }

    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
