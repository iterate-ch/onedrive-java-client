package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.OneDriveAPI;

import java.io.IOException;

public interface Get<E> {
    E get(OneDriveAPI api) throws IOException;
}
