import javax.swing.*;

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
    private JLabel P1L1JLabel;
    private JLabel P1L2JLabel;
    private JLabel P1L3JLabel;
    private JLabel P1L4JLabel;
    private JLabel P1L5JLabel;
    private JLabel P1L6JLabel;
    private JLabel P1L7JLabel;
    private JLabel P2L1JLabel;
    private JLabel P2L2JLabel;
    private JLabel P2L3JLabel;
    private JLabel P2L4JLabel;
    private JLabel P2L5JLabel;
    private JLabel P2L6JLabel;
    private JLabel P2L7JLabel;
    private JPanel mainPanel;
    private JLabel TitleLabel;
    private JPanel boardPanel;
    private JPanel scoreboardPanel;
    private JPanel turnPanel;
    private JPanel letterBagPanel;
    private JLabel turnTitleJLabel;
    private JLabel playerTurnJLabel;



    public Board() {
        Player1JLabel.setText(JOptionPane.showInputDialog("What is Player 1's name?"));
        Player2JLabel.setText(JOptionPane.showInputDialog("What is Player 2's name?"));
        PlayerTurnValueJLabel.setText(Player1JLabel.getText() + "'s turn!");

        LetterBag bag = new LetterBag();
        bag.shuffleBag();
        LettersRemainingValueJLabel.setText(String.valueOf(bag.getBagCount()));

        Player playerOne = new Player(Player1JLabel.getText());
        Player playerTwo = new Player(Player2JLabel.getText());
        for (int i=1;i<=7;i++) {
            playerOne.drawLetter(bag.drawTile());
            playerTwo.drawLetter(bag.drawTile());
        }
        P1L1JLabel.setText(String.valueOf(playerOne.getTiles().get(0)));
        P1L2JLabel.setText(String.valueOf(playerOne.getTiles().get(1)));
        P1L3JLabel.setText(String.valueOf(playerOne.getTiles().get(2)));
        P1L4JLabel.setText(String.valueOf(playerOne.getTiles().get(3)));
        P1L5JLabel.setText(String.valueOf(playerOne.getTiles().get(4)));
        P1L6JLabel.setText(String.valueOf(playerOne.getTiles().get(5)));
        P1L7JLabel.setText(String.valueOf(playerOne.getTiles().get(6)));

        P2L1JLabel.setText(String.valueOf(playerOne.getTiles().get(0)));
        P2L2JLabel.setText(String.valueOf(playerOne.getTiles().get(1)));
        P2L3JLabel.setText(String.valueOf(playerOne.getTiles().get(2)));
        P2L4JLabel.setText(String.valueOf(playerOne.getTiles().get(3)));
        P2L5JLabel.setText(String.valueOf(playerOne.getTiles().get(4)));
        P2L6JLabel.setText(String.valueOf(playerOne.getTiles().get(5)));
        P2L7JLabel.setText(String.valueOf(playerOne.getTiles().get(6)));

        Player1PointValueJLabel.setText(String.valueOf(playerOne.getScore()));
        Player2PointValueJLabel.setText(String.valueOf(playerTwo.getScore()));


    }

    public static void main(String[] args){
        JFrame board = new JFrame("Scrabble");
        board.setContentPane(new Board().mainPanel);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.pack();
        board.setVisible(true);
    }

}
