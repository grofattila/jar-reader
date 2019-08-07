package reader;


import org.codehaus.plexus.util.StringInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarReader {

    public static void main(String[] args) throws IOException {

        List<String> dependencies = new ArrayList<>();
        String path = "/home/atig/Data/github/jar-reader/target/jar-reader-1.0-SNAPSHOT.jar";
        findProperties(dependencies, path);
    }

    private static void findProperties(List<String> dependecies, String jarPath) throws IOException {
        JarFile jarFile = new JarFile(jarPath);
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            final String entryName = entry.getName();
            System.out.println("File name: " + entryName);
            if (entryName.contains(".properties")) {
                System.out.println("===========>> .properties found");
                InputStream input = jarFile.getInputStream(entry);
                String data = readFile(input);
                processConfigFile(data);
            }
        }
    }

    private static void processConfigFile(String data) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new StringInputStream(data)));

        for (Object key : properties.keySet()) {
            System.out.println(key  +" - "+ properties.getProperty(key.toString()));
        }
    }

    private static String readFile(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }


}

