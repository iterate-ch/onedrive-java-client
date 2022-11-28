package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class Package extends Facet<Package> {
    private String type;

    public String getType() {
        return type;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        if (member.getName() == "type") {
            type = member.getValue().asString();
        } else {
            super.parseMember(member);
        }
    }
}
