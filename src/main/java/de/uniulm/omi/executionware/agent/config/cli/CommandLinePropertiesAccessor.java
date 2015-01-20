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

package de.uniulm.omi.executionware.agent.config.cli;

import com.google.inject.Inject;
import de.uniulm.omi.executionware.agent.MonitoringAgent;
import org.apache.commons.cli.*;

import javax.annotation.Nullable;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 24.09.14.
 */
@SuppressWarnings("AccessStaticViaInstance")
public class CommandLinePropertiesAccessor {

    private final Options options;
    private CommandLine commandLine;
    private final static BasicParser parser = new BasicParser();
    private final static HelpFormatter helpFormatter = new HelpFormatter();

    @Inject
    @Singleton
    public CommandLinePropertiesAccessor(String[] args) {
        this.options = new Options();
        this.generateOptions(this.options);

        try {
            this.commandLine = this.parser.parse(options, args);
        } catch (ParseException e) {
            this.commandLine = null;
            this.printHelp();
            System.exit(1);
        }
    }

    private void generateOptions(Options options) {
        options.addOption(OptionBuilder
                        .withLongOpt("localIp")
                        .withDescription("IP of the local machine")
                        .isRequired()
                        .hasArg()
                        .create("ip")
        );
        options.addOption(OptionBuilder
                        .withLongOpt("configFile")
                        .withDescription("Configuration file location.")
                        .isRequired()
                        .hasArg()
                        .create("conf")
        );
    }

    public void printHelp() {
        helpFormatter.printHelp(MonitoringAgent.class.getCanonicalName(), options);
    }

    @Nullable
    protected String getCommandLineOption(String name) {
        checkState(this.commandLine != null, "Command line not parsed.");
        if (!commandLine.hasOption(name)) {
            return null;
        }
        return commandLine.getOptionValue(name);
    }

    public String getLocalIp() {
        final String ip = getCommandLineOption("ip");
        checkState(ip != null, "No command line argument value for ip (localIp)");
        return ip;
    }

    public int getPort() {
        String port = this.getCommandLineOption("p");
        checkState(port != null, "No command line argument value for p (port)");
        return Integer.valueOf(checkNotNull(port));
    }

    public String getConfFileLocation() {
        String confFile = this.getCommandLineOption("conf");
        checkState(confFile != null, "No command line argument value for conf (configFile)");
        return confFile;
    }
}