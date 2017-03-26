package org.nuxeo.onedrive.client.resources;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.nuxeo.onedrive.client.facets.Quota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @apiNote https://dev.onedrive.com/resources/drive.htm
 */
public class Drive {
    protected final String id;
    protected final DriveType driveType;
    protected final Quota quota;
    protected final Collection<Item> items;
    protected final Item root;
    protected final Collection<Item> special;

    public Drive(JsonObject jsonObject) {
        id = jsonObject.get("id").asString();
        driveType = DriveType.valueOf(jsonObject.get("driveType").asString());
        quota = new Quota(jsonObject.get("quota").asObject());
        root = new Item(jsonObject.get("root").asObject());

        final Collection<Item> items = new ArrayList<>();
        for (JsonValue value : jsonObject.get("items").asArray()) {
            items.add(new Item(value.asObject()));
        }
        this.items = Collections.unmodifiableCollection(items);

        final Collection<Item> special = new ArrayList<>();
        for (JsonValue value : jsonObject.get("special").asArray()) {
            special.add(new Item(value.asObject()));
        }
        this.special = Collections.unmodifiableCollection(special);
    }

    public String getId() {
        return id;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public Quota getQuota() {
        return quota;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public Item getRoot() {
        return root;
    }

    public Collection<Item> getSpecial() {
        return special;
    }

    /**
     * Defines a drives type
     */
    public enum DriveType {
        /**
         * OneDrive Personal
         */
        Personal,
        /**
         * OneDrive Business
         */
        Business,
        /**
         * SharePoint
         */
        DocumentLibrary
    }
}
