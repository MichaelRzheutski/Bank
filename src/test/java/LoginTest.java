import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет

    public static final String EXPECTED_USER_NAME = "admin";
    public static final String EXPECTED_USER_PASSWORD = "admin";

    public boolean login() {
//        Scanner scanner = new Scanner(System.in);

//        System.out.print("\nПожалуйста, введите ваше имя: ");
//        String enteredLogin = scanner.nextLine();

//        System.out.print("Введите пароль: ");
//        String enteredPassword = scanner.nextLine();

                // Проверка, если пользователь вышел
        User user = bank.findUser(EXPECTED_USER_NAME);
        if (user == null) {
            System.out.println(
                    ANSI_RED + "Аккаунт " + EXPECTED_USER_NAME +
                            " не зарегистрирован в системе" + ANSI_RESET + "\n"
            );
            return false;
        }

        // Проверка, если пароль неверный
        if (!user.getPassword().equals(EXPECTED_USER_PASSWORD)) {
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
