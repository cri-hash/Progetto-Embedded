package embeddedproject.takethepill;

import android.content.ContentValues;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import database.Str;

/**
 * Created by Cristian on 13/04/2018.
 */

public class therapyEntityDB {
    private Integer mID;
    private String mDrug;
    private Date mDateStart;
    private Date mDateEnd;
    private Integer mNotify;
    private Integer mDays;
    private boolean mMon, mTue, mWed, mThu, mFri, mSat, mSun;
    private int mDosaggio;

    public therapyEntityDB()
    {
        mID=null;
        mDrug=null;
        mDateStart=null;
        mDateEnd =null;
        mNotify=20;
        mDays=1;
        mMon=false;
        mTue=false;
        mWed=false;
        mThu=false;
        mFri=false;
        mSat=false;
        mSun=false;
        mDosaggio=0;
    }

    public therapyEntityDB(Date dataFine, Integer nGiorni, Integer minNotifica,
                         Boolean lun, Boolean mar, Boolean mer, Boolean gio,
                         Boolean ven, Boolean sab, Boolean dom,
                         Integer dosaggio, String nomeFarmaco){

        mID = null; //?????

        Calendar c = Calendar.getInstance();    // Data di oggi
        mDateStart= new java.util.Date(c.YEAR, c.MONTH, c.DAY_OF_MONTH);

        // Uno tra dataFine e nGiorni deve essere NULL
        this.mDateEnd=dataFine;
        this.mDays=nGiorni;

        this.mNotify=minNotifica;
        this.mMon=lun;
        this.mTue=mar;
        this.mWed=mer;
        this.mThu=gio;
        this.mFri=ven;
        this.mSat=sab;
        this.mSun=dom;
        this.mDosaggio=dosaggio;


        this.mDrug=nomeFarmaco;
    }

    // Metodo per ottere la lista dei giorni della settimana in stringa
    public String getGiorni(){
        String s="";
        Integer count=0;
        if(mMon){
            s="Lun";
            count++;
        }
        if(mTue){
            if(count>0) s+=", ";
            s+="Mar";
            count++;
        }
        if(mWed){
            if(count>0) s+=", ";
            s+="Mer";
            count++;
        }
        if(mThu){
            if(count>0) s+=", ";
            s+="Gio";
            count++;
        }
        if(mFri){
            if(count>0) s+=", ";
            s+="Ven";
            count++;
        }
        if(mSat){
            if(count>0) s+=", ";
            s+="Sab";
            count++;
        }
        if(mSun){
            if(count>0) s+=", ";
            s+="Dom";
            count++;
        }
        return s;
    }





    public Integer getID() {
        return mID;
    }
    public String getDrug(){return mDrug;}
    public void setDrug(String drug){mDrug=drug;}

    public void setID(Integer ID) {
        mID = ID;
    }

    public Date getDateStart() {
        return mDateStart;
    }

    public void setDateStart(Date dateStart) {
        mDateStart = dateStart;
    }

    public Date getDateEnd() {
        return mDateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        mDateEnd = dateEnd;
    }

    public Integer getNotify() {
        return mNotify;
    }

    public void setNotify(Integer notify) {
        mNotify = notify;
    }

    public Integer getDays() {
        return mDays;
    }

    public void setDays(Integer days) {
        mDays = days;
    }

    public boolean isMon() {
        return mMon;
    }

    public void setMon(boolean lun) {
        mMon = lun;
    }

    public boolean isTue() {
        return mTue;
    }

    public void setTue(boolean tue) {
        mTue = tue;
    }

    public boolean isWed() {
        return mWed;
    }

    public void setWed(boolean wed) {
        mWed = wed;
    }

    public boolean isThu() {
        return mThu;
    }

    public void setThu(boolean thu) {
        mThu = thu;
    }

    public boolean isFri() {
        return mFri;
    }

    public void setFri(boolean fri) {
        mFri = fri;
    }

    public boolean isSat() {
        return mSat;
    }

    public void setSat(boolean sat) {
        mSat = sat;
    }

    public boolean isSun() {
        return mSun;
    }

    public void setSun(boolean sun) {
        mSun = sun;
    }
    public void  setDosage(int dosaggio){mDosaggio=dosaggio;}
    public int getDosaggio(){return mDosaggio;}

    public ContentValues getAllValues()
    {ContentValues current= new ContentValues();
        current.put(Str.therapyDrug,mDrug);
        SimpleDateFormat myFormat=new SimpleDateFormat("dd/mm/yyyy");
        Log.d("data letta da therapy",myFormat.format(mDateEnd));
        current.put(Str.therapyDateEnd,myFormat.format(mDateEnd));
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

