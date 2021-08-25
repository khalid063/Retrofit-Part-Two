package com.example.retrofitparttwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.retrofitparttwo.models.Post;
import com.example.retrofitparttwo.network.ApiInterface;
import com.example.retrofitparttwo.network.RetrofitAdapter;
import com.example.retrofitparttwo.network.RetrofitApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;
    private Button show_ip_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tvDataShow = findViewById(R.id.tvDataShow);

        // --------------------------- find view by id -------------------------
        recyclerView = findViewById(R.id.recycler);
        show_ip_button = findViewById(R.id.show_ip_button);


        //----------------------------- set listener ----------------------------
        show_ip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

                Call<List<Post>> call = apiInterface.getAllPost();

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                        Log.d("msg_one", "Response : " + response.body().get(0).getId());
                        if (!response.isSuccessful()) {
                            //tvDataShow.setText("Code : " + response.code());
                            Log.d("msg_one", "Response : " + response.code());
                            return;
                        }
                        List<Post> allposts = response.body ();
                        retrofitAdapter = new RetrofitAdapter(MainActivity.this, allposts);
                        recyclerView.setAdapter(retrofitAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.VERTICAL, false));

                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        //tvDataShow.setText(t.getMessage());
                        Log.d("msg_one", "Response : " + t.getMessage());
                    }
                });
            }
        });






    }
}