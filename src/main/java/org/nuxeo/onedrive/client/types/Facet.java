package org.nuxeo.onedrive.client.types;

public abstract class Facet<T extends Facet<T>> extends GraphType<T> {
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("Facet");
		builder.append(" { ");
		if (printMembers(builder)) {
			builder.append(' ');
		}
		builder.append('}');
		return builder.toString();
	}
}
