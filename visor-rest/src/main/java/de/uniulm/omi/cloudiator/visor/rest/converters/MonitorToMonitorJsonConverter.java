/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.uniulm.omi.cloudiator.visor.rest.converters;


import de.uniulm.omi.cloudiator.visor.rest.resources.BaseMonitor;
import de.uniulm.omi.cloudiator.visor.rest.resources.Links;
import de.uniulm.omi.cloudiator.visor.rest.resources.MonitorEntity;
import de.uniulm.omi.cloudiator.visor.rest.resources.MonitorWithLinks;

/**
 * Created by daniel on 09.02.15.
 */
public class MonitorToMonitorJsonConverter
    implements OneWayConverter<de.uniulm.omi.cloudiator.visor.monitoring.Monitor, MonitorEntity> {

    @Override public MonitorEntity apply(de.uniulm.omi.cloudiator.visor.monitoring.Monitor input) {
        return new MonitorWithLinks(BaseMonitor.builder().metricName(input.getMetricName())
            .sensorClassName(input.getSensor().getClass().getCanonicalName())
            .interval(input.getInterval()).context(input.getMonitorContext()).build(),
            Links.selfLink("/monitors/" + input.getUuid()));
    }
}
