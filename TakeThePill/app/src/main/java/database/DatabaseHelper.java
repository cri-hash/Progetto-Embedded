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

import embeddedproject.takethepill.AssumptionEntity;
import embeddedproject.takethepill.DrugEntity;
import embeddedproject.takethepill.therapyEntityDB;

import static database.Str.*;

/**
 * Created by Cristian on 13/04/2018.
 * Class that avoid to create and use database
 */

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DatabaseVersion=1;
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
                setTypeList(db);
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
            current.setMon(checkInt(cursor.getInt(cursor.getColumnIndex(therapyMon))));
            current.setTue(checkInt(cursor.getInt(cursor.getColumnIndex(therapyTue))));
            current.setThu(checkInt(cursor.getInt(cursor.getColumnIndex(therapyThu))));
            current.setWed(checkInt(cursor.getInt(cursor.getColumnIndex(therapyWed))));
            current.setFri(checkInt(cursor.getInt(cursor.getColumnIndex(therapyFri))));
            current.setSat(checkInt(cursor.getInt(cursor.getColumnIndex(therapySat))));
            current.setSun(checkInt(cursor.getInt(cursor.getColumnIndex(therapySun))));
            current.setDrug(cursor.getString(cursor.getColumnIndex(therapyDrug)));
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
            current.setMon(checkInt(cursor.getInt(cursor.getColumnIndex(therapyMon))));
            current.setTue(checkInt(cursor.getInt(cursor.getColumnIndex(therapyTue))));
            current.setThu(checkInt(cursor.getInt(cursor.getColumnIndex(therapyThu))));
            current.setWed(checkInt(cursor.getInt(cursor.getColumnIndex(therapyWed))));
            current.setFri(checkInt(cursor.getInt(cursor.getColumnIndex(therapyFri))));
            current.setSat(checkInt(cursor.getInt(cursor.getColumnIndex(therapySat))));
            current.setSun(checkInt(cursor.getInt(cursor.getColumnIndex(therapySun))));
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
        values.put(therapyMon, checkBool(toUpdate.isMon()));
        values.put(therapyTue, checkBool(toUpdate.isTue()));
        values.put(therapyWed, checkBool(toUpdate.isWed()));
        values.put(therapyThu, checkBool(toUpdate.isThu()));
        values.put(therapyFri, checkBool(toUpdate.isFri()));
        values.put(therapySat, checkBool(toUpdate.isSat()));
        values.put(therapySun, checkBool(toUpdate.isSun()));
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
    public int removeTherapyBYId(int ID)
    {
        SQLiteDatabase db=getReadableDatabase();
        int deleteStatus =db.delete(therapyTable,therapyID +"=?",new String[]{ID+""});
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
     * simple conversion method
     * @param i 1 or 0
     * @return true or false
     */
    private boolean checkInt(int i)
    {
        if(i==1)
            return true;
        else
            return false;

    }
    /**
     * simple conversion method
     * @param value true or false
     * @return 1 or 0
     */
    private int checkBool(boolean value)
    {
        if(value) return 1;
        else return 0;
    }

    /**
     * launched when db is created, set available types of drugs
     */
    private void setTypeList(SQLiteDatabase db)
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
      //db non viene chiuso perchè lo fa già il metodo onCreate, dopo aver chiamato questo metodo

    }

    /**
     *
      * @return a String array with all type of drug available
     */
    public String[] getTypeList()
    { SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ typeTable+" ORDER BY "+ typeName;
        Cursor cursor=  db.rawQuery(query,null);
        cursor.moveToFirst();
        //create an array using size of query's result
        String[] list=new String[cursor.getCount()];
        int i=0;
        do {
            list[i]=cursor.getString(cursor.getColumnIndex(typeName));
            Log.d("inserito nella lista:",cursor.getString(cursor.getColumnIndex(typeName)));
            }
        while(cursor.moveToNext());
        cursor.close();
        db.close();
        return list;}


    public long insertAssumption(AssumptionEntity assumption)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues toInsert = new ContentValues();
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/mm/yyyy");

        String data=myFormat.format(assumption.getData());
        toInsert.put(assumptionDate,data);
        toInsert.put(assumptionHour,assumption.getOra().toString()); //formato di default hh:mm:ss. Per riconvertire:valueOf(String)
        toInsert.put(assumptiontherapy,assumption.getTerapia());
        if(assumption.getStato())
            toInsert.put(assumptionState,1); //medicina presa
        else
            toInsert.put(assumptionState,0); //medicina non presa

        long id = db.insert(assumptionTable, null, toInsert);
        Log.d("avvenuto:", "inserimento assunzione");
        // close db connection
        db.close();

        return id;


    }


    public int removeAssumption(AssumptionEntity assumption)
    {
        SQLiteDatabase db=getReadableDatabase();

        SimpleDateFormat myFormat=new SimpleDateFormat("dd/mm/yyyy");
        String data=myFormat.format(assumption.getData());

        String ora=assumption.getOra().toString();
        int deleteStatus =db.delete(drugTable,new String[]{assumptionDate,assumptionHour,assumptiontherapy}
        +"=?",new String[]{data,ora,assumption.getTerapia()+""});
        db.close();
        return deleteStatus;
    }
     public long setAssumption(AssumptionEntity assumption, boolean state)
     {

         SimpleDateFormat myFormat=new SimpleDateFormat("dd/mm/yyyy");

         String data=myFormat.format(assumption.getData());
         SQLiteDatabase db=getWritableDatabase();
         ContentValues values=new ContentValues();
         values.put(assumptionState,state);

         long id = db.update(assumptionTable,values,new String[]{assumptionDate,assumptionHour,assumptiontherapy}
         +"=?",new String[]{data,assumption.getOra().toString(),assumption.getTerapia()+""});
         return id;
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
    }*/


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
