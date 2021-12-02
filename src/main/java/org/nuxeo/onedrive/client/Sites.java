package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.types.Site;

import java.net.URL;
import java.util.Iterator;

public final class Sites {
    public static Iterator<Site.Metadata> getSites(final Site site, final Site.Select... expand) {
        return new SitesIterator(site.getApi(), createSitesUrl(site, expand));
    }

    public static Iterator<Site.Metadata> getSites(final OneDriveAPI api, final Site.Select... expand) {
        return new SitesIterator(api, createSitesUrl(api, "", expand));
    }

    public static Iterator<Site.Metadata> getSites(final OneDriveAPI api, final String search, final Site.Select... expand) {
        final QueryStringBuilder qs = new QueryStringBuilder()
                .set("search", search)
                .set("expand", expand);
        return new SitesIterator(api, createSitesUrl(api, qs, ""));
    }

    private static URL createSitesUrl(final Site site, final Site.Select... expand) {
        return new URLTemplate(site.getAction("/sites")).build(site.getApi().getBaseURL(),
                new QueryStringBuilder().set("expand", expand));
    }

    private static URL createSitesUrl(final OneDriveAPI api, final String basePath, final Site.Select... expand) {
        return createSitesUrl(api, new QueryStringBuilder().set("expand", expand), basePath);
    }

    private static URL createSitesUrl(final OneDriveAPI api, final QueryStringBuilder qs, final String basePath) {
        return new URLTemplate(createSitesPath(basePath)).build(api.getBaseURL(), qs);
    }

    private static String createSitesPath(final String basePath) {
        return basePath + "/sites";
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
