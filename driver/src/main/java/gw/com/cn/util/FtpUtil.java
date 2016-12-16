package gw.com.cn.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lusha on 2016/12/12.
 */
public class FtpUtil {

    private FTPClient ftpClient;

    public FtpUtil(String ip) {
        this.ftpClient = new FTPClient();
        ftpClient.setControlEncoding("gb2312");
        try {
            ftpClient.connect(ip);
            ftpClient.login("anonymous", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDirectory(String changePath){
        try {
            this.ftpClient.changeWorkingDirectory(changePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File downloadNewestAndroidApp(String localSavePath) throws IOException {
        this.changeDirectory("大智慧");
        FTPFile[] ftpFiles = this.ftpClient.listDirectories();
        double[] dzhAppVersions = new double[ftpFiles.length - 2];
        int index = 0;
        for ( FTPFile ftp : ftpFiles) {
            String fileName = ftp.getName();
            if(!fileName.equals(".") && !fileName.equals("..")){
                dzhAppVersions[index++] = Double.valueOf(fileName);
            }
        }
        Arrays.sort(dzhAppVersions);
        double newestVersion = dzhAppVersions[dzhAppVersions.length - 1];
        System.out.println(newestVersion);
        this.changeDirectory(String.valueOf(newestVersion));
        this.changeDirectory("android");
        FTPFile[] ftpFile = this.ftpClient.listFiles();
        List<FTPFile> ftpFileFilter = new ArrayList<FTPFile>();
        for ( FTPFile ftp : ftpFile) {
            if(ftp.getName().contains("大智慧") && ftp.getName().contains("正式环境")){
                ftpFileFilter.add(ftp);
            }
        }
        FTPFile newestApp = ftpFileFilter.get(ftpFileFilter.size() - 1);
        localSavePath = localSavePath + newestApp.getName();
        File localSaveFile = new File(localSavePath);
        OutputStream outputStream = new FileOutputStream(localSaveFile);
        this.ftpClient.retrieveFile(newestApp.getName(),outputStream);
        return localSaveFile;
    }

    public File downloadNewestIosApp(String localSavePath){
        return null;
    }

    public void closeConnect(){
        try {
            this.ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        FtpUtil ftpUtil = new FtpUtil("10.15.54.198");
        File file = ftpUtil.downloadNewestAndroidApp("d:\\");
        ftpUtil.closeConnect();
        System.out.println(file.getName());
    }
}
