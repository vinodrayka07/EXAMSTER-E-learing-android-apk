package com.example.examaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class branchFragment extends Fragment {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Movie> movieList;
    private String url1;
    LottieAnimationView myll;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_university, container, false);
        recyclerView =view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myll = view.findViewById(R.id.mylotti);

        requestQueue = VolleySingleton.getmInstance(getContext()).getRequestQueue();

        movieList = new ArrayList<>();
        fetchMovies();

        return view;
    }


    private void fetchMovies() {

        //String url = "https://firebasestorage.googleapis.com/v0/b/amit-bd504.appspot.com/o/year.json?alt=media&token=e9fd8d3d-eec3-4577-93f4-2229f7f5a0c4";

        Bundle bundle = getArguments();
        if (bundle != null) {

            url1 = bundle.getString("otherurl");


        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url1, null,
                response -> {

                    for (int i = 0 ; i < response.length() ; i ++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String subtitle = jsonObject.getString("subtitle");
                            String imageurl2 = jsonObject.getString("imageurl2");
                            String imageurl = jsonObject.getString("imageurl");
                            String otherurl = jsonObject.getString("otherurl");


                            Movie movie = new Movie(title , imageurl , subtitle , imageurl2 ,otherurl);
                            movieList.add(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        branchAdapter adapter = new branchAdapter(getContext() , movieList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        myll.setVisibility(View.GONE);

                        recyclerView.setAdapter(adapter);
                    }
                }, error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonArrayRequest);
    }
}
