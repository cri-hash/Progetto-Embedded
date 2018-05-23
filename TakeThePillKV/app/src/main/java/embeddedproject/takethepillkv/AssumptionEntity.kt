package embeddedproject.takethepillkv

import android.util.Log;

import java.sql.Time
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.List
import java.util.concurrent.TimeUnit

 class AssumptionEntity {

    // VARIABILI
     var  data: Date = Date()   //generica data..tanto siamo sicuri venga settata
     var ora : Time =Time(1) //generico tempo..tanto siamo sicuri venga settato
     var nomeFarmaco: String=String()
     var stato: Boolean=false
     var dosaggio: Int=0
     var tipoFarmaco: String=String()
     var terapia: Int?=null

    // costruttore per assunzioni DI SOLA LETTURA
     fun AssumptionEntity(data:Date, ora: Time, nomeFarmaco:String,stato:Boolean, dosaggio:Int,tipoFarmaco:String,terapia:Int){
        this.data=data
        this.ora=ora
        this.nomeFarmaco=nomeFarmaco
        this.stato=stato
        this.dosaggio=dosaggio
        this.tipoFarmaco=tipoFarmaco
        this.terapia=terapia
    }
    //costruttore per database
    fun AssumptionEntity( data: Date, ora: Time, terapia:Int, stato : Boolean)
    {
        this.data=data
        this.ora=ora
        this.terapia=terapia
        this.stato=stato
    }
    // Costruttore generico
     fun AssumptionEntity(){}


    //METODI GET e SET non servono in quanto automatici in Kotlin





    public fun generateAssumption(th :TherapyEntityDB, hour:Time, dataInizio: Calendar): List<AssumptionEntity>?{

        if(th.mDays==0){
            Log.d("errore terapia","numero giorni non trovato");
            return null
        }

         val list : List<AssumptionEntity> = ArrayList<>() as List<AssumptionEntity> //cast sennò mi dava errore
        var calendar:Calendar= Calendar.getInstance()
        if(dataInizio!=null) calendar = dataInizio;  // necessaria sta riga?
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR, 0)
        // torno indietro di un giorno per garantire la correttezza del ciclo
        calendar.add(Calendar.DAY_OF_MONTH,-1)


        var count=0
        // Se si ha la data di fine, ricavo i giorni di differenza con oggi
        if(th.mDays==-2){
            var diff = th.mDateEnd.getTime()-calendar.getTime().getTime()  // DA SISTEMARE
            var giorni = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            Log.d("Data Fine",th.mDateEnd.toString())
            Log.d("Data Inizio",calendar.getTime().toString())
            Log.d("n Giorni",giorni.toString())
            count= giorni as Int
        } else if(th.mDays==-1)count=10;// Se senza limiti???????????
        else count=th.mDays

        while(count>0){
            //scorro il calendario un giorno alla volta
            calendar.add(Calendar.DAY_OF_MONTH,1);
            String data=calendar.getTime().toString(); // occhio al formato..da controllare

            Log.d("data processata",data);

            //1=sunday,..7=saturday
            int day= calendar.get(Calendar.DAY_OF_WEEK);

            Log.d("day found",day+"");

            //compare day found and check if it's a day therapy,then add assumption
            if(day==1 && th.isSun())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }
            if(day==2 && th.isMon())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }
            if(day==3 && th.isTue())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }
            if(day==4 && th.isWed())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }
            if(day==5 && th.isThu())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }
            if(day==6 && th.isFri())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }
            if(day==7 && th.isSat())
            {
                AssumptionEntity current= new AssumptionEntity(calendar.getTime(),hour,th.getID(),false);
                list.add(current);
                count--;
                continue;
            }



        }



        return list;
    }


}
