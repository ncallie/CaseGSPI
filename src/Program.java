import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Program {
    public static void main(String[] args) {
        List<HashMap<String, String>> tasks = initTasksList();
        System.out.println(UtilsClass.numUniqueEmpOfActiveTasks(tasks));
    }

    private static List<HashMap<String, String>> initTasksList() {
        return new ArrayList<HashMap<String, String>>() {{
            add(createTask("1", "001", "active"));
            add(createTask("2", "002", "active"));
            add(createTask("3", "001", "active"));
            add(createTask("4", "007", "disabled"));
        }};
    }

    private static HashMap<String, String> createTask(String task_id, String assignee_id, String task_state) {
        return new HashMap<String, String>() {
            {
                put("task_id", task_id);
                put("assignee_id", assignee_id);
                put("task_state", task_state);
            }
        };
    }
}
