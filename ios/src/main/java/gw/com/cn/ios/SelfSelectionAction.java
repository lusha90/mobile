package gw.com.cn.ios;

import gw.com.cn.IosBaseAction;
import gw.com.cn.util.LogUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by lusha on 2016/11/28.
 */
public class SelfSelectionAction extends IosBaseAction {

    public enum StockOperator {
        BUY, SELL, DEL, TOP, CANCELTOP
    }

    public enum SelfTitle {
        SHANGZHENG, CHUANGYE, NEWS, MONEY
    }

    public enum SortType {
        ZHANGFU, ZHANGDIEE, CHENGJIAOLIANG
    }

    public CheckPoint checkPoint;

    public SelfSelectionAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhIosDriver());
    }

    public void session() {
        String sessionID = this.getDzhIosDriver().getSessionId().toString();
        Map<String, String> sessionMap = this.getDzhIosDriver().getSessionDetails();
    }

    public void backToHome() {
        while (true) {
            try {
                AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_1");
                androidElement.click();
                break;
            } catch (NoSuchElementException e) {
                this.back();
            }
        }
    }

    public void deleteAllSelfStockAndLatestBrowse() {
        this.backToHome();
        try {
            LogUtil.getLogger().info("删除所有自选股");
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/home_self_stock_layout");
            this.enterIntoEditSelectionViewOnSelfSelectionView();
            this.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
            this.back();
        } catch (NoSuchElementException e) {
            LogUtil.getLogger().info("已无自选股");
        }
        try {
            LogUtil.getLogger().info("删除所有最新浏览");
            this.getDzhIosDriver().findElementByName("最新浏览");
            this.enterIntoEditSelectionViewOnSelfSelectionView();
            this.editLatestBrowseOnEditSelectionView();
            this.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
            this.back();
        } catch (NoSuchElementException e) {
            LogUtil.getLogger().info("已无最新浏览");
        }

    }

    public void enterIntoEditSelectionViewOnSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName("编辑").click();
        this.explicitSleepForText("编辑自选股", Constant.LONG_WAIT_TIME);
    }

    public void enterIntoSearchStockViewOnSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/searchBtn").click();
        this.explicitSleepForText("搜股票", Constant.LONG_WAIT_TIME);
    }

    public void enterIntoSearchStockViewOnEditSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/head_right").click();
        this.explicitSleepForText("搜股票", Constant.LONG_WAIT_TIME);
    }

    public void enterIntoOtherView(SelfTitle selfTitle) {
        switch (selfTitle) {
            case SHANGZHENG:
                this.getDzhIosDriver().findElementByName("上证").click();
                return;
            case CHUANGYE:
                this.getDzhIosDriver().findElementByName("创业").click();
                return;
            case NEWS:
                this.getDzhIosDriver().findElementByName("新闻").click();
                return;
            case MONEY:
                this.getDzhIosDriver().findElementByName("资金").click();
                return;
        }
    }

    public void enterIntoStockDetailViewOnSearchStockView(String stockName) {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName(stockName).click();
        this.explicitSleepForText("分时", Constant.LONG_WAIT_TIME);
    }

    public void enterIntoSelfStockView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_1").click();
        this.explicitSleepForText("大智慧", Constant.LONG_WAIT_TIME);
    }

    public void editSelfSelectionOnEditSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName("编辑自选股").click();
        this.explicitSleepForText("编辑自选股", Constant.LONG_WAIT_TIME);
    }

    public void editLatestBrowseOnEditSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName("编辑最新浏览").click();
        this.explicitSleepForText("编辑最新浏览", Constant.LONG_WAIT_TIME);
    }

    public void deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(boolean isSure) {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.explicitSleepForText("确定", Constant.LONG_WAIT_TIME);
        if (isSure) {
            this.getDzhIosDriver().findElementById("android:id/button1").click();
        } else {
            this.getDzhIosDriver().findElementById("android:id/button2").click();
        }
    }

    public void typeTextOnSearchStockView(String typeContent) {
        super.createSessionAfterTimeout();
        AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/edit");
        androidElement.click();
        androidElement.clear();
        String keyboard = (String) this.getDzhIosDriver().getCapabilities().getCapability("keyboard");
        if(keyboard.equals("origin")){
            androidElement.sendKeys(typeContent);
        }
        else if(keyboard.equals("dzh")){
            this.dzhKeyboardTypeContent(typeContent);
        }
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public String deleteSelfSelectionOrStockOnEditSelectionView() {
        super.createSessionAfterTimeout();
        String text = this.getDzhIosDriver().findElementById("com.android.dazhihui:id/dzh_delete_item_name").getText();
        this.getDzhIosDriver().findElementByName("删除").click();
        this.getDzhIosDriver().findElementByName("确定").click();
        return text;
    }

    public void selfStockOperatorOnSelectionView(String stockName, StockOperator stockOperator) {
        this.selfStockOperatorOnSelectionView(stockName, stockOperator, true);
    }

    public void selfStockOperatorOnSelectionView(String stockName, StockOperator stockOperator, boolean networkIsAvailable) {
        super.createSessionAfterTimeout();
        List<AndroidElement> stocks = this.getDzhIosDriver().findElementsById("com.android.dazhihui:id/tv_name");
        for (AndroidElement androidElement : stocks) {
            if (androidElement.getText().equals(stockName)) {
                TouchAction touchAction = new TouchAction(this.getDzhIosDriver());
                touchAction.longPress(androidElement).perform();
                switch (stockOperator) {
                    case BUY:
                        this.getDzhIosDriver().findElementByName("快买").click();
                        return;
                    case SELL:
                        this.getDzhIosDriver().findElementByName("快卖").click();
                        return;
                    case DEL:
                        this.getDzhIosDriver().findElementByName("删除").click();
                        this.getDzhIosDriver().findElementById("android:id/button1").click();
                        if (networkIsAvailable) {
                            try {
                                this.getDzhIosDriver().findElementByName(stockName);
                                LogUtil.getLogger().info(stockName + " 自选股删除失败");
                                Assert.assertEquals(true, false, stockName + " 自选股删除失败");
                            } catch (NoSuchElementException e) {
                                LogUtil.getLogger().info(stockName + " 自选股删除成功");
                            }
                        } else {
                            this.getDzhIosDriver().findElementByName(stockName);
                            LogUtil.getLogger().info(stockName + " 断网时自选股删除失败");
                        }
                        return;
                    case TOP:
                        this.getDzhIosDriver().findElementByName("置顶").click();
                        return;
                    case CANCELTOP:
                        this.getDzhIosDriver().findElementByName("取消置顶").click();
                        return;
                }
            }
        }
    }

    public void dragStockOnEditSelectionView() {
        super.createSessionAfterTimeout();
        List<AndroidElement> list = this.getDzhIosDriver().findElementsByName("按住拖动");
        Point first = list.get(0).getCenter();
        Point end = list.get(list.size() - 1).getCenter();
        this.getDzhIosDriver().swipe(end.getX(), end.getY(), first.getX(), first.getY(), 1000);
        this.sleep(Constant.SHORT_WAIT_TIME);
    }

    public List<String> addStocksOnSearchStockView(int number) {
        super.createSessionAfterTimeout();
        List<String> selfStocks = new ArrayList<String>();
        List<AndroidElement> androidElements = this.getDzhIosDriver().findElementsById("com.android.dazhihui:id/searchListStockName");
        List<AndroidElement> androidElementIcons = this.getDzhIosDriver().findElementsById("com.android.dazhihui:id/searchListAddIcon");
        if (number > 0 && number < androidElements.size()) {
            for (int i = 0; i < number; i++) {
                selfStocks.add(androidElements.get(i).getText());
                androidElementIcons.get(0).click();
            }
        } else {
            throw new RuntimeException("number error, number should in [" + 1 + "-" + androidElements.size() + "]");
        }
        return selfStocks;
    }

    public void newestSortOnSelfSelectionView(boolean isDesc) {
        super.createSessionAfterTimeout();
        if (this.getDzhIosDriver().isContainImage("selfSelection/sortedDesc.png") || this.getDzhIosDriver().isContainImage("selfSelection/sortedAsc.png")) {
            this.cancelSortOnSelfSelectionView();
        }
        if (isDesc) {
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zx_sort").click();
        } else {
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zx_sort").click();
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zx_sort").click();

        }
    }

    public void switchSkin(boolean isDark) {
        boolean currentSkin = this.getDzhIosDriver().isContainImage("selfSelection/skinLight.png");
        if (currentSkin) {
            if (isDark) {
                this.getDzhIosDriver().findElementById("com.android.dazhihui:id/skinchange").click();
            } else {
            }
        } else {
            if (isDark) {
            } else {
                this.getDzhIosDriver().findElementById("com.android.dazhihui:id/skinchange").click();
            }
        }
    }

    public void increaseSortOnSelfSelectionView(boolean isDesc) {
        super.createSessionAfterTimeout();
        if (this.getDzhIosDriver().isContainImage("selfSelection/sortedDesc.png") || this.getDzhIosDriver().isContainImage("selfSelection/sortedAsc.png")) {
            this.cancelSortOnSelfSelectionView();
        }
        if (isDesc) {
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zf_sort").click();
        } else {
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zf_sort").click();
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zf_sort").click();

        }
    }

    public void cancelSortOnSelfSelectionView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/cancel_sort_text").click();
    }

    public void toggleShowLatestBrowse(boolean isCheck) {
        super.createSessionAfterTimeout();
        boolean checked = Boolean.parseBoolean(this.getDzhIosDriver().findElementById("com.android.dazhihui:id/switch_latest_checkbox").getAttribute("checked"));
        if (checked != isCheck) {
            this.getDzhIosDriver().findElementById("com.android.dazhihui:id/switch_latest_checkbox").click();
        }
    }

    public List<String> getAllSelfSelectionStock() {
        int offsetY = 1;
        super.createSessionAfterTimeout();
        List<String> allStocks = new ArrayList<String>();
        AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/homeview_listview");
        Dimension dimension = androidElement.getSize();
        Point point = androidElement.getLocation();
        int i = 1;
        while (true) {
            List<MobileElement> stocks = androidElement.findElementsById("com.android.dazhihui:id/tv_name");
            for (MobileElement mobileElement : stocks) {
                allStocks.add(mobileElement.getText());
            }
            Dimension endDimension = stocks.get(stocks.size() - 1).getSize();
            Point endPoint = stocks.get(stocks.size() - 1).getLocation();
            int centerX = this.width / 2;
            this.getDzhIosDriver().swipe(centerX, endPoint.getY() + endDimension.getHeight() - offsetY, centerX, point.getY() - offsetY, 3000);
            this.sleep(Constant.NORMAL_WAIT_TIME);
            LogUtil.getLogger().info("第" + ++i + "次向下滑动一页查找自选股");
            try {
                String key = allStocks.get(allStocks.size() - 3);
                LogUtil.getLogger().info("search key : " + key);
                this.getDzhIosDriver().findElementByName(key);
                LogUtil.getLogger().info("自选股列表已到底端");
                List<MobileElement> remainStocks = androidElement.findElementsById("com.android.dazhihui:id/tv_name");
                for (MobileElement mobileElement : remainStocks) {
                    allStocks.add(mobileElement.getText());
                }
                break;
            } catch (NoSuchElementException e) {
            }
        }
        LogUtil.getLogger().info("自选股列表下所有自选股: " + allStocks.toString());
        List<String> list = removeDuplicateItem(allStocks);
        LogUtil.getLogger().info("自选股列表下所有自选股(剔除重复的元素): " + allStocks.toString());
        return list;
    }

    private List<String> removeDuplicateItem(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public List<String> getAllLatestBrowseOnEditLatestBrowseView() {
        int offsetY = 1;
        super.createSessionAfterTimeout();
        List<String> allStocks = new ArrayList<String>();
        AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/edit_listview");
        Dimension dimension = androidElement.getSize();
        Point point = androidElement.getLocation();
        int i = 1;
        while (true) {
            List<String> pageStock = new ArrayList<String>();
            List<MobileElement> stocks = androidElement.findElementsById("com.android.dazhihui:id/dzh_delete_item_name");
            for (MobileElement mobileElement : stocks) {
                pageStock.add(mobileElement.getText());
            }
            LogUtil.getLogger().info("第" + i + "页自选股: " + pageStock.toString());
            allStocks.addAll(pageStock);
            int centerX = this.width / 2;
            this.getDzhIosDriver().swipe(centerX, point.getY() + dimension.getHeight() - offsetY, centerX, point.getY(), 5000);
            this.sleep(Constant.NORMAL_WAIT_TIME);
            LogUtil.getLogger().info("第" + ++i + "次向下滑动一页查找股票");
            try {
                String key = allStocks.get(allStocks.size() - 10);
                LogUtil.getLogger().info("search key : " + key);
                this.getDzhIosDriver().findElementByName(key);
                LogUtil.getLogger().info("最新浏览列表已到底端");
                List<MobileElement> remainStocks = androidElement.findElementsById("com.android.dazhihui:id/dzh_delete_item_name");
                for (MobileElement mobileElement : remainStocks) {
                    allStocks.add(mobileElement.getText());
                }
                break;
            } catch (NoSuchElementException e) {
            }
        }
        LogUtil.getLogger().info("最新浏览列表下所有最新浏览: " + allStocks.toString());
        List<String> list = removeDuplicateItem(allStocks);
        LogUtil.getLogger().info("最新浏览列表下所有最新浏览(剔除重复的元素): " + allStocks.toString());
        return list;
    }

    public void enterIntoLatestBrowseMoreView() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/self_stock_edit_view_layout").click();
    }

    public void switchSortType(SortType sortType) {
        super.createSessionAfterTimeout();
        String text = this.getDzhIosDriver().findElementById("com.android.dazhihui:id/zf_sort_text").getText();
        AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/tv_zf");
        switch (sortType) {
            case ZHANGDIEE:
                if (text.equals("涨幅")) {
                    androidElement.click();
                } else if (text.equals("涨跌额")) {
                } else if (text.equals("成交量")) {
                    androidElement.click();
                    androidElement.click();
                }
                return;
            case ZHANGFU:
                if (text.equals("涨幅")) {
                } else if (text.equals("涨跌额")) {
                    androidElement.click();
                    androidElement.click();
                } else if (text.equals("成交量")) {
                    androidElement.click();
                }
                return;
            case CHENGJIAOLIANG:
                if (text.equals("涨幅")) {
                    androidElement.click();
                    androidElement.click();
                } else if (text.equals("涨跌额")) {
                    androidElement.click();
                } else if (text.equals("成交量")) {
                }
                return;
        }
    }

    public String getMessageWhenDeleteAllSelfStockOrLatestBrowse() {
        super.createSessionAfterTimeout();
        return this.getDzhIosDriver().findElementById("android:id/message").getText();
    }

    public void switchMenuView(boolean isOpen) {
        super.createSessionAfterTimeout();
        boolean mark = this.textIsExist("大智慧");
        if (isOpen) {
            if (mark) {
                this.getDzhIosDriver().findElementById("com.android.dazhihui:id/messageView").click();
            }
        } else {
            if (!mark) {
                this.getDzhIosDriver().findElementById("com.android.dazhihui:id/messageView").click();
            }
        }
    }

    public void checkAddOrderOfSelfStocks(List<String> originOrder) {
        super.createSessionAfterTimeout();
        List<AndroidElement> selfList = this.getDzhIosDriver().findElementsById("com.android.dazhihui:id/tv_name");
        List<String> nowOrder = new ArrayList<String>();
        for (AndroidElement item : selfList) {
            nowOrder.add(item.getText());
        }
        Collections.reverse(nowOrder);
        LogUtil.getLogger().info("expect: " + originOrder.toString() + " actual:" + nowOrder.toString() + "自选列表下的自选股恢复为添加顺序");
        Assert.assertEquals(originOrder.toString(), nowOrder.toString(), "自选列表下的自选股恢复为添加顺序");
    }

    public void checkNewestSortOnSelfSelectionView(boolean isDesc) {
        super.createSessionAfterTimeout();
        List<AndroidElement> selfList = this.getDzhIosDriver().findElementsById("com.android.dazhihui:id/tv_zxj");
        List<Double> unSort = new ArrayList<Double>();
        for (AndroidElement item : selfList) {
            String text = item.getText();
            if (text.equals("--")) {
                text = "0.00";
            }
            Double stockPrice = Double.parseDouble(text);
            unSort.add(stockPrice);
        }
        if (isDesc) {
            String actual = unSort.toString();
            Collections.sort(unSort);
            Collections.reverse(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect + " actual:" + actual + "检查最新降序排序是否正确");
            Assert.assertEquals(actual, expect, "检查最新降序排序是否正确");
        } else {
            String actual = unSort.toString();
            Collections.sort(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect + " actual:" + actual + "检查最新升序排序是否正确");
            Assert.assertEquals(actual, expect, "检查最新升序排序是否正确");
        }

    }

    public void checkIncreaseSortOnSelfSelectionView(boolean isDesc) {
        super.createSessionAfterTimeout();
        List<AndroidElement> selfList = this.getDzhIosDriver().findElementsById("com.android.dazhihui:id/tv_zf");
        List<Double> unSort = new ArrayList<Double>();
        for (AndroidElement item : selfList) {
            String text = item.getText();
            text = text.replace("%", "").replace("+", "");
            if (text.equals("--") || text.equals("-")) {
                text = "0.00";
            }
            Double stockIncrease = 0.0;
            if (text.contains("万")) {
                text = text.replace("万", "");
                stockIncrease = Double.parseDouble(text) * 10000;
            } else {
                stockIncrease = Double.parseDouble(text);
            }
            unSort.add(stockIncrease);
        }
        if (isDesc) {
            String actual = unSort.toString();
            Collections.sort(unSort);
            Collections.reverse(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect + " actual:" + actual + "检查涨幅降序排序是否正确");
            Assert.assertEquals(actual, expect, "检查涨幅降序排序是否正确");
        } else {
            String actual = unSort.toString();
            Collections.sort(unSort);
            String expect = unSort.toString();
            LogUtil.getLogger().info("expect: " + expect + " actual:" + actual + "检查涨幅升序排序是否正确");
            Assert.assertEquals(actual, expect, "检查涨幅升序排序是否正确");
        }

    }

    public void checkNoExistSelfStockOnEditSelectionView() {
        super.createSessionAfterTimeout();
        this.checkPoint.checkIDNotExist("com.android.dazhihui:id/dzh_delete_item_name");
    }

    public void checkNoExistSpecialSelfStockOnEditSelectionViewOrSelectionView(String selfStockName) {
        super.createSessionAfterTimeout();
        this.checkPoint.checkTextNotExist(selfStockName);
    }

    public void checkExistSpecialSelfStockOnEditSelectionViewOrSelectionView(String selfStockName) {
        super.createSessionAfterTimeout();
        this.checkPoint.checkTextExist(selfStockName);
    }

    public void checkStockIsSelfOnSearchStockView(String selfStockName) {
        super.createSessionAfterTimeout();
        AndroidElement listView = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/searchstock_listview");
        List<MobileElement> stocks = listView.findElementsByClassName("android.widget.LinearLayout");
        boolean tag = false;
        for (MobileElement item : stocks) {
            try {
                if (tag) {
                    break;
                }
                item.findElementByName(selfStockName);
                item.findElementByName("已加入");
                tag = true;
            } catch (NoSuchElementException e) {
                LogUtil.getLogger().debug(selfStockName + " not found in current view");
            }
        }
        LogUtil.getLogger().info("检查" + selfStockName + "是否为自选股");
        Assert.assertEquals(true, tag, selfStockName + " is a self stock");
    }

    public void checkSelfStockTop(String stock) {
        String stockName = this.getDzhIosDriver().findElementById("com.android.dazhihui:id/tv_name").getText();
        LogUtil.getLogger().info("expect: " + stock + " actual:" + stockName + " 检查" + stock + "是否置顶");
        Assert.assertEquals(stockName, stock);
    }

}
