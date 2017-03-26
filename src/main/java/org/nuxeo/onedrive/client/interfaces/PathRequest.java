package org.nuxeo.onedrive.client.interfaces;

import org.nuxeo.onedrive.client.resources.Item;


public interface PathRequest extends Builder, Get<Item>, Children, Navigate, Create, ReadWrite {
}
