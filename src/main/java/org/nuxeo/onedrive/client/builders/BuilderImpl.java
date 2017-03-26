package org.nuxeo.onedrive.client.builders;

import org.nuxeo.onedrive.client.interfaces.Builder;

public abstract class BuilderImpl implements Builder {
    private final Builder parent;

    public BuilderImpl(Builder parent) {
        this.parent = parent;
    }

    @Override
    public Builder getParent() {
        return parent;
    }

    @Override
    public String resourcePath() {
        return "";
    }

    @Override
    public String continuePath() {
        return resourcePath();
    }

    protected String parentResourcePath() {
        if (getParent() != null) {
            return getParent().resourcePath();
        }
        return "";
    }

    protected String parentContinuePath() {
        if (getParent() != null) {
            return getParent().continuePath();
        }
        return "";
    }
}
