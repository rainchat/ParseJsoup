package com.company;

import com.company.api.SParseApi;
import com.company.database.MySql;
import com.company.sites.BlackSpigotLeak;

public class SparseImpl implements SParseApi {

    private MySql mySql;

    public SparseImpl() {

        // Подключение базы данных
        mySql = new MySql();


    }

    public void setup() {
        mySql.setup();


        // Cбор данных
        BlackSpigotLeak.parsePage();
        //DirectLeak.pageIncr();
        //DirectLeak.getLeak("https://directleaks.to/downloads/spartan-anti-cheat-advanced-cheat-detection-hack-blocker-1-7-2-1-18.4681/");
    }

    @Override
    public Main getMain() {
        return null;
    }

    @Override
    public MySql getMySql() {
        return mySql;
    }
}
