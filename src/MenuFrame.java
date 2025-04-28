import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame{
    public final Color EXIT = new Color(0xB6174B);
    private final Color SINGLEPLAYER = new Color(48, 107, 52);
    private final Color MULTIPLAYER = new Color(236, 159, 5);
    public final Color BACKGROUND = new Color(244, 253, 217);
    private final Color TITLE = new Color(0, 99, 167);
    private final Color TITLE_SHADOW = new Color(0, 50, 90);

    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private JButton exitButton;

    public MenuFrame(){
        setTitle("Spanzuratoarea");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        titlePanel.setPreferredSize(new Dimension(800, 140));
        titlePanel.setBackground(BACKGROUND);

        JPanel titleButtonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(TITLE_SHADOW);
                g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 30, 30);

                g2.setColor(TITLE);
                g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 30, 30);

                g2.dispose();
            }
        };
        titleButtonPanel.setPreferredSize(new Dimension(600, 120));
        titleButtonPanel.setOpaque(false);
        titleButtonPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Spanzuratoarea");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titleButtonPanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(titleButtonPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        singlePlayerButton = ButtonCreate.createRoundButton("Singleplayer", SINGLEPLAYER);
        multiPlayerButton = ButtonCreate.createRoundButton("Multiplayer", MULTIPLAYER);
        exitButton = ButtonCreate.createRoundButton("Exit", EXIT);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND );
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(singlePlayerButton, gbc);
        gbc.gridy = 1;
        mainPanel.add(multiPlayerButton, gbc);
        gbc.gridy = 2;
        mainPanel.add(exitButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        setupButtonListeners();

        setVisible(true);
    }


    public void setupButtonListeners(){
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SinglePlayer singlePlayer = new SinglePlayer();
                singlePlayer.setVisible(true);
            }
        });
        multiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Multiplayer multiplayer = new Multiplayer();
                multiplayer.setVisible(true);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuFrame();
        });
    }
}