package com.example.hritik.delete;

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

import com.example.hritik.delete.Adapter_ViewHolders.CategoryAdapter;
import com.example.hritik.delete.Model.Common;

import java.util.ArrayList;
import java.util.List;


public class DashBoard extends Fragment {


    private static final String TAG = "DashBoard";

    private View view;
    private RecyclerView recyclerView;

    private CategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> imageUrl = new ArrayList<>();
    private List<String> names=new ArrayList<>();

    public DashBoard() {
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dashboard_fragment,container,false);
        recyclerView=view.findViewById(R.id.rView);

        recyclerView.setHasFixedSize(true);
        final FragmentActivity c=getActivity();
        layoutManager=new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        final MainActivity mainActivity= (MainActivity) getActivity();

      //  Main2Activity main2Activity= (Main2Activity) getActivity();



//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final CategoryAdapter adapter = new CategoryAdapter(view.getContext(),mainActivity.getMyNames(),mainActivity.getImageUrl());
//                adapter.notifyDataSetChanged();
//                c.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(c, "See", Toast.LENGTH_SHORT).show();
//                        recyclerView.setAdapter(adapter);
//                    }
//                });
//            }
//        }).start();

        final CategoryAdapter adapter = new CategoryAdapter(view.getContext(), Common.names,Common.imageUrl);
        adapter.notifyDataSetChanged();
       recyclerView.setAdapter(adapter);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        final DatabaseReference category=firebaseDatabase.getReference("Category");
//        category.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Iterator<DataSnapshot> iterable=dataSnapshot.getChildren().iterator();
//                Log.d(TAG, "onDataChange: Total users "+dataSnapshot.getChildrenCount());
//                while (iterable.hasNext()){
//
//
//                    DataSnapshot tempItem=iterable.next();
//                    names.add(tempItem.child("Name").getValue().toString());
//                    imageUrl.add(tempItem.child("Image").getValue().toString());
//                }
////
////                for (String c:names){
////
////                    Log.d(TAG, "onDataChange: "+c.toString());
////
////                }
////                for (String c:imageUrl){
////
////                    Log.d(TAG, "onDataChange: "+c.toString());
////
////                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

  }
}
