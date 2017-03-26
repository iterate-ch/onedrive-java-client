package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.resources.Item;

public interface Children extends List<Item> {
    String childrenPath();
}
