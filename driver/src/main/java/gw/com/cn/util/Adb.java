package gw.com.cn.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

/**
 * Created by lusha on 2016/12/16.
 */
public class Adb {

    private String adbCmd = "adb.exe";
    private String adbShellCmd = null;

    public Adb(String sdkPath, String deviceName) {
        this.adbCmd = sdkPath + this.adbCmd + " -s " + deviceName;
        this.adbShellCmd = this.adbCmd + " shell ";
    }

    public Adb(String deviceName) {
        this.adbCmd = this.adbCmd + " -s " + deviceName;
        this.adbShellCmd = this.adbCmd + " shell ";
    }

    private File executeAdbCmd(String cmd){
        File file = null;
        try {
            String command = "cmd.exe /C " + cmd;
            LogUtil.getLogger().info(command);
            file = new File(System.getProperty("java.io.tmpdir") + File.separator + "adbTempLog");
            CommandUtil.executeCommand(command, new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void executeAdbCmdWithLogToFile(String cmd, String logPath){
        try {
            String command = "cmd.exe /C " + cmd;
            LogUtil.getLogger().info(command);
            CommandUtil.executeCommand(command, new FileOutputStream(new File(logPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adbPull(String devicePath, String localPath){
        String cmd = this.adbCmd + " pull " + devicePath + " " + localPath;
        this.executeAdbCmd(cmd);
    }

    public void adbPush(String localPath, String devicePath){
        String cmd = this.adbCmd + " push " + localPath + " " + devicePath;
        this.executeAdbCmd(cmd);
    }

    public void adbInstall(String appPath){
        String cmd = this.adbCmd + " install " + appPath;
        this.executeAdbCmd(cmd);
    }

    public void adbUninstall(String packageName){
        String cmd = this.adbCmd + " uninstall " + packageName;
        this.executeAdbCmd(cmd);
    }

    public String adbScreenshot(String savePath){
        String cmd = this.adbShellCmd + "\"screencap -p " + savePath  + "\"";
        this.executeAdbCmd(cmd);
        return savePath;
    }

    public void adbReboot(){
        String cmd = this.adbCmd + " reboot";
        this.executeAdbCmd(cmd);
    }

    public void adbClearLogcat(){
        String cmd = this.adbCmd + " logcat -c";
        this.executeAdbCmd(cmd);
    }

    public void adbGetLogcat(String logPath){
        String cmd = this.adbCmd + " logcat -d";
        this.executeAdbCmdWithLogToFile(cmd, logPath);
    }

    public int getScreenDensity(){
        String cmd = this.adbShellCmd + "getprop";
        File file = this.executeAdbCmd(cmd);
        List<String> list = null;
        int density = 160;
        try {
            list = FileUtils.readLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : list) {
            if(line.contains("density")){
                density = Integer.parseInt(line.split("\\:")[1].trim().replace("[", "").replace("]", ""));
            }
        }
        return density;
    }

    public void adbShell(){
        String cmd = this.adbCmd + "";
        this.executeAdbCmd(cmd);
    }

}
