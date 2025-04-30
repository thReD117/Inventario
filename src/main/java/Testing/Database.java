package Testing;

import Logic.Stock;

public class Database {
    public static void main(String[] args) {

        Stock stock = new Stock();
//        stock.add(new Superior("M", "Algodon", "Rojo", "Sueter", 300, 1, Season.WINTER, Gender.UNISEX, CategorySup.SWEATERS));

        stock.loadFromDB();
        stock.listFullStock().forEach(System.out::println);
    }
}
