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
import embeddedproject.takethepill.therapyEntityDB;

import static database.Str.*;

/**
 * Created by Cristian on 13/04/2018.
 * Class that avoid to create and use database
 */

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DatabaseVersion=4;
        private static final String DatabaseName="PillDb";
        public DatabaseHelper(Context context){
            super(context,DatabaseName,null,DatabaseVersion);

            Log.d("costruttore db","ok");
            SQLiteDatabase db=getWritableDatabase();
        }
        //create Database
        @Override
        public void onCreate(SQLiteDatabase db)
            {
                db.execSQL(CREATE_TYPE_TABLE);
                Log.d("tabella type ", "creata");
                db.execSQL(CREATE_DRUG_TABLE);
                Log.d("tabella drug ", "creata");
                db.execSQL(CREATE_HOUR_TABLE);
                Log.d("tabella hour ", "creata");
                db.execSQL(CREATE_THERAPY_TABLE);
                Log.d("tabella terapy ", "creata");
                db.execSQL(CREATE_ASSUMPTION_TABLE);
                Log.d("tabella assumption ", "creata");
                db.execSQL(CREATE_MOMENT_TABLE);
                Log.d("tabella moment ", "creata");

            }
        @Override
        public void onUpgrade(SQLiteDatabase db, int newDb, int old){
        //delete table
            db.execSQL("DROP TABLE IF EXISTS "+Str.therapyTable);
            db.execSQL("DROP TABLE IF EXISTS "+Str.momentTable);
            db.execSQL("DROP TABLE IF EXISTS "+Str.hourTable);
            db.execSQL("DROP TABLE IF EXISTS "+Str.assumptionTable);
            db.execSQL("DROP TABLE IF EXISTS "+Str.drugTable);
            db.execSQL("DROP TABLE IF EXISTS "+Str.typeTable);


            // Create tables again
            onCreate(db);
        }


    /**
     * Lazzarin
     * @param terapia: terapy we want to add on db
     * @return raw's id of new object we've add on db, -1 if it was inserted yet
     */

    public long insertTherapy(therapyEntityDB terapia) {
        //  write a new raw on database
                SQLiteDatabase db = getWritableDatabase();
                if(getTherapy(terapia.getID())!=null)
                    {
                        Log.d("inserimento","tentato ma fallito");
                        return -1;
                    }
                ContentValues toInsert=terapia.getAllValues();
                long id = db.insert(Str.therapyTable, null, toInsert);
                Log.d("avvenuto:","inserimento terapia");
                // close db connection
                db.close();

                return id;
    }


    public List<therapyEntityDB> getAllTherapy()
    {
        List<therapyEntityDB> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery(getAllTerapy,null);
        if(!cursor.moveToFirst())
            Log.d("getAllTherapy","No therapy found");
        do {
            therapyEntityDB current=new therapyEntityDB();
            current.setDateEnd(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateEnd))));
            current.setDateStart(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateStart))));
            current.setDays(cursor.getInt(cursor.getColumnIndex(therapyNumberDays)));
            current.setID(cursor.getString(cursor.getColumnIndex(therapyID)));
            current.setNotify(cursor.getShort(cursor.getColumnIndex(therapyNotify)));
            current.setMon(cursor.getInt(cursor.getColumnIndex(therapyMon)));
            current.setTue(cursor.getInt(cursor.getColumnIndex(therapyTue)));
            current.setThu(cursor.getInt(cursor.getColumnIndex(therapyThu)));
            current.setWed(cursor.getInt(cursor.getColumnIndex(therapyWed)));
            current.setFri(cursor.getInt(cursor.getColumnIndex(therapyFri)));
            current.setSat(cursor.getInt(cursor.getColumnIndex(therapySat)));
            current.setSun(cursor.getInt(cursor.getColumnIndex(therapySun)));
            list.add(current);

        }
        while(cursor.moveToNext());
        cursor.close();
        db.close();
        return list;
    }

    public therapyEntityDB getTherapy(String ID)
        {
            SQLiteDatabase db=getReadableDatabase();
            String query="SELECT * FROM "+ therapyTable +" WHERE "+therapyID + "=?";
            Cursor cursor=db.rawQuery(query,new String[] {ID});
            if((cursor.getCount()==0)||(cursor.getCount()==-1))
            {
                Log.d("therapy ",ID+" not found");
                return null;
            }
            int count=cursor.getCount();
            Log.d("sto marso de count",count+"");
            therapyEntityDB current=new therapyEntityDB();
            cursor.moveToFirst(); //N.B!!!! sennò dà errore
            Log.d("formato data stringa..",cursor.getString(cursor.getColumnIndex(therapyDateEnd)));
            current.setDateEnd(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateEnd))));
            current.setDateStart(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateStart))));
            current.setDays(cursor.getInt(cursor.getColumnIndex(therapyNumberDays)));
            current.setID(cursor.getString(cursor.getColumnIndex(therapyID)));
            current.setNotify(cursor.getShort(cursor.getColumnIndex(therapyNotify)));
            current.setMon(cursor.getInt(cursor.getColumnIndex(therapyMon)));
            current.setTue(cursor.getInt(cursor.getColumnIndex(therapyTue)));
            current.setThu(cursor.getInt(cursor.getColumnIndex(therapyThu)));
            current.setWed(cursor.getInt(cursor.getColumnIndex(therapyWed)));
            current.setFri(cursor.getInt(cursor.getColumnIndex(therapyFri)));
            current.setSat(cursor.getInt(cursor.getColumnIndex(therapySat)));
            current.setSun(cursor.getInt(cursor.getColumnIndex(therapySun)));
            current.setDrug(cursor.getString(cursor.getColumnIndex(therapyDrug)));
            db.close();
            cursor.close();
            return current;
        }





    public long updateTherapy(therapyEntityDB toUpdate)
    {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/mm/yyyy");
        ContentValues values = new ContentValues();
        values.put(therapyID,toUpdate.getID());
        values.put(therapyDrug, toUpdate.getDrug());
        values.put(therapyDateStart, myFormat.format(toUpdate.getDateStart()));
        values.put(therapyDateEnd, myFormat.format(toUpdate.getDateEnd()));
        values.put(therapyNumberDays,toUpdate.getDays());
        values.put(therapyNotify,toUpdate.getNotify());
        values.put(therapyMon,toUpdate.isMon());
        values.put(therapyTue,toUpdate.isTue());
        values.put(therapyWed, toUpdate.isWed());
        values.put(therapyThu, toUpdate.isThu());
        values.put(therapyFri, toUpdate.isFri());
        values.put(therapySat, toUpdate.isSat());
        values.put(therapySun, toUpdate.isSun());
        // update row
        long id = db.update(therapyTable,values,therapyID+"=?",new String[]{toUpdate.getID()});
        Log.d("avvenuto","aggiornamento terapia");
        // close db connection
        db.close();

        return id;
    }

    /**
     *
     * @param ID Therapy we want to delete
     * @return number of raws deleted(1 if found, 0 in other wise)
     */
    public int removeTherapyBYId(String ID)
    {
        SQLiteDatabase db=getReadableDatabase();
        int deleteStatus =db.delete(therapyTable,therapyID +"=?",new String[]{ID});
        db.close();
        return deleteStatus;

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
