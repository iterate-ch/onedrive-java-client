package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.types.Site;

import java.net.URL;
import java.util.Iterator;

public final class Sites {
    private static String createSitesPath(final String basePath) {
        return basePath + "/sites";
    }

    private static URL createSitesUrl(final OneDriveAPI api, final String basePath, final ODataQuery query) {
        return new URLTemplate(createSitesPath(basePath)).build(api.getBaseURL(), query);
    }

    private static URL createSitesUrl(final Site site, final ODataQuery query) {
        return new URLTemplate(site.getAction("/sites")).build(site.getApi().getBaseURL(), query);
    }

    public static Iterator<Site.Metadata> getSites(final OneDriveAPI api, final ODataQuery query) {
        return new SitesIterator(api, createSitesUrl(api, "", query));
    }

    public static Iterator<Site.Metadata> getSites(final Site site, final ODataQuery query) {
        return new SitesIterator(site.getApi(), createSitesUrl(site, query));
    }

    private static class SitesIterator implements Iterator<Site.Metadata> {
        private final OneDriveAPI api;
        private final JsonObjectIterator iterator;

        public SitesIterator(final OneDriveAPI api, final URL url) {
            this.api = api;
            iterator = new JsonObjectIterator(api, url);
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Site.Metadata next() {
            final JsonObject jsonObject = iterator.next();
            final String id = jsonObject.get("id").asString();

            return Site.byId(api, id).new Metadata().fromJson(jsonObject);
        }
    }
}
