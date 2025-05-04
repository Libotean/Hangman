import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame{
    private String wordToGuess;
    private int attempts;
    private StringBuilder currentProgress;

    private SinglePlayer singlePlayer;
    private Multiplayer multiplayer;

    private  JPanel mainPanel;
    private  JPanel wordPanel;
    private  JPanel keyboardPanel;
    private  JPanel hangmanPanel;
    private  JLabel attemptsLabel;
    private  JLabel messageLabel;
    private  JButton GoBackButton;

    public static final Color BACKGROUND = new Color(244, 253, 217);
    private final Color GO_BACK_COLOR = new Color(236, 159, 5);
    private Color KEYBOARD = new Color(100, 149, 237);

    public Game(SinglePlayer singlePlayer) throws IOException {
        this.singlePlayer = singlePlayer;
        this.wordToGuess = chooseRandomWord();
        this.attempts = 7;
        this.currentProgress = new StringBuilder("_".repeat(wordToGuess.length()));
    }


    public String chooseRandomWord() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/words.txt"));
        List<String> words = new ArrayList<>();
        String line;
        while((line = reader.readLine())!= null){
            words.add(line.trim());
        }
        reader.close();
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    public boolean guessLetter(char letter) {
        boolean found = false;
        letter = Character.toLowerCase(letter);
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                currentProgress.setCharAt(i, letter);
                found = true;
            }
        }
        if(!found) {
            attempts--;
        }
        return found;
    }

    public boolean isGameWon() {
        return wordToGuess.equals(currentProgress.toString());
    }

    public boolean isGameOver() {
        return attempts <= 0;
    }

    public String getCurrentProgress() {
        return currentProgress.toString();
    }

    public int getAttempts() {
        return attempts;
    }

    public String getWordToGuess(){
        return wordToGuess;
    }

    public Game(String word){
        this.wordToGuess = word.toLowerCase();
        this.attempts = 7;
        this.currentProgress = new StringBuilder("_".repeat(wordToGuess.length()));
    }

    public void setupUI(){
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

        attemptsLabel = new JLabel("Incercari ramase: " + getAttempts());
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
                if(singlePlayer != null){
                    singlePlayer.dispose();
                } else if(multiplayer != null){
                    multiplayer.dispose();
                }
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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getKeyboardPanel() {
        return keyboardPanel;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public void createKeyboard(){
        String[] rows = {
                "QWERTYUIOP",
                "ASDFGHJKL",
                "ZXCVBNM"
        };

        for(String row : rows){
            for(char c : row.toCharArray()){
                JButton letterButton = ButtonCreate.createRoundButton(String.valueOf(c), KEYBOARD);
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

    public void handleLetterGuess(char c){
        boolean correct = guessLetter(c);
        updateWordDisplay();
        if(correct){
            messageLabel.setText("Corect!");
        } else {
            messageLabel.setText("Incorect!");
        }

        attemptsLabel.setText("Incercari ramase: " + getAttempts());
        hangmanPanel.repaint();
        if(singlePlayer != null){
            singlePlayer.repaint();
        } else if(multiplayer != null){
            multiplayer.repaint();
        }

        if(isGameWon()){
            singlePlayer.gameOver(true);
        }else if(isGameOver()){
            singlePlayer.gameOver(false);
        }
    }

    public void resetUI(){
        keyboardPanel.removeAll();
        createKeyboard();

        for(Component c : keyboardPanel.getComponents()){
            c.setEnabled(true);
        }

        updateWordDisplay();
        attemptsLabel.setText("Incercari ramase: " + getAttempts());
        messageLabel.setText("Succes!");

        keyboardPanel.revalidate();
        keyboardPanel.repaint();
        repaint();
    }

    public void updateWordDisplay(){
        wordPanel.removeAll();
        String progress = getCurrentProgress();
        for(char c : progress.toCharArray()){
            JLabel charLabel = new JLabel(String.valueOf(c));
            charLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
            charLabel.setPreferredSize(new Dimension(35, 40));
            wordPanel.add(charLabel);
        }
        wordPanel.revalidate();
        wordPanel.repaint();
    }

    public void drawHangman(Graphics g){
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

        int attempts = getAttempts();

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
