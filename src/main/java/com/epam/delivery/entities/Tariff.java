package com.epam.delivery.entities;

public class Tariff {
    private double weight;
    private int priceUpTo500km;
    private int priceUpTo700km;
    private int priceUpTo900km;
    private int priceUpTo1200km;
    private int priceUpTo1500km;

    private Tariff(double weight, int priceUpTo500km, int priceUpTo700km, int priceUpTo900km, int priceUpTo1200km, int priceUpTo1500km) {
        this.weight = weight;
        this.priceUpTo500km = priceUpTo500km;
        this.priceUpTo700km = priceUpTo700km;
        this.priceUpTo900km = priceUpTo900km;
        this.priceUpTo1200km = priceUpTo1200km;
        this.priceUpTo1500km = priceUpTo1500km;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPriceUpTo500km() {
        return priceUpTo500km;
    }

    public void setPriceUpTo500km(int priceUpTo500km) {
        this.priceUpTo500km = priceUpTo500km;
    }

    public int getPriceUpTo700km() {
        return priceUpTo700km;
    }

    public void setPriceUpTo700km(int priceUpTo700km) {
        this.priceUpTo700km = priceUpTo700km;
    }

    public int getPriceUpTo900km() {
        return priceUpTo900km;
    }

    public void setPriceUpTo900km(int priceUpTo900km) {
        this.priceUpTo900km = priceUpTo900km;
    }

    public int getPriceUpTo1200km() {
        return priceUpTo1200km;
    }

    public void setPriceUpTo1200km(int priceUpTo1200km) {
        this.priceUpTo1200km = priceUpTo1200km;
    }

    public int getPriceUpTo1500km() {
        return priceUpTo1500km;
    }

    public void setPriceUpTo1500km(int priceUpTo1500km) {
        this.priceUpTo1500km = priceUpTo1500km;
    }

    public static Tariff createTariff(double weight, int priceUpTo500km, int priceUpTo700km, int priceUpTo900km,
                                      int priceUpTo1200km, int priceUpTo1500km) {
        return new Tariff(weight, priceUpTo500km, priceUpTo700km, priceUpTo900km,
                priceUpTo1200km, priceUpTo1500km);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "weight=" + weight +
                ", priceUpTo500km=" + priceUpTo500km +
                ", priceUpTo700km=" + priceUpTo700km +
                ", priceUpTo900km=" + priceUpTo900km +
                ", priceUpTo1200km=" + priceUpTo1200km +
                ", priceUpTo1500km=" + priceUpTo1500km +
                '}';
    }
}
