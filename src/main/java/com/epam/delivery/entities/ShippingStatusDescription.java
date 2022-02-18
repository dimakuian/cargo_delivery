package com.epam.delivery.entities;

import com.epam.delivery.doa.SimpleConnection;
import com.epam.delivery.doa.impl.LanguageDao;
import com.epam.delivery.doa.impl.ShippingStatusDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShippingStatusDescription implements Serializable {
    private static final long serialVersionUID = -9102794775682737593L;

    private ShippingStatus shippingStatus;
    private Map<Language, String> description;

    private ShippingStatusDescription(ShippingStatus shippingStatus, Map<Language, String> description) {
        this.shippingStatus = shippingStatus;
        this.description = description;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Map<Language, String> getDescription() {
        return description;
    }

    public void setDescription(Map<Language, String> description) {
        this.description = description;
    }

    public static ShippingStatusDescription create(ShippingStatus shippingStatus, Map<Language, String> description) {
        return new ShippingStatusDescription(shippingStatus, description);
    }

    @Override
    public String toString() {
        return "ShippingStatusDescription{" +
                "shippingStatus=" + shippingStatus +
                ", description=" + description +
                '}';
    }

    public static void main(String[] args) {
        LanguageDao languageDao = new LanguageDao(SimpleConnection.getConnection());
        Language en = languageDao.findById(1).get();
        Language ua = languageDao.findById(2).get();

        ShippingStatusDao shippingStatusDao = new ShippingStatusDao(SimpleConnection.getConnection());
        ShippingStatus shippingStatus = shippingStatusDao.findById(1).get();

        System.out.println(ua + " " + en + " " + shippingStatus);
        Map<Language, String> map = new HashMap<>();
        map.put(en, shippingStatus.getName());
        map.put(ua, "створений");
        System.out.println(map);
        Language en1 = getLanguageId(map, "EN");
        System.out.println(en1);
        System.out.println(map.get(en1));
    }

    private static Language getLanguageId(Map<Language, String> map, String shortName) {
        Language lang = map.entrySet().stream().map(Map.Entry::getKey)
                .filter(language -> language.getShortName().equals(shortName))
                .findFirst().get();
        return lang;
    }
}
