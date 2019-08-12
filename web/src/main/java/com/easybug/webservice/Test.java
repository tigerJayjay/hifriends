package com.easybug.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface Test {
    @WebMethod
    public @WebResult String service(@WebParam String s);
}
