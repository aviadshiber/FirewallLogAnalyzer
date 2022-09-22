package io.wiz.filters;

import io.wiz.models.FirewallLog;

public class UserIncludeFilter implements LogFilter {

    public UserIncludeFilter(String pattern) {
        //todo: filter with regex pattern on log
    }

    @Override
    public boolean filter(FirewallLog log) {
        return false;
    }
}
