import javax.swing.*;
import java.io.*;
import java.util.*;

public class TextTools {

    private static final List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showMainMenu();
        });
    }

    private static void showMainMenu() {
        String[] options = {"Comparar Archivos", "Análisis de Texto", "Buscar Palabra", "Agenda de Contactos", "Salir"};
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
                searchWord();
                break;
            case 3:
                showContactMenu();
                break;
            case 4:
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de texto para análisis");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                int wordCount = countWords(file);
                Map<String, Integer> wordFrequency = wordFrequency(file);

                JOptionPane.showMessageDialog(null,
                        "Número total de palabras: " + wordCount +
                                "\nEstadísticas de uso de palabras:\n" + wordFrequencyToString(wordFrequency));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al analizar el texto: " + e.getMessage());
            }
        }
        showMainMenu();
    }

    private static int countWords(File file) throws IOException {
        int wordCount = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\s+");
            wordCount += words.length;
        }
        reader.close();
        return wordCount;
    }

    private static Map<String, Integer> wordFrequency(File file) throws IOException {
        Map<String, Integer> wordFreq = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                word = word.toLowerCase().replaceAll("[^a-zA-Z]", "");
                if (!word.isEmpty()) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }
        }
        reader.close();
        return wordFreq;
    }

    private static String wordFrequencyToString(Map<String, Integer> wordFrequency) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }

    private static void searchWord() {
        String wordToSearch = JOptionPane.showInputDialog("Ingrese la palabra a buscar:");
        if (wordToSearch == null || wordToSearch.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una palabra válida.");
            showMainMenu();
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de texto para buscar la palabra");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                int wordCount = countWordOccurrences(file, wordToSearch);
                JOptionPane.showMessageDialog(null, "La palabra '" + wordToSearch + "' aparece " + wordCount + " veces en el documento.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
            }
        }
        showMainMenu();
    }

    private static int countWordOccurrences(File file, String word) throws IOException {
        int count = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\s+");
            for (String w : words) {
                if (w.equalsIgnoreCase(word)) {
                    count++;
                }
            }
        }
        reader.close();
        return count;
    }

    private static void showContactMenu() {
        String[] options = {"Agregar Contacto", "Listar Contactos", "Volver al Menú Principal"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Agenda de Contactos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                addContact();
                break;
            case 1:
                listContacts();
                break;
            case 2:
                showMainMenu();
                break;
            default:
                break;
        }
    }

    private static void addContact() {
        String name = JOptionPane.showInputDialog("Ingrese el nombre del contacto:");
        String email = JOptionPane.showInputDialog("Ingrese el email del contacto:");
        String phoneNumber = JOptionPane.showInputDialog("Ingrese el número de teléfono del contacto:");
        Contact contact = new Contact(name, email, phoneNumber);
        contacts.add(contact);
        JOptionPane.showMessageDialog(null, "Contacto agregado correctamente.");
        showContactMenu();
    }

    private static void listContacts() {
        StringBuilder list = new StringBuilder();
        for (Contact contact : contacts) {
            list.append(contact).append("\n");
        }
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay contactos registrados.");
        } else {
            JOptionPane.showMessageDialog(null, list.toString());
        }
        showContactMenu();
    }

    private static class Contact {
        private final String name;
        private final String email;
        private final String phoneNumber;

        public Contact(String name, String email, String phoneNumber) {
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "Nombre: " + name + ", Email: " + email + ", Teléfono: " + phoneNumber;
        }
    }
}
