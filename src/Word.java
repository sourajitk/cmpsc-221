import java.util.Scanner;

public abstract class Word {

     public static String word;

     public Word(String word) {
          Word.word = word;
     }

     public static void main (String[] args) {
          System.out.println("Please enter your word");
          Scanner wordInput = new Scanner(System.in);
          String word = wordInput.nextLine();
          System.out.println("Analyzing your word");
     }
}
