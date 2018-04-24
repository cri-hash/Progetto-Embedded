package embeddedproject.com.takethepillow;

/**
 * Created by Cristian on 10/04/2018.
 */

public class Impegno {
    private String mHour;
    private therapyEntity mDrug;
    public Impegno(therapyEntity drug, String hour)
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

    public therapyEntity getDrug() {
        return mDrug;
    }

    public void setDrug(therapyEntity drug) {
        mDrug = drug;
    }







}
