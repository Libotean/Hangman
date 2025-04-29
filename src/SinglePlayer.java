import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SinglePlayer extends JFrame {

    private Game game;
    private JPanel mainPanel;
    private JPanel wordPanel;
    private JPanel keyboardPanel;
    private JPanel hangmanPanel;
    private JLabel attemptsLabel;
    private JLabel messageLabel;
    private JButton GoBackButton;

    public final Color BACKGROUND = new Color(244, 253, 217);
    private final Color GO_BACK_COLOR = new Color(236, 159, 5);
    private Color KEYBOARD = new Color(100, 149, 237);
    private Color KEYBOARD_SHADOW = new Color(70, 104, 166);

    public SinglePlayer() {
        setTitle("Spanzuratoarea - Singleplayer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        try{
            game = new Game();
            setupUI();
        }catch (IOException e){
            JOptionPane.showMessageDialog(this,
                    "Error loading game: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        add(mainPanel);

    }

    private void setupUI(){
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        hangmanPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHangman(g);
            }
        };
        hangmanPanel.setPreferredSize(new Dimension(300, 250));
        hangmanPanel.setBackground(BACKGROUND);

        wordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        wordPanel.setBackground(BACKGROUND);
        updateWordDisplay();

        attemptsLabel = new JLabel("Incercari ramase: " + game.getAttempts());
        attemptsLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        messageLabel = new JLabel("Succes!");
        messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        statusPanel.add(attemptsLabel);
        statusPanel.add(messageLabel);
        statusPanel.setBackground(BACKGROUND);

        keyboardPanel = new JPanel(new GridLayout(3, 9, 5, 5));
        keyboardPanel.setBackground(BACKGROUND);
        keyboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createKeyboard();

        GoBackButton = ButtonCreate.createRoundButton("Go Back", GO_BACK_COLOR);
        GoBackButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        GoBackButton.setPreferredSize(new Dimension(120, 50));

        GoBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuFrame().setVisible(true);
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND);
        topPanel.add(hangmanPanel, BorderLayout.CENTER);
        topPanel.add(statusPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BACKGROUND);
        bottomPanel.add(wordPanel, BorderLayout.NORTH);
        bottomPanel.add(keyboardPanel, BorderLayout.CENTER);
        bottomPanel.add(GoBackButton, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createKeyboard(){
        String[] rows = {
            "QWERTYUIOP",
            "ASDFGHJKL",
            "ZXCVBNM"
        };

        for(String row : rows){
            for(char c : row.toCharArray()){
                JButton letterButton = createLetterButton(String.valueOf(c));
                letterButton.setPreferredSize(new Dimension(40, 40));
                letterButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));

                letterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleLetterGuess(c);
                        letterButton.setEnabled(false);
                    }
                });
                keyboardPanel.add(letterButton);
            }
        }
    }
    private void handleLetterGuess(char c){
        boolean correct = game.guessLetter(c);
        updateWordDisplay();
        if(correct){
            messageLabel.setText("Corect!");
        } else {
            messageLabel.setText("Incorect!");
        }

        attemptsLabel.setText("Incercari ramase: " + game.getAttempts());
        repaint();

        if(game.isGameWon()){
            gameOver(true);
        }else if(game.isGameOver()){
            gameOver(false);
        }
    }

    private void gameOver(boolean won){
        for(Component c : keyboardPanel.getComponents()){
            c.setEnabled(false);
        }

        if(won){
            messageLabel.setText("Felicitari! Ai castigat!");
        } else{
            messageLabel.setText("Ai pierdut! Cuvantul era: " + game.getWordToGuess());
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                won? "Ai castigat! Vrei sa continui?" : "Ai pierdut! Vrei sa continui?", "Gata",
                JOptionPane.YES_NO_OPTION
        );

        if(option == JOptionPane.YES_OPTION){
            try{
                game = new Game();
                resetUI();
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

    private void resetUI(){
        keyboardPanel.removeAll();
        createKeyboard();

        for(Component c : keyboardPanel.getComponents()){
            c.setEnabled(true);
        }

        updateWordDisplay();
        attemptsLabel.setText("Incercari ramase: " + game.getAttempts());
        messageLabel.setText("Succes!");

        keyboardPanel.revalidate();
        keyboardPanel.repaint();
        repaint();
    }

    private JButton createLetterButton(String letter) {
        Color buttonColor = KEYBOARD;
        Color shadowColor = KEYBOARD_SHADOW;

        JButton button = new JButton(letter) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isEnabled()) {
                    g2.setColor(shadowColor);
                    g2.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 10, 10);

                    g2.setColor(buttonColor);
                    g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 10, 10);
                } else {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 10, 10);
                }

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };

        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

    private void updateWordDisplay(){
        wordPanel.removeAll();
        String progress = game.getCurrentProgress();
        for(char c : progress.toCharArray()){
            JLabel charLabel = new JLabel(String.valueOf(c));
            charLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
            charLabel.setPreferredSize(new Dimension(35, 40));
            wordPanel.add(charLabel);
        }
        wordPanel.revalidate();
        wordPanel.repaint();
    }

    private void drawHangman(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));

        // Base
        g2.drawLine(50, 200, 150, 200);

        // Pole
        g2.drawLine(100, 200, 100, 50);

        // Top
        g2.drawLine(100, 50, 200, 50);

        // Rope
        g2.drawLine(200, 50, 200, 70);

        int attempts = game.getAttempts();

        if(attempts <= 6){
            // Head
            g2.drawOval(185, 70, 30, 30);
        }
        if(attempts <= 5){
            // Body
            g2.drawLine(200, 100, 200, 150);
        }
        if(attempts <= 4){
            // Left Arm
            g2.drawLine(200, 120, 180, 130);
        }
        if(attempts <= 3){
            // Right Arm
            g2.drawLine(200, 120, 220, 130);
        }
        if(attempts <= 2){
            // Left Leg
            g2.drawLine(200, 150, 180, 180);
        }
        if(attempts <= 1){
            // Right Leg
            g2.drawLine(200, 150, 220, 180);
        }
        if(attempts <= 0){
            // Dead face (x eyes)
            g2.drawLine(190, 80, 195, 85);
            g2.drawLine(195, 80, 190, 85);
            g2.drawLine(205, 80, 210, 85);
            g2.drawLine(210, 80, 205, 85);

            g2.drawArc(190, 90, 20, 10, 0, 180);
        }
    }

}
