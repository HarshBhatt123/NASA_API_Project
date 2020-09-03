package com.example.NASAexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageLib extends AppCompatActivity implements adapter.OnItemClickListener {
   public static final String EXTRA_URL = "imageUrl";
   public static final String EXTRA_TITLE= "title";
   public static final String EXTRA_MEDIA= "mediaType";
   public static final String EXTRA_DESCRIPTION = "description";
   public static final String EXTRA_ID = "id";


    private EditText mTypeSearch;
    private Button mGo;
    private String q;
    private static final String TAG = "ImageLib";


    private RecyclerView mRecyclerView;
    private adapter mExampleAdapter;
    private ArrayList<itemsList> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_lib);
        mTypeSearch = findViewById(R.id.et);

        mGo = findViewById(R.id.bn);
        mRecyclerView=findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        //listView = findViewById(R.id.lv);

        mTypeSearch.setSingleLine();


        //for List view
//        Handler handler = new Handler();
//        ListElementsArrayList= new ArrayList<String >(Arrays.asList(ListElements));
//        adapter= new ArrayAdapter<String>(ImageLib.this,android.R.layout.simple_list_item_1,ListElementsArrayList);
//        listView.setAdapter(adapter);

        mTypeSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTypeSearch.getText().toString().trim().equals("")){
                    Toast.makeText(ImageLib.this, "Type Something", Toast.LENGTH_SHORT).show();
                }else {
                    q = mTypeSearch.getText().toString();
                    getAPIdata();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTypeSearch.getText().toString().trim().equals("")){
                    Toast.makeText(ImageLib.this, "Type Something", Toast.LENGTH_SHORT).show();
                }else{
                    clearDataRecyclerView();
                    Toast.makeText(ImageLib.this, "Please wait , fetching data", Toast.LENGTH_SHORT).show();
                    q = mTypeSearch.getText().toString();
                    getAPIdata();
                }

            }
        });

    }

    private void getAPIdata(){
        RequestQueue queue ;
        queue = Volley.newRequestQueue(this);

        //String baseUrl = "https://images-api.nasa.gov/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://images-api.nasa.gov/search?q="+q   , null,
                new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject collectionObject = response.getJSONObject("collection");
                    JSONArray itemsArray = collectionObject.getJSONArray("items");

                    for (int i = 0; i < itemsArray.length(); i++){
                        JSONObject itemsObject = itemsArray.getJSONObject(i);

                        JSONArray linkArray = itemsObject.getJSONArray("links");
                        JSONObject linkObject = linkArray.getJSONObject(0);

                        String ImgLink = linkObject.getString("href");


                        JSONArray dataArray = itemsObject.getJSONArray("data");
                        JSONObject dataObject = dataArray.getJSONObject(0);


                        String description = dataObject.getString("description");
                        String nasaId = dataObject.getString("nasa_id");
                        String  title= dataObject.getString("title");
                        String  mediaType= dataObject.getString("media_type");

                        mList.add(new itemsList( ImgLink,title, nasaId,description,mediaType));

                        mExampleAdapter = new adapter(ImageLib.this, mList);
                        mRecyclerView.setAdapter(mExampleAdapter);
                        mExampleAdapter.setOnItemClickListener(ImageLib.this);


//                        ListElementsArrayList.add(ImgLink+"  "+description+"  "+nasaId+"  "+title+"  "+mediaType);
//                        adapter.notifyDataSetChanged();



                    //    Toast.makeText(ImageLib.this, ImgLink, Toast.LENGTH_LONG).show();

                    }

                    Log.d(TAG, "onResponse: working"+ response.getString("title"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ImageLib.this, "Something is  wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: Something wrong");
            }
        });

        queue.add(jsonObjectRequest);


    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        itemsList clickedItem = mList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_MEDIA, clickedItem.getType());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescription());
       detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());

        startActivity(detailIntent);
    }

    public  void clearDataRecyclerView(){
        mList.clear();
        mExampleAdapter.notifyDataSetChanged();
    }
}