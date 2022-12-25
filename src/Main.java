import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        // Start
        out.println("Hi, input text file with http: ");
        String fname = "http.txt";// i can - i do
        out.println("http.txt");

        // file is good?
        File httpFile = new File(fname);
        if(!httpFile.canRead()) {
            out.println("error, the file cannot be read");
            do{
                out.println("input text file with http:");
                fname = in.nextLine();
                httpFile = new File(fname);
            }while (!httpFile.canRead());
        }
        out.println("File is ok");

        // get http links
        ArrayList<String> listHttp;
        listHttp = fileWork(httpFile);

        out.println("Get " + listHttp.size()+" link");
        out.println("Input path to download dir: ");
        String outDir = "E:downloadTest";out.println("E:\\downloadTest");//in.nextLine();// i can - i do
        out.println("Input max speed (Kb/s): "); // set speed limit
        int maxSpeed = in.nextInt();
        for(var h : listHttp){
            String fileName = h.substring(h.lastIndexOf("/"));
            DownloadThread dt = new DownloadThread(h,outDir+fileName, maxSpeed);
            dt.start();
        }
    }

    /**
     * виймає з файлу посилання
     * @return лист з корректних ссилок для скачування
     * @throws FileNotFoundException
     */
    private static @NotNull ArrayList<String> fileWork(@NotNull File file) throws FileNotFoundException {
        ArrayList<String> listHttp = new ArrayList<>();
        Scanner f = new Scanner(file);
        while (f.hasNextLine()) {
            String s = f.next();
            if (!s.substring(0, 7).intern().equals("http://") && !s.substring(0, 8).intern().equals("https://"))
                continue;
            listHttp.add(s);
        }
        return listHttp;
    }


//    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
//        URL url = new URL(urlStr);
//        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//        fos.close();
//        rbc.close();
//    }

/*    private static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count;
        while ((count = bis.read(buffer, 0, 1024)) != -1) fis.write(buffer, 0, count);
        fis.close();
        bis.close();
    }*/

}








