package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.*;

import java.net.URL;
import java.util.Iterator;
import java.util.Objects;

public class OneDriveIterator<E> implements Iterator<E> {
    private final OneDriveAPI api;
    private final URL endPoint;
    private final ObjectCheck objectCheck;
    private final ObjectWorker<E> objectWorker;
    private final JsonObjectIterator jsonObjectIterator;

    public OneDriveIterator(OneDriveAPI api, URL endPoint, ObjectCheck objectCheck, ObjectWorker<E> objectWorker) {
        this.api = Objects.requireNonNull(api);
        this.endPoint = Objects.requireNonNull(endPoint);
        this.objectCheck = objectCheck;
        this.objectWorker = objectWorker;
        this.jsonObjectIterator = new JsonObjectIterator(api, endPoint);
    }

    @Override
    public boolean hasNext() throws OneDriveRuntimeException {
        return jsonObjectIterator.hasNext();
    }

    @Override
    public E next() throws OneDriveRuntimeException {
        JsonObject nextObject = jsonObjectIterator.next();

        if (!objectCheck.isValid(nextObject)) {
            throw new OneDriveRuntimeException(new OneDriveAPIException("The object type is currently not handled"));
        }

        return objectWorker.run(nextObject);
    }

    public interface ObjectCheck {
        boolean isValid(JsonObject jsonObject);
    }

    public interface ObjectWorker<E> {
        E run(JsonObject jsonObject) throws OneDriveRuntimeException;
    }
}