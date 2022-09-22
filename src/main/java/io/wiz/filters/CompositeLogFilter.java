package io.wiz.filters;

import io.wiz.models.FirewallLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeLogFilter implements LogFilter {
    final List<LogFilter> filters;
    public CompositeLogFilter(LogFilter... logFilters) {
        this.filters = new ArrayList<>();
        add(logFilters);
    }

    public void add(LogFilter f){
        filters.add(f);
    }
    public void add(LogFilter ... fs) {
        this.filters.addAll(Arrays.asList(fs));
    }

    public void remove(LogFilter f){
        this.filters.remove(f);
    }
    public void remove(LogFilter ... fs){
        this.filters.removeAll(Arrays.asList(fs));
    }
    @Override
    public boolean filter(FirewallLog log) {
        for (LogFilter f : filters) {
            if(!f.filter(log)){
                return false;
            }
        }
        return true;
    }
}
