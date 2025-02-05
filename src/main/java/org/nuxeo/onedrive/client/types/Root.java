package org.nuxeo.onedrive.client.types;

public class Root extends Facet<Root> {
	@Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Root");
        builder.append(" { ");
        if (printMembers(builder)) {
            builder.append(' ');
        }
        builder.append('}');
        return builder.toString();
    }
}
