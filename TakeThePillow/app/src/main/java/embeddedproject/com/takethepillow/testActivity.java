package embeddedproject.com.takethepillow;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.DatabaseHelper;

/**
 * Created by Cristian on 24/04/2018.
 */

public class testActivity extends Activity {

    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);
        int i=0;
        while(i<10)
        {
        therapyEntity example=new therapyEntity();
        example.setNotify((short)10);
        example.setID("esempio_"+i+"_bis");
        example.setDays(5);
        DateFormat format=new SimpleDateFormat("dd/mm/yyyy");
        Date start=null;
        Date end= null;
        db=new DatabaseHelper(this);
        try{
         start=format.parse("15/07/1996");
         end=format.parse("16/07/1996)");}
        catch(ParseException e){}
        example.setDateStart(start);
        example.setDateEnd(end);
        example.setMon(1);
        example.setTue(1);
        example.setThu(1);
        example.setWed(0);
        example.setFri(1);
        example.setSat(0);
        example.setSun(1);
        db.insertTherapy(example);
        example.setSun(0);
        db.updateTherapy(example);
        i++;}









    }
}
