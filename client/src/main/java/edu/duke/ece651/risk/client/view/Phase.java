package edu.duke.ece651.risk.client.view;

public class Phase {
    private String name;
    private View view;
    private String windowTitle;

    public Phase(View view, String windowTitle) {
        this.name = view.getName();
        this.view = view;
        this.windowTitle = windowTitle;
    }

    public String getName() {
        return this.name;
    }

    public View getView() {
        return this.view;
    }

    public String getWindowTitle() {
        return this.windowTitle;
    }
}
