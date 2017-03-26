package org.nuxeo.onedrive.client.interfaces;

public interface Navigate {
    IdRequest navigateId(String id);

    PathRequest navigatePath(String path);
}
