import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Board extends JFrame {

    public JLabel getPlayer1JLabel() {
        return Player1JLabel;
    }
    public JLabel getPlayer2JLabel() {
        return Player2JLabel;
    }
    private JLabel Player1JLabel;
    private JLabel Player2JLabel;
    private JLabel Player1PointValueJLabel;
    private JLabel PointsJLabel;
    private JLabel Player2PointValueJLabel;
    private JLabel TurnHeaderJLabel;
    private JLabel PlayerTurnValueJLabel;
    private JLabel LetterBagHeaderJLabel;

    private JLabel getLettersRemainingValueJLabel() {
        return LettersRemainingValueJLabel;
    }
    private JLabel LettersRemainingValueJLabel;
    private JLabel PointJLabel;
    private JButton CenterJButton;
    private JPanel mainPanel;
    private JLabel TitleLabel;
    private JPanel boardPanel;
    private JPanel scoreboardPanel;
    private JPanel turnPanel;
    private JPanel letterBagPanel;
    private JButton endTurnJButton;
    private JLabel Player1LettersJLabel;
    private JLabel Player2LettersJLabel;
    private JButton getNewLettersButton;
    private JLabel turnTitleJLabel;
    private JLabel playerTurnJLabel;



    public Board() {
        //Asking for player names
        Player1JLabel.setText(JOptionPane.showInputDialog("What is Player 1's name?"));
        Player2JLabel.setText(JOptionPane.showInputDialog("What is Player 2's name?"));
        PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");

        LetterBag bag = new LetterBag();
        LetterBag asideBag = new LetterBag();
        bag.createTiles();
        bag.shuffleBag();

        Player playerOne = new Player(Player1JLabel.getText());
        Player playerTwo = new Player(Player2JLabel.getText());
        for (int i=1;i<=7;i++) {
            playerOne.drawLetter(bag.drawTile());
            playerTwo.drawLetter(bag.drawTile());
        }
        LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));

        Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
        Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));

        Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
        Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));

        AtomicBoolean gameOver = new AtomicBoolean(false);
        AtomicInteger playerTurn = new AtomicInteger(1);

        endTurnJButton.addActionListener(e -> {
            if (isGameOver() == true) {
                gameOver.set(true);
            }
            else {
                if (playerTurn.get() == 1) {
                    for (int i = playerOne.getTiles().size(); i <= 6; i++ ) {
                        playerOne.drawLetter(bag.drawTile());
                    }
                    Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                    LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));
                    Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
                    playerTurn.set(2);
                    PlayerTurnValueJLabel.setText(Player2JLabel.getText() + "'s turn!");
                }
                else {
                    for (int i = playerTwo.getTiles().size(); i <= 6; i++ ) {
                        playerTwo.drawLetter(bag.drawTile());
                    }
                    Player2LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                    LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));
                    Player2PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
                    playerTurn.set(1);
                    PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");
                }
            }
        });

        getNewLettersButton.addActionListener(e -> {
            if (playerTurn.get() == 1) {
                newTiles(playerOne.getTiles(), bag, asideBag, playerOne);
                Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
            }
            else {
                newTiles(playerTwo.getTiles(), bag, asideBag, playerTwo);
                Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
            }
        });

        for (Component component : boardPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(e -> {
                        if (playerTurn.get() == 1) {
                            inputTile(playerOne, button);
                            Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
                        }
                        else {
                            inputTile(playerTwo, button);
                            Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
                        }
                        button.setEnabled(false);
                });
            }
        }
    }


    public boolean isGameOver() {
        return false;
    }

    public void inputTile(Player player, JButton button) {
        String tileInput = JOptionPane.showInputDialog("Choose one of your letters to input: " + player.getTiles());
        char tileCharInput = tileInput.toUpperCase().charAt(0);
        while (player.hasLetter(tileCharInput) == false) {
            tileInput = JOptionPane.showInputDialog("That letter is not in your collection, please choose one of your letters to input: " + player.getTiles());
            tileCharInput = tileInput.toUpperCase().charAt(0);
        }
        button.setText(tileInput.toUpperCase(Locale.ROOT));
        player.removeTile(tileCharInput);
    }

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
    public static void main(String[] args){
        JFrame board = new JFrame("Scrabble");
        board.setContentPane(new Board().mainPanel);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.pack();
        board.setVisible(true);
    }

}
