package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class File extends Facet<File> {
    private Hashes hashes;
    private String mimeType;

    public Hashes getHashes() {
        return hashes;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("File");
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
        builder.append("Hashes = ");
        builder.append(hashes);
        builder.append(", MimeType = ");
        builder.append(mimeType);
        return true;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch (member.getName()) {
            case "hashes":
                hashes = new Hashes().fromJson(member.getValue().asObject());
                break;

            case "mimeType":
                mimeType = member.getValue().asString();
                break;

            default:
                super.parseMember(member);
        }
    }
}
