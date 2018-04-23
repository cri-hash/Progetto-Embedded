package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import embeddedproject.com.takethepillow.therapyEntity;

/**
 * Created by Cristian on 13/04/2018.
 * Class that avoid to create and use database
 */

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DatabaseVersion=1;
        private static final String DatabaseName="chemistrDb6";
        public DatabaseHelper(Context context){
            super(context,DatabaseName,null,DatabaseVersion);

            Log.d("costruttore db","ok");
            SQLiteDatabase db=getWritableDatabase();
        }
        //create Database
        @Override
        public void onCreate(SQLiteDatabase db)
            {
                db.execSQL(Str.CREATE_TYPE_TABLE);
                Log.d("tabella type ", "creata");
                db.execSQL(Str.CREATE_DRUG_TABLE);
                Log.d("tabella drug ", "creata");
                db.execSQL(Str.CREATE_HOUR_TABLE);
                Log.d("tabella hour ", "creata");
                db.execSQL(Str.CREATE_THERAPY_TABLE);
                Log.d("tabella terapy ", "creata");
                db.execSQL(Str.CREATE_ASSUMPTION_TABLE);
                Log.d("tabella assumption ", "creata");
                db.execSQL(Str.CREATE_MOMENT_TABLE);
                Log.d("tabella moment ", "creata");

            }
        @Override
        public void onUpgrade(SQLiteDatabase db, int newDb, int old){
        //delete table
            db.execSQL("DROP TABLE IF EXISTS drugs");

            // Create tables again
            onCreate(db);
        }


    /**
     * Lazzarin
     * @param terapia: terapy we want to add on db
     * @return raw's id of new object we've add on db
     */

    public long insertTerapy(therapyEntity terapia) {
        //  write a new raw on database
                SQLiteDatabase db = getWritableDatabase();
                ContentValues toInsert=terapia.getAllValues();
                long id = db.insert("drugs", null, toInsert);
                // close db connection
                db.close();

                return id;
    }


    public List<therapyEntity> getAllTherapy()
    {
        List<therapyEntity> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery(Str.getAllTerapy,null);
        if(!cursor.moveToFirst())
            Log.d("getAllTherapy","No therapy found");
        do {
            therapyEntity current=new therapyEntity();
            current.setDateEnd(stringToDate(cursor.getString(cursor.getColumnIndex(Str.therapyDateEnd))));
            current.setDateStart(stringToDate(cursor.getString(cursor.getColumnIndex(Str.therapyDateStart))));
            current.setDays(cursor.getInt(cursor.getColumnIndex(Str.therapyNumberDays)));
            current.setID(cursor.getString(cursor.getColumnIndex(Str.therapyID)));
            current.setNotify(cursor.getShort(cursor.getColumnIndex(Str.therapyNotify)));
            current.setMon(cursor.getInt(cursor.getColumnIndex(Str.therapyMon)));
            current.setTue(cursor.getInt(cursor.getColumnIndex(Str.therapyTue)));
            current.setThu(cursor.getInt(cursor.getColumnIndex(Str.therapyThu)));
            current.setWed(cursor.getInt(cursor.getColumnIndex(Str.therapyWed)));
            current.setFri(cursor.getInt(cursor.getColumnIndex(Str.therapyFri)));
            current.setSat(cursor.getInt(cursor.getColumnIndex(Str.therapySat)));
            current.setSun(cursor.getInt(cursor.getColumnIndex(Str.therapySun)));
            list.add(current);

        }
        while(cursor.moveToNext());
        cursor.close();
        db.close();
        return list;
    }



    /**
     *

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
private Date stringToDate(String toDate)
    {
        Date data=null;
        DateFormat dt= new SimpleDateFormat("dd/mm/yyyy");
        try{
         data=dt.parse(toDate);}
         catch(ParseException e)
         {Log.d("parsing Data", "fallito");}
         return data;


    }
}
