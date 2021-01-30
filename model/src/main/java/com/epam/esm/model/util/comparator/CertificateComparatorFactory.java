package com.epam.esm.model.util.comparator;

import com.epam.esm.model.constant.Constant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.util.comparator.impl.CertificateDateComparator;
import com.epam.esm.model.util.comparator.impl.CertificateNameComparator;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class CertificateComparatorFactory {
    private final Map<String, Map<String, Comparator<Certificate>>> certificateComparators;
    private final Map<String, Comparator<Certificate>> nameCertificateComparator;
    private final Map<String, Comparator<Certificate>> createDateCertificateComparator;

    public CertificateComparatorFactory() {
        this.certificateComparators = new HashMap<>();
        this.nameCertificateComparator = new HashMap<>();
        this.createDateCertificateComparator = new HashMap<>();
        certificateComparators.put(Constant.SORT_BY_NAME, nameCertificateComparator);
        certificateComparators.put(Constant.SORT_BY_DATE, createDateCertificateComparator);
        nameCertificateComparator.put(Constant.ASC_SORT_TYPE, new CertificateNameComparator(Constant.ASC_SORT_TYPE));
        nameCertificateComparator.put(Constant.DESC_SORT_TYPE, new CertificateNameComparator(Constant.DESC_SORT_TYPE));
        createDateCertificateComparator.put(Constant.ASC_SORT_TYPE,
                new CertificateDateComparator(Constant.ASC_SORT_TYPE));
        createDateCertificateComparator.put(Constant.DESC_SORT_TYPE,
                new CertificateDateComparator(Constant.DESC_SORT_TYPE));

    }

    public Comparator<Certificate> getComparator(String sortBy, String sortType) {
        return this.certificateComparators.get(sortBy).get(sortType);
    }
}
