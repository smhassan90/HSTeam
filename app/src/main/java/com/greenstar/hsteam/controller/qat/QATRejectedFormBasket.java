package com.greenstar.hsteam.controller.qat;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.adapters.RejectedFormAdapter;
import com.greenstar.hsteam.adapters.qat.QATRejectedFormAdapter;
import com.greenstar.hsteam.controller.qtv.PendingFormsBasket;
import com.greenstar.hsteam.controller.qtv.RejectedFormBasket;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.QATFormHeader;
import com.greenstar.hsteam.model.QTVForm;

import java.util.ArrayList;
import java.util.List;

public class QATRejectedFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    QATRejectedFormAdapter basketAdapter;
    AppDatabase db =null;
    List<QATFormHeader> qatFormHeaders = new ArrayList<>();

    private QATRejectedFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public QATRejectedFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<QATFormHeader> getData(){
        List<QATFormHeader> qatForms = db.getQatFormHeaderDAO().getAllRejected();

        return qatForms ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_forms_basket, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        lvBasket = view.findViewById(R.id.lvBasket);

        List<QATFormHeader> qatForms = new ArrayList<>();
        qatForms = getData();
        basketAdapter = new QATRejectedFormAdapter(getActivity(),qatForms);
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
        if (context instanceof QATPendingFormsBasket.OnFragmentInteractionListener) {
            mListener = (QATRejectedFormBasket.OnFragmentInteractionListener) context;
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
