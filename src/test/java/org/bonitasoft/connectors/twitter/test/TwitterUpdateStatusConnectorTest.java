/**
 * Copyright (C) 20012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
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

import java.util.Map;

import org.bonitasoft.connectors.twitter.TwitterConnector;
import org.bonitasoft.connectors.twitter.TwitterUpdateStatusConnector;
import org.bonitasoft.engine.connector.Connector;

public class TwitterUpdateStatusConnectorTest extends TwitterConnectorTest {

    @Override
    public TwitterConnector getTwitterConnector(final Map<String, Object> parameters) {
        final TwitterUpdateStatusConnector status = new TwitterUpdateStatusConnector();
        final Map<String, Object> defaultParameters = getTwitterConnectorParameters();
        defaultParameters.putAll(parameters);
        status.setInputParameters(defaultParameters);
        return status;
    }

    @Override
    protected Class<? extends Connector> getConnectorClass() {
        return TwitterUpdateStatusConnector.class;
    }

}
