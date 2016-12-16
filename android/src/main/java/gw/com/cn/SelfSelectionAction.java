package gw.com.cn;

/**
 * Created by lusha on 2016/11/28.
 */
public class SelfSelectionAction extends BaseAction {

    public SelfSelectionAction(String deviceType) {
        super(deviceType);
    }

    public void enterIntoEditSelectionViewOnSelfSelectionView() {
        this.getDzhAndroidDriver().findElementByName("编辑").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void editSelfSelectionOnEditSelectionView() {
        this.getDzhAndroidDriver().findElementByName("编辑自选股").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void editLatestBrowseOnEditSelectionView() {
        this.getDzhAndroidDriver().findElementByName("编辑最新浏览").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(boolean isSure) {
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.sleep(Constant.NORMAL_WAIT_TIME);
        if(isSure){
            this.getDzhAndroidDriver().findElementByName("确认").click();
        }else{
            this.getDzhAndroidDriver().findElementByName("取消").click();
        }
    }

    public void enterIntoSearchStockViewOnEditSelectionView() {
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/head_right").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void typeTextOnSearchStockView() {
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/edit").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/edit").clear();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void deleteSelfSelectionOrStockOnEditSelectionView(){
        this.getDzhAndroidDriver().findElementByName("删除").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void dragSelfSelectionOnEditSelectionView(){
        this.getDzhAndroidDriver().findElementByName("按住拖动").getRect();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

}
