package embeddedproject.com.takethepillow;

/**
 * Created by Cristian on 10/04/2018.
 */

public class Impegno {
    private String mHour;

    private therapyEntityDB mDrug;
    public Impegno(therapyEntityDB drug, String hour)
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

    public therapyEntityDB getDrug() {
        return mDrug;
    }

    public void setDrug(therapyEntityDB drug) {
        mDrug = drug;
    }







}
