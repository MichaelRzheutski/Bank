public class Transaction {
    private Boolean transactionType;
    private Double amount;
    private String recipient;

    Transaction(Boolean transactionType, Double amount, String recipient) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.recipient = recipient;
    }

    public double performTransaction(double balance) {
        double newBalance;

        if (transactionType) {
            newBalance = balance + amount;
        } else {
            newBalance = balance - amount;
        }
        return newBalance;
    }

    public void transferMoney(User sender, User recipient) {
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        Transaction transaction = new Transaction(true, amount, sender.getLogin());
        recipient.getTransactions().add(transaction);
    }

    public Boolean getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Boolean transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

}
