package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.OneDriveAPI;

import java.util.Iterator;

public interface List<E> {
    Iterator<E> list(OneDriveAPI api);
}
