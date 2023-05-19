package com.ks.transactions.entities;

public enum Status {

    PENDIENTE("01", "Pendiente"),
    LIQUIDADA("02", "Liquidada"),
    RECHAZADA("03", "Rechazada"),
    CANCELADA("04", "Cancelada");

    private String code;
    private String description;

    Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
