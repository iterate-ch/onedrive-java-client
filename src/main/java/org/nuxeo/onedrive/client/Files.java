package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.apache.commons.io.input.NullInputStream;
import org.nuxeo.onedrive.client.types.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class Files {
    private static String versionAction(String version) {
        return "/versions/" + version;
    }

    private static String versionAction(String version, String action) {
        return versionAction(version) + action;
    }

    private static URL getChildrenUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/children")).build(item.getApi().getBaseURL());
    }

    private static URL getChildrenUrl(DriveItem item, int limit) {
        QueryStringBuilder builder = new QueryStringBuilder().set("top", limit);
        return new URLTemplate(item.getAction("/children")).build(item.getApi().getBaseURL(), builder);
    }

    private static URL getContentUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/content")).build(item.getApi().getBaseURL());
    }

    private static URL getContentUrl(DriveItem item, String version) {
        return new URLTemplate(item.getAction(versionAction(version, "/content"))).build(item.getApi().getBaseURL());
    }

    private static URL getVersionUrl(DriveItem item, String version) {
        return new URLTemplate(item.getAction(versionAction(version))).build(item.getApi().getBaseURL());
    }

    private static URL getVersionRestoreUrl(DriveItem item, String version) {
        return new URLTemplate(item.getAction(versionAction(version, "/restoreVersion"))).build(item.getApi().getBaseURL());
    }

    private static URL getVersionsUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/versions")).build(item.getApi().getBaseURL());
    }

    private static URL getUploadSessionUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/createUploadSession")).build(item.getApi().getBaseURL());
    }

    private static URL getCheckInUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/checkin")).build(item.getApi().getBaseURL());
    }

    private static URL getCheckOutUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/checkout")).build(item.getApi().getBaseURL());
    }

    private static URL getPublicationUrl(DriveItem item) {
        return new URLTemplate(item.getAction("/publication")).build(item.getApi().getBaseURL());
    }

    public static DriveItem.Metadata createFile(DriveItem parent, String filename, String mimeType) throws IOException {
        final DriveItem item = new DriveItem(parent, filename);
        final URL url = getContentUrl(item);
        final OneDriveRequest request = new OneDriveRequest(url, "PUT");
        request.addHeader("Content-Type", mimeType);
        final OneDriveResponse response = request.sendRequest(parent.getApi().getExecutor(), new NullInputStream(0));
        final OneDriveJsonResponse jsonResponse = new OneDriveJsonResponse(response.getResponse());
        final JsonObject jsonObject = jsonResponse.getContent();
        response.close();
        return DriveItem.parseJson(parent.getApi(), jsonObject);
    }

    public static DriveItem.Metadata createFolder(DriveItem parent, String foldername) throws IOException {
        final URL url = getChildrenUrl(parent);
        final JsonObject rootObject = new JsonObject();
        rootObject.add("name", foldername);
        rootObject.add("folder", new JsonObject());
        final OneDriveJsonRequest request = new OneDriveJsonRequest(url, "POST", rootObject);
        final OneDriveJsonResponse response = request.sendRequest(parent.getApi().getExecutor());
        final JsonObject responseObject = response.getContent();
        response.close();
        return DriveItem.parseJson(parent.getApi(), responseObject);
    }

    public static List<DriveItemVersion> versions(DriveItem item) throws IOException {
        final URL url = getVersionsUrl(item);
        OneDriveJsonRequest request = new OneDriveJsonRequest(url, "GET");
        OneDriveJsonResponse response = request.sendRequest(item.getApi().getExecutor());
        final List<DriveItemVersion> versions = new ArrayList<>();
        final JsonObject body = response.getContent();
        final JsonArray value = body.get("value").asArray();
        for (JsonValue version : value) {
            versions.add(new DriveItemVersion().fromJson(version.asObject()));
        }
        return versions;
    }

    public static DriveItemVersion version(DriveItem item, String version) throws IOException {
        final URL url = getVersionUrl(item, version);
        OneDriveJsonRequest request = new OneDriveJsonRequest(url, "GET");
        OneDriveJsonResponse response = request.sendRequest(item.getApi().getExecutor());
        return new DriveItemVersion().fromJson(response.getContent());
    }

    public static void restore(DriveItem item, String version) throws IOException {
        final URL url = getVersionRestoreUrl(item, version);
        new OneDriveRequest(url, "POST").sendRequest(item.getApi().getExecutor(), new NullInputStream(0)).close();
    }

    public static InputStream download(DriveItem item) throws IOException {
        final URL url = getContentUrl(item);
        OneDriveRequest request = new OneDriveRequest(url, "GET");
        OneDriveResponse response = request.sendRequest(item.getApi().getExecutor());
        return response.getContent();
    }

    public static InputStream download(DriveItem item, String range) throws IOException {
        final URL url = getContentUrl(item);
        OneDriveRequest request = new OneDriveRequest(url, "GET");
        request.addHeader("Range", String.format("bytes=%s", range));
        // Disable compression
        request.addHeader("Accept-Encoding", "identity");
        OneDriveResponse response = request.sendRequest(item.getApi().getExecutor());
        return response.getContent();
    }

    public static InputStream downloadVersion(DriveItem item, String version) throws IOException {
        final URL url = getContentUrl(item, version);
        OneDriveRequest request = new OneDriveRequest(url, "GET");
        OneDriveResponse response = request.sendRequest(item.getApi().getExecutor());
        return response.getContent();
    }

    public static InputStream downloadVersion(DriveItem item, String version, String range) throws IOException {
        final URL url = getContentUrl(item, version);
        OneDriveRequest request = new OneDriveRequest(url, "GET");
        request.addHeader("Range", String.format("bytes=%s", range));
        // Disable compression
        request.addHeader("Accept-Encoding", "identity");
        OneDriveResponse response = request.sendRequest(item.getApi().getExecutor());
        return response.getContent();
    }

    public static UploadSession createUploadSession(DriveItem item) throws IOException {
        final URL url = getUploadSessionUrl(item);
        OneDriveJsonRequest request = new OneDriveJsonRequest(url, "POST");
        try (OneDriveJsonResponse jsonResponse = request.sendRequest(item.getApi().getExecutor(), new NullInputStream(0L))) {
            return new UploadSession(item.getApi(), jsonResponse.getContent());
        }
    }

    public static void delete(DriveItem item) throws IOException {
        new OneDriveRequest(new URLTemplate(item.getPath()).build(item.getApi().getBaseURL()), "DELETE").sendRequest(item.getApi().getExecutor()).close();
    }

    public static void patch(DriveItem item, PatchOperation patch) throws IOException {
        new OneDriveJsonRequest(new URLTemplate(item.getPath()).build(item.getApi().getBaseURL()), "PATCH", patch.build()).sendRequest(item.getApi().getExecutor()).close();
    }

    public static void checkout(DriveItem item) throws IOException {
        new OneDriveRequest(getCheckOutUrl(item), "POST").sendRequest(item.getApi().getExecutor(), new NullInputStream(0)).close();
    }

    public static void checkin(DriveItem item, String comment) throws IOException {
        comment = Objects.requireNonNull(comment).trim();
        if (comment.isEmpty()) {
            throw new OneDriveAPIException("Comment must not be empty.");
        }
        final JsonObject root = new JsonObject();
        root.add("comment", comment);
        new OneDriveJsonRequest(getCheckInUrl(item), "POST", root).sendRequest(item.getApi().getExecutor()).close();
    }

    public static Publication publication(DriveItem item) throws IOException {
        return new Publication().fromJson(new OneDriveJsonRequest(getPublicationUrl(item), "GET").sendRequest(item.getApi().getExecutor()).getContent());
    }

    public static OneDriveLongRunningAction copy(DriveItem item, CopyOperation copy) throws IOException {
        final OneDriveAPI api = item.getApi();
        final URL url = new URLTemplate(item.getAction("/copy")).build(api.getBaseURL());
        OneDriveJsonResponse jsonResponse = new OneDriveJsonRequest(url, "POST", copy.build()).sendRequest(api.getExecutor());
        final URL locationUrl = new URL(jsonResponse.getLocation());
        return new OneDriveLongRunningAction(locationUrl, api);
    }

    public static Iterator<DriveItem.Metadata> getFiles(final DriveItem folder) {
        return new ItemIterator(folder.getApi(), getChildrenUrl(folder));
    }

    public static Iterator<DriveItem.Metadata> getFiles(final DriveItem folder, int limit) {
        return new ItemIterator(folder.getApi(), getChildrenUrl(folder, limit));
    }

    public static Iterator<DriveItem.Metadata> search(DriveItem item, String search) {
        final URL url = new URLTemplate(item.getAction("/search(q='%s')")).build(item.getApi().getBaseURL(), search);
        return new ItemIterator(item.getApi(), url);
    }

    public static Permission createSharedLink(DriveItem item, OneDriveSharingLink.Type type) throws IOException {
        final URL url = new URLTemplate(item.getAction("/createLink")).build(item.getApi().getBaseURL());
        OneDriveJsonRequest request = new OneDriveJsonRequest(url, "POST",
                new JsonObject().add("type", type.getType()));
        OneDriveJsonResponse response = request.sendRequest(item.getApi().getExecutor());
        final JsonObject json = response.getContent();
        return new Permission().fromJson(json);
    }

    public static Iterator<DriveItem.Metadata> getSharedWithMe(final User user) {
        return new ItemIterator(user.getApi(),
                new URLTemplate(user.getOperationPath("/drive/sharedWithMe")).build(user.getApi().getBaseURL(), new QueryStringBuilder()
                        .set("allowExternal", true)));
    }

    private final static class ItemIterator implements Iterator<DriveItem.Metadata> {
        private final OneDriveAPI api;
        private final JsonObjectIterator iterator;

        public ItemIterator(final OneDriveAPI api, final URL url) {
            this.api = api;
            this.iterator = new JsonObjectIterator(api, url);
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public DriveItem.Metadata next() {
            return DriveItem.parseJson(api, iterator.next());
        }
    }
}
