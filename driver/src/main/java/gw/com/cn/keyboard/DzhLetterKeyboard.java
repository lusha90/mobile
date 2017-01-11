package gw.com.cn.keyboard;

import gw.com.cn.DZHAndroidDriver;

/**
 * Created by lusha on 2016/12/27.
 */
public class DzhLetterKeyboard extends Keyboard{

    private DZHAndroidDriver dzhAndroidDriver;
    private int widthOffset;
    private int heightOffset;
    private int baseHeight;
    private int bottomWidthOffset;

    public DzhLetterKeyboard(DZHAndroidDriver dzhAndroidDriver) {
        super(dzhAndroidDriver);
        this.dzhAndroidDriver = dzhAndroidDriver;
        widthOffset = width / 20;
        heightOffset = height / 8;
        baseHeight = this.dzhAndroidDriver.manage().window().getSize().height - height;
        bottomWidthOffset = width / 8;
    }

    private void touchDzhLetterKeyboardOneRow(int x) {
        if (x > 10 || x < 1) {
            throw new IllegalArgumentException("args error, please validate");
        }
        int startX = (2 * x - 1) * widthOffset;
        int startY = baseHeight + heightOffset;
        dzhAndroidDriver.tap(1, startX, startY, 1);
    }

    private void touchDzhLetterKeyboardTwoRow(int x) {
        if (x > 9 || x < 1) {
            throw new IllegalArgumentException("args error, please validate");
        }
        dzhAndroidDriver.tap(1, 2 * x * widthOffset, baseHeight + 3 * heightOffset, 1);
    }

    private void touchDzhLetterKeyboardThreeRow(int x) {
        if (x > 9 || x < 1) {
            throw new IllegalArgumentException("args error, please validate");
        }
        dzhAndroidDriver.tap(1, 2 * x * widthOffset, baseHeight + 5 * heightOffset, 1);
    }

    public void tap_q() {touchDzhLetterKeyboardOneRow(1);}
    public void tap_w() {touchDzhLetterKeyboardOneRow(2);}
    public void tap_e() {touchDzhLetterKeyboardOneRow(3);}
    public void tap_r() {touchDzhLetterKeyboardOneRow(4);}
    public void tap_t() {touchDzhLetterKeyboardOneRow(5);}
    public void tap_y() {touchDzhLetterKeyboardOneRow(6);}
    public void tap_u() {touchDzhLetterKeyboardOneRow(7);}
    public void tap_i() {touchDzhLetterKeyboardOneRow(8);}
    public void tap_o() {touchDzhLetterKeyboardOneRow(9);}
    public void tap_p() {touchDzhLetterKeyboardOneRow(10);}


    public void tap_a() {touchDzhLetterKeyboardTwoRow(1);}
    public void tap_s() {touchDzhLetterKeyboardTwoRow(2);}
    public void tap_d() {touchDzhLetterKeyboardTwoRow(3);}
    public void tap_f() {touchDzhLetterKeyboardTwoRow(4);}
    public void tap_g() {
        touchDzhLetterKeyboardTwoRow(5);
    }
    public void tap_h() {touchDzhLetterKeyboardTwoRow(6);}
    public void tap_j() {touchDzhLetterKeyboardTwoRow(7);}
    public void tap_k() {touchDzhLetterKeyboardTwoRow(8);}
    public void tap_l() {touchDzhLetterKeyboardTwoRow(9);}


    public void tap_chinese() {touchDzhLetterKeyboardThreeRow(1);}
    public void tap_z() {touchDzhLetterKeyboardThreeRow(2);}
    public void tap_x() {touchDzhLetterKeyboardThreeRow(3);}
    public void tap_c() {touchDzhLetterKeyboardThreeRow(4);}
    public void tap_v() {touchDzhLetterKeyboardThreeRow(5);}
    public void tap_b() {touchDzhLetterKeyboardThreeRow(6);}
    public void tap_n() {touchDzhLetterKeyboardThreeRow(7);}
    public void tap_m() {touchDzhLetterKeyboardThreeRow(8);}
    public void tap_del() {touchDzhLetterKeyboardThreeRow(9);}


    public void tap_123() {dzhAndroidDriver.tap(1, bottomWidthOffset, 7 * heightOffset, 1);}
    public void tap_close() {dzhAndroidDriver.tap(1, bottomWidthOffset * 4, 7 * heightOffset, 1);}
    public void tap_search() {dzhAndroidDriver.tap(1, bottomWidthOffset * 7, 7 * heightOffset, 1);}

}
