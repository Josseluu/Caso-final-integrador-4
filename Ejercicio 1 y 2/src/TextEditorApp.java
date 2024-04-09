import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextEditorApp {

    private JFrame frame;
    private JTextArea textArea;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private List<File> documents;

    public TextEditorApp() {
        initialize();
        documents = new ArrayList<>();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Editor de Texto");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Archivo");
        menuBar.add(fileMenu);

        JMenuItem newMenuItem = new JMenuItem("Nuevo");
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newDocument();
            }
        });
        fileMenu.add(newMenuItem);

        JMenuItem openMenuItem = new JMenuItem("Abrir");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDocument();
            }
        });
        fileMenu.add(openMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Guardar");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDocument();
            }
        });
        fileMenu.add(saveMenuItem);

        JMenuItem listMenuItem = new JMenuItem("Listar Documentos");
        listMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listDocuments();
            }
        });
        fileMenu.add(listMenuItem);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addListSelectionListener(e -> {
            int selectedIndex = fileList.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < documents.size()) {
                loadDocument(selectedIndex);
            }
        });
        frame.getContentPane().add(new JScrollPane(fileList), BorderLayout.WEST);
    }

    private void newDocument() {
        textArea.setText("");
        documents.add(null);
        updateList();
    }

    private void openDocument() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                textArea.read(reader, null);
                reader.close();
                documents.add(selectedFile);
                updateList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDocument() {
        if (documents.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay documento para guardar.");
            return;
        }
        File currentFile = documents.get(documents.size() - 1);
        if (currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                    textArea.write(writer);
                    writer.close();
                    documents.set(documents.size() - 1, selectedFile);
                    updateList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                textArea.write(writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void listDocuments() {
        if (documents.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay documentos abiertos.");
            return;
        }
        StringBuilder documentList = new StringBuilder();
        for (int i = 0; i < documents.size(); i++) {
            documentList.append(i + 1).append(". ");
            if (documents.get(i) == null) {
                documentList.append("Nuevo Documento");
            } else {
                documentList.append(documents.get(i).getName());
            }
            documentList.append("\n");
        }
        JOptionPane.showMessageDialog(frame, documentList.toString(), "Lista de Documentos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateList() {
        listModel.clear();
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i) == null) {
                listModel.addElement("Nuevo Documento");
            } else {
                listModel.addElement(documents.get(i).getName());
            }
        }
    }

    private void loadDocument(int index) {
        if (index >= 0 && index < documents.size()) {
            File selectedFile = documents.get(index);
            if (selectedFile != null) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    textArea.read(reader, null);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void launchApp() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        TextEditorApp app = new TextEditorApp();
        app.launchApp();
    }
}

