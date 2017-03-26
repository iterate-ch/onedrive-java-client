package org.nuxeo.onedrive.client.builders;

import org.nuxeo.onedrive.client.OneDriveAPI;
import org.nuxeo.onedrive.client.interfaces.Builder;
import org.nuxeo.onedrive.client.interfaces.IdRequest;
import org.nuxeo.onedrive.client.interfaces.PathRequest;
import org.nuxeo.onedrive.client.resources.Item;

import java.io.IOException;
import java.util.Iterator;

public class PathRequestImpl extends BuilderImpl implements PathRequest {
    private String path;

    public PathRequestImpl(Builder parent, String path) {
        super(parent);
        this.path = path;
    }

    @Override
    public String resourcePath() {
        return parentResourcePath() + "/root:/" + path;
    }

    @Override
    public String continuePath() {
        return resourcePath() + ":";
    }

    @Override
    public IdRequest navigateId(String id) {
        return null;
    }

    @Override
    public PathRequest navigatePath(String path) {
        this.path = String.format("%s/%s", this.path, path);
        return this;
    }

    @Override
    public PathRequest createFolder(OneDriveAPI api, String name) {
        return null;
    }

    @Override
    public PathRequest createFile(OneDriveAPI api, String name) {
        return null;
    }

    @Override
    public Item get(OneDriveAPI api) throws IOException {
        return null;
    }

    @Override
    public Iterator<Item> list(OneDriveAPI api) {
        return null;
    }

    @Override
    public void read(OneDriveAPI api) {

    }

    @Override
    public void write(OneDriveAPI api) {

    }

    @Override
    public String childrenPath() {
        return continuePath() + "/children";
    }
}
