package com.easybug.webservice;

import javax.jws.WebService;

@WebService(targetNamespace = "http://webservice.easybug.com/",endpointInterface = "com.easybug.webservice.Test")
public class TestImp implements Test {
    @Override
    public String service(String s) {
        return "hi webservice";
    }
}
