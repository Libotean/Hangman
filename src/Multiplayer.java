import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Multiplayer extends JFrame {

    private JButton goBackButton;
    private JButton hostButton;
    private JButton joinButton;

    public final Color BACKGROUND = new Color(244, 253, 217);
    public final Color GO_BACK = new Color(0xB6174B);
    private final Color HOST = new Color(48, 107, 52);
    private final Color JOIN = new Color(236, 159, 5);

    public Multiplayer() {
        setTitle("Multiplayer");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        hostButton = ButtonCreate.createRoundButton("HOST", HOST);
        joinButton = ButtonCreate.createRoundButton("JOIN", JOIN);
        goBackButton = ButtonCreate.createRoundButton("Go Back", GO_BACK);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND );
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(hostButton, gbc);
        gbc.gridy = 1;
        mainPanel.add(joinButton, gbc);
        gbc.gridy = 2;
        mainPanel.add(goBackButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        hostButton.addActionListener(e -> {
            new Thread(() -> {
                Server server = new Server();
                server.start(1234);
            }).start();
            JOptionPane.showMessageDialog(this, "Server started pe portul 1234. Așteptăm clientul...");
        });

        joinButton.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(this, "Introdu IP-ul serverului:", "localhost");
            new Thread(() -> {
                Client client = new Client();
                client.startConnection(ip, 1234);
            }).start();
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuFrame().setVisible(true);
            }
        });
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Multiplayer().setVisible(true);
        });
    }
}
