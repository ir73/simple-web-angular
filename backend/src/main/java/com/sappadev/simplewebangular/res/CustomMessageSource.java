package com.sappadev.simplewebangular.res;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by serge_000 on 31.10.2015.
 */
public class CustomMessageSource extends ReloadableResourceBundleMessageSource {
    /**
     * Returns a merged list of all messages in the resources directory of the app
     * @param locale
     * @return
     */
    public Properties getAllMessages(Locale locale) {
        this.clearCacheIncludingAncestors();
        PropertiesHolder properties = this.getMergedProperties(locale);
        return properties.getProperties();
    }

}
