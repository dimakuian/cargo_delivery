package com.epam.delivery.db.entities;

import java.io.Serializable;

public enum InvoiceStatus implements Serializable {
    CREATED, PAID, DECLINED;

    public static InvoiceStatus getInvoiceStatus(Invoice invoice) {
        int statusID = invoice.getInvoiceStatusID();
        return InvoiceStatus.values()[statusID];

    }

    public String getName() {
        return name().toLowerCase();
    }
}
