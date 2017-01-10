package gw.com.cn.keyboard;

import gw.com.cn.DZHAndroidDriver;

/**
 * Created by lusha on 2016/12/27.
 */
public class DzhDigitKeyboard extends Keyboard{

    private int widthOffset;

    private int baseHeight;

    private DZHAndroidDriver dzhAndroidDriver;

    public DzhDigitKeyboard(DZHAndroidDriver dzhAndroidDriver) {
        super(dzhAndroidDriver);
        this.dzhAndroidDriver = dzhAndroidDriver;
        int screenWidth = dzhAndroidDriver.manage().window().getSize().getWidth();
        int screenHeight = dzhAndroidDriver.manage().window().getSize().getHeight();
        widthOffset = screenWidth / 10;
        //int dzhKeyboardHeight = 50 * dzhAndroidDriver.getScreenDensity() / 160;
        int dzhKeyboardHeight = heightOffset * 8;
        baseHeight = screenHeight - dzhKeyboardHeight;
    }

    private void touchDzhDigitKeyboard(int x, int y) {
        if (x > 5 || y > 4 || x < 1 || y < 1) {
            throw new IllegalArgumentException("args error, please validate");
        }
        int startX = (2 * x - 1) * widthOffset;
        int startY = (2 * y - 1) * heightOffset + baseHeight;
        dzhAndroidDriver.tap(1, startX, startY, 1);
    }

    public void tap_03() {
        this.touchDzhDigitKeyboard(1, 1);
    }

    public void tap_1() {
        this.touchDzhDigitKeyboard(2, 1);
    }

    public void tap_2() {
        this.touchDzhDigitKeyboard(3, 1);
    }

    public void tap_3() {
        this.touchDzhDigitKeyboard(4, 1);
    }

    public void tap_systemkey() {
        this.touchDzhDigitKeyboard(5, 1);
    }

    public void tap_04() {
        this.touchDzhDigitKeyboard(1, 2);
    }

    public void tap_4() {
        this.touchDzhDigitKeyboard(2, 2);
    }

    public void tap_5() {
        this.touchDzhDigitKeyboard(3, 2);
    }

    public void tap_6() {
        this.touchDzhDigitKeyboard(4, 2);
    }

    public void tap_letter() {
        this.touchDzhDigitKeyboard(5, 2);
    }

    public void tap_600() {
        this.touchDzhDigitKeyboard(1, 3);
    }

    public void tap_7() {
        this.touchDzhDigitKeyboard(2, 3);
    }

    public void tap_8() {
        this.touchDzhDigitKeyboard(3, 3);
    }

    public void tap_9() {
        this.touchDzhDigitKeyboard(4, 3);
    }

    public void tap_close() {
        this.touchDzhDigitKeyboard(5, 3);
    }

    public void tap_000() {
        this.touchDzhDigitKeyboard(1, 4);
    }

    public void tap_300() {
        this.touchDzhDigitKeyboard(2, 4);
    }

    public void tap_0() {
        this.touchDzhDigitKeyboard(3, 4);
    }

    public void tap_dot() {
        this.touchDzhDigitKeyboard(4, 4);
    }

    public void tap_del() {
        this.touchDzhDigitKeyboard(5, 4);
    }

}