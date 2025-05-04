import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SinglePlayer extends JFrame {

    private Game game;

    public SinglePlayer() {
        setTitle("Spanzuratoarea - Singleplayer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        try{
            game = new Game(this);
            game.setupUI();
            add(game.getMainPanel());
        }catch (IOException e){
            JOptionPane.showMessageDialog(this,
                    "Error loading game: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

    }

    public void gameOver(boolean won){
        for(Component c : game.getKeyboardPanel().getComponents()){
            c.setEnabled(false);
        }

        if(won){
            game.getMessageLabel().setText("Felicitari! Ai castigat!");
        } else{
            game.getMessageLabel().setText("Ai pierdut! Cuvantul era: " + game.getWordToGuess());
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                won? "Ai castigat! Vrei sa continui?" : "Ai pierdut! Vrei sa continui?", "Gata",
                JOptionPane.YES_NO_OPTION
        );

        if(option == JOptionPane.YES_OPTION){
            try{
                getContentPane().removeAll();

                game = new Game(this);

                game.setupUI();

                add(game.getMainPanel());

                revalidate();
                repaint();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error loading game: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else{
            dispose();
            new MenuFrame().setVisible(true);
        }
    }


}
