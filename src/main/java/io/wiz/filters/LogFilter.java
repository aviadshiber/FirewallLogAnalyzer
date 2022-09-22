package io.wiz.filters;

import io.wiz.models.FirewallLog;

public interface LogFilter {
    boolean filter(FirewallLog log);
}
