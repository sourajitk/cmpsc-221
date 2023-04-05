/**
 * Logic for word score calculations.
 */

public class CalculationsForWords {
    /* Word calculation related variables */

    int score;
    int wordValue;
    int letterValue;
    int penalty;
     public CalculationsForWords (int score, int wordValue, int letterValue, int penalty) {
         this.score = score;
         this.wordValue = wordValue;
         this.letterValue = letterValue;
         this.penalty = penalty;
     }
}
