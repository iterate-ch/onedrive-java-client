package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.resources.Drive;
import org.nuxeo.onedrive.client.resources.Item;

public interface DriveRequest extends Builder, Get<Drive>, Children, Navigate, Create {
    String rootPath();
}
