package com.example.admin.feteldocuments

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.admin.feteldocuments.Model.ContentModel

class SQLiteHandler(var context:Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    companion object {
        private val DATABASE_NAME = "historydatabase"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "History"
        private val COL_TABLE_ID = "TABLEID"
        private val COL_CONTENT_ID = "CONTENTID"
        private val COL_NAME = "LECTURENAME"
        private val COL_URL = "URL"
    }
    override fun onCreate(db: SQLiteDatabase?) {
      val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  + COL_CONTENT_ID + " TEXT," + COL_NAME + " TEXT," + COL_URL + " TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    fun insertData(content : ContentModel){
        val db = this.writableDatabase
        val cv =ContentValues()
        cv.put(COL_CONTENT_ID,content.getContentID())
        cv.put(COL_NAME,content.getContentName())
        cv.put(COL_URL,content.getContentURL())
        val result = db.insert(TABLE_NAME,null,cv)
        if (result.equals(-1.toLong())) {
           Log.d("HISTORY","INSERT FAILED")
        } else {
            Log.d("HISTORY","INSERT SUCCEED")
        }
    }
    fun readData(): ArrayList<ContentModel>{
        var list  : ArrayList<ContentModel> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if (result.moveToLast()) {
            do {
                var content = ContentModel()
                content.setContentID(result.getString(result.getColumnIndex(COL_CONTENT_ID)).toString())
                content.setContentName(result.getString(result.getColumnIndex(COL_NAME)).toString())
                content.setContentURL(result.getString(result.getColumnIndex(COL_URL)).toString())
                list.add(content)
            } while (result.moveToPrevious())
        }
        db.close()
        result.close()
        return list
    }
    fun deleteData() {
        context!!.deleteDatabase(DATABASE_NAME)
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'")
        db.close()
    }
    fun isExists(contentID : String): Boolean{
        val db = this.readableDatabase
        var cursor : Cursor? = null
        val query = "Select * from " + TABLE_NAME + " where " + COL_CONTENT_ID + " = " + contentID
        cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.getCount() > 0) {
            return true
        }
        return false
    }
}