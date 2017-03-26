package org.nuxeo.onedrive.client.builders;

import org.nuxeo.onedrive.client.*;
import org.nuxeo.onedrive.client.interfaces.*;
import org.nuxeo.onedrive.client.iterators.ItemIterator;
import org.nuxeo.onedrive.client.resources.Drive;
import org.nuxeo.onedrive.client.resources.Item;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public abstract class DriveRequestImpl extends BuilderImpl implements DriveRequest {
    public DriveRequestImpl(Builder parent) {
        super(parent);
    }

    public static DriveRequest create(Builder builder) {
        return new RootDriveRequestImpl(builder);
    }

    public static DriveRequest create(Builder builder, OneDriveDrive drive) {
        return new IdDriveRequestImpl(builder, drive.getResourceIdentifier());
    }

    public static DriveRequest create(Builder builder, Drive drive) {
        return new IdDriveRequestImpl(builder, drive.getId());
    }

    public static DriveRequest create(Builder builder, String id) {
        return new IdDriveRequestImpl(builder, id);
    }

    @Override
    public IdRequest navigateId(String id) {
        return new IdRequestImpl(this, id);
    }

    @Override
    public PathRequest navigatePath(String path) {
        return new PathRequestImpl(this, path);
    }

    @Override
    public Iterator<Item> list(OneDriveAPI api) {
        return new ItemIterator(api, childrenPath());
    }

    @Override
    public Drive get(OneDriveAPI api) throws IOException {
        final String urlString = api.getBaseURL() + resourcePath();
        final URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new OneDriveRuntimeException(new OneDriveAPIException(e.getMessage(), e));
        }
        return new Drive(new OneDriveJsonRequest(url, "GET").sendRequest(api.getExecutor()).getContent());
    }

    @Override
    public PathRequest createFolder(OneDriveAPI api, String name) {
        return null;
    }

    @Override
    public PathRequest createFile(OneDriveAPI api, String name) {
        return null;
    }
}
