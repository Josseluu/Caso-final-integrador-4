import javax.swing.*;
import java.io.*;

public class TextTools {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextTools::showMainMenu);
    }

    private static void showMainMenu() {
        String[] options = {"Comparar Archivos", "Análisis de Texto", "Salir"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Herramientas de Texto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                compareFiles();
                break;
            case 1:
                analyzeText();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private static void compareFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar primer archivo");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file1 = fileChooser.getSelectedFile();

            fileChooser.setDialogTitle("Seleccionar segundo archivo");
            result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file2 = fileChooser.getSelectedFile();

                try {
                    boolean areEqual = areFilesEqual(file1, file2);
                    if (areEqual) {
                        JOptionPane.showMessageDialog(null, "Los archivos son iguales.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Los archivos son diferentes.");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al comparar los archivos: " + e.getMessage());
                }
            }
        }
        showMainMenu();
    }

    private static boolean areFilesEqual(File file1, File file2) throws IOException {
        if (file1.length() != file2.length()) {
            return false;
        }

        try (InputStream is1 = new FileInputStream(file1);
             InputStream is2 = new FileInputStream(file2)) {
            int byte1, byte2;
            while ((byte1 = is1.read()) != -1 && (byte2 = is2.read()) != -1) {
                if (byte1 != byte2) {
                    return false;
                }
            }
            return true;
        }
    }

    private static void analyzeText() {

        JOptionPane.showMessageDialog(null, "Funcionalidad de análisis de texto no implementada aún.");
        showMainMenu();
    }
}
