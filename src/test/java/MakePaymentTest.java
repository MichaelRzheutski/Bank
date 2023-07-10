import org.testng.Assert;
import org.testng.annotations.Test;

public class MakePaymentTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String SHOP = "Onliner.by";
    public static final double AMOUNT_OF_MONEY = 50;

    public static double makePayment(Bank bank, String shopRecipient, double sum) {
        double money;
        Transaction transaction;
        String recipient;

//        System.out.println(ANSI_GREEN + "Введите сумму платежа:" + ANSI_RESET);
//        money = scanner2.nextDouble();
        money = sum;

//        System.out.println(ANSI_GREEN + "Введите интернет-магазин:" + ANSI_RESET);
//        recipient = "*" + scanner.nextLine();
        recipient = "*" + shopRecipient;
        transaction = new Transaction(false, money, recipient);

        // Сэтаем активного пользователя, админа
        bank.setActiveUser(new User("admin", "admin"));

        bank.getActiveUser().setBalance(
                transaction.performTransaction(bank.getActiveUser().getBalance())
        );
        bank.getActiveUser().getTransactions().add(transaction);

        System.out.println(ANSI_GREEN + "Операция проведена успешно!" + ANSI_RESET + "\n");

        return money;
    }

    @Test
    public void sendMoneyTest() {
        Main.createAdmin(bank);
        double amountOfMmoneyTransferredToTheShop = makePayment(
                bank, SHOP, AMOUNT_OF_MONEY
        );
        bank.getActiveUser().listOfTransactions();

        Assert.assertEquals(amountOfMmoneyTransferredToTheShop, AMOUNT_OF_MONEY);
    }
}
