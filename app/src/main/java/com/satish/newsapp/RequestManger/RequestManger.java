package com.satish.newsapp.RequestManger;

import android.content.Context;
import android.widget.Toast;

import com.satish.newsapp.ApiInterface.CallNewsApi;
import com.satish.newsapp.DataFetchListner.OnFetchDataListener;
import com.satish.newsapp.Models.NewsApiResponse;
import com.satish.newsapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManger {
    Context context;

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getNewsHeadlines(OnFetchDataListener listener,String category, String query){
        CallNewsApi callNewsApi=retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call =callNewsApi.callHeadlines("in",category,query, context.getString(R.string.api_key));
        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
                    }
                    listener.onFetchData(response.body().getArticles() ,response.message());

                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request failed");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public RequestManger(Context context) {this.context = context;}


}
