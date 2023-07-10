import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

public class SetUserStatusTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет
    public static final String USER_TO_CHANGE_STATUS = "user1";
    public static final String USER_TO_CHANGE_STATUS_PASSWORD = "weyt546";
    public static final boolean ADMIN_STATUS = true;
    public static final boolean USER_STATUS = false;

    public void createUser() {
//        Scanner scanner = new Scanner(System.in);

//        System.out.print("\nПожалуйста, введите имя аккаунта: ");
//        String enteredLogin = scanner.nextLine();
//        System.out.print("Придумайте пароль: ");
//        String enteredPassword = scanner.nextLine();

        // Проверка, используется ли уже такое имя
        User selUser = bank.findUser(USER_TO_CHANGE_STATUS);
        if (selUser != null) {
            System.out.println(
                    ANSI_RED + "Аккаунт с таким именем уже существует!" + ANSI_RESET + "\n"
            );
            return;
        }

        // Создать нового пользователя
        User user = new User(USER_TO_CHANGE_STATUS, USER_TO_CHANGE_STATUS_PASSWORD);
        user.setBalance(100);
        bank.getUsers().add(user);
        System.out.println(
                ANSI_GREEN + "Аккаунт " + USER_TO_CHANGE_STATUS +
                        " успешно зарегистрирован в системе. Присвоен ID: " +
                        user.getId() + ANSI_RESET + "\n"
        );
    }

    public boolean setUserStatus() {
//        Scanner scanner = new Scanner(System.in);

//        System.out.println(ANSI_GREEN + "\nВведите имя аккаунта:" + ANSI_RESET);
//        String enteredLogin = scanner.nextLine();
//        System.out.println(
//                ANSI_GREEN + "Введите права доступа (0-Пользователь, 1-Администратор):" +
//                ANSI_RESET
//        );

//        int status = scanner.nextInt();

        boolean isAdminSet = false;
        User selUser = bank.findUser(USER_TO_CHANGE_STATUS);
        if (selUser != null) {
            selUser.setAdmin(ADMIN_STATUS);
            isAdminSet = selUser.getAdmin();
            System.out.println(ANSI_GREEN + "Операция проведена успешно!" + ANSI_RESET + "\n");
        } else {
            System.out.println(
                    ANSI_RED + "Аккаунт " + USER_TO_CHANGE_STATUS +
                            " не зарегистрирован в системе" + ANSI_RESET + "\n"
            );
        }
        return isAdminSet;
    }

    @Test
    public void setUserStatusTest() {
        createUser();
        bank.showUserAccounts();
        setUserStatus();
        bank.showUserAccounts();
        Assert.assertTrue(setUserStatus());
    }
}
