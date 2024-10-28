package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class TestActionPanel extends JPanel {
    private JComboBox<String> actionTypeComboBox;
    private JComboBox<String> actionNameComboBox;
    private JPanel actionFieldsPanel;
    private JButton collapseExpandButton;
    private boolean isCollapsed;
    private JsonCreatorApp app;

    private Map<String, JTextField> dynamicFields; // Stores dynamic action_fields inputs

    public TestActionPanel(JsonCreatorApp app) {
        this.app = app;
        this.isCollapsed = false; // Initially expanded

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Action"));

        // Initialize components
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        // Action Type Dropdown
        mainPanel.add(new JLabel("Action Type:"));
        actionTypeComboBox = new JComboBox<>(new String[]{"UI", "API"});
        mainPanel.add(actionTypeComboBox);

        // Action Name Dropdown
        mainPanel.add(new JLabel("Action Name:"));
        actionNameComboBox = new JComboBox<>(new String[]{"Open Browser", "Navigate", "Click", "Input"});
        mainPanel.add(actionNameComboBox);

        // Panel for dynamic action_fields
        actionFieldsPanel = new JPanel();
        actionFieldsPanel.setLayout(new GridLayout(3, 2, 5, 5));

        // Initialize dynamic fields map
        dynamicFields = new HashMap<>();

        // Add listeners to update fields based on selected action type and name
        actionTypeComboBox.addActionListener(e -> updateActionFields());
        actionNameComboBox.addActionListener(e -> updateActionFields());

        // Add main panel and dynamic fields panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.NORTH);
        contentPanel.add(actionFieldsPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // Collapse/Expand button
        collapseExpandButton = new JButton("Collapse");
        collapseExpandButton.addActionListener(e -> toggleCollapseExpand());
        add(collapseExpandButton, BorderLayout.SOUTH);

        // Initialize fields based on default selections
        updateActionFields();
    }

    // Method to update action fields based on selected action type and action name
    private void updateActionFields() {
        actionFieldsPanel.removeAll();
        dynamicFields.clear();

        String actionType = (String) actionTypeComboBox.getSelectedItem();
        String actionName = (String) actionNameComboBox.getSelectedItem();

        if ("UI".equals(actionType) && "Open Browser".equals(actionName)) {
            addDynamicField("browser_name", "Browser Name");
            addDynamicField("sso_login", "SSO Login");
            addDynamicField("browser_zoom", "Browser Zoom");

        } else if ("UI".equals(actionType) && "Navigate".equals(actionName)) {
            addDynamicField("url", "URL");

        } else if ("UI".equals(actionType) && "Click".equals(actionName)) {
            addDynamicField("object_name", "Object Name");

        } else if ("UI".equals(actionType) && "Input".equals(actionName)) {
            addDynamicField("field_name", "Field Name");
            addDynamicField("input_value", "Input Value");

        } else if ("API".equals(actionType)) {
            if ("Open Browser".equals(actionName)) {
                addDynamicField("endpoint", "API Endpoint");
            } else if ("Navigate".equals(actionName)) {
                addDynamicField("resource", "Resource");
                addDynamicField("headers", "Headers");
            }
        }

        actionFieldsPanel.revalidate();
        actionFieldsPanel.repaint();
    }

    // Utility method to add a dynamic field
    private void addDynamicField(String key, String label) {
        JLabel fieldLabel = new JLabel(label + ":");
        JTextField fieldInput = new JTextField();
        actionFieldsPanel.add(fieldLabel);
        actionFieldsPanel.add(fieldInput);
        dynamicFields.put(key, fieldInput); // Store input field in map with its key
    }

    private void toggleCollapseExpand() {
        isCollapsed = !isCollapsed;
        removeAll();

        if (isCollapsed) {
            // Collapse mode: show only action name in the title and expand button
            String actionName = actionNameComboBox.getSelectedItem() == null ? "Unnamed Action" : actionNameComboBox.getSelectedItem().toString();
            setBorder(BorderFactory.createTitledBorder("Action: " + actionName));

            collapseExpandButton.setText("Expand");
            add(collapseExpandButton, BorderLayout.SOUTH); // Only show collapse/expand button in collapsed state
        } else {
            // Expanded mode: show all fields and buttons
            setBorder(BorderFactory.createTitledBorder("Action"));

            JPanel mainPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            mainPanel.add(new JLabel("Action Type:"));
            mainPanel.add(actionTypeComboBox);

            mainPanel.add(new JLabel("Action Name:"));
            mainPanel.add(actionNameComboBox);

            actionFieldsPanel.setVisible(true); // Ensure actionFieldsPanel is visible when expanded

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(mainPanel, BorderLayout.NORTH);
            contentPanel.add(actionFieldsPanel, BorderLayout.CENTER);

            add(contentPanel, BorderLayout.CENTER);
            add(collapseExpandButton, BorderLayout.SOUTH);

            collapseExpandButton.setText("Collapse");
        }
        revalidate();
        repaint();
    }

    public JSONObject getActionJson() {
        JSONObject actionJson = new JSONObject();
        actionJson.put("action_type", ((String) actionTypeComboBox.getSelectedItem()).toLowerCase());
        actionJson.put("action_name", ((String) actionNameComboBox.getSelectedItem()).toLowerCase().replace(" ", "_"));

        JSONObject actionFieldsJson = new JSONObject();
        for (Map.Entry<String, JTextField> entry : dynamicFields.entrySet()) {
            actionFieldsJson.put(entry.getKey(), entry.getValue().getText());
        }
        actionJson.put("action_fields", actionFieldsJson);

        return actionJson;
    }
}
