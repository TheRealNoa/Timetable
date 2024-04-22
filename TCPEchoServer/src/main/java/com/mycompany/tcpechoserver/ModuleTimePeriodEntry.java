package com.mycompany.tcpechoserver;

/**
 *
 * @author noaca
 */
public class ModuleTimePeriodEntry {
    private Module module;
    private TimePeriod timePeriod;

    public ModuleTimePeriodEntry(Module module, TimePeriod timePeriod) {
        this.module = module;
        this.timePeriod = timePeriod;
    }

    // Getters and setters for module and timePeriod

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
