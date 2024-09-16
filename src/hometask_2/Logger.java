package hometask_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    /* Продублировать импровизированный лог (историю) чата в файле; */
    public static void writelog(String nick, String text) {

        try (FileOutputStream fos = new FileOutputStream("hometask_2\\" + nick + "_log.txt", true)) {
            text=text+"\n";
            byte[] buffer = (text).getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            new File(".",nick + "_log.txt");
            writelog( nick,  text);
        }
    }

    /*
     * При запуске клиента чата заполнять поле истории из файла, если он существует.
     */
    public static String readlog(String nick) {

        try (FileInputStream fin = new FileInputStream("hometask_2\\" + nick + "_log.txt")) {
            String out = "";
            byte[] buffer = new byte[256];
            System.out.println("File data:");
            int count;
            while ((count = fin.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    System.out.print((char) buffer[i]);
                    out += String.valueOf((char) buffer[i]);
                }
            }
            return out;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("файл истории не создан");
            return null;
        }       
    }

    public static String getTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj) + " ";
    }

}
