package org.nuxeo.onedrive.client.builders;

import org.nuxeo.onedrive.client.OneDriveAPI;
import org.nuxeo.onedrive.client.OneDriveDrive;
import org.nuxeo.onedrive.client.interfaces.*;
import org.nuxeo.onedrive.client.iterators.DriveIterator;
import org.nuxeo.onedrive.client.resources.Drive;

import java.util.Iterator;

public class RequestImpl implements Request {
    @Override
    public Iterator<Drive> list(OneDriveAPI api) {
        return new DriveIterator(api);
    }

    @Override
    public Builder getParent() {
        return null;
    }

    @Override
    public String resourcePath() {
        return "";
    }

    @Override
    public String continuePath() {
        return "";
    }

    @Override
    public IdRequest navigateId(String id) {
        return null;
    }

    @Override
    public PathRequest navigatePath(String path) {
        return null;
    }

    @Override
    public DriveRequest root() {
        return DriveRequestImpl.create(this);
    }

    @Override
    public DriveRequest setDrive(OneDriveDrive drive) {
        return DriveRequestImpl.create(this, drive);
    }

    @Override
    public DriveRequest setDrive(Drive drive) {
        return DriveRequestImpl.create(this, drive);
    }

    @Override
    public DriveRequest setDrive(String id) {
        return DriveRequestImpl.create(this, id);
    }
}
