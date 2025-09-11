package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public final class CreateUploadSessionOptions extends GraphType<CreateUploadSessionOptions> {
    private DriveItemUploadableProperties item;

    public DriveItemUploadableProperties getItem() {
        return item;
    }

    public void setItem(final DriveItemUploadableProperties item) {
        this.item = item;
    }

    @Override
    protected void populateJsonObject(JsonObject jsonObject) {
        if (item != null) {
            jsonObject.set("item", item.toJson());
        }
    }
}
