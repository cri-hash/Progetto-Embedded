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

import embeddedproject.takethepill.DrugEntity;
import embeddedproject.takethepill.therapyEntityDB;

import static database.Str.*;

/**
 * Created by Cristian on 13/04/2018.
 * Class that avoid to create and use database
 */

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DatabaseVersion=6;
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
                setType(db);
                Log.d("tipi inseriti","ok");
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
     *               LASCIARE ID NULL( DOVREBBE INCREMENTARSI DA SOLO)
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


    public List<therapyEntityDB> getAllTherapies()
    {
        List<therapyEntityDB> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery(getAllTerapies,null);
        if(!cursor.moveToFirst())
            Log.d("getAllTherapy","No therapy found");
        do {
            therapyEntityDB current=new therapyEntityDB();
            current.setDateEnd(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateEnd))));
            current.setDateStart(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateStart))));
            current.setDays(cursor.getInt(cursor.getColumnIndex(therapyNumberDays)));
            current.setID(cursor.getInt(cursor.getColumnIndex(therapyID)));
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

    /**
     * search a therapy using ID
     * @param ID
     * @return therapy if fund, null in otherwise
     */
    public therapyEntityDB getTherapy(Integer ID)
        {
            SQLiteDatabase db=getReadableDatabase();
            String query="SELECT * FROM "+ therapyTable +" WHERE "+therapyID + "=?";
            Cursor cursor=db.rawQuery(query,new String[] {ID+""});
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
            current.setID(cursor.getInt(cursor.getColumnIndex(therapyID)));
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
        long id = db.update(therapyTable,values,therapyID+"=?",new String[]{toUpdate.getID()+""});
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
     * * Lazzarin
     * @param drug: drug we want to add on db
     * @return raw's id of new object we've add on db, -1 if it was inserted yet
     */
    public long insertDrug(DrugEntity drug) {
        SQLiteDatabase db = getWritableDatabase();

        if (getDrugByName(drug.getNome()) != null) {
            Log.d("inserimento fallito", "farmaco già presente");
            return -1;
        }
        ContentValues toInsert = drug.getAllValues();
        long id = db.insert(Str.drugTable, null, toInsert);
        Log.d("avvenuto:", "inserimento farmaco");
        // close db connection
        db.close();

        return id;
    }

    /**
     *
     * @param name of drug we want
     * @return drug if found, else null.
     */

    public DrugEntity getDrugByName(String name)
    {

        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ drugTable +" WHERE "+ drugName+ "=?";
        Cursor cursor=db.rawQuery(query,new String[] {name+""});
        if((cursor.getCount()==0)||(cursor.getCount()==-1))
        {
            Log.d("drug ",name+" not found");
            return null;
        }
        DrugEntity  current=new DrugEntity();
        cursor.moveToFirst();
        current.setNome(cursor.getString(cursor.getColumnIndex(drugName)));
        current.setDescrizione(cursor.getString(cursor.getColumnIndex(drugDescription)));
        current.setPrezzo(cursor.getDouble(cursor.getColumnIndex(drugPrice)));
        current.setScorte(cursor.getInt(cursor.getColumnIndex(drugQuantities)));
        current.setTipo(cursor.getString(cursor.getColumnIndex(drugType)));
        cursor.close();
        db.close();
        return current;

    }



    /**
     *
     * @param nome drug we want to delete
     * @return number of raw deleted(1 if found, 0 in other wise)
     */
    public int removeDrugBYName(String nome)
    {
        SQLiteDatabase db=getReadableDatabase();
        int deleteStatus =db.delete(drugTable,drugName +"=?",new String[]{nome+""});
        db.close();
        return deleteStatus;

    }

    public List<DrugEntity> getAllDrugs()
    {
        List<DrugEntity> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery(getAllDrugs,null);
        if(!cursor.moveToFirst())
            Log.d("getAllTherapy","No therapy found");
        do {
            DrugEntity  current=new DrugEntity();

            current.setNome(cursor.getString(cursor.getColumnIndex(drugName)));
            current.setDescrizione(cursor.getString(cursor.getColumnIndex(drugDescription)));
            current.setPrezzo(cursor.getDouble(cursor.getColumnIndex(drugPrice)));
            current.setScorte(cursor.getInt(cursor.getColumnIndex(drugQuantities)));
            current.setTipo(cursor.getString(cursor.getColumnIndex(drugType)));
            list.add(current);

        }
        while(cursor.moveToNext());
        cursor.close();
        db.close();
        return list;
    }

    public long updateDrug(DrugEntity toUpdate)
    {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(drugName,toUpdate.getNome());
        values.put(drugPrice,toUpdate.getPrezzo());
        values.put(drugDescription,toUpdate.getDescrizione());
        values.put(drugQuantities,toUpdate.getScorte());
        values.put(drugType,toUpdate.getTipo());
        // update row
        long id = db.update(drugTable,values,drugName+"=?",new String[]{toUpdate.getNome()+""});
        Log.d("avvenuto","aggiornamento farmaco");
        // close db connection
        db.close();

        return id;
    }

    /**
     * launched when db is created, set available types of drugs
     */
    private void setType(SQLiteDatabase db)
    {

        ContentValues[] values=new ContentValues[7];
        for(int i=0;i<7;i++)
            values[i]=new ContentValues();
        ContentValues prova=new ContentValues();
        prova.put("prova","riuscita");
        values[0].put(typeName,"pastiglia");
        values[1].put(typeName,"pillola");
        values[2].put(typeName,"iniezione");
        values[3].put(typeName,"supposta");
        values[4].put(typeName,"bustina");
        values[5].put(typeName, "gocce");
        values[6].put(typeName,"altro");
        for(int i=0;i<7;i++)
            db.insert(typeTable,null,values[i]);

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
