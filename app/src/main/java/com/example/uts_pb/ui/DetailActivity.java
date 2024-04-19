package com.example.uts_pb.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uts_pb.R;
import com.example.uts_pb.data.model.GitHubUser;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView avatarImageView;
    private TextView usernameTextView, nameTextView, bioTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        avatarImageView = findViewById(R.id.avatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        nameTextView = findViewById(R.id.nameTextView);
        bioTextView = findViewById(R.id.bioTextView);

        // Ambil data pengguna dari Intent
        GitHubUser user = (GitHubUser) getIntent().getSerializableExtra("user");

        // Tampilkan data pengguna di views
        if (user != null) {
            Picasso.get().load(user.getAvatarUrl()).into(avatarImageView);
            usernameTextView.setText(user.getUsername());
            nameTextView.setText(user.getName());
            bioTextView.setText(user.getBio());
        }
    }
}
