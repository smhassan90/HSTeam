package com.greenstar.hsteam.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.adapters.SuccessfulFormAdapter;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.QTVForm;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Syed Muhammad Hassan
 * 16th July, 2019
 */

public class SuccessfulFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    SuccessfulFormAdapter basketAdapter;
    AppDatabase db =null;
    List<QTVForm> qtvForms = new ArrayList<>();

    private SuccessfulFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public SuccessfulFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<QTVForm> getData(){
        List<QTVForm> qtvForms = db.getQTVFormDAO().getAllSuccessfulForms();

        return qtvForms ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_forms_basket, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        lvBasket = view.findViewById(R.id.lvBasket);

        List<QTVForm> qtvForms = new ArrayList<>();
        qtvForms = getData();
        basketAdapter = new SuccessfulFormAdapter(getActivity(),qtvForms);
        lvBasket.setAdapter(basketAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PendingFormsBasket.OnFragmentInteractionListener) {
            mListener = (SuccessfulFormBasket.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
