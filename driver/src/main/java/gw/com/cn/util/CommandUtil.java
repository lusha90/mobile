package gw.com.cn.util;

import java.io.*;

/**
 * Created by lusha on 2016/12/16.
 */
public class CommandUtil {

    public static int executeCommand(String cmd, OutputStream outputStream) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd);
        StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "error", outputStream);
        StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "output", outputStream);
        errorGobbler.start();
        outputGobbler.start();
        int exitVal = 0;
        try {
            exitVal = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.getLogger().info(cmd + " exitValue: " + exitVal);
        outputStream.flush();
        outputStream.close();
        return exitVal;
    }

}

class StreamGobbler extends Thread {
    private InputStream inputStream;
    private String type;
    private OutputStream outputStream;

    public StreamGobbler(InputStream is, String type) {
        this(is, type, null);
    }

    public StreamGobbler(InputStream is, String type, OutputStream redirect) {
        this.inputStream = is;
        this.type = type;
        this.outputStream = redirect;
    }

    public void run() {
        try {
            PrintWriter pw = null;
            if (outputStream != null) {
                pw = new PrintWriter(outputStream);
            }
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (pw != null)
                    pw.println(line);
                LogUtil.getLogger().debug(type + ">" + line);
            }
            if (pw != null)
                pw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}