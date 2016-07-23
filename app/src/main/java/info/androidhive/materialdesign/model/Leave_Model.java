package info.androidhive.materialdesign.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class Leave_Model implements Serializable {
    public String fromLeave;
    public String toLeave;
    public String Qunatity;
    public String  reason;
    public String applytime;

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public Leave_Model(String applyDate, String applytime, String leaveStatus, String titleEn) {
        ApplyDate = applyDate;
        LeaveStatus = leaveStatus;
        TitleEn = titleEn;

        applytime=applytime;

    }

    public String isPaid;
    public String LeaveStatus;
    public String ApplyDate;
    public String TermStart;
    public String TermEnd;

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public String getLeaveStatus() {
        return LeaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        LeaveStatus = leaveStatus;
    }

    public String getTitleEn() {
        return TitleEn;
    }

    public void setTitleEn(String titleEn) {
        TitleEn = titleEn;
    }

    public String ProcessDate;
    public String TitleEn;
    public String ConcurrencyType;
    public String UnitType;
}
