package org.example;

import javax.swing.*;
import java.awt.*;

public class TestItemPanel extends JPanel {
    private JTextField testNameField;
    private JTextField descriptionField;
    private JTextField executeField;

    public TestItemPanel() {
        setLayout(new GridLayout(4, 2, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Test Item Info"));

        testNameField = createInputField("Test Name:");
        descriptionField = createInputField("Description:");
        executeField = createInputField("Execute (yes/no):");
    }

    private JTextField createInputField(String label) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField();
        add(jLabel);
        add(textField);
        return textField;
    }

    public void clearFields() {
        testNameField.setText("");
        descriptionField.setText("");
        executeField.setText("");
    }

    public String getTestName() { return testNameField.getText(); }
    public String getDescription() { return descriptionField.getText(); }
    public String getExecute() { return executeField.getText(); }
}
