package in.ishankhanna.salesconverter.data.model;

/**
 * Created by ishan on 03/04/16.
 */
public class Transaction {

    private double amount;
    private String sku;
    private String currency;
    private double amountInGBP;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmountInGBP() {
        return amountInGBP;
    }

    public void setAmountInGBP(double amountInGBP) {
        this.amountInGBP = amountInGBP;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", sku='" + sku + '\'' +
                ", currency='" + currency + '\'' +
                ", amountInGBP=" + amountInGBP +
                '}';
    }
}
