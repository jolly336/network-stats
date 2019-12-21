package com.nelson.stats.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.nelson.stats.NetworkStatsManager;
import com.nelson.stats.NetworkStatsManager.LoadCallBack;
import com.nelson.stats.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit test
 *
 * <a href="https://github.com/square/retrofit/blob/master/samples/src/main/java/com/example/retrofit/SimpleService.java">Retrofit</a>
 *
 * Created by Nelson on 2019-11-25.
 */
public class RetrofitActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Button getBtn = findViewById(R.id.btn_get);

        NetworkStatsManager.setUser(new User("110", "police"));

        getBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                useRetrofit();
            }
        });

        Button byteBtn = findViewById(R.id.btn_bytes);
        byteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkStatsManager.getTodayBytes(new LoadCallBack() {
                    @Override
                    public void onSuccess(long bytes) {
                        Toast.makeText(RetrofitActivity.this, "bytes: " + bytes, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {
                        Toast.makeText(RetrofitActivity.this, "错误： " + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void useRetrofit() {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        // Fetch and print a list of the contributors to the library.
        // List<Contributor> contributors = call.execute().body();
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                Toast.makeText(RetrofitActivity.this, "请求成功！", Toast.LENGTH_SHORT).show();
                List<Contributor> contributors = response.body();
                for (Contributor contributor : contributors) {
                    Log.e("nelson", contributor.login + " (" + contributor.contributions + ")");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "请求失败，原因：" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface GitHub {

        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(@Path("owner") String owner, @Path("repo") String repo);
    }

    public static final String API_URL = "https://api.github.com";

    public static class Contributor {

        public String login;
        public int contributions;

        public Contributor() {
        }

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

}
