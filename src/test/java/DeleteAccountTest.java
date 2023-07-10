import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteAccountTest {
    Bank bank = new Bank();
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет
    public static final String USER_TO_DELETE = "admin";

    public boolean deleteAccount(String login) {
        boolean isUserRemoved = false;

        User user = bank.findUser(login);
        if (user != null) {
            isUserRemoved = bank.getUsers().remove(user);
            System.out.println(
                    ANSI_GREEN + "Аккаунт " + login + " успешно удалён!" + ANSI_RESET + "\n"
            );
        } else {
            System.out.println(
                    ANSI_RED + "Аккаунт " + login + " не зарегистрирован в системе" + ANSI_RESET + "\n"
            );
        }

        return isUserRemoved;
    }

    @Test
    public void deleteAccountTest() {
        Main.createAdmin(bank);
        Assert.assertTrue(deleteAccount(USER_TO_DELETE));
    }
}
