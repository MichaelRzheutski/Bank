import org.testng.Assert;
import org.testng.annotations.Test;

public class SendMoneyTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет
    public static final String RECIPIENT_LOGIN = "user1";
    public static final String RECIPIENT_PASSWORD = "543968";
    public static final double AMOUNT_OF_MONEY = 50;

    public boolean createUser() {
//        Scanner scanner = new Scanner(System.in);

//        System.out.print("\nПожалуйста, введите имя аккаунта: ");
//        String enteredLogin = scanner.nextLine();

//        System.out.print("Придумайте пароль: ");
//        String enteredPassword = scanner.nextLine();

        // Проверка, используется ли уже такое имя
        User selUser = bank.findUser(RECIPIENT_LOGIN);
        if (selUser != null) {
            System.out.println(
                    ANSI_RED + "Аккаунт с таким именем уже существует!" + ANSI_RESET + "\n"
            );
            return false;
        }

        // Создать нового пользователя
        User user = new User(RECIPIENT_LOGIN, RECIPIENT_PASSWORD);
        user.setBalance(100);
        bank.getUsers().add(user);
        System.out.println(
                ANSI_GREEN + "Аккаунт " + RECIPIENT_LOGIN +
                        " успешно зарегистрирован в системе. Присвоен ID: " +
                        user.getId() + ANSI_RESET + "\n"
        );

        return true;
    }

    private static boolean sendMoney(Bank bank, String userRecipient, double sum) {
        Transaction transaction;
        String recipient;
        double money;
//        System.out.println(ANSI_GREEN + "Введите сумму перевода:" + ANSI_RESET);
//        money = scanner2.nextDouble();
        money = sum;

//        System.out.println(ANSI_GREEN + "Введите получателя:" + ANSI_RESET);
//        recipient = scanner.nextLine();
        recipient = userRecipient;

        User user = bank.findUser(recipient);
        if (user != null) {
            transaction = new Transaction(false, money, recipient);

            // Сэтаем активного пользователя, user1
            bank.setActiveUser(new User(RECIPIENT_LOGIN, RECIPIENT_PASSWORD));

            transaction.transferMoney(bank.getActiveUser(), user);
            bank.getActiveUser().getTransactions().add(transaction);
            System.out.println(
                    ANSI_GREEN + "Операция проведена успешно!" + ANSI_RESET + "\n"
            );
            return true;
        } else {
            System.out.println(
                    ANSI_RED + "Аккаунт " + recipient + " не зарегистрирован в системе" +
                            ANSI_RESET + "\n"
            );
            return false;
        }
    }

    @Test
    public void sendMoneyTest() {
        Main.createAdmin(bank);
        createUser();
        boolean isMoneyTransferred = sendMoney(bank, RECIPIENT_LOGIN, AMOUNT_OF_MONEY);
        bank.getActiveUser().listOfTransactions();

        Assert.assertTrue(isMoneyTransferred);
    }

}
