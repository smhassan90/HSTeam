package com.greenstar.hsteam.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class DonorProvider {
    @NonNull
    @PrimaryKey
    private int donor;
    private String providerCode;

    @NonNull
    public int getDonor() {
        return donor;
    }

    public void setDonor(@NonNull int donor) {
        this.donor = donor;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }
}
