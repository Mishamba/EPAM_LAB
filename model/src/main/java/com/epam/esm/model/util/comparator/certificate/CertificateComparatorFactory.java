package com.epam.esm.model.util.comparator.certificate;

import com.epam.esm.model.constant.CertificateSortParametersConstant;
import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.util.comparator.certificate.impl.CertificateDateComparator;
import com.epam.esm.model.util.comparator.certificate.impl.CertificateNameComparator;
import com.epam.esm.model.util.entity.PaginationData;
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
        certificateComparators.put(CertificateSortParametersConstant.SORT_BY_NAME, nameCertificateComparator);
        certificateComparators.put(CertificateSortParametersConstant.SORT_BY_DATE, createDateCertificateComparator);
        nameCertificateComparator.put(SortOrderConstant.ASC_SORT_TYPE,
                new CertificateNameComparator(SortOrderConstant.ASC_SORT_TYPE));
        nameCertificateComparator.put(SortOrderConstant.DESC_SORT_TYPE,
                new CertificateNameComparator(SortOrderConstant.DESC_SORT_TYPE));
        createDateCertificateComparator.put(SortOrderConstant.ASC_SORT_TYPE,
                new CertificateDateComparator(SortOrderConstant.ASC_SORT_TYPE));
        createDateCertificateComparator.put(SortOrderConstant.DESC_SORT_TYPE,
                new CertificateDateComparator(SortOrderConstant.DESC_SORT_TYPE));

    }

    public Comparator<Certificate> getComparator(PaginationData paginationData) {
        return this.certificateComparators.get(paginationData.getSortBy()).get(paginationData.getSortType());
    }
}
