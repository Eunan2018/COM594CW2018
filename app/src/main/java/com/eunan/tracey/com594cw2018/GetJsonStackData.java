package com.eunan.tracey.com594cw2018;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetJsonStackData extends AppCompatActivity {
    private static final String TAG = "GetJsonStackData";
    private String baseUrl;
    private ArrayList<Stack> stacks;

    public GetJsonStackData(String bUrl) {
        Log.d(TAG, "GetJsonStackData: called");
        baseUrl = bUrl;
        onDownLoadComplete(baseUrl);

    }


//
//    void exectuteOnSameThread(String searchCriteria){
//        Log.d(TAG, "exectuteOnSameThread: starts");
//        String destinationUri = createUri(searchCriteria);
//
//        GetDataTask getDataTask = new GetDataTask();
//        getDataTask.execute(destinationUri);
//    }
//
//    private String createUri(String searchCriteria){
//        Log.d(TAG, "createUri: starts");
//
//        return Uri.parse(baseUrl).buildUpon()
//                .appendQueryParameter("",searchCriteria)
//                .build().toString();
//    }


    public void onDownLoadComplete(String data) {
        Log.d(TAG, "onDownLoadComplete: starts");

        stacks = new ArrayList<>();

        try {

//            JSONObject jsonData = new JSONObject(data);
//            JSONArray itemsArray = jsonData.getJSONArray("items");
//            //JSONObject owner = jsonData.getJSONObject("owner");
//            JSONObject outputJson = itemsArray.getJSONArray(0).getJSONObject(1);
//            //JSONObject style = outputJson.getJSONObject("owner").getJSONArray("items").getJSONObject(1);
//            String n = String.valueOf(outputJson.getString("display_name"));
//            Stack ss = new Stack();
//            ss.setDisplayName(n);


            JSONObject root = new JSONObject(data);
            JSONArray itemsArray = root.getJSONArray("items");
            JSONObject tag = itemsArray.getJSONObject(0);
          //  JSONArray tags = tag.getJSONArray("tags");

            JSONObject owners = tag.getJSONObject("owner");

            String name = String.valueOf(owners.getString("display_name"));
            Log.d(TAG, "onDownLoadComplete: " + name);





//            for (int i = 0; i < itemsArray.length(); i++) {
//                JSONObject jsonStack = itemsArray.getJSONObject(i);
//
//                Log.d(TAG, "onDownLoadComplete: " + jsonStack.toString());
//                for(int j=0;j<jsonStack.getJSONArray("tags").length();j++){
//                    Stack stack = new Stack();
//                    //String n = jsonStack.getJSONArray("tags").getJSONObject(j).toString();
//                    Log.d(TAG, "onDownLoadComplete: " + n);
//                    //weatherConditions.temperature = String.valueOf(main.getDouble("temp"))
//                    Log.d(TAG, "onDownLoadComplete: " + stack.displayName);
//                }
//


//                String name = jsonStack.getString("display_name");
//                String image = jsonStack.getString("profile_image");
//                String link = jsonStack.getString("link");
//                String title = jsonStack.getString("title");
////
////                Stack stack = new Stack(name, image, link, title);
////                stacks.add(stack);
////                Log.d(TAG, "onDownLoadComplete: finished" + stack.toString());
//                Log.d(TAG, "onDownLoadComplete: " + jsonStack.toString());
//             //   Log.d(TAG, "onDownLoadComplete: " + name);
           // }

        } catch (Exception e) {
            Log.d(TAG, "onDownLoadComplete: Exception " + e.getMessage());
            Log.d(TAG, "onDownLoadComplete: ");

        }
}


}
