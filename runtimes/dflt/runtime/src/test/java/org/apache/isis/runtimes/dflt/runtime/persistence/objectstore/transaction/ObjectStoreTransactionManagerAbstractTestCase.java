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


package org.apache.isis.runtimes.dflt.runtime.persistence.objectstore.transaction;


import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.apache.isis.runtimes.dflt.runtime.persistence.PersistenceSession;
import org.apache.isis.runtimes.dflt.runtime.persistence.objectstore.ObjectStoreTransactionManagement;
import org.apache.isis.runtimes.dflt.runtime.persistence.objectstore.transaction.ObjectStoreTransactionManager;
import org.apache.isis.runtimes.dflt.runtime.session.IsisSession;


@RunWith(JMock.class)
public abstract class ObjectStoreTransactionManagerAbstractTestCase {

    protected Mockery mockery = new JUnit4Mockery();


    protected ObjectStoreTransactionManager transactionManager;
    protected IsisSession mockSession;
    protected PersistenceSession mockPersistenceSession;
    protected ObjectStoreTransactionManagement mockObjectStore;
    
    @Before
    public void setUp() throws Exception {
        mockSession = mockery.mock(IsisSession.class);
        mockPersistenceSession = mockery.mock(PersistenceSession.class);
        mockObjectStore = mockery.mock(ObjectStoreTransactionManagement.class);
    }


    
    ////////////////////////////////////////////////////
    // Helpers
    ////////////////////////////////////////////////////
    

    protected void ignoreCallsToPersistenceSession() {
        mockery.checking(new Expectations() {
            {
                ignoring(mockPersistenceSession);
            }
        });
    }

    protected void ignoreCallsToObjectStore() {
        mockery.checking(new Expectations() {
            {
                ignoring(mockObjectStore);
            }
        });
    }

}