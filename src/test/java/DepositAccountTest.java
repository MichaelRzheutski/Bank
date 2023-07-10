import org.testng.Assert;
import org.testng.annotations.Test;

public class DepositAccountTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final double MONEY_TO_DEPOSIT = 500;

    private static double depositAccount(Bank bank, double amountOfMoney) {
        double money;
        Transaction transaction;

        System.out.println(ANSI_GREEN + "Введите сумму пополнения:" + ANSI_RESET);
//        money = scanner.nextDouble();
        money = amountOfMoney;

        transaction = new Transaction(true, money, "");

        // Сэтаем активного пользователя, админа
        bank.setActiveUser(new User("admin", "admin"));

        bank.getActiveUser().setBalance(
//                transaction.performTransaction(bank.getActiveUser().getBalance())
                transaction.performTransaction(bank.getActiveUser().getBalance())
        );

        bank.getActiveUser().getTransactions().add(transaction);

        System.out.println(ANSI_GREEN + "Счёт пополнен успешно!" + ANSI_RESET + "\n");

        return amountOfMoney;
    }

    @Test
    public void depositAccountTest() {
        Main.createAdmin(bank);
        double depositedSumOfMoney = depositAccount(bank, MONEY_TO_DEPOSIT);
        bank.getActiveUser().listOfTransactions();

        Assert.assertEquals(depositedSumOfMoney, MONEY_TO_DEPOSIT);
    }
}
