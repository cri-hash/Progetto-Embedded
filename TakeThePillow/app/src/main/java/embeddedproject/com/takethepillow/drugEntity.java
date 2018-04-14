package embeddedproject.com.takethepillow;
/**
 * Created by Cristian on 13/04/2018.
 */

public class drugEntity {

    private String mName;
    private String mScope;
    private float mPrice;
    public drugEntity()
    {mName=null;
        mScope=null;
        mPrice=0;}




    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }





}
