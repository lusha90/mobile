package gw.com.cn;

/**
 * Created by lusha on 2016/11/28.
 */
public class MarketAction extends BaseAction{

    public CheckPoint checkPoint;

    public MarketAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhAndroidDriver());
    }

    public void test(){

    }
}
