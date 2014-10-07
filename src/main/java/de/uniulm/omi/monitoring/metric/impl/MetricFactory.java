/*
 *
 *  * Copyright (c) 2014 University of Ulm
 *  *
 *  * See the NOTICE file distributed with this work for additional information
 *  * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package de.uniulm.omi.monitoring.metric.impl;

import de.uniulm.omi.monitoring.cli.CliOptions;
import de.uniulm.omi.monitoring.server.IllegalRequestException;

/**
 * Created by daniel on 22.09.14.
 */
public class MetricFactory {

    /**
     * Singleton instance.
     */
    private static MetricFactory instance = new MetricFactory();

    /**
     * Private constructor for singleton pattern.
     */
    private MetricFactory() {

    }

    /**
     * Returns instance of the metric factory.
     * Implementation of the singleton pattern.
     *
     * @return unique instance of metric factory.
     */
    public static MetricFactory getInstance() {
        return instance;
    }

    public ApplicationMetric fromRequest(String request) throws IllegalRequestException {
        return this.parseRequest(request);
    }

    public ServerMetric fromNameAndValue(String name, Object value) {
        return new ServerMetric(name, value, System.currentTimeMillis(), CliOptions.getLocalIp());
    }

    protected ApplicationMetric parseRequest(String request) throws IllegalRequestException {
        // split the request at blanks
        String[] parts = request.split(" ");

        if(parts.length != 4) {
            throw new IllegalRequestException("Illegal request.");
        }

        String applicationName = parts[0];
        String metricName = parts[1];
        String value = parts[2];
        long timestamp = Long.valueOf(parts[3]);

        return new ApplicationMetric(metricName, value, timestamp, applicationName, CliOptions.getLocalIp());

    }

}