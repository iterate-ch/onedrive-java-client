package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.OneDriveAPI;

public interface Create {
    PathRequest createFolder(OneDriveAPI api, String name);

    PathRequest createFile(OneDriveAPI api, String name);
}
