package org.nuxeo.onedrive.client.iterators;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.nuxeo.onedrive.client.OneDriveAPI;
import org.nuxeo.onedrive.client.OneDriveIterator;
import org.nuxeo.onedrive.client.OneDriveRuntimeException;
import org.nuxeo.onedrive.client.resources.Drive;

import java.util.Iterator;

public class DriveIterator implements Iterator<Drive>, OneDriveIterator.ObjectCheck, OneDriveIterator.ObjectWorker<Drive> {
    private final OneDriveIterator<Drive> driveIterator;

    public DriveIterator(OneDriveAPI api) {
        driveIterator = new OneDriveIterator<>(api, "/drives", this, this);
    }

    @Override
    public boolean hasNext() {
        return driveIterator.hasNext();
    }

    @Override
    public Drive next() {
        return driveIterator.next();
    }

    @Override
    public boolean isValid(JsonObject jsonObject) {
        final JsonValue idValue = jsonObject.get("id");
        final JsonValue driveTypeValue = jsonObject.get("driveType");
        if (idValue == null || idValue.isNull()) {
            return false;
        }
        if (driveTypeValue == null || driveTypeValue.isNull()) {
            return false;
        }
        return true;
    }

    @Override
    public Drive run(JsonObject jsonObject) throws OneDriveRuntimeException {
        return new Drive(jsonObject);
    }
}
