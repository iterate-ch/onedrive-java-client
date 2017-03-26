package org.nuxeo.onedrive.client.facets;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.interfaces.Facet;

public class SpecialFolder implements Facet {
    private final String name;

    public SpecialFolder(JsonObject facetObject) {
        name = facetObject.get("name").asString();
    }

    public String getName() {
        return name;
    }
}
