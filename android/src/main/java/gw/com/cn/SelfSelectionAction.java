package gw.com.cn;

import gw.com.cn.util.LogUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by lusha on 2016/11/28.
 */
public class SelfSelectionAction extends BaseAction {

    public enum StockOperator {
        BUY, SELL, DEL, TOP
    }

    public CheckPoint checkPoint;

    public SelfSelectionAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhAndroidDriver());
    }

    public void session(){
        String sessionID = this.getDzhAndroidDriver().getSessionId().toString();
        Map<String, String> sessionMap = this.getDzhAndroidDriver().getSessionDetails();
    }

    public void enterIntoEditSelectionViewOnSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementByName("编辑").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void enterIntoSearchStockViewOnSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/searchBtn").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void enterIntoSearchStockViewOnEditSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/head_right").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void editSelfSelectionOnEditSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementByName("编辑自选股").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void editLatestBrowseOnEditSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().ignoreUnimportantViews(false);
        this.getDzhAndroidDriver().findElementByName("编辑最新浏览").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public void deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(boolean isSure) {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.sleep(Constant.NORMAL_WAIT_TIME);
        if(isSure){
            this.getDzhAndroidDriver().findElementById("android:id/button1").click();
        }else{
            this.getDzhAndroidDriver().findElementById("android:id/button2").click();
        }
    }

    public void typeTextOnSearchStockView(String typeContent) {
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/edit").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/edit").clear();
        this.sleep(Constant.SHORT_WAIT_TIME);
        this.dzhKeyboardTypeContent(typeContent);
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public String deleteSelfSelectionOrStockOnEditSelectionView(){
        super.createSessionAfterTimeout();
        String text = this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/dzh_delete_item_name").getText();
        this.getDzhAndroidDriver().findElementByName("删除").click();
        this.getDzhAndroidDriver().findElementByName("确定").click();
        this.sleep(Constant.SHORT_WAIT_TIME);
        return text;
    }

    public void selfStockOperatorOnSelectionView(String stockName, StockOperator stockOperator ){
        super.createSessionAfterTimeout();
        List<AndroidElement> stocks = this.getDzhAndroidDriver().findElementsById("com.android.dazhihui:id/tv_name");
        for (AndroidElement androidElement : stocks) {
            if(androidElement.getText().equals(stockName)){
                TouchAction touchAction = new TouchAction(this.getDzhAndroidDriver());
                touchAction.longPress(androidElement).perform();
                switch (stockOperator) {
                    case BUY:
                        this.getDzhAndroidDriver().findElementByName("快买").click();
                        return;
                    case SELL:
                        this.getDzhAndroidDriver().findElementByName("快卖").click();
                        return;
                    case DEL:
                        this.getDzhAndroidDriver().findElementByName("删除").click();
                        this.getDzhAndroidDriver().findElementById("android:id/button1").click();
                        return;
                    case TOP:
                        this.getDzhAndroidDriver().findElementByName("置顶").click();
                        return;
                }
            }
        }
    }

    public void dragSelfSelectionOnEditSelectionView(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementByName("按住拖动").getRect();
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public List<String> addStocksOnSearchStockView(int number){
        List<String> selfStocks = new ArrayList<String>();
        List<AndroidElement> androidElements =  this.getDzhAndroidDriver().findElementsById("com.android.dazhihui:id/searchListStockName");
        List<AndroidElement> androidElementIcons =  this.getDzhAndroidDriver().findElementsById("com.android.dazhihui:id/searchListAddIcon");
        if(number > 0 && number < androidElements.size()){
            for (int i = 0; i < number; i++) {
                selfStocks.add(androidElements.get(i).getText());
                androidElementIcons.get(0).click();
            }
        }else{
            throw new RuntimeException("number error, number should in [" + 1 + "-" + androidElements.size() + "]");
        }
        return selfStocks;
    }

    public void newestSortOnSelfSelectionView(boolean isDesc){
        if(this.getDzhAndroidDriver().isContainImage("selfSelection/sortedDesc.png") || this.getDzhAndroidDriver().isContainImage("selfSelection/sortedAsc.png")){
            this.cancelSortOnSelfSelectionView();
        }else{
            if(isDesc){
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/zx_sort").click();
            }else{
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/zx_sort").click();
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/zx_sort").click();

            }
        }
    }

    public void  increaseSortOnSelfSelectionView(boolean isDesc){
        if(this.getDzhAndroidDriver().isContainImage("selfSelection/sortedDesc.png") || this.getDzhAndroidDriver().isContainImage("selfSelection/sortedAsc.png")){
            this.cancelSortOnSelfSelectionView();
        }else{
            if(isDesc){
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/zf_sort").click();
            }else{
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/zf_sort").click();
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/zf_sort").click();

            }
        }
    }

    public void cancelSortOnSelfSelectionView(){
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/cancel_sort_text").click();
    }

    public void checkAddOrderOfSelfStocks(List<String> originOrder){
        List<AndroidElement> selfList = this.getDzhAndroidDriver().findElementsById("com.android.dazhihui:id/tv_name");
        List<String> nowOrder = new ArrayList<String>();
        for (AndroidElement item : selfList ) {
            nowOrder.add(item.getText());
        }
        Collections.reverse(nowOrder);
        LogUtil.getLogger().info("expect: " + originOrder.toString()  + " actual:" + nowOrder.toString() + "自选列表下的自选股恢复为添加顺序");
        Assert.assertEquals(originOrder.toString(), nowOrder.toString() , "自选列表下的自选股恢复为添加顺序");
    }

    public void checkNewestSortOnSelfSelectionView(boolean isDesc){
        List<AndroidElement> selfList = this.getDzhAndroidDriver().findElementsById("com.android.dazhihui:id/tv_zxj");
        List<Double> unSort = new ArrayList<Double>();
        for (AndroidElement item : selfList ) {
            String text = item.getText();
            if(text.equals("--")){
                text = "0.00";
            }
            Double stockPrice = Double.parseDouble(text);
            unSort.add(stockPrice);
        }
        if(isDesc){
            String actual = unSort.toString();
            Collections.sort(unSort);
            Collections.reverse(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect  + " actual:" + actual + "检查最新降序排序是否正确");
            Assert.assertEquals(actual, expect , "检查最新降序排序是否正确");
        }else{
            String actual = unSort.toString();
            Collections.sort(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect  + " actual:" + actual + "检查最新升序排序是否正确");
            Assert.assertEquals(actual, expect , "检查最新升序排序是否正确");
        }

    }

    public void checkIncreaseSortOnSelfSelectionView(boolean isDesc){
        List<AndroidElement> selfList = this.getDzhAndroidDriver().findElementsById("com.android.dazhihui:id/tv_zf");
        List<Double> unSort = new ArrayList<Double>();
        for (AndroidElement item : selfList ) {
            String text = item.getText();
            text = text.replace("%", "").replace("+", "");
            if(text.equals("--")){
                text = "0.00";
            }
            Double stockIncrease = Double.parseDouble(text);
            unSort.add(stockIncrease);
        }
        if(isDesc){
            String actual = unSort.toString();
            Collections.sort(unSort);
            Collections.reverse(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect  + " actual:" + actual + "检查涨幅降序排序是否正确");
            Assert.assertEquals(actual, expect , "检查涨幅降序排序是否正确");
        }else{
            String actual = unSort.toString();
            Collections.sort(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect  + " actual:" + actual + "检查涨幅升序排序是否正确");
            Assert.assertEquals(actual, expect , "检查涨幅升序排序是否正确");
        }

    }

    public void checkNoExistSelfStockOnEditSelectionView(){
        this.checkPoint.checkIDNotExist("com.android.dazhihui:id/dzh_delete_item_name");
    }

    public void checkNoExistSpecialSelfStockOnEditSelectionViewOrSelectionView(String selfStockName){
        this.checkPoint.checkTextNotExist(selfStockName);
    }

    public void checkStockIsSelfOnSearchStockView(String selfStockName){
        AndroidElement listView = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/searchstock_listview");
        List<MobileElement> stocks = listView.findElementsByClassName("android.widget.LinearLayout");
        boolean tag = false;
        for (MobileElement item : stocks ) {
            try{
                if(tag){
                    break;
                }
                item.findElementByName(selfStockName);
                item.findElementByName("已加入");
                tag = true;
            }catch (NoSuchElementException e){
                LogUtil.getLogger().debug(selfStockName + " not found in current view");
            }
        }
        LogUtil.getLogger().info("检查" + selfStockName + "是否为自选股");
        Assert.assertEquals(true, tag , selfStockName + " is a self stock");
    }

}
