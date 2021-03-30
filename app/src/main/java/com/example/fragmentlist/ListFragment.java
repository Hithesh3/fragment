package com.example.fragmentlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {
    final String URL = "https://jsonplaceholder.typicode.com/";
    final String TAG = "debug";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: fragment");
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = view.findViewById(R.id.lvList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ListApi listApi = retrofit.create(ListApi.class);
        Call<List<ListModel>> call = listApi.getList();

        call.enqueue(new Callback<List<ListModel>>() {
            @Override
            public void onResponse(Call<List<ListModel>> call, Response<List<ListModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: failure");
                    Toast.makeText(getContext(), "Enter valid City", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<ListModel> list = response.body();
                String[] content = new String[100];
                int i = 0;

                for (ListModel listModels : list) {
                    content[i] = "";
                    content[i] += "ID: " + listModels.getId() + "\n";
                    content[i] += "User ID: " + listModels.getUserId() + "\n";
                    content[i] += "Title: " + listModels.getTitle() + "\n";
                    content[i] += "Text: " + listModels.getBody() + "\n\n";
                    i++;
                }
                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, content);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<ListModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getContext(), "failed inside failure", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}