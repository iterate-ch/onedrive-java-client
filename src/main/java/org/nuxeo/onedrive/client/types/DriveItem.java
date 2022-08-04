package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.nuxeo.onedrive.client.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriveItem extends BaseItem {
    private final ItemIdentifierType itemIdentifierType;
    private final ParentReference parent;

    public DriveItem(Drive parent) {
        // Root Folder of Drive
        super(parent.getApi(), null);
        this.parent = new DriveParent(parent);
        itemIdentifierType = null;
    }

    public DriveItem(Drive parent, String id) {
        // Single Item (directly used with /drive/items/<id>)
        super(parent.getApi(), id);
        this.parent = new DriveParent(parent);
        itemIdentifierType = ItemIdentifierType.Id;
    }

    public DriveItem(DriveItem parent, String path) {
        // Path-relative item (/drive/root/children:/Path/To/File)
        super(parent.getApi(), path);
        this.parent = new ItemParent(parent);
        this.itemIdentifierType = ItemIdentifierType.Path;
    }

    public static DriveItem.Metadata parseJson(final OneDriveAPI api, final JsonObject jsonObject) {
        final String id = jsonObject.get("id").asString();

        final Drive drive;
        final JsonValue parentReference = jsonObject.get("parentReference");
        if (null == parentReference) {
            drive = new Drive(api);
        } else {
            drive = new Drive(api, parentReference.asObject().get("driveId").asString());
        }
        final DriveItem item = new DriveItem(drive, id);
        return item.new Metadata().fromJson(jsonObject);
    }

    public JsonObject createParentReferenceObject() {
        final JsonObject root = new JsonObject();

        if (ItemIdentifierType.Id == itemIdentifierType) {
            root.add("driveId", getDrive().getId());
            root.add("id", getId());
        } else {
            final StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(getPath());
            if (null == itemIdentifierType) {
                pathBuilder.append(":");
            }
            root.add("path", pathBuilder.toString());
        }

        return root;
    }

    @Override
    public String getAction(String action) {
        final StringBuilder actionPathBuilder = new StringBuilder();
        actionPathBuilder.append(getPath());

        if (ItemIdentifierType.Path == itemIdentifierType) {
            actionPathBuilder.append(":");
        }
        actionPathBuilder.append(action);

        return actionPathBuilder.toString();
    }

    public Drive getDrive() {
        if (parent instanceof DriveParent) {
            return ((DriveParent) parent).getParent();
        }
        return ((ItemParent) parent).getParent().getDrive();
    }

    public ItemIdentifierType getItemIdentifierType() {
        return itemIdentifierType;
    }

    @Override
    public Metadata getMetadata(final ODataQuery query) throws IOException {
        final URL url = new URLTemplate(getPath()).build(getApi().getBaseURL(), query);
        OneDriveJsonRequest request = new OneDriveJsonRequest(url, "GET");
        OneDriveJsonResponse response = request.sendRequest(getApi().getExecutor());
        return new Metadata().fromJson(response.getContent());
    }

    @Override
    public String getPath() {
        if (parent instanceof DriveParent) {
            final DriveParent drive = (DriveParent) parent;
            if (null == itemIdentifierType) {
                return drive.getParent().getAction("/root");
            } else if (ItemIdentifierType.Id == itemIdentifierType) {
                return drive.getParent().getAction("/items/" + getId());
            }
        } else if (parent instanceof ItemParent) {
            final ItemParent item = (ItemParent) parent;
            return item.getParent().getAction(":/" + getId()); //TODO Force non-leading slash in Path?
        }
        return null;
    }

    public enum ItemIdentifierType {
        Id,
        Path
    }

    public enum Property implements IDriveItemProperty {
        Activities,
        Analytics,
        Audio,
        Bundle,
        CTag,
        Children,
        Content,
        CreatedByUser,
        Deleted,
        File,
        FileSystemInfo,
        Folder,
        Image,
        LastModifiedByUser,
        Location,
        Malware,
        Package,
        PendingOperations,
        Permissions,
        Photo,
        Publication,
        RemoteItem,
        Root,
        SearchResult,
        Shared,
        SharepointIds,
        Size,
        SpecialFolder,
        Subscriptions,
        Thumbnails,
        Versions,
        Video,
        WebDavUrl,
        ;

        @Override
        public String getKey() {
            return name();
        }
    }

    public interface IDriveItemProperty extends IBaseItemProperty {
    }

    private static class DriveParent extends ParentReference<Drive> {
        DriveParent(Drive parent) {
            super(parent);
        }
    }

    private static class ItemParent extends ParentReference<DriveItem> {
        ItemParent(DriveItem parent) {
            super(parent);
        }
    }

    private static abstract class ParentReference<T> {
        private final T parent;

        ParentReference(T parent) {
            this.parent = parent;
        }

        public T getParent() {
            return parent;
        }
    }

    public class Metadata extends BaseItem.Metadata<Metadata> {
        private final Map<Class, Facet> facetMap = new HashMap<>();
        private String cTag;
        private DriveItem.Metadata remoteItem;
        private Long size;
        private String webDavUrl;

        public <T extends Facet> T getFacet(Class<T> clazz) {
            return (T) facetMap.getOrDefault(clazz, null);
        }

        public File getFile() {
            return getFacet(File.class);
        }

        public Folder getFolder() {
            return getFacet(Folder.class);
        }

        public Package getPackage() {
            return getFacet(Package.class);
        }

        public Publication getPublication() {
            return getFacet(Publication.class);
        }

        public Metadata getRemoteItem() {
            return remoteItem;
        }

        public Long getSize() {
            return size;
        }

        public String getWebDavUrl() {
            return webDavUrl;
        }

        public String getcTag() {
            return cTag;
        }

        public boolean isFile() {
            return null != getFacet(File.class);
        }

        public boolean isFolder() {
            return null != getFacet(Folder.class);
        }

        public boolean isPackage() {
            return null != getFacet(Package.class);
        }

        @Override
        protected void parseMember(JsonObject.Member member) {
            switch (member.getName()) {
                case "cTag":
                    cTag = member.getValue().asString();
                    break;

                case "remoteItem":
                    remoteItem = DriveItem.parseJson(getApi(), member.getValue().asObject());
                    break;

                case "size":
                    size = member.getValue().asLong();
                    break;

                case "webDavUrl":
                    webDavUrl = member.getValue().asString();
                    break;

                case "file":
                    facetMap.put(File.class, new File().fromJson(member.getValue().asObject()));
                    break;

                case "fileSystemInfo":
                    facetMap.put(FileSystemInfo.class, new FileSystemInfo().fromJson(member.getValue().asObject()));
                    break;

                case "folder":
                    facetMap.put(Folder.class, new Folder().fromJson(member.getValue().asObject()));
                    break;

                case "publication":
                    facetMap.put(Publication.class, new Publication().fromJson(member.getValue().asObject()));
                    break;

                // Properties
                case "audio":
                case "content":
                case "deleted":
                case "image":
                case "location":
                case "pendingOperations":
                case "photo":
                case "root":
                case "searchResult":
                case "shared":
                case "specialFolder":
                case "video":
                    break;

                // Relations
                case "activities":
                case "analytics":
                case "children":
                case "createdByUser":
                case "lastModifiedByUser":
                case "permissions":
                case "subscriptions":
                case "thumbnails":
                case "versions":
                    break;

                default:
                    super.parseMember(member);
            }
        }
    }
}
