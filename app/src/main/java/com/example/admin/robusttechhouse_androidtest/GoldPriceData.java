package com.example.admin.robusttechhouse_androidtest;

import java.util.Date;

/**
 * Created by Truong Nhat Tan on 1/3/2018.
 *
 *
 * Define class GoldPriceData to store information.
 */

public class GoldPriceData {
    private String mAmount;
    private Date mDate;

    public GoldPriceData(String mAmount, Date mDate) {
        this.mAmount = mAmount;
        this.mDate = mDate;
    }

    public String getmAmount() {
        return mAmount;
    }

    public Date getmDate() {
        return mDate;
    }
}
