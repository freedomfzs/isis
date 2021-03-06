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

package org.apache.isis.core.metamodel.facets.collections.layout;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.members.render.RenderFacet;
import org.apache.isis.core.metamodel.facets.members.render.RenderFacetAbstract;

public class RenderFacetForCollectionLayoutAnnotation extends RenderFacetAbstract {

    public static RenderFacet create(final CollectionLayout collectionLayout, FacetHolder holder) {
        if (collectionLayout == null) {
            return null;
        }
        final CollectionLayout.RenderType renderType = collectionLayout.render();
        return renderType != null ? new RenderFacetForCollectionLayoutAnnotation(renderType, holder) : null;
    }

    private RenderFacetForCollectionLayoutAnnotation(final CollectionLayout.RenderType renderType, final FacetHolder holder) {
        super(CollectionLayout.RenderType.typeOf(renderType), holder);
    }

}
