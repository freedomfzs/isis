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

package org.apache.isis.viewer.wicket.viewer;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.apache.isis.core.testsupport.jmock.AbstractJMockForInterfacesTest;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.settings.ISecuritySettings;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

public class IsisWicketApplication_init extends AbstractJMockForInterfacesTest {

    private IsisWicketApplication application;

    private ISecuritySettings mockSecuritySettings;
    private IResourceSettings mockResourceSettings;

    @Before
    public void setUp() throws Exception {
        mockSecuritySettings = context.mock(ISecuritySettings.class);
        mockResourceSettings = context.mock(IResourceSettings.class);
        ignoring(mockSecuritySettings);
        ignoring(mockResourceSettings);

        application = new IsisWicketApplication() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void initWicketComponentInjection(final Injector injector) {
                // ignore
            }

            @Override
            public ISecuritySettings getSecuritySettings() {
                return mockSecuritySettings;
            }

            @Override
            public IResourceSettings getResourceSettings() {
                return mockResourceSettings;
            }
        };
    }

    @Test
    public void injectedApplicationCssUrl() {
        application.init();
        assertThat(application.getApplicationCssUrl(), is(notNullValue()));
    }

    @Test
    public void injectedComponentFactoryRegistry() {
        application.init();
        assertThat(application.getComponentFactoryRegistry(), is(notNullValue()));
    }

    @Test
    public void injectedImageCache() {
        application.init();
        assertThat(application.getImageCache(), is(notNullValue()));
    }

}