package gw.com.cn.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lusha on 2017/2/8.
 */
public class OcrUtil {

    public static String recognizedText(String tessdataPath, String imagePath) {
        File imageFile = new File(imagePath);
        Tesseract instance = Tesseract.getInstance();
        instance.setDatapath(tessdataPath);
        instance.setLanguage("chi_sim");
        String result = "";
        try {
            result = instance.doOCR(imageFile);
            Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
            Matcher m = CRLF.matcher(result);
            if (m.find()) {
                result = m.replaceAll("<br>");
            }
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> recognizedTextSpiltResult(String tessdataPath, String imagePath) {
        String result = OcrUtil.recognizedText(tessdataPath, imagePath);
        List<String> list = new ArrayList<String>();
        String[] content = result.split("<br>");
        for (int i = 0; i < content.length; i++){
            list.add(content[i]);
        }
        return list;
    }
}
