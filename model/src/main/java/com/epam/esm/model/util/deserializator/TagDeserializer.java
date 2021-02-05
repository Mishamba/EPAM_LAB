package com.epam.esm.model.util.deserializator;

import com.epam.esm.model.entity.Tag;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TagDeserializer extends StdDeserializer<Tag> {
    protected TagDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Tag deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);
        return deserializeTagByJsonNode(node);
    }

    protected Tag deserializeTagByJsonNode(JsonNode node) {
        int tagId = node.get("id").asInt(0);
        String name = node.get("name").asText(null);

        Tag tag;
        if (tagId < 1) {
            tag = new Tag(name);
        } else {
            tag = new Tag(tagId, name);
        }

        return tag;
    }
}
