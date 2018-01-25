package final_project.utils;

import java.io.*;

public class FilesIO {

    public static String readFile(String path) throws IOException {
        validate(path);

        String content = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null)
                content = content.concat("\n").concat(line);
            if(content.length() > 1)
                content = content.substring(1);
        } catch (IOException e) {
            throw new IOException("FilesIO.readFile : Can't read file " + path);
        }
        return content;
    }

    public static void writeFile(String path, String content, boolean param) throws IOException {
        validate(path);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, param))) {
            bw.append(content);
        } catch (IOException e) {
            throw new IOException("FilesIO.writeFile : Can't write to file " + path);
        }
    }

    private static void validate(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            throw new FileNotFoundException("FilesIO.validate : File " + filePath + " does not exist");
        if (!file.canRead() || !file.canWrite())
            throw new IOException("FilesIO.validate : Can't operate with file " + filePath);
    }

}
