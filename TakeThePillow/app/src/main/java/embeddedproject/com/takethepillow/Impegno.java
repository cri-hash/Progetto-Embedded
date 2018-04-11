package embeddedproject.com.takethepillow;

/**
 * Created by Cristian on 10/04/2018.
 */

public class Impegno {
    private String mHour;
    private String mDrug;
    public Impegno(String drug, String hour)
    {
        mHour=hour;
        mDrug=drug;

    }
    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    public String getDrug() {
        return mDrug;
    }

    public void setDrug(String drug) {
        mDrug = drug;
    }







}
