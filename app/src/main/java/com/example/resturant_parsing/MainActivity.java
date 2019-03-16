package com.example.resturant_parsing;

import android.R.layout;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    RequestParams params;


    ArrayList<String>pro_name;

    ListView listview;
    LayoutInflater inflate;

    String url="http://srishti-systems.info/projects/bite_project/api/viewproductsamp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=findViewById(R.id.lviewxml);

        client=new AsyncHttpClient();
        params=new RequestParams();


        pro_name=new ArrayList<>();

        Log.e("In","Out");
        client.get(url,params,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    Log.e("innn","ouut");
                    JSONObject jobjmain=new JSONObject(content);
                    if (jobjmain.getString("success").equals("true")) {
                        JSONArray jarraycat = jobjmain.getJSONArray("category");
                       for (int i = 0; i < jarraycat.length(); i++) {
                            JSONObject jobjcat = jarraycat.getJSONObject(i);


                            JSONArray jarrayapp=jobjcat.getJSONArray("Appetizers");
                            for (int j=0;j<jarrayapp.length();j++){
                                JSONObject jobjapp=jarrayapp.getJSONObject(j);
                                String pronm=jobjapp.getString("productname");
                                pro_name.add(pronm);

                            }
                        }

                    }
                    Adapter adp=new Adapter();
                    listview.setAdapter(adp);

                }
                catch(Exception e){

                }
            }
        });


    }
     public class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pro_name.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflate=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflate.inflate(R.layout.resturantxml,null);
            TextView name=convertView.findViewById(R.id.productnamexml);
            name.setText(pro_name.get(position));
            return convertView;
        }
    }

}
