package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.nuxeo.onedrive.client.OneDriveIdentitySet;
import org.nuxeo.onedrive.client.OneDriveSharingLink;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Permission extends GraphType<Permission> {
    private String id;
    private OneDriveIdentitySet grantedTo;
    private List<OneDriveIdentitySet> grantedToIdentities;
    private ItemReference inheritedFrom;
    // private SharingInvitation invitation;
    private OneDriveSharingLink link;
    private List<String> roles;
    private String shareId;
    private ZonedDateTime expirationDateTime;
    private Boolean hasPassword;

    public String getId() {
        return id;
    }

    public OneDriveIdentitySet getGrantedTo() {
        return grantedTo;
    }

    public List<OneDriveIdentitySet> getGrantedToIdentities() {
        return grantedToIdentities;
    }

    public ItemReference getInheritedFrom() {
        return inheritedFrom;
    }

    public OneDriveSharingLink getLink() {
        return link;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getShareId() {
        return shareId;
    }

    public ZonedDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public Boolean hasPassword() {
        return hasPassword;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Permission");
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
        builder.append("Id = ");
        builder.append(id);
        builder.append(", GrantedTo = ");
        builder.append(grantedTo);
        builder.append(", GrantedToIdentities = ");
        builder.append(grantedToIdentities);
        builder.append(", InheritedFrom = ");
        builder.append(inheritedFrom);
        builder.append(", Link = ");
        builder.append(link);
        builder.append(", Roles = ");
        builder.append(roles);
        builder.append(", ShareId = ");
        builder.append(shareId);
        builder.append(", ExpirationDateTime = ");
        builder.append(expirationDateTime);
        builder.append(", HasPassword = ");
        builder.append(hasPassword);
        return true;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch (member.getName()) {
            case "id":
                id = member.getValue().asString();
                break;
            case "grantedTo":
                grantedTo = new OneDriveIdentitySet(member.getValue().asObject());
                break;
            case "grantedToIdentities":
                grantedToIdentities = member.getValue().asArray().values().stream()
                        .map(i -> new OneDriveIdentitySet(i.asObject()))
                        .collect(Collectors.toList());
                break;
            case "inheritedFrom":
                inheritedFrom = new ItemReference().fromJson(member.getValue().asObject());
                break;
            case "invitation":
                break;
            case "link":
                link = new OneDriveSharingLink(member.getValue().asObject());
                break;
            case "roles":
                roles = member.getValue().asArray().values().stream().map(JsonValue::asString).collect(Collectors.toList());
                break;
            case "shareId":
                shareId = member.getValue().asString();
                break;
            case "expirationDateTime":
                expirationDateTime = ZonedDateTime.parse(member.getValue().asString());
                break;
            case "hasPassword":
                hasPassword = member.getValue().asBoolean();
                break;

            default:
                super.parseMember(member);
        }
    }
}
