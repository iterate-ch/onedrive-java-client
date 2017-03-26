package org.nuxeo.onedrive.client.facets;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.OneDriveAPIException;
import org.nuxeo.onedrive.client.OneDriveRuntimeException;
import org.nuxeo.onedrive.client.interfaces.Facet;

public class FacetFactory {
    public static Facet create(JsonObject.Member member) throws OneDriveRuntimeException {
        final JsonObject facetObject = member.getValue().asObject();
        switch (member.getName()) {
            case "file":
                return new File(facetObject);
            case "fileSystemInfo":
                return new FileSystemInfo(facetObject);
            case "folder":
                return new Folder(facetObject);
            case "quota":
                return new Quota(facetObject);
            case "remoteItem":
                return new RemoteItem(facetObject);
            case "root":
                return new Root(facetObject);
            case "specialFolder":
                return new SpecialFolder(facetObject);
            default:
                throw new OneDriveRuntimeException(new OneDriveAPIException(String.format("Facet \"%s\" is not recognized.", member.getName())));
        }
    }
}
