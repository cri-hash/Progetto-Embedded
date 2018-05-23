package embeddedproject.takethepillkv

import android.content.ContentValues
import android.util.Log

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

import database.Str

public class TherapyEntityDB {
    //variabili rese pubbliche, per poter sfruttare i getter ed i setter impliciti di Kotlin
     var mID : Int?=null
     var mDrug: String?=null
     var mDateStart : Date?=null
     var mDateEnd :Date?=null
     var mNotify : Int =20   //-1=nessuna notifica, 0=notifica allo stesso momento, >0=min prima
     var mDays : Int=1     //-2=DataFine, -1=Senza limiti
     var mMon : Boolean=false
     var mTue : Boolean=false
     var mWed : Boolean=false
     var mThu : Boolean=false
     var mFri : Boolean=false
     var mSat : Boolean=false
     var mSun : Boolean=false
     var mDosaggio: Int=0
    val stringa: String="ciao";

//serve...?
    public fun TherapyEntityDB()
    {
        mID=null
        mDrug=null
        mDateStart=null
        mDateEnd =null
        mNotify=20
        mDays=1
        mMon=false
        mTue=false
        mWed=false
        mThu=false
        mFri=false
        mSat=false
        mSun=false
        mDosaggio=0
    }

     fun TherapyEntityDB( dataFine: Date?,  nGiorni: Int, minNotifica: Int,
            lun:Boolean, mar:Boolean, mer:Boolean, gio:Boolean, ven:Boolean, sab:Boolean, dom:Boolean,
     dosaggio: Int, nomeFarmaco: String){

        mID = null //?????

         val c: Calendar = Calendar.getInstance()    // Data di oggi
        mDateStart=  Date(c.YEAR, c.MONTH, c.DAY_OF_MONTH);

        // Uno tra dataFine e nGiorni deve essere NULL
        this.mDateEnd=dataFine
        this.mDays=nGiorni

        this.mNotify=minNotifica
        this.mMon=lun
        this.mTue=mar
        this.mWed=mer
        this.mThu=gio
        this.mFri=ven
        this.mSat=sab
        this.mSun=dom
        this.mDosaggio=dosaggio


        this.mDrug=nomeFarmaco
    }

    // Metodo per ottere la lista dei giorni della settimana in stringa
     fun  getGiorni() : String{
        var s =""
        var count=0
        if(mMon){
            s="Lun"
            count++
        }
        if(mTue){
            if(count>0) s+=", "
            s+="Mar"
            count++
        }
        if(mWed){
            if(count>0) s+=", "
            s+="Mer"
            count++
        }
        if(mThu){
            if(count>0) s+=", "
            s+="Gio"
            count++
        }
        if(mFri){
            if(count>0) s+=", "
            s+="Ven"
            count++
        }
        if(mSat){
            if(count>0) s+=", "
            s+="Sab"
            count++
        }
        if(mSun){
            if(count>0) s+=", "
            s+="Dom"
            count++
        }
        return s
    }





 fun getAllValues() : ContentValues
    {   val current=  ContentValues()
        current.put(,mDrug);
        val myFormat=SimpleDateFormat("dd/mm/yyyy");
        //Log.d("data letta da therapy",myFormat.format(mDateEnd));

        if(mDateEnd==null)current.put(Str.therapyDateEnd,(String)null);
        else current.put(Str.therapyDateEnd,myFormat.format(mDateEnd));

        current.put(Str.therapyDateStart,myFormat.format(mDateStart));
        current.put(Str.therapyNotify,mNotify);
        current.put(Str.therapyNumberDays,mDays);
        current.put(Str.therapyID,mID);
        current.put(Str.therapyDosage,mDosaggio);
        current.put(Str.therapyMon,checkBool(mMon));
        current.put(Str.therapyTue,checkBool(mTue));
        current.put(Str.therapyWed,checkBool(mWed));
        current.put(Str.therapyThu,checkBool(mThu));
        current.put(Str.therapyFri,checkBool(mFri));
        current.put(Str.therapySat,checkBool(mSat));
        current.put(Str.therapySun,checkBool(mSun));

        return current;
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



}

