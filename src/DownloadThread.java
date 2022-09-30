import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class DownloadThread extends Thread{
    private final String urlStr;
    private final String file;
    private long time;
    private final int maxSpeedInKbPerSec;

    DownloadThread(String urlStr, String file, int maxSpeedInKbPerSec){
        super("DownloadTread_"+ file.substring(file.lastIndexOf("/")+1));//    для зручності тестування 
        this.urlStr = urlStr;
        this.file = file;
        this.maxSpeedInKbPerSec = maxSpeedInKbPerSec;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName() + "....started");//    для зручності тестування

        try {
            URL url = new URL(urlStr);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(file);
            byte[] buffer = new byte[1024];

            int count, speed = 0;
            time = System.currentTimeMillis();
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                if(speed == maxSpeedInKbPerSec-1){
                    if(isSecNotPass()) sleep(((time+1000)-System.currentTimeMillis()));
                    speed = 0;
                    time = System.currentTimeMillis();
                }
                fis.write(buffer, 0, count);
                speed++;
            }

            fis.close();
            bis.close();
        }catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println(Thread.currentThread().getName() + "....ERROR!!!!");//    для зручності тестування
            System.out.println(e);
        }

        System.out.println(Thread.currentThread().getName() + "....finished");
    }
    private boolean isSecNotPass(){
        long t = time+(long)1000;
        time = System.currentTimeMillis();
        return time <= t;
    }
}
