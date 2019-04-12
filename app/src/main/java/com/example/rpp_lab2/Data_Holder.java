package com.example.rpp_lab2;

import org.json.JSONArray;

public class Data_Holder {

    private static volatile Data_Holder instance;
    private JSONArray data;

    public static Data_Holder getInstance() {
        Data_Holder localInstance = instance;

        if (localInstance == null) {
            instance = localInstance = new Data_Holder();
        }

        return localInstance;
    }

    public void setData(JSONArray data){
        this.data = data;
    }

    public JSONArray getData(){
        return this.data;
    }

}
