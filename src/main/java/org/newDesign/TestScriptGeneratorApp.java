package org.newDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONObject;

// Main class
public class TestScriptGeneratorApp extends JFrame {
    private JTextArea jsonDisplayArea;

    public TestScriptGeneratorApp() {
        setTitle("Test Script Generator");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left panel for buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        add(leftPanel, BorderLayout.WEST);

        // Test Suite Info Button
        JButton testSuiteButton = new JButton("Test Suite Info");
        testSuiteButton.addActionListener(e -> openTestSuiteDialog());
        leftPanel.add(testSuiteButton);

        // Test Case Info Button
        JButton testCaseButton = new JButton("Test Case Info");
        testCaseButton.addActionListener(e -> openTestCaseDialog());
        leftPanel.add(testCaseButton);

        // Add Action Button
        JButton addActionButton = new JButton("Add Action");
        addActionButton.addActionListener(e -> addAction());
        leftPanel.add(addActionButton);

        // Right panel for JSON display
        jsonDisplayArea = new JTextArea();
        jsonDisplayArea.setEditable(false);
        JScrollPane jsonScrollPane = new JScrollPane(jsonDisplayArea);
        jsonScrollPane.setBorder(BorderFactory.createTitledBorder("Generated JSON"));
        add(jsonScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void openTestSuiteDialog() {
        TestSuiteDialog testSuiteDialog = new TestSuiteDialog(this);
        testSuiteDialog.setVisible(true);
    }

    private void openTestCaseDialog() {
        TestCaseDialog testCaseDialog = new TestCaseDialog(this);
        testCaseDialog.setVisible(true);
    }

    public void updateJsonDisplay(JSONObject jsonObject) {
        jsonDisplayArea.setText(jsonObject.toString(4));
    }

    private void addAction() {
        // Logic to add action and update JSON display
        JSONObject actionJson = new JSONObject();
        actionJson.put("action_type", "Sample Action Type");
        actionJson.put("action_name", "Sample Action Name");

        // Adding to main JSON display area
        updateJsonDisplay(actionJson);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestScriptGeneratorApp::new);
    }
}