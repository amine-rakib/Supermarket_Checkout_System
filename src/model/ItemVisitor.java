package model;

public interface ItemVisitor {
    double visitFruit(FruitItem item);
    double visitDairy(DairyItem item);
    double visitMeat(MeatItem item);
}