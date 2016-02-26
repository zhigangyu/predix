/*
 * Copyright (c) 2013 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.pactera.predix.seed.machine.httpclient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import aQute.bnd.annotation.component.Component;

@SuppressWarnings({"javadoc", "nls"})
interface IHttpClientSampleRestServer
{
    // Rest paths should have a version
    /** Relative path to this web service namespace */
    public static final String PATH = "/httpclient/seed/v1";
}

/**
 * 
 */
@SuppressWarnings(
{
        "nls", "javadoc"
})
@Component
@Path(IHttpClientSampleRestServer.PATH)
public class HttpClientSampleRestServer
        implements IHttpClientSampleRestServer
{
    

    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_PLAIN)
    public String get()
    {
        return "ok";
    }
}
