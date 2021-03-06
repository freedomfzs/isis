#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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

package fixture.simple.scenario;

import fixture.simple.SimpleObjectsTearDownFixture;
import fixture.simple.objects.SimpleObjectForBar;
import fixture.simple.objects.SimpleObjectForBaz;
import fixture.simple.objects.SimpleObjectForFoo;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SimpleObjectsFixture extends FixtureScript {

    public SimpleObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        executeChild(new SimpleObjectsTearDownFixture(), executionContext);

        executeChild(new SimpleObjectForFoo(), executionContext);
        executeChild(new SimpleObjectForBar(), executionContext);
        executeChild(new SimpleObjectForBaz(), executionContext);
    }

}
