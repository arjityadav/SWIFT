package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class JsonGenerator {
    private TestifactInfoPanel testifactInfoPanel;
    private TestItemPanel testItemPanel;
    private List<TestActionPanel> testActions;

    public JsonGenerator(TestifactInfoPanel testifactInfoPanel, TestItemPanel testItemPanel, List<TestActionPanel> testActions) {
        this.testifactInfoPanel = testifactInfoPanel;
        this.testItemPanel = testItemPanel;
        this.testActions = testActions;
    }

    public JSONObject generateJson() {
        JSONObject json = new JSONObject();

        JSONObject testifactInfo = new JSONObject();
        testifactInfo.put("testsuite_name", testifactInfoPanel.getTestsuiteName());
        testifactInfo.put("testsuite_owner", testifactInfoPanel.getTestsuiteOwner());
        testifactInfo.put("object_map_external", testifactInfoPanel.getObjectMapExternal());
        testifactInfo.put("variable_map_external", testifactInfoPanel.getVariableMapExternal());
        json.put("testifact_info", testifactInfo);

        JSONArray testItemsArray = new JSONArray();
        JSONObject testItem = new JSONObject();
        testItem.put("test_name", testItemPanel.getTestName());
        testItem.put("description", testItemPanel.getDescription());
        testItem.put("execute", testItemPanel.getExecute());

        JSONArray testActionsArray = new JSONArray();
        for (TestActionPanel actionPanel : testActions) {
            testActionsArray.put(actionPanel.getActionJson());
        }
        testItem.put("test_actions", testActionsArray);
        testItemsArray.put(testItem);
        json.put("testifact_items", testItemsArray);

        return json;
    }
}
