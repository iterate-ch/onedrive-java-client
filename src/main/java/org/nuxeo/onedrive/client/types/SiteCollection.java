package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class SiteCollection extends Facet<SiteCollection> {
    private String hostname;
    private String dataLocationCode;
    private Root root;

    public String getHostname() {
        return hostname;
    }

    public String getDataLocationCode() {
        return dataLocationCode;
    }

    public Root getRoot() {
        return root;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("SiteCollection");
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
        builder.append("Hostname = ");
        builder.append(hostname);
        builder.append(", DataLocationCode = ");
        builder.append(dataLocationCode);
        builder.append(", Root = ");
        builder.append(root);
        return true;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch(member.getName()) {
            case "hostname":
                hostname = member.getValue().asString();
                break;
            case "dataLocationCode":
                dataLocationCode = member.getValue().asString();
                break;
            case "root":
                root = new Root().fromJson(member.getValue().asObject());
                break;
            default:
                super.parseMember(member);
        }
    }
}
