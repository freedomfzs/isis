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

package org.apache.isis.core.runtime.services;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ServicesInstallerUtils  {

    private static final Logger LOG = LoggerFactory.getLogger(ServicesInstallerFromConfiguration.class);

    private static final char DELIMITER = '#';

    private ServicesInstallerUtils (){}

    static <V extends Comparable<V>> LinkedHashSet<V> flatten(SortedMap<String, SortedSet<V>> positionedServices) {
        final LinkedHashSet<V> serviceList = Sets.newLinkedHashSet();
        final Set<String> keys = positionedServices.keySet();
        for (String position : keys) {
            final SortedSet<V> list = positionedServices.get(position);
            serviceList.addAll(list);
        }
        return serviceList;
    }

    static <V> void appendInPosition(SortedMap<String, SortedSet<String>> positionedServices, String position, String service) {
        if(service == null) {
            return;
        }
        SortedSet<String> serviceList = positionedServices.get(position);
        if(serviceList == null) {
            serviceList = Sets.newTreeSet();
            positionedServices.put(position, serviceList);
        }
        serviceList.add(service);
    }

    static Object instantiateService(String serviceName, ServiceInstantiator serviceInstantiator) {
        final int pos = serviceName.indexOf(DELIMITER);
        if( pos == 0) {
            // a commented out line, in other words...
            return null;
        }

        final String type;
        if (pos != -1) {
            type = serviceName.substring(0, pos);
            // disregard, assume the stuff after the delimiter (#) was a comment
        } else {
            type = serviceName;
        }

        final Class<?> cls = loadClass(type);
        if(cls.isAnonymousClass()) {
            // eg a test class
            return null;
        }
        return serviceInstantiator.createInstance(cls);
    }

    private static Class<?> loadClass(final String className) {
        try {
            LOG.debug("loading class for service: " + className);
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (final ClassNotFoundException ex) {
            throw new InitialisationException(String.format("Cannot find class '%s' for service", className));
        }
    }


    static List<Object> instantiateServicesFrom(SortedMap<String, SortedSet<String>> positionedServices, final ServiceInstantiator serviceInstantiator) {
        LinkedHashSet<String> serviceNameList = flatten(positionedServices);

        final Collection<Object> filter = Collections2.filter(
                Collections2.transform(serviceNameList, instantiator(serviceInstantiator)), Predicates.notNull());
        return Lists.newArrayList(filter);
    }

    private static Function<String, Object> instantiator(final ServiceInstantiator serviceInstantiator) {
        return new Function<String, Object>() {
            @Override
            public Object apply(String serviceName) {
                return instantiateService(serviceName, serviceInstantiator);
            }
        };
    }

}
