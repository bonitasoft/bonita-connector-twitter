/**
 * Copyright (C) 2009-2014 BonitaSoft S.A.
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
package org.bonitasoft.connectors.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.connector.Connector;
import org.bonitasoft.engine.connector.ConnectorException;
import org.bonitasoft.engine.connector.ConnectorValidationException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This connector provides a twitter sending service.
 * 
 * @author Matthieu Chaffotte
 * @author Yanyan Liu
 * @author Baptiste Mesta
 * @author Haris Subašić
 * @author Maxence Raoux
 */

public abstract class TwitterConnector implements Connector {

    private String proxyHost;

    private Integer proxyPort;

    private String proxyUser;

    private String proxyPass;

    private String consumerKey;

    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;

    @Override
    public void setInputParameters(final Map<String, Object> parameters) {
        final Object proxyHostObject = parameters.get("proxyHost");
        proxyHost = proxyHostObject != null ? (String) proxyHostObject : "";
        final Object proxyPortObject = parameters.get("proxyPort");
        proxyPort = proxyPortObject != null ? (Integer) proxyPortObject : 0;
        final Object proxyUserObject = parameters.get("proxyUser");
        proxyUser = proxyUserObject != null ? (String) proxyUserObject : "";
        final Object proxyPassObject = parameters.get("proxyPass");
        proxyPass = proxyPassObject != null ? (String) proxyPassObject : "";
        final Object consumerKeyObject = parameters.get("consumerKey");
        consumerKey = consumerKeyObject != null ? (String) consumerKeyObject
                : "";
        final Object consumerSecretObject = parameters.get("consumerSecret");
        consumerSecret = consumerSecretObject != null ? (String) consumerSecretObject
                : "";
        final Object accessTokenObject = parameters.get("accessToken");
        accessToken = accessTokenObject != null ? (String) accessTokenObject
                : "";
        final Object accessTokenSecretObject = parameters
                .get("accessTokenSecret");
        accessTokenSecret = accessTokenSecretObject != null ? (String) accessTokenSecretObject
                : "";

    }

    @Override
    public void validateInputParameters() throws ConnectorValidationException {
        final List<String> errors = new ArrayList<String>(1);

        if (proxyPort < 0) {
            errors.add("proxyPort cannot be less than 0!");
        } else if (proxyPort > 65535) {
            errors.add("proxyPort cannot be greater than 65535!");
        }

        if (!errors.isEmpty()) {
            throw new ConnectorValidationException(this, errors);
        }
    }

    @Override
    public Map<String, Object> execute() throws ConnectorException {

        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        if (proxyHost != null && proxyPort != null) {
            configurationBuilder.setHttpProxyHost(proxyHost);
            configurationBuilder.setHttpProxyPort(proxyPort);
            if (proxyUser != null && proxyPass != null) {
                configurationBuilder.setHttpProxyUser(proxyUser);
                configurationBuilder.setHttpProxyPassword(proxyPass);
            }
        }
        configurationBuilder.setOAuthConsumerKey(consumerKey);
        configurationBuilder.setOAuthConsumerSecret(consumerSecret);
        configurationBuilder.setOAuthAccessToken(accessToken);
        configurationBuilder.setOAuthAccessTokenSecret(accessTokenSecret);

        try {
            final TwitterFactory tf = new TwitterFactory(
                    configurationBuilder.build());
            final Twitter twitter = tf.getInstance();
            executeTask(twitter);
        } catch (final Exception e) {
            throw new ConnectorException(e);
        }
        return Collections.emptyMap();
    }

    protected abstract void executeTask(Twitter twitter) throws Exception;

    @Override
    public void connect() throws ConnectorException {
    }

    @Override
    public void disconnect() throws ConnectorException {
    }
}
