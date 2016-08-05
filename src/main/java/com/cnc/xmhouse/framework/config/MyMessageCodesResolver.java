package com.cnc.xmhouse.framework.config;

import org.springframework.validation.DefaultMessageCodesResolver;

public class MyMessageCodesResolver extends DefaultMessageCodesResolver {

    @Override
    public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class fieldType) {
        String formatCode = errorCode + CODE_SEPARATOR + objectName + CODE_SEPARATOR + field;
        return new String[]{formatCode};
    }
}

