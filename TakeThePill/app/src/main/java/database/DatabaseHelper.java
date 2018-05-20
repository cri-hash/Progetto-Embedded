package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import embeddedproject.takethepill.AssumptionEntity;
import embeddedproject.takethepill.DrugEntity;
import embeddedproject.takethepill.TherapyEntityDB;

import static database.Str.*;

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DatabaseVersion=13;
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
                //db.execSQL(CREATE_HOUR_TABLE);
                //Log.d("tabella hour ", "creata");
                db.execSQL(CREATE_THERAPY_TABLE);
                Log.d("tabella terapy ", "creata");
                db.execSQL(CREATE_ASSUMPTION_TABLE);
                Log.d("tabella assumption ", "creata");
                // db.execSQL(CREATE_MOMENT_TABLE);
                //Log.d("tabella moment ", "creata");
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



/////////////////////////////////////////////////////////////////
////////////////////////////// TERAPIE /////////////////////////
/////////////////////////////////////////////////////////////////
    public long insertTherapy(TherapyEntityDB terapia) {
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


    public List<TherapyEntityDB> getAllTherapies(){
        List<TherapyEntityDB> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery(getAllTherapies,null);
        if(!cursor.moveToFirst()) Log.d("getAllTherapy","No therapy found");
        else{
            do {
                TherapyEntityDB current=new TherapyEntityDB();
                if(cursor.getString(cursor.getColumnIndex(therapyDateEnd))==null)current.setDateEnd(null);
                else current.setDateEnd(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateEnd))));
                current.setDateStart(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateStart))));
                current.setDays(cursor.getInt(cursor.getColumnIndex(therapyNumberDays)));
                current.setID(cursor.getInt(cursor.getColumnIndex(therapyID)));
                current.setNotify(cursor.getInt(cursor.getColumnIndex(therapyNotify)));
                current.setMon(checkInt(cursor.getInt(cursor.getColumnIndex(therapyMon))));
                current.setTue(checkInt(cursor.getInt(cursor.getColumnIndex(therapyTue))));
                current.setThu(checkInt(cursor.getInt(cursor.getColumnIndex(therapyThu))));
                current.setWed(checkInt(cursor.getInt(cursor.getColumnIndex(therapyWed))));
                current.setFri(checkInt(cursor.getInt(cursor.getColumnIndex(therapyFri))));
                current.setSat(checkInt(cursor.getInt(cursor.getColumnIndex(therapySat))));
                current.setSun(checkInt(cursor.getInt(cursor.getColumnIndex(therapySun))));
                current.setDrug(cursor.getString(cursor.getColumnIndex(therapyDrug)));
                current.setDosage(cursor.getInt(cursor.getColumnIndex(therapyDosage)));
                list.add(current);

            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public TherapyEntityDB getTherapy(Integer ID){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ therapyTable +" WHERE "+therapyID + "=?";
        Cursor cursor=db.rawQuery(query,new String[] {ID+""});
        if((cursor.getCount()==0)||(cursor.getCount()==-1)){
            Log.d("therapy ",ID+" not found");
            return null;
        }
        int count=cursor.getCount();
        Log.d("sto marso de count",count+"");
        TherapyEntityDB current=new TherapyEntityDB();
        cursor.moveToFirst(); //N.B!!!! sennò dà errore
        //Log.d("formato data stringa..",cursor.getString(cursor.getColumnIndex(therapyDateEnd)));
        if(cursor.getString(cursor.getColumnIndex(therapyDateEnd))==null)current.setDateEnd(null);
        else current.setDateEnd(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateEnd))));
        current.setDateStart(stringToDate(cursor.getString(cursor.getColumnIndex(therapyDateStart))));
        current.setDays(cursor.getInt(cursor.getColumnIndex(therapyNumberDays)));
        current.setID(cursor.getInt(cursor.getColumnIndex(therapyID)));
        current.setNotify(cursor.getInt(cursor.getColumnIndex(therapyNotify)));
        current.setMon(checkInt(cursor.getInt(cursor.getColumnIndex(therapyMon))));
        current.setTue(checkInt(cursor.getInt(cursor.getColumnIndex(therapyTue))));
        current.setThu(checkInt(cursor.getInt(cursor.getColumnIndex(therapyThu))));
        current.setWed(checkInt(cursor.getInt(cursor.getColumnIndex(therapyWed))));
        current.setFri(checkInt(cursor.getInt(cursor.getColumnIndex(therapyFri))));
        current.setSat(checkInt(cursor.getInt(cursor.getColumnIndex(therapySat))));
        current.setSun(checkInt(cursor.getInt(cursor.getColumnIndex(therapySun))));
        current.setDrug(cursor.getString(cursor.getColumnIndex(therapyDrug)));
        current.setDosage(cursor.getInt(cursor.getColumnIndex(therapyDosage)));
        db.close();
        cursor.close();
        return current;
    }

    public long updateTherapy(TherapyEntityDB toUpdate)
    {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/MM/yyyy");
        ContentValues values = new ContentValues();
        values.put(therapyID,toUpdate.getID());
        values.put(therapyDrug, toUpdate.getDrug());
        values.put(therapyDateStart, myFormat.format(toUpdate.getDateStart()));

        if(toUpdate.getDateEnd()==null)values.put(therapyDateEnd, (String)null);
        else values.put(therapyDateEnd, myFormat.format(toUpdate.getDateEnd()));

        values.put(therapyNumberDays,toUpdate.getDays());
        values.put(therapyNotify,toUpdate.getNotify());
        values.put(therapyMon, checkBool(toUpdate.isMon()));
        values.put(therapyTue, checkBool(toUpdate.isTue()));
        values.put(therapyWed, checkBool(toUpdate.isWed()));
        values.put(therapyThu, checkBool(toUpdate.isThu()));
        values.put(therapyFri, checkBool(toUpdate.isFri()));
        values.put(therapySat, checkBool(toUpdate.isSat()));
        values.put(therapySun, checkBool(toUpdate.isSun()));
        values.put(therapyDosage,toUpdate.getDosaggio());
        // update row
        long id = db.update(therapyTable,values,therapyID+"=?",new String[]{toUpdate.getID()+""});
        Log.d("avvenuto","aggiornamento terapia");
        // close db connection
        db.close();
        return id;
    }

    public int removeTherapyBYId(int ID){
        SQLiteDatabase db=getWritableDatabase();
        int deleteStatus =db.delete(therapyTable,therapyID +"=?",new String[]{ID+""});
        db.close();
        return deleteStatus;
    }

    // Ottenere la lista delle ore di una terapia
    public List<Time> getTherapyHour(TherapyEntityDB th){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT DISTINCT "+assumptionHour+" FROM "+assumptionTable +" WHERE "+ assumptiontherapy
                +" = "+th.getID(),null);

        if(cursor.getCount()==0){
            Log.d("assumption error","nessun orario trovato per la terapia inserita");
            return null;
        }

        List<Time> list = new ArrayList<>();
        cursor.moveToFirst();
        do{
            String time=cursor.getString(cursor.getColumnIndex(assumptionHour));
            Time hour=Time.valueOf(time);

            Log.d("letto orario:",hour.toString());

            list.add(hour);
        }
        while(cursor.moveToNext());

        return list;
    }


/////////////////////////////////////////////////////////////////
////////////////////////////// FARMACI //////////////////////////
/////////////////////////////////////////////////////////////////
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


    public DrugEntity getDrugByName(String name) {

        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ drugTable +" WHERE "+ drugName+ "=?";
        Cursor cursor=db.rawQuery(query,new String[] {name+""});
        if((cursor.getCount()==0)||(cursor.getCount()==-1)){
            Log.d("getDrugByName",name+" non trovata");
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


    public int removeDrugBYName(String nome){
        SQLiteDatabase db=getWritableDatabase();
        int deleteStatus =db.delete(drugTable,drugName +"=?",new String[]{nome+""});
        db.close();
        return deleteStatus;
    }


    public List<DrugEntity> getAllDrugs(){
        List<DrugEntity> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery(getAllDrugs,null);
        if(!cursor.moveToFirst())
            Log.d("getAllDrugs","Nessuna terapia trovata");
        else{
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
        }

        cursor.close();
        db.close();
        return list;
    }

    public long updateDrug(DrugEntity toUpdate){
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
        Log.d("updateDrug","aggiornata");
        // close db connection
        db.close();

        return id;
    }



/////////////////////////////////////////////////////////////////
////////////////////////////// TIPI /////////////////////////////
/////////////////////////////////////////////////////////////////
    // Richiamata all'avvio dell'applicazione
    private void setTypeList(SQLiteDatabase db){
        ContentValues[] values=new ContentValues[13];   // Abbiamo 13 tipi predefiniti
        for(int i=0;i<13;i++) values[i]=new ContentValues();
        values[0].put(typeName,"Applicazione/i");
        values[1].put(typeName,"Capsula/e");
        values[2].put(typeName,"Fiala/e");
        values[3].put(typeName,"Goccia/e");
        values[4].put(typeName,"Grammo/i");
        values[5].put(typeName, "Inalazione/i");
        values[6].put(typeName,"Iniezione/i");
        values[7].put(typeName,"Milligrammo/i");
        values[8].put(typeName,"Millilitro/i");
        values[9].put(typeName, "Pezzo/i");
        values[10].put(typeName,"Pillola/e");
        values[11].put(typeName,"Supposta/e");
        values[12].put(typeName,"Unità");

        for(int i=0;i<13;i++) db.insert(typeTable,null,values[i]);
      //db non viene chiuso perchè lo fa già il metodo onCreate, dopo aver chiamato questo metodo

    }

    public String[] getTypeList(){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ typeTable+" ORDER BY "+ typeName;
        Cursor cursor=  db.rawQuery(query,null);
        cursor.moveToFirst();
        //create an array using size of query's result
        String[] list=new String[cursor.getCount()];
        int i=0;
        do{
            list[i]=cursor.getString(cursor.getColumnIndex(typeName));
            i++;
            Log.d("getTypeList()","Inserito: "+cursor.getString(cursor.getColumnIndex(typeName)));
        }
        while(cursor.moveToNext());
        cursor.close();
        db.close();
        return list;
    }



////////////////////////////////////////////////////////////////////
////////////////////////////// ASSUNZIONI //////////////////////////
////////////////////////////////////////////////////////////////////
    public long insertAssumption(AssumptionEntity assumption){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues toInsert = new ContentValues();
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/MM/yyyy");

        String data=myFormat.format(assumption.getData());
        toInsert.put(assumptionDate,data);
        toInsert.put(assumptionHour,assumption.getOra().toString()); //formato di default hh:mm:ss. Per riconvertire:valueOf(String)
        toInsert.put(assumptiontherapy,assumption.getTerapia());
        if(assumption.getStato())
            toInsert.put(assumptionState,1); //medicina presa
        else
            toInsert.put(assumptionState,0); //medicina non presa

        long id = db.insert(assumptionTable, null, toInsert);
        Log.d("insertAssuption()", "inserimento assunzione di TerapiaID: "+assumption.getTerapia()+", Data:"+data);
        // close db connection
        db.close();

        return id;
    }

    public int removeAssumption(AssumptionEntity assumption){
        SQLiteDatabase db=getWritableDatabase();

        SimpleDateFormat myFormat=new SimpleDateFormat("dd/MM/yyyy");
        String data=myFormat.format(assumption.getData());

        String ora=assumption.getOra().toString();
        int deleteStatus =db.delete(drugTable,new String[]{assumptionDate,assumptionHour,assumptiontherapy}
        +"=?",new String[]{data,ora,assumption.getTerapia()+""});
        db.close();
        return deleteStatus;
    }
    public int removeAssumptionByTherapy(TherapyEntityDB therapy){
        SQLiteDatabase db=getWritableDatabase();

        int deleteStatus =db.delete(assumptionTable,assumptiontherapy+"="+therapy.getID().toString(),null);
        db.close();
        return deleteStatus;
    }

    public void setAssumption(AssumptionEntity assumption, boolean state){
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/MM/yyyy");

        String data=myFormat.format(assumption.getData());
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(assumptionState,state);

        int stato=0;
        if(state) stato=1;

        db.update(assumptionTable,values,
             assumptionDate+"=? and "+assumptionHour+"=? and "+assumptiontherapy+"=?",
             new String[]{data,assumption.getOra().toString(),String.valueOf(assumption.getTerapia())});
        /*String q="UPDATE "+assumptionTable+" SET "+assumptionState+ "="+stato+" WHERE "
             +assumptionDate+"='"+data+"' AND "+assumptionHour+"='"+assumption.getOra().toString()
             +"' AND "+assumptiontherapy+"="+String.valueOf(assumption.getTerapia())+";";

        db.rawQuery(q,null);
        Log.d("Update:",q);*/
    }

    // Lista delle Assunzioni di oggi
    public List<AssumptionEntity> getAssumptionByDate(Date data){
        SQLiteDatabase db=getReadableDatabase();
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/MM/yyyy");

        String dataToRead=myFormat.format(data);
        //Query di ricerca
        Cursor current=db.rawQuery("SELECT "+assumptionDate+","+assumptionHour+","+assumptionState+","+therapyDrug+","+therapyDosage
          +","+assumptiontherapy+" FROM "+assumptionTable+" INNER JOIN "+ therapyTable+" ON "+ assumptiontherapy+"="+therapyID
                +" WHERE "+assumptionDate+ "='"+ dataToRead+"'" , null);

        List<AssumptionEntity> list=new ArrayList<>();

        if(current.getCount()==0) //nessuna assunzione con quella data
        { Log.d("nessuna assunzione in data",dataToRead);
            //return null;
        }
        else{
            current.moveToFirst();
            do{
                //inserisco nella lista tutte le assunzioni trovate

                String farmaco=current.getString(current.getColumnIndex(therapyDrug));
                //query per leggere il tipo(uso un cursore temporaneo)
                Cursor temp=db.rawQuery("SELECT "+typeName
                        +" FROM "+drugTable+" INNER JOIN "+ typeTable+" ON "+ drugType+"="+typeName
                        +" WHERE "+drugName+ "='"+ farmaco+"'", null);
                temp.moveToFirst();
                String tipo=temp.getString(temp.getColumnIndex(typeName));
                temp.close();
                Time ora=Time.valueOf(current.getString(current.getColumnIndex(assumptionHour)));
                Boolean stato=checkInt(current.getInt(current.getColumnIndex(assumptionState)));
                Log.d("Stato Assunzione:",current.getInt(current.getColumnIndex(assumptionState))+stato.toString());
                int dosaggio=current.getInt(current.getColumnIndex(therapyDosage));
                int terapiaId=current.getInt(current.getColumnIndex(assumptiontherapy));
                AssumptionEntity assunzione=new AssumptionEntity(data,ora,farmaco,stato,dosaggio,tipo,terapiaId);
                list.add(assunzione);
            }
            while(current.moveToNext());
        }


        db.close();
        current.close();

        Log.d("lista assunzioni","creata");
        return list;

    }

    // Lista delle assunzioni di una Terapia(usata per le terapie con durata senza limiti)
    public List<AssumptionEntity> getAssumptionsByTherapy(TherapyEntityDB terapia){
        SQLiteDatabase db=getReadableDatabase();
        String query = "SELECT "+assumptionDate+","+assumptionHour+","+assumptionState+","+therapyDrug+","+therapyDosage +","+assumptiontherapy
                        +" FROM "+assumptionTable
                        +" INNER JOIN "+ therapyTable+" ON "+ assumptiontherapy+"="+therapyID
                        +" WHERE " + assumptiontherapy+"="+terapia.getID();

        List<AssumptionEntity> list=new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        if(!cursor.moveToFirst()) Log.d("getAssumptionsByTherapy","Nessuna assunzione trovata");
        else{
            do {
                // Tipo del farmaco
                String farmaco=cursor.getString(cursor.getColumnIndex(therapyDrug));
                String query2="SELECT "+typeName
                        +" FROM "+drugTable+" INNER JOIN "+ typeTable+" ON "+ drugType+"="+typeName
                        +" WHERE "+drugName+ "='"+ farmaco+"'";
                Cursor temp=db.rawQuery(query2, null);
                temp.moveToFirst();
                String tipo=temp.getString(temp.getColumnIndex(typeName));
                temp.close();

                Date data= stringToDate(cursor.getString(cursor.getColumnIndex(assumptionDate)));
                Time ora=Time.valueOf(cursor.getString(cursor.getColumnIndex(assumptionHour)));
                Boolean stato=checkInt(cursor.getInt(cursor.getColumnIndex(assumptionState)));
                Log.d("Stato Assunzione:",cursor.getInt(cursor.getColumnIndex(assumptionState))+stato.toString());
                int dosaggio=cursor.getInt(cursor.getColumnIndex(therapyDosage));
                int terapiaId=cursor.getInt(cursor.getColumnIndex(assumptiontherapy));
                AssumptionEntity assunzione=new AssumptionEntity(data,ora,farmaco,stato,dosaggio,tipo,terapiaId);
                list.add(assunzione);
            }
            while(cursor.moveToNext());
        }

        cursor.close();

        db.close();
        return list;
    }


////////////////////////////////////////////////////////////////////////////
////////////////////////////// METODI DI SUPPORTO //////////////////////////
///////////////////////////////////////////////////////////////////////////

    // Converte INT in BOOLEAN
    private boolean checkInt(int i){
        if(i==1)
            return true;
        else
            return false;

    }

    // Converte BOOLEAN in INT
    private int checkBool(boolean value){
        if(value) return 1;
        else return 0;
    }

    private Date stringToDate(String toDate){
        Date data=null;
        DateFormat dt= new SimpleDateFormat("dd/MM/yyyy");
        try{
            data=dt.parse(toDate);
        }
        catch(ParseException e){
            Log.d("parsing Data", "fallito");
        }
        return data;
    }


    // Usato in fase di test
    public boolean popolaDB(){
        /*SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+assumptionTable+";",null);
        c.moveToFirst();
        while(!c.isLast()) {
            String s=c.getString(c.getColumnIndex(assumptionDate)) + "|"
                + c.getString(c.getColumnIndex(assumptionHour)) + "|"
                + c.getString(c.getColumnIndex(assumptiontherapy)) + "|"
                + c.getString(c.getColumnIndex(assumptionState));
            Log.d("Tabella Assunzioni:",s);
            c.moveToNext();
        }*/

        return true;
    }



}
