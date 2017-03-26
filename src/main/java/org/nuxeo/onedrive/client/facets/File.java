package org.nuxeo.onedrive.client.facets;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.interfaces.Facet;

public class File implements Facet {
    private final String mimeType;
    private final boolean processingMetadata;

    public File(JsonObject jsonObject) {
        mimeType = jsonObject.get("mimeType").asString();
        // ignore (for the moment) Hashes!
        processingMetadata = jsonObject.get("processingMetadata").asBoolean();
    }

    public String getMimeType() {
        return mimeType;
    }

    public boolean isProcessingMetadata() {
        return processingMetadata;
    }
}
