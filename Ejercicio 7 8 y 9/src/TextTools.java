import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TextTools {

    private static final Map<String, JFrame> documentWindows = new HashMap<>();

    public class Main {

        public static void main(String[] args) {

        }
    }


    private static void showMainMenu() {
        String[] options = {"Crear Nuevo Documento", "Comparar Archivos", "Análisis de Texto", "Buscar Palabra", "Agenda de Contactos", "Salir"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Herramientas de Texto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                createNewDocument();
                break;
            case 1:
                compareFiles();
                break;
            case 2:
                analyzeText();
                break;
            case 3:
                searchWord();
                break;
            case 4:
                showContactMenu();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private static void createNewDocument() {
        JFrame documentWindow = new JFrame("Nuevo Documento");
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        documentWindow.add(scrollPane);

        documentWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        documentWindow.setSize(600, 400);
        documentWindow.setVisible(true);
        documentWindows.put("Documento " + (documentWindows.size() + 1), documentWindow);

        showMainMenu();
    }

    private static void compareFiles() {

        JOptionPane.showMessageDialog(null, "Funcionalidad de comparación de archivos no implementada aún.");
        showMainMenu();
    }

    private static void analyzeText() {

        JOptionPane.showMessageDialog(null, "Funcionalidad de análisis de texto no implementada aún.");
        showMainMenu();
    }

    private static void searchWord() {

        JOptionPane.showMessageDialog(null, "Funcionalidad de búsqueda de palabras no implementada aún.");
        showMainMenu();
    }

    private static void showContactMenu() {

        JOptionPane.showMessageDialog(null, "Funcionalidad de agenda de contactos no implementada aún.");
        showMainMenu();
    }

    public static class MousePositionTracker extends MouseAdapter {
        private final JLabel label;

        public MousePositionTracker(JLabel label) {
            this.label = label;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            label.setText("Posición del ratón: (" + e.getX() + ", " + e.getY() + ")");
        }
    }

    public static class InteractiveScrollBar extends JFrame {
        private final JScrollBar scrollBar;
        private final JTextArea textArea;

        public InteractiveScrollBar() {
            setTitle("Barra de Desplazamiento Interactiva");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            textArea = new JTextArea(10, 30);
            scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 10, 0, 100);

            scrollBar.addAdjustmentListener(e -> {
                int value = scrollBar.getValue();
                textArea.setCaretPosition(value);
            });

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBar(scrollBar);

            getContentPane().add(scrollPane);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InteractiveScrollBar();
        });
    }
}
