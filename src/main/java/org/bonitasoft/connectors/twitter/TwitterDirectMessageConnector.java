/**
 * Copyright (C) 2012-2014 BonitaSoft S.A.
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
package org.bonitasoft.connectors.twitter;

import java.util.Map;
import java.util.Optional;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterDirectMessageConnector extends TwitterConnector {

    private Optional<String> message;
    private Optional<String> recipientId;

    @Override
    public void setInputParameters(Map<String, Object> parameters) {
        super.setInputParameters(parameters);
        message = getStringParameter(parameters, "message");
        recipientId = getStringParameter(parameters, "recipientId");
    }

    @Override
    protected void executeTask(Twitter twitter) throws TwitterException {
        twitter.v1().directMessages().sendDirectMessage(recipientId.orElse(""), message.orElse(""));
    }

}
