package database;

/**
 * Created by Cristian on 13/04/2018.
 */

public class Drug {

    private String mName;
    private String mScope;
    private String mPrice;
    public Drug()
    {mName=null;
        mScope=null;
        mPrice=null;}
    public static final String nameTable="drugs";
    public static final String columnName ="nome";
    public static final String columnPrice="prezzo";
    public static final String columnEffect="effetti";


public static final String CREATE_TABLE= "CREATE TABLE "+ nameTable + " (" + columnName + " VARCHAR(50) PRIMARY KEY,"
  +  columnPrice +" FLOAT,"+ columnEffect+" VARCHAR(150);";

}
