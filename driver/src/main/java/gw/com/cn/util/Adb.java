package gw.com.cn.util;

import java.io.*;

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

    private void executeAdbCmd(String cmd){
        try {
            String command = "cmd.exe /C " + cmd;
            LogUtil.getLogger().info(command);
            CommandUtil.executeCommand(command, new FileOutputStream(System.getProperty("java.io.tmpdir") + File.separator + "adbTempLog"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void adbShell(){
        String cmd = this.adbCmd + "";
        this.executeAdbCmd(cmd);
    }

}
