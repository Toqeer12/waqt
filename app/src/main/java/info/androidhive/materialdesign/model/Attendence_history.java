package info.androidhive.materialdesign.model;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class Attendence_history {
    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public Attendence_history(String check_in, String check_out, String check_in_time, String check_out_time, String total_work_hour, String employeeId) {
        this.check_in = check_in;
        this.check_out = check_out;
        this.total_work_hour=total_work_hour;
        this.employeeId=employeeId;
        this.check_in_time=check_in_time;
        this.check_out_time=check_out_time;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getTotal_work_hour() {
        return total_work_hour;
    }

    public void setTotal_work_hour(String total_work_hour) {
        this.total_work_hour = total_work_hour;
    }

    String check_in;
    String check_out;
    String total_work_hour;
    String employeeId;
    String check_in_time;
    String check_out_time;
}
