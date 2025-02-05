package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class Drive extends BaseItem {
    private final DriveParent parent;

    public Drive(OneDriveAPI api) {
        super(api);
        parent = null;
    }

    public Drive(OneDriveAPI api, String id) {
        super(api, id);
        parent = null;
    }

    public Drive(Site parent) {
        super(parent.getApi());
        this.parent = new SiteParent(parent);
    }

    public Drive(Site parent, String id) {
        super(parent.getApi(), id);
        this.parent = new SiteParent(parent);
    }

    public Drive(DirectoryObject parent) {
        super(parent.getApi());
        this.parent = new DirectoryObjectParent(parent);
    }

    public Drive(DirectoryObject parent, String id) {
        super(parent.getApi(), id);
        this.parent = new DirectoryObjectParent(parent);
    }

    private static List<DriveItem.Metadata> map(JsonArray array, OneDriveAPI api) {
        return array.values().stream()
                .map(x -> DriveItem.parseJson(api, x.asObject()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPath() {
        final String id = getId();
        final String action;
        if (null == id) {
            action = "/drive";
        } else {
            action = "/drives/" + id;
        }
        if (null == parent) {
            return action;
        } else if (parent instanceof SiteParent) {
            return ((SiteParent) parent).getParent().getAction(action);
        } else if (parent instanceof DirectoryObjectParent) {
            return ((DirectoryObjectParent) parent).getParent().getPath() + action;
        }
        return "";
    }

    @Override
    public String getAction(String action) {
        return getPath() + action;
    }

    public DriveItem getRoot() {
        return new DriveItem(this);
    }

    @Override
    public Metadata getMetadata(final ODataQuery query) throws IOException {
        final URL url = new URLTemplate(getPath()).build(getApi().getBaseURL(), query);
        OneDriveJsonRequest request = new OneDriveJsonRequest(url, "GET");
        OneDriveJsonResponse response = request.sendRequest(getApi().getExecutor());
        return new Metadata().fromJson(response.getContent());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Drive");
        builder.append(" { ");
        if (printMembers(builder)) {
            builder.append(' ');
        }
        builder.append('}');
        return builder.toString();
    }

    @Override
    protected boolean printMembers(final StringBuilder builder) {
        if (super.printMembers(builder)) {
            builder.append(", ");
        }
        builder.append("Parent = ");
        builder.append(parent);
        return true;
    }

    public enum DriveType {
        personal, business, documentLibrary
    }

    public enum Property implements IDriveProperty {
        DriveType,
        Following,
        Items,
        Owner,
        Quota,
        Root,
        SharepointIds,
        Special,
        System;

        @Override
        public String getKey() {
            return name();
        }
    }

    public interface IDriveProperty extends IBaseItemProperty {
    }

    private static abstract class DriveParent<T> {
        private final T parent;

        DriveParent(T parent) {
            this.parent = parent;
        }

        public T getParent() {
            return parent;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("DriveParent");
            builder.append(" { ");
            if (printMembers(builder)) {
                builder.append(' ');
            }
            builder.append('}');
            return builder.toString();
        }

        protected boolean printMembers(final StringBuilder builder) {
            builder.append("Parent = ");
            builder.append(parent);
            return true;
        }
    }

    private static class DirectoryObjectParent extends DriveParent<DirectoryObject> {
        DirectoryObjectParent(DirectoryObject parent) {
            super(parent);
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("DirectoryObjectParent");
            builder.append(" { ");
            if (printMembers(builder)) {
                builder.append(' ');
            }
            builder.append('}');
            return builder.toString();
        }
    }

    private static class SiteParent extends DriveParent<Site> {
        SiteParent(Site parent) {
            super(parent);
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("SiteParent");
            builder.append(" { ");
            if (printMembers(builder)) {
                builder.append(' ');
            }
            builder.append('}');
            return builder.toString();
        }
    }

    public class Metadata extends BaseItem.Metadata<Metadata> {
        private DriveType driveType;
        private List<DriveItem.Metadata> following;
        private List<DriveItem.Metadata> items;
        private OneDriveIdentitySet owner;
        private Quota quota;
        private Root root;
        private SharePointIds sharePointIds;
        private List<DriveItem.Metadata> special;
        // private System system;

        public DriveType getDriveType() {
            return driveType;
        }

        public List<DriveItem.Metadata> getFollowing() {
            return following;
        }

        public List<DriveItem.Metadata> getItems() {
            return items;
        }

        public OneDriveIdentitySet getOwner() {
            return owner;
        }

        public Quota getQuota() {
            return quota;
        }

        public Root getRoot() {
            return root;
        }

        public SharePointIds getSharePointIds() {
            return sharePointIds;
        }

        public List<DriveItem.Metadata> getSpecial() {
            return special;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("Metadata");
            builder.append(" { ");
            if (printMembers(builder)) {
                builder.append(' ');
            }
            builder.append('}');
            return builder.toString();
        }

        @Override
        protected boolean printMembers(final StringBuilder builder) {
            if (super.printMembers(builder)) {
                builder.append(", ");
            }
            builder.append("DriveType = ");
            builder.append(driveType);
            builder.append(", Following = ");
            builder.append(following);
            builder.append(", Items = ");
            builder.append(items);
            builder.append(", Owner = ");
            builder.append(owner);
            builder.append(", Quota = ");
            builder.append(quota);
            builder.append(", Root = ");
            builder.append(root);
            builder.append(", SharePointIds = ");
            builder.append(sharePointIds);
            builder.append(", Special = ");
            builder.append(special);
            return true;
        }

        @Override
        protected void parseMember(JsonObject.Member member) {
            switch (member.getName()) {
                case "driveType":
                    driveType = DriveType.valueOf(member.getValue().asString());
                    break;
                case "following":
                    following = map(member.getValue().asArray(), getApi());
                    break;
                case "items":
                    items = map(member.getValue().asArray(), getApi());
                    break;
                case "owner":
                    owner = new OneDriveIdentitySet(member.getValue().asObject());
                    break;
                case "quota":
                    quota = new Quota().fromJson(member.getValue().asObject());
                    break;
                case "root":
                    root = new Root().fromJson(member.getValue().asObject());
                    break;
                case "sharepointIds":
                    sharePointIds = new SharePointIds().fromJson(member.getValue().asObject());
                    break;
                case "special":
                    special = map(member.getValue().asArray(), getApi());
                case "system":
                    break;

                default:
                    super.parseMember(member);
            }
        }
    }
}
