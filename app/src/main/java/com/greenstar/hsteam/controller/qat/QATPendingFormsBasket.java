package com.greenstar.hsteam.controller.qat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.hsteam.R;
import com.greenstar.hsteam.adapters.qat.QATPendingFormAdapter;
import com.greenstar.hsteam.dao.FormDeleteListener;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.QATFormHeader;

import java.util.ArrayList;
import java.util.List;

public class QATPendingFormsBasket extends Fragment implements FormDeleteListener {
    View view= null;
    ListView lvBasket;
    QATPendingFormAdapter basketAdapter;
    AppDatabase db =null;
    List<QATFormHeader> forms = new ArrayList<>();

    private QATPendingFormsBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public QATPendingFormsBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<QATFormHeader> getData(){
       List<QATFormHeader> qatForms = db.getQatFormHeaderDAO().getAllPending();

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
        basketAdapter = new QATPendingFormAdapter(getActivity(),qatForms, this);
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
            mListener = (QATPendingFormsBasket.OnFragmentInteractionListener) context;
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

    private void deleteQATForm(long orderId){
        try{
            db.getQatFormHeaderDAO().deleteFormById(orderId);
            Toast.makeText(getActivity(),"QAT Form deleted",Toast.LENGTH_SHORT).show();
            basketAdapter = new QATPendingFormAdapter(getActivity(),getData(), this);
            lvBasket.setAdapter(basketAdapter);
            basketAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Crashlytics.logException(e);
        }

    }

    @Override
    public void deleteForm(final long orderId) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this form?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteQATForm(orderId);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
