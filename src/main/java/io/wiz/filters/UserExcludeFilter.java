package io.wiz.filters;

import io.wiz.models.FirewallLog;

public class UserExcludeFilter extends UserIncludeFilter {
    public UserExcludeFilter(String pattern) {
        super("^"+pattern);
    }

    @Override
    public boolean filter(FirewallLog log) {
        return super.filter(log);
    }
}
