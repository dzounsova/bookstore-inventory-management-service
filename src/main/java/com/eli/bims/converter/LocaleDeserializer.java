package com.eli.bims.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Locale;

public class LocaleDeserializer extends JsonDeserializer<Locale> {

    @Override
    public Locale deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String localeString = jsonParser.getText();
        String[] parts = localeString.split("_");
        if (parts.length == 2) {
            return Locale.of(parts[0], parts[1]);
        } else if (parts.length == 1) {
            return Locale.of(parts[0]);
        }
        return null;
    }
}
