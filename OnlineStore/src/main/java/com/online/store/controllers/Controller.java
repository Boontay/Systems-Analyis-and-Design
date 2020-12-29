package com.online.store.controllers;

import com.online.store.services.Service;

public class Controller {
    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
