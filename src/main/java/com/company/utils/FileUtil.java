package com.company.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void write(String string) {
        try(FileWriter writer = new FileWriter("text.txt", true))
        {

            writer.write(string);
            writer.append('\n');

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
