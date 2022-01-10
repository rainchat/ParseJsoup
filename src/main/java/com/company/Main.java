package com.company;


import com.company.api.SParseApi;

import java.io.IOException;


public class Main {

    private static SparseImpl sParseApi;

    public static SParseApi getParseApi() {
        return sParseApi;
    }

    public static void main(String[] args) throws IOException {

        sParseApi = new SparseImpl();
        sParseApi.setup();
    }
}
