class Stock {
    public String symbol;
    public int totalShares;
    public double totalCost;

    public Stock(String theSymbol) {
        symbol = theSymbol;
        totalShares = 0;
        totalCost = 0.0;
    }

    // public Stock(){

    // }

    public int getTotalShares() {
        return totalShares;
    }

    public double getProfit(double currentPrice) {
        double marketValue = totalShares * currentPrice;
        return marketValue - totalCost;
    }

    public void purchase(int shares, double pricePerShare) {
        totalShares += shares;
        totalCost += shares * pricePerShare;
    }
    public static void main(String[] args) {
        Stock s = new Stock();
    }
}