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
import java.util.Optional;

import org.bonitasoft.engine.connector.Connector;
import org.bonitasoft.engine.connector.ConnectorException;
import org.bonitasoft.engine.connector.ConnectorValidationException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public abstract class TwitterConnector implements Connector {

    private Optional<String> proxyHost;

    private Optional<Integer> proxyPort;

    private Optional<String> proxyUser;

    private Optional<String> proxyPass;

    private Optional<String> consumerKey;

    private Optional<String> consumerSecret;

    private Optional<String> accessToken;

    private Optional<String> accessTokenSecret;

    @Override
    public void setInputParameters(Map<String, Object> parameters) {
        proxyHost = getStringParameter(parameters, "proxyHost");
        proxyPort = getIntegerParameter(parameters, "proxyPort");
        proxyUser = getStringParameter(parameters, "proxyUser");
        proxyPass = getStringParameter(parameters, "proxyPass");

        consumerKey = getStringParameter(parameters, "consumerKey");
        consumerSecret = getStringParameter(parameters, "consumerSecret");
        accessToken = getStringParameter(parameters, "accessToken");
        accessTokenSecret = getStringParameter(parameters, "accessTokenSecret");
    }

    @Override
    public void validateInputParameters() throws ConnectorValidationException {
        List<String> errors = new ArrayList<>();

        if (proxyPort.orElse(0) < 0) {
            errors.add("proxyPort cannot be less than 0!");
        } else if (proxyPort.orElse(0) > 65535) {
            errors.add("proxyPort cannot be greater than 65535!");
        }

        if (!errors.isEmpty()) {
            throw new ConnectorValidationException(this, errors);
        }
    }

    @Override
    public Map<String, Object> execute() throws ConnectorException {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        setProxyConfiguration(configurationBuilder);
        setOAuthCOnfiguration(configurationBuilder);
        try {
            TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
            Twitter twitter = tf.getInstance();
            executeTask(twitter);
        } catch (TwitterException e) {
            throw new ConnectorException(e);
        }
        return Collections.emptyMap();
    }

    private void setOAuthCOnfiguration(ConfigurationBuilder configurationBuilder) {
        configurationBuilder.setOAuthConsumerKey(consumerKey.orElse(""));
        configurationBuilder.setOAuthConsumerSecret(consumerSecret.orElse(""));
        configurationBuilder.setOAuthAccessToken(accessToken.orElse(""));
        configurationBuilder.setOAuthAccessTokenSecret(accessTokenSecret.orElse(""));
    }

    private void setProxyConfiguration(ConfigurationBuilder configurationBuilder) {
        if (proxyHost.isPresent() && proxyPort.isPresent()) {
            configurationBuilder.setHttpProxyHost(proxyHost.get());
            configurationBuilder.setHttpProxyPort(proxyPort.get());
            if (proxyUser.isPresent() && proxyPass.isPresent()) {
                configurationBuilder.setHttpProxyUser(proxyUser.get());
                configurationBuilder.setHttpProxyPassword(proxyPass.get());
            }
        }
    }

    protected abstract void executeTask(Twitter twitter) throws TwitterException;

    @Override
    public void connect() throws ConnectorException {
    }

    @Override
    public void disconnect() throws ConnectorException {
    }

    protected Optional<String> getStringParameter(Map<String, Object> parameters, String key) {
        return getParameter(parameters, key).filter(String.class::isInstance).map(String.class::cast);
    }

    protected Optional<Integer> getIntegerParameter(Map<String, Object> parameters, String key) {
        return getParameter(parameters, key).filter(Integer.class::isInstance).map(Integer.class::cast);
    }

    private Optional<Object> getParameter(Map<String, Object> parameters, String key) {
        return Optional.ofNullable(parameters.get(key));
    }
}
