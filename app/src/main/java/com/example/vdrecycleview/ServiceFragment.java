package com.example.vdrecycleview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ServiceFragment extends Fragment {

    RecyclerView recview;
    myadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        recview= view.findViewById(R.id.recview);
        recview.setHasFixedSize(true);

        setUpRecycleview();

        return view;
    }
    public void setUpRecycleview(){
        Query query = FirebaseDatabase.getInstance().getReference().child("product");
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(query, model.class)
                        .build();
        adapter = new myadapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recview.setLayoutManager(linearLayoutManager);
        recview.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

}
