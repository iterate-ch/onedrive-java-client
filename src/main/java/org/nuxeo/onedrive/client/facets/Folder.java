package org.nuxeo.onedrive.client.facets;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.interfaces.Facet;

public class Folder implements Facet {
    private final long childCount;

    public Folder(JsonObject facetObject) {
        childCount = facetObject.get("childCount").asLong();
    }

    public long getChildCount() {
        return childCount;
    }
}
