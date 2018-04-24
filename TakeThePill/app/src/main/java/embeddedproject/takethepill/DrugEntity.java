package embeddedproject.takethepill;

public class DrugEntity {

// VARIABILI
    private int id;
    private String nome;
    private String descrizione;
    private String tipo;
    private double prezzo;
    private int scorte;

//COSTRUTTORE
    public DrugEntity(int id, String nome, String descrizione, String tipo, double prezzo, int scorte){
        this.id=id;
        this.nome=nome;
        this.descrizione=descrizione;
        this.tipo=tipo;
        this.prezzo=prezzo;
        this.scorte=scorte;
    }

//METODI GET e SET

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getScorte() {
        return scorte;
    }

    public void setScorte(int scorte) {
        this.scorte = scorte;
    }
}
