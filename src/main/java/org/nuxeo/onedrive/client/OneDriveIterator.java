package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;

public class OneDriveIterator<E> implements Iterator<E> {
    private final OneDriveAPI api;
    private final String endPoint;
    private final ObjectCheck objectCheck;
    private final ObjectWorker<E> objectWorker;
    private final JsonObjectIterator jsonObjectIterator;

    public OneDriveIterator(OneDriveAPI api, String endPoint, ObjectCheck objectCheck, ObjectWorker<E> objectWorker) throws OneDriveRuntimeException {
        this.api = Objects.requireNonNull(api);
        this.endPoint = Objects.requireNonNull(endPoint);
        this.objectCheck = objectCheck;
        this.objectWorker = objectWorker;

        final String urlString = api.getBaseURL() + endPoint;
        final URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new OneDriveRuntimeException(new OneDriveAPIException(e.getMessage(), e));
        }

        this.jsonObjectIterator = new JsonObjectIterator(api, url);
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
