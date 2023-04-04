import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class LetterBag {
    private ArrayList<Tile> bag;

    public LetterBag() {
        this.bag = new ArrayList<>();
    }

    //Method to create Tile objects and add them to ArrayList ''bag''
    public void createTiles() {
        for (int i=1;i<=12;i++) {
            bag.add(new Tile('E', 1));
        }
        for (int i=1;i<=9;i++) {
            bag.add(new Tile('A', 1));
            bag.add(new Tile('I', 1));
        }
        for (int i=1;i<=8;i++) {
            bag.add(new Tile('O', 1));
        }
        for (int i=1;i<=6;i++) {
            bag.add(new Tile('N', 1));
            bag.add(new Tile('R', 1));
            bag.add(new Tile('T', 1));
        }
        for (int i=1;i<=4;i++) {
            bag.add(new Tile('D', 2));
            bag.add(new Tile('L', 1));
            bag.add(new Tile('S', 1));
            bag.add(new Tile('U', 1));
        }
        for (int i=1;i<=3;i++) {
            bag.add(new Tile('G', 2));
        }
        for (int i=1;i<=2;i++) {
            bag.add(new Tile('B', 3));
            bag.add(new Tile('C', 3));
            bag.add(new Tile('F', 4));
            bag.add(new Tile('H', 4));
            bag.add(new Tile('P', 3));
            bag.add(new Tile('V', 4));
            bag.add(new Tile('W', 4));
            bag.add(new Tile('Y', 4));
        }
        bag.add(new Tile('J', 8));
        bag.add(new Tile('K', 5));
        bag.add(new Tile('Q', 10));
        bag.add(new Tile('X', 8));
        bag.add(new Tile('Z', 10));
    }

    //Method to shuffle letter bag
    public void shuffleBag() {
        Collections.shuffle(bag);
    }

    public void addTile(Tile tile) {
        bag.add(tile);
    }

    //Method to return amount of letters left in bag
    public int getBagCount() {
        return bag.size();
    }

    //Method to draw a tile from bag, and reshuffle after draw
    public Tile drawTile() {
        if (bag.isEmpty()) {
            return null;
        }
        Tile tile = bag.get(0);
        bag.remove(0);
        shuffleBag();
        return tile;
    }

}
