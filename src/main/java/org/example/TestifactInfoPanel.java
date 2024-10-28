package org.example;

import javax.swing.*;
import java.awt.*;

public class TestifactInfoPanel extends JPanel {
    private JTextField testsuiteNameField;
    private JTextField testsuiteOwnerField;
    private JTextField objectMapExternalField;
    private JTextField variableMapExternalField;

    public TestifactInfoPanel() {
        setLayout(new GridLayout(5, 2, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Testifact Info"));

        testsuiteNameField = createInputField("Testsuite Name:");
        testsuiteOwnerField = createInputField("Testsuite Owner:");
        objectMapExternalField = createInputField("Object Map External:");
        variableMapExternalField = createInputField("Variable Map External:");
    }

    private JTextField createInputField(String label) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField();
        add(jLabel);
        add(textField);
        return textField;
    }

    public void clearFields() {
        testsuiteNameField.setText("");
        testsuiteOwnerField.setText("");
        objectMapExternalField.setText("");
        variableMapExternalField.setText("");
    }

    public String getTestsuiteName() { return testsuiteNameField.getText(); }
    public String getTestsuiteOwner() { return testsuiteOwnerField.getText(); }
    public String getObjectMapExternal() { return objectMapExternalField.getText(); }
    public String getVariableMapExternal() { return variableMapExternalField.getText(); }
}
