package embeddedproject.takethepill;

import android.content.ContentValues;

import database.Str;

public class DrugEntity {

// VARIABILI

    private var nome:String?
    private var descrizione:String?
    private var tipo:String?
    private var prezzo:Double
    private var scorte:Int

//COSTRUTTORE
    constructor( nome :String, descrizione:String ,tipo:String ,prezzo: Double ,scorte: Int){

        this.nome=nome
        this.descrizione=descrizione
        this.tipo=tipo
        this.prezzo=prezzo
        this.scorte=scorte
    }


    fun DrugEntity()
    {

        this.nome=null
        this.descrizione=null
        this.tipo=null
        this.prezzo=0.0
        this.scorte=0
    }



   fun  getAllValues() : ContentValues
    {
        val values =ContentValues()
        val str=Str()
        values.put(str.drugName,nome)
        values.put(str.drugPrice,prezzo)
        values.put(str.drugDescription,descrizione)
        values.put(str.drugQuantities,scorte)
        values.put(str.drugType,tipo)
        return values
    }
}
