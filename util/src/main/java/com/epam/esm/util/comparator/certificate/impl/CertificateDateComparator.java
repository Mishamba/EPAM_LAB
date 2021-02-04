package com.epam.esm.util.comparator.certificate.impl;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.util.comparator.AscDescCheck;

import java.util.Comparator;

public class CertificateDateComparator extends AscDescCheck implements Comparator<Certificate> {
    public CertificateDateComparator(String sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(Certificate certificate, Certificate t1) {
        int result = certificate.getCreateDate().compareTo(t1.getCreateDate());
        return correctOrder(result);
    }
}
