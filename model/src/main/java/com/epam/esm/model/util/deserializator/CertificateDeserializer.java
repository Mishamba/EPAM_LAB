package com.epam.esm.model.util.deserializator;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CertificateDeserializer extends StdDeserializer<Certificate> {
    protected CertificateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Certificate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);
        return findCertificateFromNode(node);
    }

    private Certificate findCertificateFromNode(JsonNode node) {
        int certificateId = node.get("certificateId").asInt(0);
        String certificateName = node.get("certificateName").asText();
        String description = node.get("description").asText();
        int price = node.get("price").asInt();
        int duration = node.get("duration").asInt();
        List<Tag> tags = new ArrayList<>();
        Iterator<JsonNode> tagsNodeIterator = node.get("tags").elements();
        while (tagsNodeIterator.hasNext()) {
            tags.add(findTagFromNode(tagsNodeIterator.next()));
        }

        Certificate certificate;
        if (certificateId < 1) {
            certificate = new Certificate(certificateName, description, price, duration, null, null, tags);
        } else {
            certificate = new Certificate(certificateId, certificateName, description, price, duration, null, null,
                    tags);
        }

        return certificate;
    }

    private Tag findTagFromNode(JsonNode node) {
        int tagId = node.get("id").asInt(0);
        String tagName = node.get("name").asText(null);

        Tag tag;
        if (tagId < 1) {
            tag = new Tag(tagId, tagName);
        } else {
            tag = new Tag(tagName);
        }

        return tag;
    }
}
