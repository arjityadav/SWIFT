package org.newDesign;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TestCaseDialog extends JDialog {
    private JTextField caseNameField, descriptionField;
    private JComboBox<String> executeField;
    private TestScriptGeneratorApp parent;

    public TestCaseDialog(TestScriptGeneratorApp parent) {
        super(parent, "Test Case Info", true);
        this.parent = parent;
        setLayout(new GridLayout(4, 2));
        setSize(400, 250);

        // Test Case Name
        add(new JLabel("Test Case Name:"));
        caseNameField = new JTextField();
        add(caseNameField);

        // Description
        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        // Execute dropdown
        add(new JLabel("Execute:"));
        executeField = new JComboBox<>(new String[]{"Yes", "No"});
        add(executeField);

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject caseJson = new JSONObject();
                caseJson.put("test_case_name", caseNameField.getText());
                caseJson.put("description", descriptionField.getText());
                caseJson.put("execute", executeField.getSelectedItem());

                parent.updateJsonDisplay(caseJson);
                dispose();
            }
        });
        add(okButton);
    }
}