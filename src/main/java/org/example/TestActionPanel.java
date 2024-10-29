package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private Map<String, JComponent> dynamicFields;

    public TestActionPanel(JsonCreatorApp app) {
        this.app = app;
        this.isCollapsed = false;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Action"));
        setBackground(new Color(240, 240, 240));

        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.NORTH);

        actionFieldsPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        actionFieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(actionFieldsPanel, BorderLayout.CENTER);

        collapseExpandButton = new JButton("Collapse");
        collapseExpandButton.addActionListener(e -> toggleCollapseExpand());
        add(collapseExpandButton, BorderLayout.SOUTH);

        dynamicFields = new HashMap<>();
        updateActionFields();
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Action Type Label and Dropdown
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Action Type:"), gbc);

        actionTypeComboBox = new JComboBox<>(new String[]{"UI", "API"});
        gbc.gridx = 1;
        mainPanel.add(actionTypeComboBox, gbc);

        // Action Name Label and Dropdown
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Action Name:"), gbc);

        actionNameComboBox = new JComboBox<>(new String[]{"Open Browser", "Navigate", "Click", "Input"});
        gbc.gridx = 1;
        mainPanel.add(actionNameComboBox, gbc);

        actionTypeComboBox.addActionListener(e -> updateActionFields());
        actionNameComboBox.addActionListener(e -> updateActionFields());

        return mainPanel;
    }

    private void updateActionFields() {
        actionFieldsPanel.removeAll();
        dynamicFields.clear();

        String actionType = (String) actionTypeComboBox.getSelectedItem();
        String actionName = (String) actionNameComboBox.getSelectedItem();

        if ("UI".equals(actionType) && "Open Browser".equals(actionName)) {
            addDynamicField("browser_name", "Browser Name", new JComboBox<>(new String[]{"Chrome", "Edge"}));
            addDynamicField("sso_login", "SSO Login", new JComboBox<>(new String[]{"Yes", "No"}));

            // Browser Zoom Slider in a nested panel
            JLabel zoomLabel = new JLabel("Browser Zoom: 100%");
            JSlider browserZoomSlider = new JSlider(50, 200, 100);
            browserZoomSlider.setMajorTickSpacing(10);
            browserZoomSlider.setPaintTicks(true);
            browserZoomSlider.setBackground(new Color(245, 245, 245));

            browserZoomSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    int zoomValue = browserZoomSlider.getValue();
                    zoomLabel.setText("Browser Zoom: " + zoomValue + "%");
                }
            });

            JPanel sliderPanel = new JPanel(new BorderLayout(5, 5));
            sliderPanel.add(browserZoomSlider, BorderLayout.CENTER);
            sliderPanel.add(zoomLabel, BorderLayout.SOUTH);
            actionFieldsPanel.add(new JLabel("Browser Zoom:"));
            actionFieldsPanel.add(sliderPanel);

            dynamicFields.put("browser_zoom", browserZoomSlider);

        } else if ("UI".equals(actionType) && "Navigate".equals(actionName)) {
            addDynamicField("url", "URL", new JTextField());

        } else if ("UI".equals(actionType) && "Click".equals(actionName)) {
            addDynamicField("object_name", "Object Name", new JTextField());

        } else if ("UI".equals(actionType) && "Input".equals(actionName)) {
            addDynamicField("field_name", "Field Name", new JTextField());
            addDynamicField("input_value", "Input Value", new JTextField());

        } else if ("API".equals(actionType)) {
            if ("Open Browser".equals(actionName)) {
                addDynamicField("endpoint", "API Endpoint", new JTextField());
            } else if ("Navigate".equals(actionName)) {
                addDynamicField("resource", "Resource", new JTextField());
                addDynamicField("headers", "Headers", new JTextField());
            }
        }

        actionFieldsPanel.revalidate();
        actionFieldsPanel.repaint();
    }

    private void addDynamicField(String key, String label, JComponent inputComponent) {
        JLabel fieldLabel = new JLabel(label + ":");
        actionFieldsPanel.add(fieldLabel);
        actionFieldsPanel.add(inputComponent);
        dynamicFields.put(key, inputComponent);
    }

    private void toggleCollapseExpand() {
        isCollapsed = !isCollapsed;
        removeAll();

        if (isCollapsed) {
            String actionName = actionNameComboBox.getSelectedItem() == null ? "Unnamed Action" : actionNameComboBox.getSelectedItem().toString();
            setBorder(BorderFactory.createTitledBorder("Action: " + actionName));
            collapseExpandButton.setText("Expand");
            add(collapseExpandButton, BorderLayout.SOUTH);
        } else {
            setBorder(BorderFactory.createTitledBorder("Action"));
            add(createMainPanel(), BorderLayout.NORTH);
            add(actionFieldsPanel, BorderLayout.CENTER);
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
        for (Map.Entry<String, JComponent> entry : dynamicFields.entrySet()) {
            if (entry.getValue() instanceof JTextField) {
                actionFieldsJson.put(entry.getKey(), ((JTextField) entry.getValue()).getText());
            } else if (entry.getValue() instanceof JComboBox) {
                actionFieldsJson.put(entry.getKey(), ((JComboBox<?>) entry.getValue()).getSelectedItem());
            } else if (entry.getValue() instanceof JSlider) {
                actionFieldsJson.put(entry.getKey(), ((JSlider) entry.getValue()).getValue() + "%");
            }
        }
        actionJson.put("action_fields", actionFieldsJson);

        return actionJson;
    }
}
