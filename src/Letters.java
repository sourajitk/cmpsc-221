import java.util.ArrayList;

public abstract class Letters {
     private String letter;
     private ArrayList<String> alphabets;

     public Letters(String letter, ArrayList<String> alphabets) {
          this.letter = letter;
          this.alphabets = alphabets;
     }

     public static void main (String[] args) {
          System.out.println("Analyzing your word");
     }
}
