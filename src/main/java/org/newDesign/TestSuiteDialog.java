package org.newDesign;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Dialog for Test Suite Info
class TestSuiteDialog extends JDialog {
    private JTextField suiteNameField, suiteOwnerField;
    private TestScriptGeneratorApp parent;

    public TestSuiteDialog(TestScriptGeneratorApp parent) {
        super(parent, "Test Suite Info", true);
        this.parent = parent;
        setLayout(new GridLayout(3, 2));
        setSize(400, 200);

        // Test Suite Name
        add(new JLabel("Test Suite Name:"));
        suiteNameField = new JTextField();
        add(suiteNameField);

        // Test Suite Owner
        add(new JLabel("Test Suite Owner:"));
        suiteOwnerField = new JTextField();
        add(suiteOwnerField);

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject suiteJson = new JSONObject();
                suiteJson.put("test_suite_name", suiteNameField.getText());
                suiteJson.put("test_suite_owner", suiteOwnerField.getText());

                parent.updateJsonDisplay(suiteJson);
                dispose();
            }
        });
        add(okButton);
    }
}