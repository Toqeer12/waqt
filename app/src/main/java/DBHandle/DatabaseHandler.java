package DBHandle;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.androidhive.materialdesign.model.Addendance_DB_Model;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "Waqat_DB";

	// Contacts table name
	private static final String TABLE_CONTACTS = "Waqt_Attendance";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_COMP_ID = "name";
	private static final String KEY_EMP_ID = "phone_number";
	private static final String KEY_DATETIME="DateTime";
	private static final String KEY_IBEACON_ID="ibeaconId";
	private static final String KEY_Status="status";
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_COMP_ID + " TEXT,"+ KEY_EMP_ID + " TEXT,"+ KEY_IBEACON_ID + " TEXT,"
				+ KEY_DATETIME + " TEXT,"+KEY_Status + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void addContact(Addendance_DB_Model contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_COMP_ID, contact.get_CompId()); // Addendance_DB_Model Name
		values.put(KEY_DATETIME, contact.get_DateTime());
		values.put(KEY_EMP_ID,contact.get_EmpId());
		values.put(KEY_IBEACON_ID,contact.get_IbeaconId());// Addendance_DB_Model Phone
		values.put(KEY_Status,contact.getStatus());
		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public Addendance_DB_Model getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
						KEY_EMP_ID,KEY_COMP_ID, KEY_DATETIME,KEY_IBEACON_ID,KEY_Status }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Addendance_DB_Model contact = new Addendance_DB_Model(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
				cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5));
		// return contact
		return contact;
	}

	// Getting All Contacts
	public List<Addendance_DB_Model> getAllContacts() {
		List<Addendance_DB_Model> contactList = new ArrayList<Addendance_DB_Model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Addendance_DB_Model contact = new Addendance_DB_Model();
				contact.set_id(Integer.parseInt(cursor.getString(0)));
				contact.set_CompId(cursor.getString(1));
				contact.set_EmpId(cursor.getString(2));
				contact.set_IbeaconId(cursor.getString(3));
				contact.set_DateTime(cursor.getString(4));
				contact.setStatus(cursor.getString(5));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}



	// Deleting single contact
	public void deleteContact(Addendance_DB_Model contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.get_id()) });
		db.close();
	}


	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
