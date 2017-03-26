package org.nuxeo.onedrive.client.resources;

import com.eclipsesource.json.JsonObject;

/**
 * @apiNote https://dev.onedrive.com/resources/item.htm
 */
public class Item {


    public Item(JsonObject jsonObject) {
        for (JsonObject.Member member : jsonObject) {
        }
    }
}
