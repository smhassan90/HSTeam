package com.greenstar.hsteam.adapters.qat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.greenstar.hsteam.controller.qat.QATPendingFormsBasket;
import com.greenstar.hsteam.controller.qat.QATRejectedFormBasket;
import com.greenstar.hsteam.controller.qat.QATSuccessfulFormBasket;
import com.greenstar.hsteam.controller.qtv.PendingFormsBasket;
import com.greenstar.hsteam.controller.qtv.RejectedFormBasket;
import com.greenstar.hsteam.controller.qtv.SuccessfulFormBasket;

public class QATSubmittedFormAdapter extends FragmentStatePagerAdapter {
    final int noOfTabs=3;

    public QATSubmittedFormAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new QATPendingFormsBasket();
            case 1: return new QATRejectedFormBasket();
            case 2: return new QATSuccessfulFormBasket();
        }

        return null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
