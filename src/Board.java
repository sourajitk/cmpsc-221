/**
 * Main Swing based GUI for the game.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

public class Board extends JFrame {

    /* Define all Board and other related variables */
    public static String inputText;
    public String meaning = null;
    WordsAPIClient client = new WordsAPIClient();

    public JLabel Player1JLabel;
    public JLabel Player2JLabel;
    public JLabel Player1PointValueJLabel;
    public JLabel PointsJLabel;
    public JLabel Player2PointValueJLabel;
    public JLabel TurnHeaderJLabel;
    public JLabel PlayerTurnValueJLabel;
    public JLabel LetterBagHeaderJLabel;
    public JLabel LettersRemainingValueJLabel;
    public JLabel PointJLabel;
    public JButton CenterJButton;
    public JPanel mainPanel;
    public JLabel TitleLabel;
    public JPanel boardPanel;
    public JPanel scoreboardPanel;
    public JPanel turnPanel;
    public JPanel letterBagPanel;
    public JButton endTurnJButton;
    public JLabel Player1LettersJLabel;
    public JLabel Player2LettersJLabel;
    public JButton getNewLettersButton;
    public JButton donTKnowTheButton;
    public JLabel turnTitleJLabel;
    public JLabel playerTurnJLabel;

    /* Initialize the Board constructor */
    public Board() throws UnirestException {
        //Asking for player names
        Player1JLabel.setText(JOptionPane.showInputDialog("What is Player 1's name?"));
        Player2JLabel.setText(JOptionPane.showInputDialog("What is Player 2's name?"));
        PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");

        /* Creation of letter bag and extra bag */
        LetterBag bag = new LetterBag();
        LetterBag asideBag = new LetterBag();
        bag.createTiles();
        bag.shuffleBag();
        ArrayList masterArray = new ArrayList();
        ArrayList turnArray = new ArrayList();
        ArrayList turnButtons = new ArrayList();
        ArrayList turnButtonsText = new ArrayList();

        /* Dealing tiles to players, and displaying amount of letters */
        Player playerOne = new Player(Player1JLabel.getText());
        Player playerTwo = new Player(Player2JLabel.getText());
        for (int i=1;i<=7;i++) {
            playerOne.drawLetter(bag.drawTile());
            playerTwo.drawLetter(bag.drawTile());
        }
        LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));

        /* Displaying player information (score and letters) */
        Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
        Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
        Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
        Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));

        /* Creating gameOver and playerTurn variables */
        AtomicBoolean gameOver = new AtomicBoolean(false);
        AtomicInteger playerTurn = new AtomicInteger(1);
        AtomicInteger scoreTurn = new AtomicInteger(0);

        /* actionListener for endTurn button */
        endTurnJButton.addActionListener(e -> {
            if (validateWord() == false) {
                JOptionPane.showMessageDialog(mainPanel, "Not everybody agrees on this word, turn invalid!");
                if (playerTurn.get() == 1) {
                    for (Object element : turnArray) {
                        playerOne.getTiles().add((Tile) element);
                    }
                    int counter = 0;
                    for (Object button : turnButtons) {
                        if (button instanceof JButton) {
                            ((JButton) button).setEnabled(true);
                            ((JButton) button).setText((String) turnButtonsText.get(counter));
                            ++counter;
                        }
                    }
                    turnArray.clear();
                    turnButtons.clear();
                    turnButtonsText.clear();
                }
                else {
                    for (Object element : turnArray) {
                        playerTwo.getTiles().add((Tile) element);
                    }
                    int counter = 0;
                    for (Object button : turnButtons) {
                        if (button instanceof JButton) {
                            ((JButton) button).setEnabled(true);
                            ((JButton) button).setText((String) turnButtonsText.get(counter));
                            ++counter;
                        }
                    }
                    turnArray.clear();
                    turnButtons.clear();
                    turnButtonsText.clear();
                }
                Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
            } else {
                if (isGameOver(bag, playerOne, playerTwo) == true) {
                    gameOver.set(true);
                } else {
                    if (playerTurn.get() == 1) {
                        for (int i = playerOne.getTiles().size(); i <= 6; i++) {
                            playerOne.drawLetter(bag.drawTile());
                        }
                        Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                        LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));
                        Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
                        playerTurn.set(2);
                        PlayerTurnValueJLabel.setText(Player2JLabel.getText() + "'s turn!");
                        turnArray.clear();
                        turnButtons.clear();
                        turnButtonsText.clear();
                    } else {
                        for (int i = playerTwo.getTiles().size(); i <= 6; i++) {
                            playerTwo.drawLetter(bag.drawTile());
                        }
                        Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
                        LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));
                        Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));
                        playerTurn.set(1);
                        PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");
                        turnArray.clear();
                        turnButtons.clear();
                        turnButtonsText.clear();
                    }
                }
            }
        });

        /* actionListener for getNewLetters button */
        getNewLettersButton.addActionListener(e -> {
            if (playerTurn.get() == 1) {
                newTiles(playerOne.getTiles(), bag, asideBag, playerOne);
                Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
                playerTurn.set(2);
                PlayerTurnValueJLabel.setText(Player2JLabel.getText() + "'s turn!");
            }
            else {
                newTiles(playerTwo.getTiles(), bag, asideBag, playerTwo);
                Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
                Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));
                playerTurn.set(1);
                PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");
            }
        });

        /* actionListener for donTKnowTheButton button */
        donTKnowTheButton.addActionListener(e -> {
            inputText = JOptionPane.showInputDialog("Please enter the word you want to find the meaning for");
            try {
                meaning = client.definition();
                JOptionPane.showMessageDialog(null, meaning);
            } catch (JSONException | UnirestException ex) {
                JOptionPane.showMessageDialog(null, "Sorry this word does not exist.");
            }
        });

        /* loop creating actionListeners for each button in the 15x15 grid */
        for (Component component : boardPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(e -> {
                    if (playerTurn.get() == 1) {
                        scoreTurn.set(inputTile(playerOne, button, scoreTurn.get(), masterArray, turnArray, turnButtonsText));
                        Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                        turnButtons.add(button);
                    }
                    else {
                        scoreTurn.set(inputTile(playerTwo, button, scoreTurn.get(), masterArray, turnArray, turnButtonsText));
                        Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
                        turnButtons.add(button);
                    }
                    button.setEnabled(false);
                });
            }
        }
    }

    /* Boolean constructor to determine gameState */
    public boolean isGameOver(LetterBag bag, Player playerOne, Player playerTwo) {
        if (bag.getBagCount() == 0 && playerOne.getLetterCount() == 0 && playerTwo.getLetterCount() == 0) {
            JOptionPane.showMessageDialog(null, "Game Over!");
            if (playerOne.getScore() > playerTwo.getScore()) {
                JOptionPane.showMessageDialog(null, "Player" + playerOne + "has won!");
            } else if (playerTwo.getScore() > playerOne.getScore()) {
                JOptionPane.showMessageDialog(null, "Player" + playerTwo + "has won!");
            } else {
                JOptionPane.showMessageDialog(null, "No way, it's a draw!");
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean validateWord() {
        int response = JOptionPane.showConfirmDialog(mainPanel, "Do all players agree with this word?");
        if (response == JOptionPane.YES_OPTION) {
            return true;
        } else if (response == JOptionPane.NO_OPTION) {
            return false;
        }
        return true;
    }

    /* Method for inputting a letter into the board */
    public int inputTile(Player player, JButton button, int scoreTurn, ArrayList masterArray, ArrayList turnArray, ArrayList turnButtonsText) {
        String tileInput = JOptionPane.showInputDialog("Choose one of your letters to input: " + player.getTiles());
        while (tileInput.length() > 1) {
            tileInput = JOptionPane.showInputDialog("Choose ONE of your letters to input: " + player.getTiles());
        }
        char tileCharInput = tileInput.toUpperCase().charAt(0);
        while (player.hasLetter(tileCharInput) == false) {
            tileInput = JOptionPane.showInputDialog("That letter is not in your collection, please choose one of your letters to input: " + player.getTiles());
            tileCharInput = tileInput.toUpperCase().charAt(0);
        }
        if (button.getText() == "---") {
            turnButtonsText.add("---");
            scoreTurn = player.updateScore(tileCharInput, 1);
            masterArray.add(player.getTile(tileCharInput));
            turnArray.add(player.getTile(tileCharInput));
            player.removeTile(tileCharInput);
        }
        if (button.getText() == "★") {
            turnButtonsText.add("★");
            scoreTurn = player.updateScore(tileCharInput, 1);
            masterArray.add(player.getTile(tileCharInput));
            turnArray.add(player.getTile(tileCharInput));
            player.removeTile(tileCharInput);
        }
        if (button.getText() == "2L") {
            turnButtonsText.add("2L");
            scoreTurn = player.updateScore(tileCharInput, 2);
            masterArray.add(player.getTile(tileCharInput));
            turnArray.add(player.getTile(tileCharInput));
            player.removeTile(tileCharInput);
        }
        if (button.getText() == "3L") {
            turnButtonsText.add("3L");
            scoreTurn = player.updateScore(tileCharInput, 2);
            masterArray.add(player.getTile(tileCharInput));
            turnArray.add(player.getTile(tileCharInput));
            player.removeTile(tileCharInput);
        }
        if (button.getText() == "2W") {
            turnButtonsText.add("2W");
            scoreTurn = player.updateScore(tileCharInput, 1);
            masterArray.add(player.getTile(tileCharInput));
            turnArray.add(player.getTile(tileCharInput));
            player.removeTile(tileCharInput);
        }
        button.setText(tileInput.toUpperCase(Locale.ROOT));
        return scoreTurn;
    }

    /* Method for a player getting new letters */
    public void newTiles(ArrayList playerLetters, LetterBag bag, LetterBag asideBag, Player player) {
        for (int i = 0; i <= 6; i++) {
            asideBag.addTile((Tile) playerLetters.get(i));
        }
        for (int i = 0; i <= 6; i++) {
            bag.addTile(asideBag.drawTile());
        }
        playerLetters.clear();
        bag.shuffleBag();
        for (int i = 0; i <= 6; i++) {
            player.drawLetter(bag.drawTile());
        }
    }

    /* Constructor to allow access to inputText in WordsAPIClient for processing */
    public static String getInputText() {
        return inputText;
    }

    /* Main method, creating the scrabble GUI */
    public static void main(String[] args) throws UnirestException {
        JFrame board = new JFrame("Scrabble");
        board.setContentPane(new Board().mainPanel);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.pack();
        board.setVisible(true);
    }
}
