package model;

public class PriceCalculatorVisitor implements ItemVisitor {
    @Override
    public double visitFruit(FruitItem item) {
        double basePrice = item.getUPrice() * item.getQuantity();
        return item.isOrganic() ? basePrice * 0.85 : basePrice;
    }

    @Override
    public double visitDairy(DairyItem item) {
        double basePrice = item.getUPrice() * item.getQuantity();
        return item.getDaysUntilExpiration() < 2 ? basePrice * 0.70 : basePrice;
    }

    @Override
    public double visitMeat(MeatItem item) {
        double basePrice = item.getUPrice() * item.getQuantity();
        return !item.isFresh() ? basePrice * 0.90 : basePrice;
    }
}