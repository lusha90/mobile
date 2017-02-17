package gw.com.cn.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lusha on 2016/12/16.
 */
public class Adb {

    private String deviceName = null;
    private String adbCmd = "adb.exe";
    private String adbShellCmd = null;

    private boolean isOnLine() {
        boolean tag = true;
        String cmd = this.adbShellCmd + "\"echo 'ping'\"";
        File file = this.executeAdbCmdForOnline(cmd);
        try {
            String str = FileUtils.readFileToString(file, "utf-8");
            if (str.contains("not found")) {
                tag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public Adb(String sdkPath, String deviceName) {
        this.deviceName = deviceName;
        this.adbCmd = sdkPath + File.separator + this.adbCmd + " -s " + deviceName;
        this.adbShellCmd = this.adbCmd + " shell ";
    }

    public Adb(String deviceName) {
        this.adbCmd = this.adbCmd + " -s " + deviceName;
        this.adbShellCmd = this.adbCmd + " shell ";
    }

    private File executeAdbCmdForOnline(String cmd) {
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

    private File executeAdbCmd(String cmd) {
        if (!this.isOnLine()) {
            LogUtil.getLogger().error(deviceName + " not found, please sure connected!");
            return null;
        }
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

    private void executeAdbCmdWithLogToFile(String cmd, String logPath) {
        if (!this.isOnLine()) {
            LogUtil.getLogger().error(deviceName + " not found, please sure connected!");
            return;
        }
        try {
            String command = "cmd.exe /C " + cmd;
            LogUtil.getLogger().info(command);
            CommandUtil.executeCommand(command, new FileOutputStream(new File(logPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adbPull(String devicePath, String localPath) {
        String cmd = this.adbCmd + " pull " + devicePath + " " + localPath;
        this.executeAdbCmd(cmd);
    }

    public void adbPush(String localPath, String devicePath) {
        String cmd = this.adbCmd + " push " + localPath + " " + devicePath;
        this.executeAdbCmd(cmd);
    }

    public void adbInstall(String appPath) {
        String cmd = this.adbCmd + " install " + appPath;
        this.executeAdbCmd(cmd);
    }

    public void adbUninstall(String packageName) {
        String cmd = this.adbCmd + " uninstall " + packageName;
        this.executeAdbCmd(cmd);
    }

    public String adbScreenshot(String savePath) {
        String cmd = this.adbShellCmd + "\"screencap -p " + savePath + "\"";
        this.executeAdbCmd(cmd);
        return savePath;
    }

    public void adbReboot() {
        String cmd = this.adbCmd + " reboot";
        this.executeAdbCmd(cmd);
    }

    public void adbClearLogcat() {
        String cmd = this.adbCmd + " logcat -c";
        this.executeAdbCmd(cmd);
    }

    public void adbGetLogcat(String logPath) {
        String cmd = this.adbCmd + " logcat -d";
        this.executeAdbCmdWithLogToFile(cmd, logPath);
    }

    public int getScreenDensity() {
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
            if (line.contains("density")) {
                density = Integer.parseInt(line.split("\\:")[1].trim().replace("[", "").replace("]", ""));
            }
        }
        return density;
    }

    public String screenRecord(String videoPath, int timeLimit) {
        //--size 1280x720
        //String endChar = " &\"";
        String endChar = "";
        String cmd = this.adbShellCmd + "\"screenrecord --verbose --bugreport --time-limit " + timeLimit + " " + videoPath + endChar;
        File file = this.executeAdbCmd(cmd);
        try {
            LogUtil.getLogger().info(FileUtils.readFileToString(file, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoPath;
    }

    public void killProcess(String phoneBrand, String processName) {
        String cmd = "";
        if (phoneBrand.equals("oppo")) {
            cmd = this.adbShellCmd + "\"ps|busybox grep " + processName + "\"";
        } else if (phoneBrand.equals("google")) {
            cmd = this.adbShellCmd + "\"ps|grep " + processName + "\"";
        }
        File file = this.executeAdbCmd(cmd);
        try {
            List<String> process = FileUtils.readLines(file, "utf-8");
            for (String str : process) {
                LogUtil.getLogger().info(str);
                String[] array = str.split("\\s+");
                if (array.length > 2) {
                    this.executeAdbCmd(this.adbShellCmd + "\"kill -6 " + array[1] + "\"");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean networkIsAvailable() {
        String address = "www.qq.com";
        String cmd = this.adbShellCmd + "ping -c 10 " + address;
        File file = this.executeAdbCmd(cmd);
        String data = "";
        try {
            data = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean tag = false;
        if (data.contains("10 received, 0% packet loss")) {
            tag = true;
        }
        return tag;
    }

    public void adbShell() {
        String cmd = this.adbCmd + "";
        this.executeAdbCmd(cmd);
    }

}
