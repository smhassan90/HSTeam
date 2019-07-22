package com.greenstar.hsteam.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.adapters.PendingFormAdapter;
import com.greenstar.hsteam.dao.QTVFormDeleteListener;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.QTVForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Syed Muhammad Hassan
 * 15th July, 2019
 */

public class PendingFormsBasket extends Fragment implements QTVFormDeleteListener{
    View view= null;
    ListView lvBasket;
    PendingFormAdapter basketAdapter;
    AppDatabase db =null;
    List<QTVForm> qtvForms = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    Activity activity;

    public PendingFormsBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<QTVForm> getData(){
        List<QTVForm> qtvForms = db.getQTVFormDAO().getAllPendingForms();

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
        basketAdapter = new PendingFormAdapter(getActivity(),qtvForms, this);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void deleteQTVForm(long orderId) {
        db.getQTVFormDAO().deleteQtvFormById(orderId);
        Toast.makeText(getActivity(),"QTV Form deleted",Toast.LENGTH_SHORT).show();
        basketAdapter = new PendingFormAdapter(getActivity(),getData(), this);
        lvBasket.setAdapter(basketAdapter);
        basketAdapter.notifyDataSetChanged();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
