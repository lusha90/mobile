package gw.com.cn.util;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by lusha on 2016/11/28.
 */
public class JsonUtil {

    public static JSONObject  stringToJson(String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject;
    }

    public static JSONObject readJson(String jsonFilePath){
        File file = new File(jsonFilePath);
        return  readJson(file);
    }

    public static JSONObject readJson(File jsonFile){
        String jsonContent = null;
        if(jsonFile.exists()){
            try {
                jsonContent = FileUtils.readFileToString(jsonFile, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            LogUtil.getLogger().info(jsonFile + " not found! ");
        }
        return  stringToJson(jsonContent);
    }

}
