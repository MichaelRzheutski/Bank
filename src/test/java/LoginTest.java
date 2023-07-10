import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет

    public final String EXPECTED_USER_NAME = "admin";
    public final String EXPECTED_USER_PASSWORD = "admin";

    public boolean login() {
//        Scanner scanner = new Scanner(System.in);

//        System.out.print("\nПожалуйста, введите ваше имя: ");
//        String enteredLogin = scanner.nextLine();
        String enteredLogin = EXPECTED_USER_NAME;

//        System.out.print("Введите пароль: ");
//        String enteredPassword = scanner.nextLine();
        String enteredPassword = EXPECTED_USER_PASSWORD;

                // Проверка, если пользователь вышел
        User user = bank.findUser(enteredLogin);
        if (user == null) {
            System.out.println(
                    ANSI_RED + "Аккаунт " + enteredLogin + " не зарегистрирован в системе" + ANSI_RESET + "\n"
            );
            return false;
        }

        // Проверка, если пароль неверный
        if (!user.getPassword().equals(enteredPassword)) {
            System.out.println(ANSI_RED + "Неверный пароль!" + ANSI_RESET + "\n");
            return false;
        }
        bank.setActiveUser(user);
        return true;
    }

    @Test
    public void loginTest() {
        Main.createAdmin(bank);
        boolean actualLoginResult = login();

        Assert.assertTrue(actualLoginResult);
    }
}
