package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import embeddedproject.com.takethepillow.drugEntity;

/**
 * Created by Cristian on 13/04/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DatabaseVersion=1;
        private static final String DatabaseName="chemistrDb";
        public DatabaseHelper(Context context){
            super(context,DatabaseName,null,DatabaseVersion);

        }
        //create Database
        @Override
        public void onCreate(SQLiteDatabase db)
            {
                /*db.execSQL(Drug.CREATE_TABLE);
            */}
        @Override
        public void onUpgrade(SQLiteDatabase db, int newDb, int old){
        //delete table
            db.execSQL("DROP TABLE IF EXISTS drugs");

            // Create tables again
            onCreate(db);
        }


    /**
     * Lazzarin
     * @param farmaco drug we want to add on db.
     * @return raw's id of new object we've add on db
     */
     /*
    public long insertDrug(drugEntity farmaco) {
        // get writable database as we want to write data
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(Drug.columnName, farmaco.getName());
                values.put(Drug.columnPrice,farmaco.getPrice());
                values.put(Drug.columnEffect,farmaco.getScope());
                // insert row
                long id = db.insert("drugs", null, values);

                // close db connection
                db.close();

                return id;
    }

    /**
     *
     * @param name of drug we want to find
     * @return drugEntity object, if found, or null if not
     */
     /*
    public drugEntity getDrug(String name)
        {
            drugEntity find=new drugEntity();
            // get only readable database
            SQLiteDatabase db=getReadableDatabase();

            //do the query and save the result using a Cursor
            Cursor current=db.query(Drug.nameTable,new String[] {Drug.columnName, Drug.columnEffect, Drug.columnPrice},Drug.columnName
            +"=?",new String[]{name},null,null,null);
            return find;
        }



    /**
     *
     * @param selection define the order of selection. 1=order by name; 2=order by price; 3=order by effect
     * @return list of all element on this table, order like specified on selection. If no elements are found, return Null
     */
     /*
    public List<drugEntity> getAll(int selection)
        {
            String option;
            switch(selection) {
                case 1: {
                    option=Drug.columnName;

                break;}
                case 2: {
                    option=Drug.columnPrice;

                    break;}
                case 3:
                {
                    option=Drug.columnEffect;
                    break;
                }
                default:
                {option=Drug.columnName;
                break;}

            }
            String queryRequest= "SELECT * FROM "+ Drug.nameTable +"ORDER BY "+ option;
            SQLiteDatabase db=getReadableDatabase();   //try to use getReadable...
            Cursor current=db.rawQuery(queryRequest, null);
            if(!current.moveToFirst())
            {  Log.d("current tag","current is empty, no value found!");
                return null;}
            List<drugEntity> list =new ArrayList<>();
            do {
                drugEntity dr=new drugEntity();
                dr.setName(current.getString(current.getColumnIndex(Drug.columnName)));
                dr.setPrice(current.getFloat(current.getColumnIndex(Drug.columnPrice)));
                dr.setScope(current.getString(current.getColumnIndex(Drug.columnEffect)));
                list.add(dr);
            }
            while(current.moveToNext());// moveToNext return false if there aren't other object to read
            db.close();
            current.close();     // not sure if this line is request

            return list;
        }

    /**
     *
     * @return number of drugs storaged on the db
     */
     /*
 public int countElements()
    {
        int count=0;
        String queryRequest= "SELECT * FROM "+ Drug.nameTable;
        SQLiteDatabase db=getReadableDatabase();
        Cursor current =db.rawQuery(queryRequest,null);
        count=current.getCount();
        db.close();
        current.close();
        return 0;
    }

    /**
     *
     * @param drug object we want to update
     * @return  raw's id of new object we've update on db
     */
     /*
 public long updateDrug(drugEntity drug)
 {
     // get writable database as we want to write data
     SQLiteDatabase db = this.getWritableDatabase();

     ContentValues values = new ContentValues();

     values.put(Drug.columnName, drug.getName());
     values.put(Drug.columnPrice,drug.getPrice());
     values.put(Drug.columnEffect,drug.getScope());
     // update row
     long id = db.update("drugs",values,Drug.columnName+"=?",new String[]{drug.getName()});

     // close db connection
     db.close();

     return id;
 }
    public void deleteDrug(drugEntity drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Drug.nameTable, Drug.columnName+ " = ?",
                new String[]{drug.getName()});
        db.close();
    }

*/

}
