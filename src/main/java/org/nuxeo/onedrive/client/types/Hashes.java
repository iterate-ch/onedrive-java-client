package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class Hashes extends GraphType<Hashes> {
    private String crc32Hash;
    private String sha1Hash;
    private String sha256Hash;
    private String quickXorHash;

    public String getCRC32Hash() {
        return crc32Hash;
    }

    public String getSHA1Hash() {
        return sha1Hash;
    }

    public String getSHA256Hash() {
        return sha256Hash;
    }

    public String getQuickXorHash() {
        return quickXorHash;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Hashes");
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
        builder.append("CRC32Hash = ");
        builder.append(crc32Hash);
        builder.append(", SHA1Hash = ");
        builder.append(sha1Hash);
        builder.append(", SHA256hash = ");
        builder.append(sha256Hash);
        builder.append(", QuickXorHash = ");
        builder.append(quickXorHash);
        return true;
    }

    @Override
    protected void parseMember(JsonObject.Member member) {
        switch (member.getName()) {
            case "crc32Hash":
                crc32Hash = member.getValue().asString();
                break;
            case "sha1Hash":
                sha1Hash = member.getValue().asString();
                break;
            case "sha256Hash":
                sha256Hash = member.getValue().asString();
                break;
            case "quickXorhash":
                quickXorHash = member.getValue().asString();
                break;

            default:
                super.parseMember(member);
        }
    }
}
