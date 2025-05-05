package com.xmind.services;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private static MessageSource messageSource;

    @Autowired
    public TranslationService(MessageSource messageSource) {
        TranslationService.messageSource = messageSource;
    }

    public static String translate(String key, Locale locale) {
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (Exception e) {
            return key;
        }
    }

    public static String translate(String key) {
        Locale effectiveLocale = LocaleContextHolder.getLocale();;

        if (effectiveLocale == null) {
            effectiveLocale = new Locale("tr");
        }

        try {
            return messageSource.getMessage(key, null, effectiveLocale);
        } catch (Exception e) {
            return key;
        }
    }
}