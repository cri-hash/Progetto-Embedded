package database;



/**
 * Created by Cristian on 13/04/2018.
 * this class contains all column's name and values's name of db
 */

public class Str {
   public static final String drugTable="FARMACO";
   public static final String drugId="ID";
   public static final String drugName="nome";
   public static final String drugPrice="prezzo";
   public static final String drugQuantities="scorte";
   public static final String drugType="tipo";
   public static final String drugDescription="descrizione";
   public static final String typeTable="TIPO";
   public static final String typeName="nome";
   public static final String momentTable="MOMENTO";
   public static final String momentTerapy="terapia";
   public static final String momentHour="ora";
   public static final String assumptionTable="ASSUNZIONE";
   public static final String assumptionDate="data";
   public static final String assumptionHour="ora";
   public static final String assumptionTerapy="terapia";
   public static final String assumptionState="stato";
   public static final String hourTable="ORARIO";
   public static final String hourHour="ora";
   public static final String terapyTable="TERAPIA";
   public static final String terapyID="ID";
   public static final String terapyDateStart="dataInizio";
   public static final String terapyDateEnd="dataFine";
   public static final String terapyNotify="minNotifica";
   public static final String terapyNumberDays="numeroGiorni";
   public static final String terapyMon="Lunedì";
   public static final String terapyTue="Martedì";
   public static final String terapyWed="Mercoledì";
   public static final String terapyThu="Giovedì";
   public static final String terapyFri="Venerdì";
   public static final String terapySat="Sabato";
   public static final String terapySun="Domenica";
   public static final String terapyDrug="farmaco";



   //QUERY
    public static final String CREATE_DRUG_TABLE = "CREATE TABLE " + drugTable + " (" + drugId + " VARCHAR(15) PRIMARY KEY,"
            + drugName + " VARCHAR(50)," + drugDescription + " VARCHAR(150)," + drugPrice +" REAL,"+ drugQuantities + " INTEGER,"+ drugType
           +" VARCHAR(30), FOREIGN KEY("+ drugType +") REFERENCES "+ typeTable +" ("+ typeName +") ON UPDATE CASCADE ON DELETE NO ACTION);";
    public static final String CREATE_TYPE_TABLE = "CREATE TABLE "+ typeTable +" (" + typeName +" VARCHAR(30) PRIMARY KEY );";
    public static final String CREATE_ASSUMPTION_TABLE = "CREATE TABLE " + assumptionTable + " (" + assumptionDate+ " DATE,"
            + assumptionHour + " TIME(6)," + assumptionTerapy + " VARCHAR(15)," + assumptionState +" BOOL ," +"PRIMARY KEY ("+assumptionDate +", "+ assumptionHour +", "
   +assumptionTerapy+"),  FOREIGN KEY("+ assumptionTerapy +") REFERENCES "+ terapyTable +" ("+ terapyID +") ON UPDATE CASCADE ON DELETE NO ACTION);";
    public static final String CREATE_HOUR_TABLE=  "CREATE TABLE "+ hourTable +" (" + hourHour +" TIME(6) PRIMARY KEY );";
    public static final String CREATE_MOMENT_TABLE = "CREATE TABLE "+ momentTable +" (" + momentTerapy +" VARCHAR(15), "+ momentHour +" TIME(6), PRIMARY KEY("
            +momentTerapy +","+momentHour +"), FOREIGN KEY "+ momentTerapy +" REFERENCES "+ terapyTable+"("+terapyID+") ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY "
            + momentHour+ " REFERENCES "+ hourTable+"("+hourHour+") ON UPDATE CASCADE ON DELETE NO ACTION" ;
    public static final String CREATE_TERAPY_TABLE =  "CREATE TABLE " + terapyTable + " (" + terapyID + " VARCHAR(15) PRIMARY KEY,"
            + terapyDateStart + " DATE," + terapyDateEnd + " DATE," + terapyNotify +" SMALLINT,"+ terapyNumberDays + " INTEGER,"
            +terapyMon +" BOOL, "+terapyTue +" BOOL, "+terapyWed +" BOOL, "+terapyThu +" BOOL, "+terapyFri +" BOOL, "+terapySat +" BOOL, "+terapySun +" BOOL, "
            +" VARCHAR(15),"+terapyDrug+" VARCHAR(15), FOREIGN KEY("+ terapyDrug +") REFERENCES "+ drugTable +" ("+ drugId +") ON UPDATE CASCADE ON DELETE NO ACTION);";


}
