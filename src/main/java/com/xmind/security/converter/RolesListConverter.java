package com.xmind.security.converter;

import com.xmind.security.models.Roles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RolesListConverter implements AttributeConverter<List<Roles>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<Roles> roles) {
        if (roles == null || roles.isEmpty()) {
            return "";
        }

        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<Roles> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(dbData.split(DELIMITER))
                .map(Roles::valueOf)
                .collect(Collectors.toList());
    }
}
