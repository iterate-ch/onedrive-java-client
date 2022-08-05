package org.nuxeo.onedrive.client;

import java.util.*;

public class ODataQuery {
    private final QueryStringBuilder builder = new QueryStringBuilder();
    private final Map<String, Set<QueryStringCommaParameter>> cache = new HashMap<>();

    public static String get(final ODataQuery query) {
        return Optional.ofNullable(query).map(ODataQuery::build).map(QueryStringBuilder::toString).orElse("");
    }

    public QueryStringBuilder build() {
        for (Map.Entry<String, Set<QueryStringCommaParameter>> entry : cache.entrySet()) {
            builder.set(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    public ODataQuery expand(QueryStringCommaParameter value) {
        final Set<QueryStringCommaParameter> set = getSet("$expand");
        set.add(value);
        return this;
    }

    public ODataQuery expand(QueryStringCommaParameter... values) {
        final Set<QueryStringCommaParameter> set = getSet("$expand");
        set.addAll(Arrays.asList(values));
        return this;
    }

    public ODataQuery expand(Set<QueryStringCommaParameter> values) {
        final Set<QueryStringCommaParameter> set = getSet("$expand");
        set.addAll(values);
        return this;
    }

    public ODataQuery filter(String filter) {
        builder.set("$filter", filter);
        return this;
    }

    private Set<QueryStringCommaParameter> getSet(final String key) {
        return Optional.ofNullable(cache.get(key)).orElseGet(() -> {
            final Set<QueryStringCommaParameter> set = new HashSet<>();
            cache.put(key, set);
            return set;
        });
    }

    public ODataQuery search(String search) {
        builder.set("$search", search);
        return this;
    }

    public ODataQuery select(QueryStringCommaParameter value) {
        final Set<QueryStringCommaParameter> set = getSet("$select");
        set.add(value);
        return this;
    }

    public ODataQuery select(QueryStringCommaParameter... values) {
        final Set<QueryStringCommaParameter> set = getSet("$select");
        set.addAll(Arrays.asList(values));
        return this;
    }

    public ODataQuery select(Set<QueryStringCommaParameter> values) {
        final Set<QueryStringCommaParameter> set = getSet("$select");
        set.addAll(values);
        return this;
    }

    public ODataQuery set(final String key, final String value) {
        builder.set(key, value);
        return this;
    }

    public ODataQuery set(final String key, QueryStringCommaParameter value) {
        final Set<QueryStringCommaParameter> set = getSet("$expand");
        set.add(value);
        return this;
    }

    public ODataQuery set(final String key, QueryStringCommaParameter... values) {
        final Set<QueryStringCommaParameter> set = getSet("$expand");
        set.addAll(Arrays.asList(values));
        return this;
    }

    public ODataQuery set(final String key, Set<QueryStringCommaParameter> values) {
        final Set<QueryStringCommaParameter> set = getSet("$expand");
        set.addAll(values);
        return this;
    }

    public ODataQuery skip(int skip) {
        builder.set("$skip", skip);
        return this;
    }

    public ODataQuery top(int top) {
        builder.set("$top", top);
        return this;
    }
}
