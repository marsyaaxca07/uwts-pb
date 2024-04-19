package com.example.uts_pb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uts_pb.R;
import com.example.uts_pb.data.api.ApiClient;
import com.example.uts_pb.data.retrofit.ApiService;
import com.example.uts_pb.data.model.GitHubUser;
import com.example.uts_pb.data.response.GitHubSearchResponse;
import com.example.uts_pb.ui.UserAdapter; // Pastikan package ini sesuai dengan struktur proyek Anda

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<GitHubUser> userList = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new UserAdapter(userList);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);
        fetchGitHubUsers();
    }

    private void fetchGitHubUsers() {
        Call<GitHubSearchResponse> call = apiService.searchUsers("your_username_here");
        call.enqueue(new Callback<GitHubSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<GitHubSearchResponse> call, @NonNull Response<GitHubSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body().getItems());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitHubSearchResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        GitHubUser clickedUser = userList.get(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("user", clickedUser); // Pastikan GitHubUser mengimplementasikan Parcelable atau Serializable
        startActivity(intent);
    }
}
