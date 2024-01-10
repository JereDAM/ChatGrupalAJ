package net.salesianos.shared.models;

import java.io.Serializable;

public class ClientModel implements Serializable{
    private String name;

    public ClientModel() {
    }

    public ClientModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClientModel [name=" + name + "]";
    }

    
}
