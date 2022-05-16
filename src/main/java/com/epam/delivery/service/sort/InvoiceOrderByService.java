package com.epam.delivery.service.sort;

import com.epam.delivery.db.entities.Entity;
import com.epam.delivery.db.entities.Invoice;

import java.util.Comparator;
import java.util.List;

public class InvoiceOrderByService implements OrderByService<Invoice> {

    @Override
    public List<Invoice> sort(String sortType, List<Invoice> list) {
        switch (sortType) {
            case "id ASC":
                list.sort(Comparator.comparingLong(Entity::getId));
                break;
            case "id DESC":
                list.sort(Comparator.comparingLong(Entity::getId).reversed());
                break;
            case "date ASC":
                list.sort(Comparator.comparing(Invoice::getCreationDatetime));
                break;
            case "date DESC":
                list.sort(Comparator.comparing(Invoice::getCreationDatetime).reversed());
                break;
            case "sum ASC":
                list.sort(Comparator.comparing(Invoice::getSum));
                break;
            case "sum DESC":
                list.sort(Comparator.comparing(Invoice::getSum).reversed());
                break;
            case "status ASC":
                list.sort(Comparator.comparing(Invoice::getInvoiceStatusID));
                break;
            case "status DESC":
                list.sort(Comparator.comparing(Invoice::getInvoiceStatusID).reversed());
                break;
        }
        return list;
    }
}
