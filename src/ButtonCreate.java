import javax.swing.*;
import java.awt.*;

public class ButtonCreate {

    public static JButton createRoundButton(String text, Color color) {
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

                if(isEnabled()){
                    g2.setColor(shadowColor);
                    g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 20, 20);

                    g2.setColor(color);
                    g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 20, 20);
                } else {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 20, 20);
                }

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

        button.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        button.setPreferredSize(new Dimension(350, 100));

        return button;
    }
}
