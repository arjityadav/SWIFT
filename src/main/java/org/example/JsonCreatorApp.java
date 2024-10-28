package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JsonCreatorApp {
    private JFrame frame;
    private JTextArea jsonOutputArea;
    private TestifactInfoPanel testifactInfoPanel;
    private TestItemPanel testItemPanel;
    private JPanel actionsPanel;
    private JScrollPane actionsScrollPane;
    private ArrayList<TestActionPanel> testActions;

    public JsonCreatorApp() {
        frame = new JFrame("JSON Creator Application");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        testActions = new ArrayList<>();

        // Initialize TestifactInfoPanel and TestItemPanel
        testifactInfoPanel = new TestifactInfoPanel();
        testItemPanel = new TestItemPanel();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(testifactInfoPanel);
        infoPanel.add(testItemPanel);

        // Set up the actions panel with scrolling
        actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Test Actions"));

        actionsScrollPane = new JScrollPane(actionsPanel);
        actionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        actionsScrollPane.setPreferredSize(new Dimension(400, 400));

        JButton addActionBtn = new JButton("Add Action");
        addActionBtn.addActionListener(e -> addTestActionPanel());

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.add(addActionBtn);

        JPanel actionsContainerPanel = new JPanel(new BorderLayout());
        actionsContainerPanel.add(addButtonPanel, BorderLayout.NORTH);
        actionsContainerPanel.add(actionsScrollPane, BorderLayout.CENTER);

        // Split pane for layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infoPanel, actionsContainerPanel);
        splitPane.setDividerLocation(0.25);
        splitPane.setResizeWeight(0.25);

        // JSON output and control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton generateButton = new JButton("Generate JSON");
        generateButton.addActionListener(e -> generateJson());

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAllFields());

        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        jsonOutputArea = new JTextArea();
        jsonOutputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jsonOutputArea);

        frame.setLayout(new BorderLayout());
        frame.add(splitPane, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addTestActionPanel() {
        TestActionPanel actionPanel = new TestActionPanel(this);
        testActions.add(actionPanel);
        actionsPanel.add(actionPanel);
        actionsPanel.revalidate();
        actionsPanel.repaint();
    }

    public void moveTestActionPanelUp(TestActionPanel panel) {
        int index = testActions.indexOf(panel);
        if (index > 0) {
            testActions.remove(index);
            testActions.add(index - 1, panel);
            refreshActionsPanel();
        }
    }

    public void moveTestActionPanelDown(TestActionPanel panel) {
        int index = testActions.indexOf(panel);
        if (index < testActions.size() - 1) {
            testActions.remove(index);
            testActions.add(index + 1, panel);
            refreshActionsPanel();
        }
    }

    public void removeTestActionPanel(TestActionPanel panel) {
        testActions.remove(panel);
        actionsPanel.remove(panel);
        actionsPanel.revalidate();
        actionsPanel.repaint();
    }

    private void refreshActionsPanel() {
        actionsPanel.removeAll();
        for (TestActionPanel action : testActions) {
            actionsPanel.add(action);
        }
        actionsPanel.revalidate();
        actionsPanel.repaint();
        generateJson();
    }

    private void generateJson() {
        JsonGenerator jsonGenerator = new JsonGenerator(
                testifactInfoPanel,
                testItemPanel,
                testActions
        );
        jsonOutputArea.setText(jsonGenerator.generateJson().toString(4));
    }

    private void clearAllFields() {
        testifactInfoPanel.clearFields();
        testItemPanel.clearFields();

        for (TestActionPanel actionPanel : testActions) {
            actionsPanel.remove(actionPanel);
        }
        testActions.clear();
        jsonOutputArea.setText("");

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        new JsonCreatorApp();
    }
}
