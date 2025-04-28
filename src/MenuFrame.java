import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame{
    private Color EXIT = new Color(0xB6174B);
    private Color SINGLEPLAYER = new Color(48, 107, 52);
    private Color MULTIPLAYER = new Color(236, 159, 5);
    private Color BACKGROUND = new Color(244, 253, 217);
    private Color TITLE = new Color(0, 99, 167);
    private Color TITLE_SHADOW = new Color(0, 50, 90);
    // 1, 167, 194 CYAN

    public MenuFrame(){
        setTitle("Spanzuratoarea");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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

        JButton singlePlayerButton = createRoundButton("Singleplayer", SINGLEPLAYER);
        JButton multiPlayerButton = createRoundButton("Multiplayer", MULTIPLAYER);
        JButton exitButton = createRoundButton("Exit", EXIT);

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

        setVisible(true);
    }

    private JButton createRoundButton(String text, Color color) {
        Color shadowColor = new Color(
                Math.max((int)(color.getRed() * 0.7), 0),
                Math.max((int)(color.getGreen() * 0.7), 0),
                Math.max((int)(color.getBlue() * 0.7), 0)
        );

        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(shadowColor);
                g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 20, 20);

                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 20, 20);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 28));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        button.setPreferredSize(new Dimension(350, 100));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuFrame();
        });
    }
}