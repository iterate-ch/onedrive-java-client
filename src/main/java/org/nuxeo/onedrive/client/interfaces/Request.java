package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.OneDriveDrive;
import org.nuxeo.onedrive.client.resources.Drive;

public interface Request extends Builder, Navigate, List<Drive> {
    DriveRequest root();

    DriveRequest setDrive(OneDriveDrive drive);

    DriveRequest setDrive(Drive drive);

    DriveRequest setDrive(String id);
}
