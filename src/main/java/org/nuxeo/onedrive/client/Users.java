package org.nuxeo.onedrive.client;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.types.GroupItem;
import org.nuxeo.onedrive.client.types.User;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

public class Users {

    public static User.Metadata get(final User user, final ODataQuery query) throws IOException {
        final URL url = new URLTemplate(user.getPath()).build(user.getApi().getBaseURL(), query);
        final OneDriveJsonRequest request = new OneDriveJsonRequest(url, "GET");
        final OneDriveJsonResponse response = request.sendRequest(user.getApi().getExecutor());
        final JsonObject jsonObject = response.getContent();
        response.close();
        return user.new Metadata(jsonObject);
    }
    
    public static Iterator<GroupItem.Metadata> memberOfGroups(final User user) {
        return new GroupItemIterator(user.getApi(),
            new URLTemplate(user.getOperationPath("/memberOf/$/microsoft.graph.group")).build(user.getApi().getBaseURL()));
    }

    private final static class GroupItemIterator implements Iterator<GroupItem.Metadata> {
        private final OneDriveAPI api;
        private final JsonObjectIterator iterator;

        public GroupItemIterator(final OneDriveAPI api, final URL url) {
            this.api = api;
            this.iterator = new JsonObjectIterator(api, url);
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public GroupItem.Metadata next() {
            return GroupItem.fromJson(api, iterator.next());
        }
    }
}
