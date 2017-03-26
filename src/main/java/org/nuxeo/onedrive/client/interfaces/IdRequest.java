package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.resources.Item;

public interface IdRequest extends Builder, Get<Item>, Children, Navigate, Create, ReadWrite {
}
