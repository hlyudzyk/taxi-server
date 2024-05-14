package com.taxiapp.main.services.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Set<String> AVAILABLE_ENUM_NAMES;

    @Override
    public void initialize(ValidEnum validEnum) {
        Class<? extends Enum<?>> enumSelected = validEnum.enumClass();
        AVAILABLE_ENUM_NAMES = getNamesSet(enumSelected);
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            return AVAILABLE_ENUM_NAMES.contains(value.name());
        }
    }

    private Set<String> getNamesSet(Class<? extends Enum<?>> e) {
        Enum<?>[] enums = e.getEnumConstants();
        String[] names = new String[enums.length];
        for (int i = 0; i < enums.length; i++) {
            names[i] = enums[i].name();
        }
        return new HashSet<>(Arrays.asList(names));
    }
}
