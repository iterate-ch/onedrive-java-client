/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Kevin Leturc
 */
package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @since 1.0
 */
public abstract class OneDriveJsonObject {
    private final Map<String, JsonValue> additionalData = new HashMap<>();

    protected OneDriveJsonObject() {
    }

    protected OneDriveJsonObject(JsonObject json) {
        parseMember(json);
    }

    protected static void parseMember(JsonObject json, Consumer<JsonObject.Member> consumer, Predicate<JsonObject.Member> filterUnsafe, Consumer<JsonObject.Member> additionalConsumer) {
        for (JsonObject.Member member : json) {
            if (!filterUnsafe.test(member) && !member.getValue().isNull()) {
                // parseMember assumes that member.getValue() is never null.
                // which causes much trouble.
                try {
                    consumer.accept(member);
                } catch (Exception ex) {
                    additionalConsumer.accept(member);
                }
            }
        }
    }

    public Map<String, JsonValue> getAdditionalData() {
        return additionalData;
    }

    public final void parseMember(JsonObject json) {
        parseMember(json, this::parseMember, this::parseMemberUnsafe, this::addAdditionalData);
    }

    protected void parseMember(JsonObject.Member member) {
        addAdditionalData(member);
    }

    protected boolean parseMemberUnsafe(JsonObject.Member member) {
        return false;
    }

    private final void addAdditionalData(JsonObject.Member member) {
        additionalData.put(member.getName(), member.getValue());
    }
}
