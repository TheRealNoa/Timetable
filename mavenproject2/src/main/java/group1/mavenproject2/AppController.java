package group1.mavenproject2;

/**
 *
 * @author noaca
 */
public class AppController {
    private AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    public void handleButtonAction(String label) {
        if (label.equals("Add Class")) {
            model.openDateStage();
        } else if (label.equals("Remove Class")) {
            model.openRemoveClassStage();
        } else if (label.equals("Display Timetable button")) {
            model.displayTimeTable();
        } else if (label.equals("Stop button")) {
            model.terminateConnection();
        } else if (label.equals("Move afternoon classes to mornings")) {
            model.afternoonToMorning();
        }
    }

}