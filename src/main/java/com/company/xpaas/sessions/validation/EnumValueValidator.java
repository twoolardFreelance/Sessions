package com.company.xpaas.sessions.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(s.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && s.equalsIgnoreCase(enumValue.toString())))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }


}
