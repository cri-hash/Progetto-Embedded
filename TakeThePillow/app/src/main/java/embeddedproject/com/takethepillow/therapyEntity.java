package embeddedproject.com.takethepillow;

import android.content.ContentValues;

import java.util.Date;

import database.Str;

/**
 * Created by Cristian on 13/04/2018.
 */

public class therapyEntity {
    private String mID,mDrug;
    private Date mDateStart;
    private Date mDateEnd;
    private short mNotify;
    private int mDays;
    private int mMon, mTue, mWed, mThu, mFri, mSat, mSun;


    public therapyEntity()
    {
        mID=null;
        mDrug=null;
        mDateStart=null;
        mDateEnd =null;
        mNotify=20;
        mDays=1;
        mMon=0;
        mTue=0;
        mWed=0;
        mThu=0;
        mFri=0;
        mSat=0;
        mSun=0;

    }

    public String getID() {
        return mID;
    }
    public String getDrug(){return mDrug;}
    public void setDrug(String drug){mDrug=drug;}

    public void setID(String ID) {
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

    public short getNotify() {
        return mNotify;
    }

    public void setNotify(short notify) {
        mNotify = notify;
    }

    public int getDays() {
        return mDays;
    }

    public void setDays(int days) {
        mDays = days;
    }

    public int isMon() {
        return mMon;
    }

    public void setMon(int lun) {
        mMon = lun;
    }

    public int isTue() {
        return mTue;
    }

    public void setTue(int tue) {
        mTue = tue;
    }

    public int isWed() {
        return mWed;
    }

    public void setWed(int wed) {
        mWed = wed;
    }

    public int isThu() {
        return mThu;
    }

    public void setThu(int thu) {
        mThu = thu;
    }

    public int isFri() {
        return mFri;
    }

    public void setFri(int fri) {
        mFri = fri;
    }

    public int isSat() {
        return mSat;
    }

    public void setSat(int sat) {
        mSat = sat;
    }

    public int isSun() {
        return mSun;
    }

    public void setSun(int sun) {
        mSun = sun;
    }

    public ContentValues getAllValues()
    {ContentValues current= new ContentValues();
        current.put(Str.therapyDrug,mDrug);
        current.put(Str.therapyDateEnd,mDateEnd.toString());
        current.put(Str.therapyDateStart,mDateStart.toString());
        current.put(Str.therapyNotify,mNotify);
        current.put(Str.therapyNumberDays,mDays);
        current.put(Str.therapyID,mID);
        current.put(Str.therapyMon,mMon);
        current.put(Str.therapyThu,mThu);
        current.put(Str.therapyTue,mTue);
        current.put(Str.therapyWed,mWed);
        current.put(Str.therapyFri,mFri);
        current.put(Str.therapySat,mSat);
        current.put(Str.therapySun,mSun);
        return current;
    }




}

