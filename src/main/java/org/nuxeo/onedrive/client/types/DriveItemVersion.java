package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.OneDriveIdentitySet;

import java.time.OffsetDateTime;

public class DriveItemVersion extends Facet<DriveItemVersion> {
    private String id;
    private OneDriveIdentitySet lastModifiedBy;
    private OffsetDateTime lastModifiedDateTime;
    private Long size;
    private String contentUrl;

    public String getId() {
        return id;
    }

    public OneDriveIdentitySet getLastModifiedBy() {
        return lastModifiedBy;
    }

    public OffsetDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public Long getSize() {
        return size;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("DriveItemVersion");
        builder.append(" { ");
        if (printMembers(builder)) {
            builder.append(' ');
        }
        builder.append('}');
        return builder.toString();
    }

    @Override
    protected boolean printMembers(StringBuilder builder) {
        if (super.printMembers(builder)) {
            builder.append(", ");
        }
        builder.append("ID = ");
        builder.append(id);
        builder.append(", LastModifiedBy = ");
        builder.append(lastModifiedBy);
        builder.append(", LastModifiedDateTime = ");
        builder.append(lastModifiedDateTime);
        builder.append(", Size = ");
        builder.append(size);
        builder.append(", ContentUrl = ");
        builder.append(contentUrl);
        return true;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch (member.getName()) {
            case "id":
                id = member.getValue().asString();
                break;

            case "lastModifiedBy":
                lastModifiedBy = new OneDriveIdentitySet(member.getValue().asObject());
                break;

            case "lastModifiedDateTime":
                lastModifiedDateTime = OffsetDateTime.parse(member.getValue().asString());
                break;

            case "size":
                size = member.getValue().asLong();
                break;

            case "@microsoft.graph.downloadUrl":
                contentUrl = member.getValue().asString();
                break;

            default:
                super.parseMember(member);
        }
    }
}
