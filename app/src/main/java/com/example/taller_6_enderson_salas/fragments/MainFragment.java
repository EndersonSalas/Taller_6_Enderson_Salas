package com.example.taller_6_enderson_salas.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.taller_6_enderson_salas.R;
import com.example.taller_6_enderson_salas.adapters.ItemAdapter;
import com.example.taller_6_enderson_salas.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    FirebaseFirestore db;
    private final String TAG= "Firebase";
    final ArrayList<Item> listItems= new ArrayList<Item>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView= (ListView) view.findViewById(R.id.listView2);
        getItems(view);
        FloatingActionButton btnFloatingAction = (FloatingActionButton)  view.findViewById(R.id.floatingAdd);
        Bundle bundle = getArguments();
        btnFloatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                FormFragment fragment = new FormFragment();
                ft.replace(android.R.id.content, fragment);
                ft.commit();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                deleteItem(listItems.get(pos).getId(), view);
                return true;
            }
        });

        if(bundle != null){
            Item item = (Item)bundle.getSerializable("item");
            addItems(item);
            getItems(view);
        }
        return view;
    }


    public  void deleteItem(final String id, final View view){

        AlertDialog deleteDialogBox =new AlertDialog.Builder(this.getContext())
                //set message, title, and icon
                .setTitle("Borrar")
                .setMessage("Quieres eliminar el artículo?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int whichButton) {
                        //your deleting code
                        db.collection("items").document(id)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar snackbar = Snackbar
                                                .make(view , "Se ha borrado el artículo exitosamente", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        getItems(view);
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }

                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        deleteDialogBox.show();
    }


    public void addItems(Item item){

        // Add a new document with a generated ID
        db.collection("items")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }


    public void getItems(final View view ){
        final ProgressDialog mProgress = new ProgressDialog(this.getContext());
        mProgress.setMessage("Cargando los artículos");
        mProgress.show();
        db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        listItems.clear();
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String subtitle= "";
                                String title= document.get("title").toString();
                                String id =document.getId();
                                if(document.get("subTitle")!=null){
                                 subtitle =document.get("subTitle").toString();
                                }else{
                                    subtitle=document.get("subtitle").toString();

                                }
                                Item item = new Item(title, subtitle,id);
                                listItems.add(item);
                                Log.d(TAG, document.getId() + " => " + document.getData());

                            }
                            ItemAdapter itemAdapter= new ItemAdapter(view.getContext(), R.layout.list_element_item,listItems);
                            ListView listView = (ListView)  view.findViewById(R.id.listView2);
                            listView.setAdapter(itemAdapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());


                        }
                        mProgress.dismiss();
                    }
                });

    }


}
