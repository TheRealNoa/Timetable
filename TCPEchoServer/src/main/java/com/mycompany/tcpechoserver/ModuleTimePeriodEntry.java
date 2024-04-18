package com.mycompany.tcpechoserver;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
