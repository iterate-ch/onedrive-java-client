package org.nuxeo.onedrive.client.iterators;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.nuxeo.onedrive.client.OneDriveAPI;
import org.nuxeo.onedrive.client.OneDriveIterator;
import org.nuxeo.onedrive.client.OneDriveRuntimeException;
import org.nuxeo.onedrive.client.resources.Drive;
import org.nuxeo.onedrive.client.resources.Item;

import java.util.Iterator;

public class ItemIterator implements Iterator<Item>, OneDriveIterator.ObjectCheck, OneDriveIterator.ObjectWorker<Item> {
    private final OneDriveIterator<Item> itemIterator;

    public ItemIterator(OneDriveAPI api, String path) {
        itemIterator = new OneDriveIterator<>(api, path, this, this);
    }

    @Override
    public boolean hasNext() {
        return itemIterator.hasNext();
    }

    @Override
    public Item next() {
        return null;
    }

    @Override
    public boolean isValid(JsonObject jsonObject) {
        final JsonValue idValue = jsonObject.get("id");
        final JsonValue nameValue = jsonObject.get("name");
        if (idValue == null || idValue.isNull()) {
            return false;
        }
        if (nameValue == null || nameValue.isNull()) {
            return false;
        }
        return true;
    }

    @Override
    public Item run(JsonObject jsonObject) throws OneDriveRuntimeException {
        return new Item(jsonObject);
    }
}
