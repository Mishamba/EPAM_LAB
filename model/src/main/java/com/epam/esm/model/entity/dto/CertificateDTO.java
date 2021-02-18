package com.epam.esm.model.entity.dto;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.util.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CertificateDTO extends RepresentationModel<CertificateDTO> {
    private final int id;
    private final String name;
    private final String description;
    private final int price;
    private final int duration;
    @JsonSerialize(using = DateTimeSerializer.class)
    private final LocalDateTime createDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    private final LocalDateTime lastUpdateDate;
    private final List<TagDTO> tags;

    protected CertificateDTO(int id, String name, String description, int price, int duration, LocalDateTime createDate,
                             LocalDateTime lastUpdateDate, List<TagDTO> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration =duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public static CertificateDTO createFromCertificate(Certificate certificate) {
        if (certificate == null) {
            return null;
        }

        List<TagDTO> tagDTOS = certificate.getTags().stream().map(TagDTO::createFromTag).collect(Collectors.toList());
        return new CertificateDTO(certificate.getId(), certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), tagDTOS);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }
}
