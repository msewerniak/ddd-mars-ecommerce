package com.mars.ecommerce.sales.domain.client;

public class Client {

    private final ClientId id;
    private String name;

    public Client(ClientId clientId, String name) {
        this.id = clientId;
        updateName(name);
    }

    public void updateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Cannot set blank name");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Cannot set name longer than 50 characters");
        }
        this.name = name;
    }

    public ClientSnapshot snapshot() {
        return new ClientSnapshot(id.id(), name);
    }
}
