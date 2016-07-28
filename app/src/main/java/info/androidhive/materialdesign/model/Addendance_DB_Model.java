/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package info.androidhive.materialdesign.model;

public class Addendance_DB_Model {
	
	//private variables
	int _id;
	String _EmpId;
	String _CompId;
	String _DateTime;
	String _IbeaconId;

	// Empty constructor
	public Addendance_DB_Model( String _EmpId, String _CompId, String _DateTime, String _IbeaconId) {
		this._EmpId = _EmpId;
		this._CompId = _CompId;
		this._DateTime = _DateTime;
		this._IbeaconId = _IbeaconId;
 	}

	public Addendance_DB_Model() {

	}
	// constructor

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_IbeaconId() {
		return _IbeaconId;
	}

	public void set_IbeaconId(String _IbeaconId) {
		this._IbeaconId = _IbeaconId;
	}

	public String get_DateTime() {
		return _DateTime;
	}

	public void set_DateTime(String _DateTime) {
		this._DateTime = _DateTime;
	}

	public String get_EmpId() {
		return _EmpId;
	}

	public void set_EmpId(String _EmpId) {
		this._EmpId = _EmpId;
	}

	public String get_CompId() {
		return _CompId;
	}

	public void set_CompId(String _CompId) {
		this._CompId = _CompId;
	}

	public Addendance_DB_Model(int id,String _EmpId, String _CompId, String _DateTime, String _IbeaconId) {
		this._EmpId = _EmpId;
		this._CompId = _CompId;
		this._DateTime = _DateTime;
		this._IbeaconId = _IbeaconId;
		this._id=id;
	}
}
