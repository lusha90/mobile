package gw.com.cn.keyboard;

import gw.com.cn.DZHAndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;

/**
 * Created by lusha on 2017/1/9.
 */
public class Keyboard {

    int width;
    int height;
    private  DZHAndroidDriver dzhAndroidDriver;

    public Keyboard(DZHAndroidDriver dzhAndroidDriver) {
        this.dzhAndroidDriver = dzhAndroidDriver;
        Dimension dimension;
        try{
            dimension = this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_view").getSize();
            height = dimension.getHeight();
        }catch (NoSuchElementException e){
            dimension = this.dzhAndroidDriver.findElementById("com.android.dazhihui:id/keyboard_title").getSize();
            height = dimension.getHeight() * 5;
        }
        width = dimension.getWidth();
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
