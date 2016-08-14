/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package info.androidhive.materialdesign.model;

/**
 * Created by Arrowtec on 8/13/2016.
 */
public class Apply_Leave

{
    public String LeaveType;
    public String LeaveTypeId;

    public String getLeaveTypeId() {
        return LeaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        LeaveTypeId = leaveTypeId;
    }

    public Apply_Leave(String leaveType,String LeaveTypeId) {
        this.LeaveType = leaveType;
        this.LeaveTypeId=LeaveTypeId;
    }

    public String getLeaveType() {

        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }
}
