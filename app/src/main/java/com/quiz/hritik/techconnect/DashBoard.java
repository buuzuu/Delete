package com.quiz.hritik.techconnect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quiz.hritik.techconnect.Adapter_ViewHolders.CategoryAdapter;
import com.quiz.hritik.techconnect.Model.Common;

import java.util.ArrayList;
import java.util.List;


public class DashBoard extends Fragment {


    private static final String TAG = "DashBoard";

    private View view;
    private RecyclerView recyclerView;

    private CategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> imageUrl = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    public DashBoard() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        recyclerView = view.findViewById(R.id.rView);

        recyclerView.setHasFixedSize(true);
        final FragmentActivity c = getActivity();
        layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        final MainActivity mainActivity = (MainActivity) getActivity();


        final CategoryAdapter adapter = new CategoryAdapter(view.getContext(), Common.names, Common.imageUrl);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
