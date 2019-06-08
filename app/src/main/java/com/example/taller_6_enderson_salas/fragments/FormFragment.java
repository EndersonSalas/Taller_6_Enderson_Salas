package com.example.taller_6_enderson_salas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taller_6_enderson_salas.R;
import com.example.taller_6_enderson_salas.models.Item;

public class FormFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_form, container, false);
        setupUI(view);
        return view;
    }
    public void setupUI(final View view){
        Button btn = (Button)  view.findViewById(R.id.btn_save);
        final EditText editDescription= (EditText)  view.findViewById(R.id.editText_description);
        final EditText editQuantity= (EditText)  view.findViewById(R.id.editText_quantity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strEditDescription = editDescription.getText().toString();
                String strEditQuantity =  editQuantity.getText().toString();

                if(!strEditDescription.isEmpty() && !strEditQuantity.isEmpty()){
                     Item item = new Item(strEditDescription,strEditQuantity);
                    enviandoResultado(item);
                }else{
                    Toast.makeText(getContext(), "FALTAN DATOS PARA CREAR ITEM", Toast.LENGTH_LONG).show();
                }
//                getActivity().finish();
            }
        });
    }

    public void enviandoResultado(Item item){

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        MainFragment listFragment= new MainFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        listFragment.setArguments(bundle);
        ft.replace(android.R.id.content, listFragment);
        ft.commit();
    }

}
