package info.androidhive.materialdesign.model;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class Leave_Model {
    public Leave_Model(String status, String hour, String endLeave, String startLeave, String applied, String op) {
        this.status = status;
        this.hour = hour;
        this.endLeave = endLeave;
        this.startLeave = startLeave;
        this.applied = applied;
        this.op = op;
    }

    String startLeave;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartLeave() {
        return startLeave;
    }

    public void setStartLeave(String startLeave) {
        this.startLeave = startLeave;
    }

    public String getEndLeave() {
        return endLeave;
    }

    public void setEndLeave(String endLeave) {
        this.endLeave = endLeave;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getApplied() {
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }

    String endLeave;
    String hour;
    String status;
    String applied;
    String op;
}
