package yalong.site.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * 读写文件
 *
 * @author yaLong
 */
public class FileUtil {

    public static ArrayList<String> readFile(String path) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeFile(String path, String... lines) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines) {
                out.write(line);
                out.newLine();
            }
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
