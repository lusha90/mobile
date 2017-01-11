package gw.com.cn.keyboard;

import gw.com.cn.DZHAndroidDriver;
import org.openqa.selenium.Rectangle;

/**
 * Created by lusha on 2017/1/9.
 */
public class Keyboard {

    int width;
    int height;
    private  DZHAndroidDriver dzhAndroidDriver;

    public Keyboard(DZHAndroidDriver dzhAndroidDriver) {
        this.dzhAndroidDriver = dzhAndroidDriver;
        Rectangle rectangle = this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_view").getRect();
        width = rectangle.getWidth();
        height = rectangle.getHeight();
    }

    public void switchDigitKeyboard(){
        this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_numeral").click();
    }

    public void switchLetterKeyboard(){
        this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_letter").click();
    }

    public void switchSystemKeyboard(){
        this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_chinese").click();
    }

    public void openSpeech(){
        this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_speech").click();
    }

    public void closeKeyboard(){
        this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_close").click();
    }
}
