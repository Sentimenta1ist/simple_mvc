package com.WetherRestAPI.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Utils {

    public static String getErrorString(BindingResult br) {
        StringBuilder errorsMsg = new StringBuilder();

        for (FieldError error : br.getFieldErrors()) {
            errorsMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        return errorsMsg.toString();
    }
}
