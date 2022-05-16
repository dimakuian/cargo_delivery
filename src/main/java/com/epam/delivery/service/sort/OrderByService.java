package com.epam.delivery.service.sort;

import java.util.List;

public interface OrderByService <T>{
    List <T> sort (String sortType, List<T> list);
}
