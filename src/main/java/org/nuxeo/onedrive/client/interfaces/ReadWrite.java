package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.OneDriveAPI;

public interface ReadWrite {
    void read(OneDriveAPI api);

    void write(OneDriveAPI api);
}
