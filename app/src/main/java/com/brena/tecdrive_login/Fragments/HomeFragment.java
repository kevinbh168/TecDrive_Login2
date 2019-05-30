package com.brena.tecdrive_login.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brena.tecdrive_login.Adapters.PostsAdapter;
import com.brena.tecdrive_login.Interfaces.JsonPostsApi;
import com.brena.tecdrive_login.Models.Posts;
import com.brena.tecdrive_login.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment  implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView recyclerView;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.postslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        initialize();
        return v;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }


    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {

        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    private void initialize(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://shareinfotecsup.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPostsApi jsonPostsApi=retrofit.create(JsonPostsApi.class);
        Call<List<Posts>> call=jsonPostsApi.getPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext,"Error de "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                PostsAdapter adapter = new PostsAdapter();
                adapter.setPostsList(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Posts>> calml, Throwable t) {
                Toast.makeText(mContext,"Error de "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
