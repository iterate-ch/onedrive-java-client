package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class SharePointIds extends Facet<SharePointIds> {
    private String listId;
    private String listItemId;
    private String listItemUniqueId;
    private String siteId;
    private String siteUrl;
    private String tenantId;
    private String webId;

    public String getListId() {
        return listId;
    }

    public String getListItemId() {
        return listItemId;
    }

    public String getListItemUniqueId() {
        return listItemUniqueId;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getWebId() {
        return webId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("SharePointIds");
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
        builder.append("ListId = ");
        builder.append(listId);
        builder.append(", ListItemId = ");
        builder.append(listItemId);
        builder.append(", ListItemUniqueId = ");
        builder.append(listItemUniqueId);
        builder.append(", SiteId = ");
        builder.append(siteId);
        builder.append(", SiteUrl = ");
        builder.append(siteUrl);
        builder.append(", TenantId = ");
        builder.append(tenantId);
        builder.append(", WebId = ");
        builder.append(webId);
        return true;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch (member.getName()) {
            case "listId":
                listId = member.getValue().asString();
                break;
            case "listItemid":
                listItemId = member.getValue().asString();
                break;
            case "listItemUniqueId":
                listItemUniqueId = member.getValue().asString();
                break;
            case "siteId":
                siteId = member.getValue().asString();
                break;
            case "siteUrl":
                siteUrl = member.getValue().asString();
                break;
            case "tenantId":
                tenantId = member.getValue().asString();
                break;
            case "webId":
                webId = member.getValue().asString();
                break;
            default:
                super.parseMember(member);
        }
    }
}
