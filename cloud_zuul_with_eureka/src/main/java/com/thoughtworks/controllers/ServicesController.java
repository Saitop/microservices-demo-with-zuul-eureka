package com.thoughtworks.controllers;

import com.netflix.discovery.converters.Auto;
import com.thoughtworks.ServicesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServicesController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    ServicesUtils servicesUtils;

    @RequestMapping("/services/{applicationName}")
    @ResponseBody
    public List<ServiceInstance> getServiceInstances(@PathVariable String applicationName) {

        return discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/services-url/{applicationName}")
    @ResponseBody
    public String getServiceURLByLoadBalancer(@PathVariable String applicationName) {

        return servicesUtils.getServiceUrl(applicationName).toString();
    }



}
