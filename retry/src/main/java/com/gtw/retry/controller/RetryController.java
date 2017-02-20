package com.gtw.retry.controller;

import com.gtw.retry.service.IAnnotationRetryService;
import com.gtw.retry.service.ITemplateRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {
    @Autowired
    private IAnnotationRetryService annotationRetryService;
    @Autowired
    private ITemplateRetryService templateRetryService;

    @RequestMapping(value = "/annotationRetry", method = RequestMethod.GET)
    public void testAnnotationRetry(){
        annotationRetryService.getCurrentRate();
    }

    @RequestMapping(value = "/templateRetry", method = RequestMethod.GET)
    public void testTemplateRetry(){
        templateRetryService.templateRetry();
    }
}
