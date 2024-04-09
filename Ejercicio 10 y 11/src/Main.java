import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Email Validator & Drawing Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JTextField emailField = new JTextField(20);
        panel.add(emailField, BorderLayout.NORTH);


        JLabel validationLabel = new JLabel(" ");
        panel.add(validationLabel, BorderLayout.CENTER);


        JPanel drawingPanel = new JPanel();
        drawingPanel.setPreferredSize(new Dimension(400, 300));
        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Graphics g = drawingPanel.getGraphics();
                g.setColor(Color.black);
                g.fillOval(e.getX(), e.getY(), 5, 5);
            }
        });
        panel.add(drawingPanel, BorderLayout.SOUTH);


        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEmail();
            }

            private void validateEmail() {
                String email = emailField.getText();
                if (isValidEmail(email)) {
                    validationLabel.setText("Correo electrónico válido");
                    validationLabel.setForeground(Color.GREEN);
                } else {
                    validationLabel.setText("Correo electrónico no válido");
                    validationLabel.setForeground(Color.RED);
                }
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
