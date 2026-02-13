package com.worldbet.antirisk_bot.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;


@Converter(autoApply = false)
public class JsonbConverter<T> implements AttributeConverter<T, PGobject> {

    private final static ObjectMapper mapper = new ObjectMapper();

    private final Class<T> clazz;

    public JsonbConverter (Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public PGobject convertToDatabaseColumn(T attribute) {
        if (attribute == null ) return null;

        try {

            PGobject pGobject = new PGobject();
            pGobject.setType("jsonb");
            pGobject.setValue(mapper.writeValueAsString(attribute));
            return pGobject;

        }
        catch (Exception e){
            throw new IllegalArgumentException("Error converting object to jsonb", e);
        }

    }

    @Override
    public T convertToEntityAttribute(PGobject dbData) {
        if(dbData == null || dbData.getValue() == null) return null;

        try {

            return mapper.readValue(dbData.getValue(),clazz);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting jsonb to object",e);
        }
    }
}
