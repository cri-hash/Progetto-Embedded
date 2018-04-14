package embeddedproject.com.takethepillow;

/**
 * Created by Cristian on 10/04/2018.
 */

public class Impegno {
    private String mHour;
    private drugEntity mDrug;
    public Impegno(drugEntity drug, String hour)
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

    public drugEntity getDrug() {
        return mDrug;
    }

    public void setDrug(drugEntity drug) {
        mDrug = drug;
    }







}
