package kfupm.clinic.model;

public class Action {
    private ActionType type;
    private Object data;

    public Action(ActionType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public ActionType getType() { return type; }
    public Object getData() { return data; }
}