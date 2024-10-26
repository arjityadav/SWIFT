package org.example;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JsonCreatorApp {
    private JFrame frame;
    private JTextField testsuiteNameField;
    private JTextField testsuiteOwnerField;
    private JTextField objectMapExternalField;
    private JTextField variableMapExternalField;
    private JTextField testNameField;
    private JTextField descriptionField;
    private JTextField executeField;
    private JTextArea jsonOutputArea;
    private ArrayList<TestActionPanel> testActions;

    public JsonCreatorApp() {
        // Set up main frame
        frame = new JFrame("JSON Creator Application");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Left panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        // Testifact Info Section
        JPanel testifactInfoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        testifactInfoPanel.setBorder(BorderFactory.createTitledBorder("Testifact Info"));

        testsuiteNameField = createInputField("Testsuite Name:", testifactInfoPanel);
        testsuiteOwnerField = createInputField("Testsuite Owner:", testifactInfoPanel);
        objectMapExternalField = createInputField("Object Map External:", testifactInfoPanel);
        variableMapExternalField = createInputField("Variable Map External:", testifactInfoPanel);

        // Test Item Info Section
        JPanel testItemPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        testItemPanel.setBorder(BorderFactory.createTitledBorder("Test Item Info"));

        testNameField = createInputField("Test Name:", testItemPanel);
        descriptionField = createInputField("Description:", testItemPanel);
        executeField = createInputField("Execute (yes/no):", testItemPanel);

        // Test Actions Section
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Test Actions"));

        testActions = new ArrayList<>();
        JButton addActionBtn = new JButton("Add Action");
        addActionBtn.addActionListener(e -> addTestActionPanel(actionsPanel));
        actionsPanel.add(addActionBtn);

        // Generate and Clear Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton generateButton = new JButton("Generate JSON");
        generateButton.addActionListener(e -> generateJson());

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAllFields());

        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        // Right panel for displaying JSON
        jsonOutputArea = new JTextArea();
        jsonOutputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jsonOutputArea); // Adds scroll functionality to JSON output

        // Add all panels to inputPanel
        inputPanel.add(testifactInfoPanel);
        inputPanel.add(testItemPanel);
        inputPanel.add(actionsPanel);
        inputPanel.add(buttonPanel);

        // Add panels to frame
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JTextField createInputField(String label, JPanel panel) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField();
        panel.add(jLabel);
        panel.add(textField);
        return textField;
    }

    private void addTestActionPanel(JPanel actionsPanel) {
        TestActionPanel actionPanel = new TestActionPanel();
        testActions.add(actionPanel);
        actionsPanel.add(actionPanel);
        actionsPanel.revalidate();
        actionsPanel.repaint();
    }

    private void generateJson() {
        JSONObject json = new JSONObject();

        // Populate testifact_info section
        JSONObject testifactInfo = new JSONObject();
        testifactInfo.put("testsuite_name", testsuiteNameField.getText());
        testifactInfo.put("testsuite_owner", testsuiteOwnerField.getText());
        testifactInfo.put("object_map_external", objectMapExternalField.getText());
        testifactInfo.put("variable_map_external", variableMapExternalField.getText());
        json.put("testifact_info", testifactInfo);

        // Populate testifact_items section
        JSONArray testItemsArray = new JSONArray();
        JSONObject testItem = new JSONObject();
        testItem.put("test_name", testNameField.getText());
        testItem.put("description", descriptionField.getText());
        testItem.put("execute", executeField.getText());

        // Add test actions
        JSONArray testActionsArray = new JSONArray();
        for (TestActionPanel actionPanel : testActions) {
            JSONObject actionJson = actionPanel.getActionJson();
            testActionsArray.put(actionJson);
        }
        testItem.put("test_actions", testActionsArray);
        testItemsArray.put(testItem);
        json.put("testifact_items", testItemsArray);

        // Display JSON with indentation
        jsonOutputArea.setText(json.toString(4));
    }

    private void clearAllFields() {
        // Clear testifact_info fields
        testsuiteNameField.setText("");
        testsuiteOwnerField.setText("");
        objectMapExternalField.setText("");
        variableMapExternalField.setText("");

        // Clear test item fields
        testNameField.setText("");
        descriptionField.setText("");
        executeField.setText("");

        // Clear test actions and remove them from the actions panel
        for (TestActionPanel actionPanel : testActions) {
            actionPanel.getParent().remove(actionPanel); // Remove from the panel
        }
        testActions.clear(); // Clear the list of test actions
        jsonOutputArea.setText("");

        // Refresh the frame to reflect the cleared UI
        frame.revalidate();
        frame.repaint();
    }


    // Inner class for Test Action Panel
    class TestActionPanel extends JPanel {
        private JTextField actionNameField;
        private JTextField actionTypeField;
        private JTextField actionConfigField;

        public TestActionPanel() {
            setLayout(new GridLayout(3, 2, 5, 5));
            setBorder(BorderFactory.createTitledBorder("Action"));

            actionNameField = createInputField("Action Name:", this);
            actionTypeField = createInputField("Action Type:", this);
            actionConfigField = createInputField("Action Config:", this);
        }

        public JSONObject getActionJson() {
            JSONObject actionJson = new JSONObject();
            actionJson.put("action_name", actionNameField.getText());
            actionJson.put("action_type", actionTypeField.getText());

            JSONObject actionConfigJson = new JSONObject();
            actionConfigJson.put("method_argument", actionConfigField.getText());
            actionJson.put("action_config", actionConfigJson);

            return actionJson;
        }
    }

    public static void main(String[] args) {
        new JsonCreatorApp();
    }
}
