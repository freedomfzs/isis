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
package fixture.todo;

import fixture.todo.scenarios.ToDoItemsRecreateAndCompleteSeveral;

import java.util.List;
import org.apache.isis.applib.annotation.ClassLayout;
import org.apache.isis.applib.annotation.CssClassFa;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

/**
 * Enables fixtures to be installed from the application.
 */
@ClassLayout(
        named = "Prototyping"
)
@DomainService(menuBar = DomainService.MenuBar.SECONDARY, menuOrder = "10")
public class ToDoItemsFixturesService extends FixtureScripts {

    public ToDoItemsFixturesService() {
        super("fixture.todo");
    }

    @CssClassFa("fa fa-bolt")
    @Override
    public List<FixtureResult> runFixtureScript(
            final FixtureScript fixtureScript,
            final @ParameterLayout(
                    named="Parameters",
                    describedAs = "Script-specific parameters (key=value) ",
                    multiLine = 10)
            @Optional
            String parameters) {
        return super.runFixtureScript(fixtureScript, parameters);
    }

    @Override
    public FixtureScript default0RunFixtureScript() {
        return findFixtureScriptFor(ToDoItemsRecreateAndCompleteSeveral.class);
    }

    /**
     * Raising visibility to <tt>public</tt> so that choices are available for first param
     * of {@link ${symbol_pound}runFixtureScript(FixtureScript, String)}.
     */
    @Override
    public List<FixtureScript> choices0RunFixtureScript() {
        return super.choices0RunFixtureScript();
    }

    // //////////////////////////////////////


    @Prototype
    @CssClassFa("fa fa-list")
    @MemberOrder(sequence="20")
    public Object recreateToDoItemsReturnFirst() {
        final List<FixtureResult> run = findFixtureScriptFor(ToDoItemsRecreateAndCompleteSeveral.class).run(null);
        return run.get(0).getObject();
    }
}
