package org.nuxeo.onedrive.client;

import org.nuxeo.onedrive.client.builders.RequestImpl;
import org.nuxeo.onedrive.client.interfaces.Request;

public final class OneDriveBuilder {
    private OneDriveBuilder() {
    }

    public static Request createRequest() {
        return new RequestImpl();
    }
}
