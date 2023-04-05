package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class Publication extends Facet<Publication> {
    public enum State {
        published,
        checkout,
        draft
    }

    private State level;

    private String versionId;

    public State getLevel() {
        return level;
    }

    public String getVersionId() {
        return versionId;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch (member.getName()) {
            case "level":
                level = State.valueOf(member.getValue().asString());
                break;
            case "versionId":
                versionId = member.getValue().asString();
                break;
        }
    }
}
