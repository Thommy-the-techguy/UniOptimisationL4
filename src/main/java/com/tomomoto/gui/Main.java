package com.tomomoto.gui;

import com.tomomoto.algorithm.GreedyCoverageAlgorithm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JFrame {
    private JTable jTable;
    private JScrollPane jScrollPane;
    private JLabel jLabel;
    private JTextField matrixRowsSizeTextField;
    private JTextField matrixColumnsSizeTextField;
    private JButton saveMatrixToFileButton;
    private JButton loadMatrixFromFileButton;
    private JButton findCoverageButton;
    private JButton loadDefaultMatrixButton;
    private Path currentPath;

    public Main() {
        super("Coverage");
        setLayout(null);
        setSize(400, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeComponents();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    private void initializeComponents() {
        initializeButtons();
        initializeJTable();
        initializeScrollPane();
        try {
            Path path = Path.of("src", "main", "resources", "matrix.txt");
            currentPath = path;
            fillJTable(readFromFile(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeJLabel();
    }

    private void initializeJTable() {
        jTable = new JTable();
        jTable.setGridColor(Color.BLACK);
    }

    private void initializeScrollPane() {
        jScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setSize(380, 400);
        jScrollPane.setLocation(10, 30);
        this.add(jScrollPane);
    }

    private void initializeButtons() {
        initializeSaveMatrixToFileButton();
        initializeLoadMatrixFromFileButton();
        initializeFindCoverageButton();
        initializeLoadDefaultMatrixButton();
    }

    private void initializeJLabel() {
        jLabel = new JLabel("{}");
        jLabel.setSize(200, 30);
        jLabel.setLocation(10, 430);
        this.add(jLabel);
    }

    private void initializeSaveMatrixToFileButton() {
        saveMatrixToFileButton = new JButton("Сохранить");
        saveMatrixToFileButton.setLocation(0, 0);
        saveMatrixToFileButton.setSize(100, 30);
        saveMatrixToFileButton.addActionListener(event -> {
            Path path = Path.of("src", "main", "resources", "user-matrix.txt");
            try {
                saveMatrixToFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.add(saveMatrixToFileButton);
    }

    private void initializeLoadMatrixFromFileButton() {
        loadMatrixFromFileButton = new JButton("Загрузить");
        loadMatrixFromFileButton.setLocation(100, 0);
        loadMatrixFromFileButton.setSize(100, 30);
        loadMatrixFromFileButton.addActionListener(event -> {
            Path path = Path.of("src", "main", "resources", "user-matrix.txt");
            currentPath = path;
            try {
                fillJTable(readFromFile(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.add(loadMatrixFromFileButton);
    }

    private void initializeFindCoverageButton() {
        findCoverageButton = new JButton("Покрытие");
        findCoverageButton.setLocation(200, 0);
        findCoverageButton.setSize(100, 30);
        findCoverageButton.addActionListener(event -> {
//            TODO: write logic here later
            try {
                jLabel.setText(String.format("{%s}", GreedyCoverageAlgorithm.getCoverage(readFromFile(currentPath), readFromFile(currentPath).get(0).length())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.add(findCoverageButton);
    }

    private void initializeLoadDefaultMatrixButton() {
        loadDefaultMatrixButton = new JButton("Стандарт");
        loadDefaultMatrixButton.setLocation(300, 0);
        loadDefaultMatrixButton.setSize(100, 30);
        loadDefaultMatrixButton.addActionListener(event -> {
            Path path = Path.of("src", "main", "resources", "matrix.txt");
            currentPath = path;
            try {
                fillJTable(readFromFile(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.add(loadDefaultMatrixButton);
    }

    private void saveMatrixToFile(Path path) throws IOException {
        StringBuilder stringToWrite = new StringBuilder();
        for (int i = 0; i < jTable.getRowCount(); i++) {
            for (int j = 0; j < jTable.getColumnCount(); j++) {
                if (j < jTable.getColumnCount() - 1) {
                    stringToWrite.append(jTable.getValueAt(i, j)).append(" ");
                } else {
                    stringToWrite.append(jTable.getValueAt(i, j));
                }
            }
            stringToWrite.append("\n");
        }
        Files.writeString(path, stringToWrite.toString(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    private List<String> readFromFile(Path path) throws IOException {
        List<String> rows = Files.readAllLines(path);
        List<String> result = new ArrayList<>();
        rows.forEach(row -> result.add(row.replace(" ", "")));
        return result;
    }

    private void fillJTable(List<String> contentFromFile) {
        DefaultTableModel tableModel = new DefaultTableModel(contentFromFile.size(), contentFromFile.get(0).length());
        jTable.setModel(tableModel);
        for (int i = 0; i < contentFromFile.size(); i++) {
            String row = contentFromFile.get(i);
            for (int j = 0; j < contentFromFile.get(0).length(); j++) {
                Object value = row.charAt(j);
                jTable.setValueAt(value, i, j);
            }
        }
    }
}
