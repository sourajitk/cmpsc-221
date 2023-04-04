import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Board extends JFrame {

    public static Player playerOne;
    public static Player playerTwo;

    public JLabel getPlayer1JLabel() {
        return Player1JLabel;
    }
    public JLabel getPlayer2JLabel() {
        return Player2JLabel;
    }
    public JLabel Player1JLabel;
    public JLabel Player2JLabel;
    public JLabel Player1PointValueJLabel;
    public JLabel PointsJLabel;
    public JLabel Player2PointValueJLabel;
    public JLabel TurnHeaderJLabel;
    public JLabel PlayerTurnValueJLabel;
    public JLabel LetterBagHeaderJLabel;

    public JLabel getLettersRemainingValueJLabel() {
        return LettersRemainingValueJLabel;
    }
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
    public JLabel turnTitleJLabel;
    public JLabel playerTurnJLabel;

    public Board() throws IOException {

        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(new File("res/background.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override
                public void paintComponent(Graphics g) {
                    g.drawImage(backgroundImage, 0, 0, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLocationRelativeTo(mainPanel);
        setVisible(true);

        //Asking for player names
        Player1JLabel.setText(JOptionPane.showInputDialog("What is Player 1's name?"));
        Player2JLabel.setText(JOptionPane.showInputDialog("What is Player 2's name?"));
        PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");

        //Creation of letter bag and extra bag
        LetterBag bag = new LetterBag();
        LetterBag asideBag = new LetterBag();
        bag.createTiles();
        bag.shuffleBag();

        //Dealing tiles to players, and displaying amount of letters
        Player playerOne = new Player(Player1JLabel.getText());
        Player playerTwo = new Player(Player2JLabel.getText());
        for (int i=1;i<=7;i++) {
            playerOne.drawLetter(bag.drawTile());
            playerTwo.drawLetter(bag.drawTile());
        }
        LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));

        //Displaying player information (score and letters)
        Player1LettersJLabel.setText(String.valueOf(playerOne.getTiles()));
        Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
        Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
        Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));

        //Creating gameOver and playerTurn variables
        AtomicBoolean gameOver = new AtomicBoolean(false);
        AtomicInteger playerTurn = new AtomicInteger(1);

        //actionListener for endTurn button
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
                    Player2LettersJLabel.setText(String.valueOf(playerTwo.getTiles()));
                    LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));
                    Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));
                    playerTurn.set(1);
                    PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");
                }
            }
        });

        //actionListener for getNewLetters button
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

        //loop creating actionListeners for each button in the 15x15 grid
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

    // TODO method for determining if the game is over
    public boolean isGameOver() {
        boolean gameOver = false;
        if (gameOver == true) {
            int player1Score = Board.playerOne.getScore();
            int player2Score = Board.playerTwo.getScore();
            if (player1Score > player2Score) {
                JOptionPane.showMessageDialog(null, Player1JLabel.getText() + " wins with a score of " + player1Score);
            } else if (player2Score > player1Score) {
                JOptionPane.showMessageDialog(null, Player2JLabel.getText() + " wins with a score of " + player2Score);
            } else {
                JOptionPane.showMessageDialog(null, "It's a tie!");
            }
        }
        return gameOver;
    }

    //Method for inputting a letter into the board
    public void inputTile(Player player, JButton button) {
        String tileInput = JOptionPane.showInputDialog("Choose one of your letters to input: " + player.getTiles());
        try {
            char tileCharInput = tileInput.toUpperCase().charAt(0);
            while (!player.hasLetter(tileCharInput))
                tileInput = JOptionPane.showInputDialog("That letter is not in your collection, please choose one of your letters to input: " + player.getTiles());
            tileCharInput = tileInput.toUpperCase().charAt(0);
            if (button.getText() == "---") {
                player.removeTile(tileCharInput, 1);
            }
            if (button.getText() == "★") {
                player.removeTile(tileCharInput, 1);
            }
            if (button.getText() == "2L") {
                player.removeTile(tileCharInput, 2);
            }
            if (button.getText() == "3L") {
                player.removeTile(tileCharInput, 3);
            }
            if (button.getText() == "2W") {
                player.removeTile(tileCharInput, 3);
            }
            button.setText(tileInput.toUpperCase(Locale.ROOT));
        } catch (NullPointerException e) {
            System.out.println("No input! Unable to uppercase");
        }
    }

    //Method for a player getting new letters
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

    //Main method, creating the scrabble GUI
    public static void main(String[] args) throws IOException {
        JFrame board = new JFrame("Scrabble");
        board.setContentPane(new Board().mainPanel);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.pack();
        board.setVisible(true);
    }

}
