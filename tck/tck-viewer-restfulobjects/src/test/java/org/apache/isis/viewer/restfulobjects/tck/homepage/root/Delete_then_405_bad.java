/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.viewer.restfulobjects.tck.homepage.root;

import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.core.webserver.WebServer;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.LinkRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.Rel;
import org.apache.isis.viewer.restfulobjects.applib.RestfulHttpMethod;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulClient;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.DomainObjectRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.homepage.HomePageRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.homepage.HomePageResource;
import org.apache.isis.viewer.restfulobjects.tck.IsisWebServerRule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Delete_then_405_bad {

    @Rule
    public IsisWebServerRule webServerRule = new IsisWebServerRule();

    protected RestfulClient client;
    private HomePageResource homePageResource;

    @Before
    public void setUp() throws Exception {
        final WebServer webServer = webServerRule.getWebServer();
        client = new RestfulClient(webServer.getBase());
        homePageResource = client.getHomePageResource();
    }

    @Test
    public void followLink() throws Exception {

        // given
        final Response serviceResp = homePageResource.homePage();
        final RestfulResponse<HomePageRepresentation> serviceJsonResp = RestfulResponse.ofT(serviceResp);
        final HomePageRepresentation serviceRepr = serviceJsonResp.getEntity();
        final LinkRepresentation selfLink = serviceRepr.getLinkWithRel(Rel.SELF);
        final LinkRepresentation deleteLink = selfLink.withMethod(RestfulHttpMethod.DELETE);

        // when
        final RestfulResponse<JsonRepresentation> restfulResponse = client.follow(deleteLink);

        // then
        assertThat(restfulResponse.getStatus(), is(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED));
        assertThat(restfulResponse.getHeader(RestfulResponse.Header.WARNING), is("Deleting the home page resource is not allowed."));
    }

}
