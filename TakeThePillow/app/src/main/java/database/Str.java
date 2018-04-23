package database;



/**
 * Created by Cristian on 13/04/2018.
 * this class contains all column's name and values's name of db
 */

@SuppressWarnings("WeakerAccess")
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
   public static final String momenttherapy="terapia";
   public static final String momentHour="ora";
   public static final String assumptionTable="ASSUNZIONE";
   public static final String assumptionDate="data";
   public static final String assumptionHour="ora";
   public static final String assumptiontherapy="terapia";
   public static final String assumptionState="stato";
   public static final String hourTable="ORARIO";
   public static final String hourHour="ora";
   public static final String therapyTable="TERAPIA";
   public static final String therapyID="ID";
   public static final String therapyDateStart="dataInizio";
   public static final String therapyDateEnd="dataFine";
   public static final String therapyNotify="minNotifica";
   public static final String therapyNumberDays="numeroGiorni";
   public static final String therapyMon="Lunedì";
   public static final String therapyTue="Martedì";
   public static final String therapyWed="Mercoledì";
   public static final String therapyThu="Giovedì";
   public static final String therapyFri="Venerdì";
   public static final String therapySat="Sabato";
   public static final String therapySun="Domenica";
   public static final String therapyDrug="farmaco";



   //QUERY
   public static final String CREATE_DRUG_TABLE = "CREATE TABLE " + drugTable + " (" + drugId + " VARCHAR(15) PRIMARY KEY,"
           + drugName + " VARCHAR(50)," + drugDescription + " VARCHAR(150)," + drugPrice +" REAL,"+ drugQuantities + " INTEGER,"+ drugType
           +" VARCHAR(30), FOREIGN KEY("+ drugType +") REFERENCES "+ typeTable +" ("+ typeName +") ON UPDATE CASCADE ON DELETE NO ACTION);";

   public static final String CREATE_TYPE_TABLE = "CREATE TABLE "+ typeTable +" (" + typeName +" VARCHAR(30) PRIMARY KEY );";

   public static final String CREATE_ASSUMPTION_TABLE = "CREATE TABLE " + assumptionTable + " (" + assumptionDate+ " DATE,"
           + assumptionHour + " TIME(6)," + assumptiontherapy + " VARCHAR(15)," + assumptionState +" BOOL ," +"PRIMARY KEY ("+assumptionDate +", "+ assumptionHour +", "
           +assumptiontherapy+"),  FOREIGN KEY("+ assumptiontherapy +") REFERENCES "+ therapyTable +" ("+ therapyID +") ON UPDATE CASCADE ON DELETE NO ACTION);";

   public static final String CREATE_HOUR_TABLE=  "CREATE TABLE "+ hourTable +" (" + hourHour +" TIME(6) PRIMARY KEY );";

   public static final String CREATE_MOMENT_TABLE = "CREATE TABLE "+ momentTable +"(" + momenttherapy +" VARCHAR(15), "+ momentHour  +" TIME(6), PRIMARY KEY("
           +momenttherapy +","+momentHour +"), FOREIGN KEY ("+ momenttherapy +") REFERENCES "+ therapyTable+"("+therapyID+") ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY ("
           + momentHour+ ") REFERENCES "+ hourTable+"("+hourHour+") ON UPDATE CASCADE ON DELETE NO ACTION);" ;


   public static final String CREATE_THERAPY_TABLE =  "CREATE TABLE " + therapyTable + " (" + therapyID + " VARCHAR(15) PRIMARY KEY,"
           + therapyDateStart + " DATE," + therapyDateEnd + " DATE," + therapyNotify +" SMALLINT,"+ therapyNumberDays + " INTEGER,"
           +therapyMon +" BOOL, "+therapyTue +" BOOL, "+therapyWed +" BOOL, "+therapyThu +" BOOL, "+therapyFri +" BOOL, "+therapySat +" BOOL, "+therapySun +" BOOL, "
           +therapyDrug+" VARCHAR(15), FOREIGN KEY("+ therapyDrug +") REFERENCES "+ drugTable +" ("+ drugId +") ON UPDATE CASCADE ON DELETE NO ACTION);";



 public static final String getAllTerapy = "SELECT * FROM "+ therapyTable + " ;";
}