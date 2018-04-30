package com.example.hp.recycleviewhorizontaldemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView_horixontal;
    LinearLayoutManager linearLayoutManager;
    ArrayList<DataPojo> arrayList_horizontal;
    HorizontalAdapter horizontalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList_horizontal= new ArrayList<>();
        recyclerView_horixontal=(RecyclerView)findViewById(R.id.rl_horizonatl);
        recyclerView_horixontal.setHasFixedSize(true);
        linearLayoutManager= new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
        recyclerView_horixontal.setLayoutManager(linearLayoutManager);
        fetchdata();

    }


    public  class  HorizontalAdapter extends  RecyclerView.Adapter<HorizontalAdapter.ViewHolder>{
        ArrayList<DataPojo> dataPojos;
        Context context;

        public HorizontalAdapter(Context context,ArrayList<DataPojo> dataPojos) {
            this.dataPojos=dataPojos;
            this.context=context;
        }

        @NonNull
        @Override
        public HorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal,parent,false);
            HorizontalAdapter.ViewHolder viewHolder = new HorizontalAdapter.ViewHolder(view);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HorizontalAdapter.ViewHolder holder, int position) {
            holder.text.setText(dataPojos.get(position).getCat_name());

        }

        @Override
        public int getItemCount() {
            return dataPojos.size();
        }

        public  class  ViewHolder extends RecyclerView.ViewHolder {
            TextView text;

            public ViewHolder(View itemView) {
                super(itemView);
                text=(TextView)itemView.findViewById(R.id.text);
            }
        }
    }

    public  void  fetchdata(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(AppConstants.URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<DataPojo>  records = parseHorizonatl(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                horizontalAdapter= new HorizontalAdapter(getApplicationContext(),arrayList_horizontal);
                recyclerView_horixontal.setAdapter(horizontalAdapter);
                horizontalAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);

    }

    private  ArrayList<DataPojo> parseHorizonatl  (JSONObject jsonObject) throws JSONException{
        JSONArray jsonArray = jsonObject.getJSONArray("categories");

        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

            DataPojo dataPojo = new DataPojo();

            dataPojo.setCat_name(jsonObject1.getString("category"));

            arrayList_horizontal.add(dataPojo);
        }
        return  arrayList_horizontal;
    }
}
