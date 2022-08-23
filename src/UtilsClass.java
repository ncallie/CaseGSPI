import java.util.*;
import java.util.stream.Collectors;

public final class UtilsClass {
    public static int numUniqueEmpOfActiveTasks(List<HashMap<String, String>> tasks) {
        boolean isUni = false;
        Set<String> uniEmp = new HashSet<>();

        if (tasks == null || tasks.isEmpty())  //закоментировать если валидация не нужна
            throw new IllegalArgumentException("List must contain at least one element");

        for (HashMap<String, String> map : tasks) {
            validationTask(map); //закоментировать если валидация не нужна

            if (!isUni) { //закоментировать если валидация не нужна TODO не лучший вариант
                checkUniTask(tasks);
                isUni = true;
            }

            if (map.get(ArgsMap.task_state.toString()).equals(TaskState.active.toString()) &&
                !map.get(ArgsMap.assignee_id.toString()).isEmpty())
                    uniEmp.add(map.get(ArgsMap.assignee_id.toString()));
        }
        return uniEmp.size();
    }

    private static void checkUniTask(List<HashMap<String, String>> tasks) {
        Set<String> collect = tasks.stream().map(task -> task.get(ArgsMap.task_id.toString())).collect(Collectors.toSet());
        if (tasks.size() != collect.size())
           throw new IllegalStateException("Duplicate key " + ArgsMap.task_id);
    }

    private static void validationTask(HashMap<String, String> map) {
        boolean isValid = false;
        String mapValue;

        if (map.size() != ArgsMap.NUM_ELEMENTS_MAP)
            throw new IllegalArgumentException("Map must contain " + ArgsMap.NUM_ELEMENTS_MAP + " elements");
        for (ArgsMap e : ArgsMap.values()) {
            if (!map.containsKey(e.toString())) throw new IllegalArgumentException("Map must contain " + e);
        }

        { //TODO вынести как отдельный метод (чтобы не указывать вручную что нужно проверить на null, empty)
            mapValue = map.get(ArgsMap.task_id.toString());
            isEmptyOrNull(mapValue, ArgsMap.task_id);
            if (map.get(ArgsMap.assignee_id.toString()) == null)
                throw new IllegalArgumentException("Key " + ArgsMap.assignee_id + " not be null");
            mapValue = map.get(ArgsMap.task_state.toString());
            isEmptyOrNull(mapValue, ArgsMap.task_state);
        }

        for (TaskState e : TaskState.values()) {
            if (mapValue.equals(e.toString())) {
                isValid = true;
                break;
            }
        }
        if (!isValid)
            throw new IllegalArgumentException(ArgsMap.task_state + " must contain one of the options" + Arrays.toString(TaskState.values()));
    }

    private static void isEmptyOrNull(String mapValue, ArgsMap e) {
        if (mapValue == null || mapValue.isEmpty())
            throw new IllegalArgumentException("Key " + e.toString() + " not be empty or null");
    }

    private enum ArgsMap {
        task_id, //unique, not null, not empty
        assignee_id, //not null
        task_state; //not null -> TaskState
        final static int NUM_ELEMENTS_MAP = 3;
    }

    private enum TaskState {
        active, disabled
    }
}
