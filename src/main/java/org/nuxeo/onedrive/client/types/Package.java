package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class Package extends Facet<Package> {
    private String type;

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Package");
        builder.append(" { ");
        if (printMembers(builder)) {
            builder.append(' ');
        }
        builder.append('}');
        return builder.toString();
    }

    @Override
    protected boolean printMembers(StringBuilder builder) {
        if(super.printMembers(builder)) {
            builder.append(", ");
        }
        builder.append("Type = ");
        builder.append(type);
        return true;
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
