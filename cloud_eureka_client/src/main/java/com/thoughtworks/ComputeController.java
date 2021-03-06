package com.thoughtworks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "math")
public class ComputeController {

    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value="/add", method= RequestMethod.GET)
    public String add(@RequestParam Integer a, @RequestParam Integer b){
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);

        return "host:" + instance.getHost() + ", \n\n\r service_id:" + instance.getServiceId() + ", \n\n\r result:" + r;
    }


    @RequestMapping(value="/subtract", method= RequestMethod.GET)
    public String subtract(@RequestParam Integer a, @RequestParam Integer b){
        Integer r = a - b;

        return " result:" + r;
    }

}
