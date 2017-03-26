package org.nuxeo.onedrive.client.builders;

import org.nuxeo.onedrive.client.interfaces.Builder;

public class RootDriveRequestImpl extends DriveRequestImpl {
    public RootDriveRequestImpl(Builder parent) {
        super(parent);
    }

    @Override
    public String resourcePath() {
        return parentContinuePath() + "/drive";
    }

    @Override
    public String continuePath() {
        return rootPath();
    }

    @Override
    public String childrenPath() {
        return rootPath() + "/children";
    }

    @Override
    public String rootPath() {
        return resourcePath() + "/root";
    }
}
