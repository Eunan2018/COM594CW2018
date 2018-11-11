package com.eunan.tracey.com594cw2018;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final String TAG = "PropertyManager";
    private Context context;
    private Properties properties;

    public PropertyManager(Context context) {
        Log.d(TAG, "PropertyManager: called");
        this.context = context;
        //creates a new object ‘Properties’
        properties = new Properties();
    }

    public Properties getProperties(String FileName) {
        Log.d(TAG, "getProperties: starts");
        try {
            //access to the folder ‘assets’
            AssetManager am = context.getAssets();
            //opening the file
            InputStream inputStream = am.open(FileName);
            //loading of the properties
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e("PropertiesReader", e.toString());
        }
        return properties;
    }

    public String readJSONFromAsset(String FileName) {

        String json = null;
        try {
            InputStream is = context.getAssets().open(FileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
