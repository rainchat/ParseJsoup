package com.company;


import com.company.database.MySql;
import com.company.objects.LeakInfo;
import com.company.sites.BlackSpigotLeak;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Main {

    private static MySql mySql;

    public static void main(String[] args) throws IOException {

        // Подключение базы данных
       // mySql = new MySql();
       // mySql.setup();


        // Cбор данных

        //directLeakLeak.getLeak("https://blackspigot.com/downloads/hub-bedwars-palm-village-300x300.23009/");
        //directLeakLeak.parseSite(2);

        List<LeakInfo> leakInfoList = BlackSpigotLeak.pageIncr();


        try(FileWriter writer = new FileWriter("text.txt", false))
        {
            for (LeakInfo leakInfo: leakInfoList) {
                String text = leakInfo.toString();
                writer.write(text);
                writer.append('\n');
            }

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }





    }
}
