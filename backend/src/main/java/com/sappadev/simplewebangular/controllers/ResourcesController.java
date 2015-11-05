package com.sappadev.simplewebangular.controllers;

import com.sappadev.simplewebangular.res.CustomMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Properties;

@RestController
/**
 * Created by serge_000 on 31.10.2015.
 */
public class ResourcesController {

    private final CustomMessageSource customMessageSource;

    @Autowired
    public ResourcesController(CustomMessageSource customMessageSource) {
        this.customMessageSource = customMessageSource;
    }

    @RequestMapping("/res/messages/")
    /**
     * Returns list of all messages to client that are stored in messages.properties
     */
    public Properties messages(@RequestParam String lang) {
        return customMessageSource.getAllMessages(new Locale(lang));
    }

}
