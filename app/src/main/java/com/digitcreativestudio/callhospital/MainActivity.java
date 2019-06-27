package com.digitcreativestudio.callhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHOTO = "photo";
    public static final String REGION = "region";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    private String URL = "https://indiaforensic.com/certifications/wp-content/uploads/2015/12/hospital-696x464.jpg";

    private ImageView banner;
    private List<HospitalModel> list_data;
    private HospitalAdapter adapter;
    private RecyclerView rv_hospitals;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner = findViewById(R.id.banner);
        Glide.with(getApplicationContext()).load(URL).into(banner);

        list_data = new ArrayList<HospitalModel>();
        adapter = new HospitalAdapter(getApplicationContext(), list_data);
        rv_hospitals = findViewById(R.id.list_hospitals);
        llm = new GridLayoutManager(getApplicationContext(), 2);
        rv_hospitals.setHasFixedSize(true);
        rv_hospitals.setLayoutManager(llm);
        rv_hospitals.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        requestListHospitalsByVolley();

        //onClick
        ItemClickSupport.addTo(rv_hospitals)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailActivity.class);
                        moveWithObjectIntent.putExtra(ID, list_data.get(position).getId());
                        moveWithObjectIntent.putExtra(NAME, list_data.get(position).getName());
                        moveWithObjectIntent.putExtra(PHOTO, list_data.get(position).getPhoto());
                        moveWithObjectIntent.putExtra(REGION, list_data.get(position).getRegion());
                        moveWithObjectIntent.putExtra(EMAIL, list_data.get(position).getEmail());
                        moveWithObjectIntent.putExtra(PHONE, list_data.get(position).getPhone());
                        startActivity(moveWithObjectIntent);
                    }
                });

        //onLongClick
        ItemClickSupport.addTo(rv_hospitals)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        list_data.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "posisi: "+position+" berhasil dihapus", Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
    }

    // (fungsi) request & retrieve list blog
    private void requestListHospitalsByVolley() {

        StringRequest srblogList = new StringRequest(Request.Method.GET, "https://mafmudin.github.io/json.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objHospital = jsonArray.getJSONObject(i);
                                HospitalModel obj = new HospitalModel();
                                obj.setId(objHospital.getString("hospital_id"));
                                obj.setName(objHospital.getString("hospital_name"));
                                obj.setRegion(objHospital.getString("region"));
                                obj.setPhoto(objHospital.getString("hospital_photo"));
                                obj.setEmail(objHospital.getString("hospital_email"));
                                obj.setPhone(objHospital.getString("hospital_phone"));
                                list_data.add(obj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //request handler volley
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(srblogList);

    }
}
