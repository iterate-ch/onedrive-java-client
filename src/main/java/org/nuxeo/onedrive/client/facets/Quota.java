package org.nuxeo.onedrive.client.facets;

import com.eclipsesource.json.JsonObject;
import org.nuxeo.onedrive.client.interfaces.Facet;

/**
 * @apiNote https://dev.onedrive.com/facets/quotainfo_facet.htm
 */
public class Quota implements Facet {
    private final long total;
    private final long used;
    private final long remaining;
    private final long deleted;
    private final State state;

    public Quota(JsonObject facetObject) {
        this.total = facetObject.get("total").asLong();
        this.used = facetObject.get("used").asLong();
        this.remaining = facetObject.get("remaining").asLong();
        this.deleted = facetObject.get("deleted").asLong();
        this.state = State.valueOf(facetObject.get("state").asString());
    }

    /**
     * @return Total allowed storage space, in bytes.
     */
    public long getTotal() {
        return total;
    }

    /**
     * @return Total space used, in bytes.
     */
    public long getUsed() {
        return used;
    }

    /**
     * @return Total space remaining before reaching the quota limit, in bytes.
     */
    public long getRemaining() {
        return remaining;
    }

    /**
     * @return Total space consumed by files in the recycle bin, in bytes.
     */
    public long getDeleted() {
        return deleted;
    }

    /**
     * @return Enumeration value that indicates the state of the storage space.
     */
    public State getState() {
        return state;
    }

    public enum State {
        /**
         * The drive has plenty of remaining quota left.
         */
        normal,
        /**
         * Remaining quota is less than 10% of total quota space.
         */
        nearing,
        /**
         * Remaining quota is less than 1% of total quota space.
         */
        critical,
        /**
         * The used quota has exceeded the total quota. New files or folders cannot be added to the drive until it is under the total quota amount or more storage space is purchased.
         */
        exceeded
    }
}
