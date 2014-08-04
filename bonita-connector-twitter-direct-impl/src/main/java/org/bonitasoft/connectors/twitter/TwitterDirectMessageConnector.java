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

import twitter4j.Twitter;

/**
 * @author Matthieu Chaffotte
 * @author Haris Subašić
 */
public class TwitterDirectMessageConnector extends TwitterConnector {

    private String message;

    private String recipientId;

    @Override
    public void setInputParameters(final Map<String, Object> parameters) {
        super.setInputParameters(parameters);
        final Object messageObject = parameters.get("message");
        message = messageObject != null ? (String) messageObject : "";
        final Object recipientIdObject = parameters.get("recipientId");
        recipientId = recipientIdObject != null ? (String) recipientIdObject
                : "";
    }

    @Override
    protected void executeTask(final Twitter twitter) throws Exception {
        twitter.sendDirectMessage(recipientId, message);
    }

}
