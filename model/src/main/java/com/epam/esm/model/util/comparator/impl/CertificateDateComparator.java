package com.epam.esm.model.util.comparator.impl;

import com.epam.esm.model.constant.Constant;
import com.epam.esm.model.entity.Certificate;

import java.util.Comparator;

public class CertificateDateComparator implements Comparator<Certificate> {
    private String compareWay;

    public CertificateDateComparator(String compareWay) {
        this.compareWay = compareWay;
    }

    @Override
    public int compare(Certificate certificate, Certificate t1) {
        int result = certificate.getCreateDate().compareTo(t1.getCreateDate());
        if (compareWay.equals(Constant.DESC_SORT_TYPE)) {
            if (result > 0) {
                return -1;
            }
            if (result < 0) {
                return 1;
            }
        }

        return result;
    }
}
