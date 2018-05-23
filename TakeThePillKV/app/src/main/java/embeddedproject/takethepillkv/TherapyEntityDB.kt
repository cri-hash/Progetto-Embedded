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
       //istanza di un'oggetto Str, necessario per leggere le stringhe
     val str: Str=Str()

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
        mDateStart=  Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))  // da testare se funziona

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
        current.put(str.therapyDrug,mDrug)
        val myFormat=SimpleDateFormat("dd/mm/yyyy")
        //Log.d("data letta da therapy",myFormat.format(mDateEnd))

        if(mDateEnd==null)
            current.put(str.therapyDateEnd,null as? String)
        else
            current.put(str.therapyDateEnd,myFormat.format(mDateEnd))

        current.put(str.therapyDateStart,myFormat.format(mDateStart))
        current.put(str.therapyNotify,mNotify)
        current.put(str.therapyNumberDays,mDays)
        current.put(str.therapyID,mID)
        current.put(str.therapyDosage,mDosaggio)
        current.put(str.therapyMon,checkBool(mMon))
        current.put(str.therapyTue,checkBool(mTue))
        current.put(str.therapyWed,checkBool(mWed))
        current.put(str.therapyThu,checkBool(mThu))
        current.put(str.therapyFri,checkBool(mFri))
        current.put(str.therapySat,checkBool(mSat))
        current.put(str.therapySun,checkBool(mSun))

        return current;
    }

    /**
     * simple conversion method
     * @param value true or false
     * @return 1 or 0
     */
    private fun checkBool( value: Boolean) : Int
    {
        if(value) return 1
        else return 0
    }



}

