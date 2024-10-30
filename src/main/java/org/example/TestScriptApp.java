package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestScriptApp {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel testSuitePanel, testCasePanel, testActionPanel, jsonPanel;
    private JTextArea testSuiteJsonArea, testCaseJsonArea, testActionJsonArea;
    private Map<String, JTextField> testSuiteFields, testCaseFields, actionFields;

    public TestScriptApp() {
        // Initialize Frame
        frame = new JFrame("Test Script Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 5, 5));
        frame.add(mainPanel);

        // Left Panels
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(leftPanel);

        // Test Suite Info Panel with compact layout
        testSuitePanel = new JPanel(new GridBagLayout());
        testSuitePanel.setBorder(new TitledBorder("Test Suite Info"));
        leftPanel.add(testSuitePanel);
        testSuitePanel.setBorder(BorderFactory.createCompoundBorder(
                testSuitePanel.getBorder(),
                new EmptyBorder(5, 5, 5, 5)  // Compact spacing within the panel
        ));
        testSuiteFields = createCompactInputFields(testSuitePanel, new String[]{"Test Suite Name:", "Test Suite Owner:", "Object Map External:"}, 200);

        // Test Case Info Panel with compact layout
        testCasePanel = new JPanel(new GridBagLayout());
        testCasePanel.setBorder(new TitledBorder("Test Case Info"));
        leftPanel.add(testCasePanel);
        testCasePanel.setBorder(BorderFactory.createCompoundBorder(
                testCasePanel.getBorder(),
                new EmptyBorder(5, 5, 5, 5)  // Compact spacing within the panel
        ));
        testCaseFields = createCompactInputFields(testCasePanel, new String[]{"Test Case Name:", "Description:", "Execute:"}, 200);

        // Test Action Info Panel with full height
        testActionPanel = new JPanel(new GridBagLayout());
        testActionPanel.setBorder(new TitledBorder("Action Info"));
        leftPanel.add(testActionPanel);
        testActionPanel.setBorder(BorderFactory.createCompoundBorder(
                testActionPanel.getBorder(),
                new EmptyBorder(5, 5, 5, 5)  // Compact spacing within the panel
        ));
        actionFields = createCompactInputFields(testActionPanel, new String[]{"Action Type:", "Action Name:"}, 200);

        // Right JSON Display Panels
        jsonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        mainPanel.add(jsonPanel);

        // Test Suite JSON Display
        testSuiteJsonArea = new JTextArea(5, 30);
        addJsonPanel("Test Suite JSON", jsonPanel, testSuiteJsonArea);

        // Test Case JSON Display
        testCaseJsonArea = new JTextArea(5, 30);
        addJsonPanel("Test Case JSON", jsonPanel, testCaseJsonArea);

        // Test Action JSON Display
        testActionJsonArea = new JTextArea(5, 30);
        addJsonPanel("Test Action JSON", jsonPanel, testActionJsonArea);

        frame.setVisible(true);
    }

    private Map<String, JTextField> createCompactInputFields(JPanel panel, String[] fieldNames, int fieldWidth) {
        Map<String, JTextField> fieldMap = new HashMap<>();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < fieldNames.length; i++) {
            JLabel label = new JLabel(fieldNames[i]);
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            panel.add(label, gbc);

            JTextField field = new JTextField(fieldWidth / 10);
            gbc.gridx = 1;
            gbc.weightx = 1;
            panel.add(field, gbc);
            fieldMap.put(fieldNames[i], field);
        }
        return fieldMap;
    }

    private void addJsonPanel(String title, JPanel parentPanel, JTextArea jsonArea) {
        JPanel jsonDisplayPanel = new JPanel(new BorderLayout());
        jsonDisplayPanel.setBorder(new TitledBorder(title));
        jsonArea.setEditable(false);
        jsonDisplayPanel.add(new JScrollPane(jsonArea), BorderLayout.CENTER);
        parentPanel.add(jsonDisplayPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestScriptApp::new);
    }
}
