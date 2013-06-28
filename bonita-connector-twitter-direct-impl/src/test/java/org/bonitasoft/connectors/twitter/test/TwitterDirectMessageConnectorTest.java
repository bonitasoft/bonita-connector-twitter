/**
 * Copyright (C) 2009-2011 BonitaSoft S.A.
 * BonitaSoft, 31 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.connectors.twitter.test;

import java.util.HashMap;
import java.util.Map;

import org.bonitasoft.connectors.twitter.TwitterConnector;
import org.bonitasoft.connectors.twitter.TwitterDirectMessageConnector;
import org.bonitasoft.engine.connector.Connector;

public class TwitterDirectMessageConnectorTest extends TwitterConnectorTest {

    @Override
    public TwitterConnector getTwitterConnector(final Map<String, Object> parameters) {
        final TwitterDirectMessageConnector message = new TwitterDirectMessageConnector();
        final Map<String, Object> defaultParameters = new HashMap<String, Object>();
        defaultParameters.put("consumerKey", "zerzer");
        defaultParameters.put("consumerSecret", "mljpoi");
        defaultParameters.put("accesToken", "qsdfo");
        defaultParameters.put("accessTokenSecret", "poqsfhe");
        defaultParameters.put("status", "poafjaofj");
        defaultParameters.put("proxyHost", "myproxy.bonitasoft.com");
        defaultParameters.put("proxyPort", 8080);
        defaultParameters.putAll(parameters);
        message.setInputParameters(defaultParameters);

        return message;
    }

    @Override
    protected Class<? extends Connector> getConnectorClass() {
        return TwitterDirectMessageConnector.class;
    }
}
