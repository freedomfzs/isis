/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.viewer.json.viewer.resources.domainobjects;

import java.util.List;
import java.util.Map;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.OneToOneAssociation;
import org.apache.isis.viewer.json.applib.JsonRepresentation;
import org.apache.isis.viewer.json.applib.RepresentationType;
import org.apache.isis.viewer.json.viewer.ResourceContext;
import org.apache.isis.viewer.json.viewer.representations.LinkFollower;
import org.apache.isis.viewer.json.viewer.representations.Rel;
import org.apache.isis.viewer.json.viewer.representations.RendererFactory;
import org.apache.isis.viewer.json.viewer.representations.RendererFactoryRegistry;
import org.apache.isis.viewer.json.viewer.representations.ReprRenderer;
import org.apache.isis.viewer.json.viewer.representations.ReprRendererFactoryAbstract;
import org.apache.isis.viewer.json.viewer.resources.domainobjects.AbstractObjectMemberReprRenderer.Mode;
import org.apache.isis.viewer.json.viewer.resources.domaintypes.DomainTypeReprRenderer;
import org.apache.isis.viewer.json.viewer.resources.domaintypes.TypePropertyReprRenderer;

import com.google.common.collect.Lists;

public class ObjectPropertyReprRenderer extends AbstractObjectMemberReprRenderer<ObjectPropertyReprRenderer, OneToOneAssociation> {

    public static class Factory extends ReprRendererFactoryAbstract {

        public Factory() {
            super(RepresentationType.OBJECT_PROPERTY);
        }

        @Override
        public ReprRenderer<?,?> newRenderer(ResourceContext resourceContext, LinkFollower linkFollower, JsonRepresentation representation) {
            return new ObjectPropertyReprRenderer(resourceContext, linkFollower, getRepresentationType(), representation);
        }
    }
    

    private ObjectPropertyReprRenderer(ResourceContext resourceContext, LinkFollower linkFollower, RepresentationType representationType, JsonRepresentation representation) {
        super(resourceContext, linkFollower, representationType, representation);
    }

    public JsonRepresentation render() {
        // id and memberType are put in eagerly
        
        addValue();

        putDisabledReasonIfDisabled();

        addMemberContentSpecificToMode();
        if(mode.isStandalone()) {
            addChoices();
        }
        
        return representation;
    }

    
    /////////////////////////////////////////////////////
    // value
    /////////////////////////////////////////////////////

    private void addValue() {
        representation.mapPut("value", valueRep());
    }

    private Object valueRep() {
        ObjectAdapter valueAdapter = objectMember.get(objectAdapter);
        if(valueAdapter == null) {
            return null;
        }
        return DomainObjectReprRenderer.valueOrRef(resourceContext, valueAdapter, objectMember.getSpecification());
    }



    /////////////////////////////////////////////////////
    // details link
    /////////////////////////////////////////////////////

    /**
     * Mandatory hook method to support x-ro-follow-links
     */
    @Override
    protected void followDetailsLink(JsonRepresentation detailsLink) {
        RendererFactory factory = RendererFactoryRegistry.instance.find(RepresentationType.OBJECT_PROPERTY);
        final ObjectPropertyReprRenderer renderer = 
                (ObjectPropertyReprRenderer) factory.newRenderer(getResourceContext(), getLinkFollower(), JsonRepresentation.newMap());
        renderer.with(new ObjectAndProperty(objectAdapter, objectMember)).asFollowed();
        detailsLink.mapPut("value", renderer.render());
    }

    /////////////////////////////////////////////////////
    // mutators
    /////////////////////////////////////////////////////


    @Override
    protected void addMutatorsIfEnabled() {
        if(usability().isVetoed()) {
            return;
        }
        Map<String, MutatorSpec> mutators = memberType.getMutators();
        for(String mutator: mutators.keySet()) {
            MutatorSpec mutatorSpec = mutators.get(mutator);
            addLinkFor(mutatorSpec);
        }
        return;
    }


	
    /////////////////////////////////////////////////////
    // choices
    /////////////////////////////////////////////////////

	private ObjectPropertyReprRenderer addChoices() {
		Object propertyChoices = propertyChoices();
		if(propertyChoices != null) {
			representation.mapPut("choices", propertyChoices);
		}
		return this;
	}

	private Object propertyChoices() {
		ObjectAdapter[] choiceAdapters = objectMember.getChoices(objectAdapter);
		if(choiceAdapters == null || choiceAdapters.length == 0) {
			return null;
		}
        List<Object> list = Lists.newArrayList();
        for (final ObjectAdapter choiceAdapter : choiceAdapters) {
        	ObjectSpecification objectSpec = objectMember.getSpecification();
        	list.add(DomainObjectReprRenderer.valueOrRef(resourceContext, choiceAdapter, objectSpec));
        }
        return list;
	}

	
    /////////////////////////////////////////////////////
    // extensions and links
    /////////////////////////////////////////////////////
    
    protected void addLinksToFormalDomainModel() {
        getLinks().arrayAdd(
                TypePropertyReprRenderer.newLinkToBuilder(getResourceContext(), Rel.DESCRIBEDBY, objectAdapter.getSpecification(), objectMember).build());
    }

    @Override
    protected void addLinksIsisProprietary() {
        // none
    }

    @Override
    protected void putExtensionsIsisProprietary() {
        // none
    }

}