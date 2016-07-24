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
    public String LeaveStatus;

    public String ApplyDate;
    public String TermStart;
    public String TermEnd;
    public String ProcessDate;
    public String TitleEn;
    public String ConcurrencyType;
    public String UnitType;

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public Leave_Model(String fromLeave, String toLeave, String qunatity, String reason, String applytime, String isPaid, String leaveStatus, String applyDate, String termStart, String termEnd, String processDate, String titleEn, String concurrencyType, String unitType) {
        this.fromLeave = fromLeave;
        this.toLeave = toLeave;
        this.Qunatity = qunatity;
        this.reason = reason;
        this.applytime = applytime;
        this.isPaid = isPaid;
        this.LeaveStatus = leaveStatus;
        this.ApplyDate = applyDate;
        this.TermStart = termStart;
        this.TermEnd = termEnd;
        this.ProcessDate = processDate;
        this.TitleEn = titleEn;
        this.ConcurrencyType = concurrencyType;
        this.UnitType = unitType;

    }

    public String isPaid;

    public String getFromLeave() {
        return fromLeave;
    }

    public void setFromLeave(String fromLeave) {
        this.fromLeave = fromLeave;
    }

    public String getConcurrencyType() {
        return ConcurrencyType;
    }

    public void setConcurrencyType(String concurrencyType) {
        ConcurrencyType = concurrencyType;
    }

    public String getUnitType() {
        return UnitType;
    }

    public void setUnitType(String unitType) {
        UnitType = unitType;
    }

    public String getProcessDate() {
        return ProcessDate;
    }

    public void setProcessDate(String processDate) {
        ProcessDate = processDate;
    }

    public String getTermEnd() {
        return TermEnd;
    }

    public void setTermEnd(String termEnd) {
        TermEnd = termEnd;
    }

    public String getTermStart() {
        return TermStart;
    }

    public void setTermStart(String termStart) {
        TermStart = termStart;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getToLeave() {
        return toLeave;
    }

    public void setToLeave(String toLeave) {
        this.toLeave = toLeave;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getQunatity() {
        return Qunatity;
    }

    public void setQunatity(String qunatity) {
        Qunatity = qunatity;
    }


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


}
