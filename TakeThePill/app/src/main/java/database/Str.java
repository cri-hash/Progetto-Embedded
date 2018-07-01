package database;


public class Str {
   static final String drugTable="FARMACO";
   static final String drugName="nomeFarmaco";
   static final String drugPrice="prezzo";
   static final String drugQuantities="scorte";
   static final String drugType="tipo";
   static final String drugDescription="descrizione";
   static final String typeTable="TIPO";
   static final String typeName="nomeTipo";
   static final String assumptionTable="ASSUNZIONE";
   static final String assumptionDate="data";
   static final String assumptionHour="ora";
   static final String assumptiontherapy="terapiaAssunzione";
   static final String assumptionState="stato";
   static final String therapyTable="TERAPIA";
   static final String therapyID="ID";
   static final String therapyDateStart="dataInizio";
   static final String therapyDateEnd="dataFine";
   static final String therapyNotify="minNotifica";
   static final String therapyNumberDays="numeroGiorni";
   static final String therapyMon="Lunedì";
   static final String therapyTue="Martedì";
   static final String therapyWed="Mercoledì";
   static final String therapyThu="Giovedì";
   static final String therapyFri="Venerdì";
   static final String therapySat="Sabato";
   static final String therapySun="Domenica";
   static final String therapyDrug="farmaco";
   static final String therapyDosage="dosaggio";



   //QUERY
 static final String CREATE_DRUG_TABLE =
           "CREATE TABLE " + drugTable + "("
                   + drugName + " VARCHAR(50) PRIMARY KEY,"
                   + drugDescription + " VARCHAR(150),"
                   + drugPrice +" REAL,"
                   + drugQuantities + " INTEGER,"
                   + drugType +" VARCHAR(30),"
                   + "FOREIGN KEY("+ drugType +") REFERENCES "+ typeTable +" ("+ typeName +") ON UPDATE CASCADE ON DELETE CASCADE"
                   +");";

    static final String CREATE_TYPE_TABLE =
           "CREATE TABLE " + typeTable + "("
                   + typeName + " VARCHAR(30) PRIMARY KEY"
                   + ");";

    static final String CREATE_ASSUMPTION_TABLE =
           "CREATE TABLE " + assumptionTable + "("
                   + assumptionDate+ " CHARACTER(10),"
                   + assumptionHour + " CHARACTER(8),"
                   + assumptiontherapy + " INT,"
                   + assumptionState +" INT,"
                   + "PRIMARY KEY("+ assumptionDate + ", "+ assumptionHour +", " +assumptiontherapy +"),"
                   + "FOREIGN KEY("+ assumptiontherapy + ") REFERENCES "+ therapyTable +" ("+ therapyID +") ON UPDATE CASCADE ON DELETE CASCADE"
                   + ");";

   static final String CREATE_THERAPY_TABLE =
           "CREATE TABLE " + therapyTable + "("
                   + therapyID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + therapyDateStart + " CHARACTER(10),"
                   + therapyDateEnd + " CHARACTER(10),"
                   + therapyNotify + " INTEGER,"
                   + therapyNumberDays + " INTEGER,"
                   + therapyDosage + " INT,"
                   + therapyMon + " INT,"
                   + therapyTue + " INT,"
                   + therapyWed + " INT,"
                   + therapyThu + " INT,"
                   + therapyFri + " INT,"
                   + therapySat + " INT,"
                   + therapySun + " INT,"
                   + therapyDrug + " VARCHAR(50),"
                   + "FOREIGN KEY("+ therapyDrug +") REFERENCES "+ drugTable +" ("+ drugName+") ON UPDATE CASCADE ON DELETE CASCADE"
                   + ");";


    static final String getAllTherapies = "SELECT * FROM "+ therapyTable + " ;";
    static final String getAllDrugs="SELECT * FROM "+ drugTable + " ;";
}
