package org.nuxeo.onedrive.client.builders;

import org.nuxeo.onedrive.client.interfaces.Builder;

public class IdDriveRequestImpl extends DriveRequestImpl{
    private final String id;

    public IdDriveRequestImpl(Builder parent, String id) {
        super(parent);
        this.id = id;
    }

    @Override
    public String resourcePath() {
        return parentContinuePath() + "/drives/" + id;
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
