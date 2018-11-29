
package com.rc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Consent {

    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("consent")
    @Expose
    private Consent_ consent;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Consent_ getConsent() {
        return consent;
    }

    public void setConsent(Consent_ consent) {
        this.consent = consent;
    }

}
