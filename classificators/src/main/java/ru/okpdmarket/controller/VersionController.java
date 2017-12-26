package ru.okpdmarket.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.okpdmarket.util.ComponentVersion;

@RestController
public class VersionController implements InitializingBean {

    private static final Map<String, String> VERSION = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        VERSION.put("version", ComponentVersion.getComponentVersion(getClass()));
        VERSION.put("buildTimestamp", ComponentVersion.getComponentTitle(getClass()));
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getVersion() {
        return VERSION;
    }
}
