import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет

    public static final String EXPECTED_NEW_USER_NAME = "user1";
    public static final String EXPECTED_NEW_USER_PASSWORD = "543968";

    public boolean createUser() {
//        Scanner scanner = new Scanner(System.in);

//        System.out.print("\nПожалуйста, введите имя аккаунта: ");
//        String enteredLogin = scanner.nextLine();

//        System.out.print("Придумайте пароль: ");
//        String enteredPassword = scanner.nextLine();

        // Проверка, используется ли уже такое имя
        User selUser = bank.findUser(EXPECTED_NEW_USER_NAME);
        if (selUser != null) {
            System.out.println(
                    ANSI_RED + "Аккаунт с таким именем уже существует!" + ANSI_RESET + "\n"
            );
            return false;
        }

        // Создать нового пользователя
        User user = new User(EXPECTED_NEW_USER_NAME, EXPECTED_NEW_USER_PASSWORD);
        user.setBalance(100);
        bank.getUsers().add(user);
        System.out.println(
                ANSI_GREEN + "Аккаунт " + EXPECTED_NEW_USER_NAME +
                        " успешно зарегистрирован в системе. Присвоен ID: " +
                        user.getId() + ANSI_RESET + "\n"
        );

        return true;
    }

    @Test
    public void createUserTest() {
        Assert.assertTrue(createUser());
    }
}
