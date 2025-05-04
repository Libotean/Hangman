import javax.swing.*;
import java.awt.*;

public class Multiplayer extends JFrame {

    public Multiplayer() {
        setTitle("Multiplayer Hangman");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton hostButton = new JButton("Host Game");
        JButton joinButton = new JButton("Join Game");

        hostButton.addActionListener(e -> {
            // Rulăm Server-ul într-un thread separat
            new Thread(() -> {
                Server server = new Server();
                server.start(1234);
            }).start();
            JOptionPane.showMessageDialog(this, "Server started on port 1234. Așteptăm clientul...");
        });

        joinButton.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(this, "Enter server IP:", "localhost");

            // Rulăm Client-ul într-un thread separat
            new Thread(() -> {
                Client client = new Client();
                client.startConnection(ip, 1234);
            }).start();
        });

        setLayout(new FlowLayout());
        add(hostButton);
        add(joinButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Multiplayer().setVisible(true);
        });
    }
}
