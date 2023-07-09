import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

class User {
    int id;
    static int counter;
    private boolean isAdmin;
    private String login;
    private String password;
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();

    User(String login, String password) {
        this.id = counter++;
        this.isAdmin = false;
        this.login = login;
        this.password = password;
    }

    // Список транзакций
    public void listOfTransactions() {
        System.out.println("\nСписок транзакций:");

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType()) {
                if (transaction.getRecipient().isEmpty()) {
                    System.out.printf(
                            "%s | Пополнение         | + %.2f руб.\n",
                            getTransactionDateTime(), transaction.getAmount()
                    );
                } else {
                    System.out.printf(
                            "%s | Получение перевода | + %.2f руб. | Отправитель: %s\n",
                            getTransactionDateTime(), transaction.getAmount(), transaction.getRecipient()
                    );
                }
            } else {

                if (transaction.getRecipient().contains("*")) {
                    System.out.printf(
                            "%s | Платёж             | - %.2f руб. | Получатель: %s\n",
                            getTransactionDateTime(), transaction.getAmount(), transaction.getRecipient()
                    );

                } else {
                    System.out.printf(
                            "%s | Отправка перевода  | - %.2f руб. | Получатель: %s\n",
                            getTransactionDateTime(), transaction.getAmount(), transaction.getRecipient()
                    );
                }
            }
        }
        System.out.println();
    }

    // Выводим дату и время транзакции
    private static String getTransactionDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now(
                ZoneId.of("Europe/Minsk")
        );

        return localDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.MEDIUM, FormatStyle.MEDIUM
                )
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
