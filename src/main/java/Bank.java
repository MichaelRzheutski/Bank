import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bank {
    private String bankName = "Bank";
    private List<User> users = new ArrayList<>();
    private User activeUser;

    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет

    // Логин
    public boolean login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nПожалуйста, введите ваше имя: ");
        String enteredLogin = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String enteredPassword = scanner.nextLine();

        // Проверка, если пользователь вышел
        User user = findUser(enteredLogin);
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
        setActiveUser(user);
        return true;
    }

    // Создать нового пользователя
    public void createUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nПожалуйста, введите имя аккаунта: ");
        String enteredLogin = scanner.nextLine();
        System.out.print("Придумайте пароль: ");
        String enteredPassword = scanner.nextLine();

        // Проверка, используется ли уже такое имя
        User selUser = findUser(enteredLogin);
        if (selUser != null) {
            System.out.println(
                    ANSI_RED + "Аккаунт с таким именем уже существует!" + ANSI_RESET + "\n"
            );
            return;
        }

        // Создать нового пользователя
        User user = new User(enteredLogin, enteredPassword);
        user.setBalance(100);
        users.add(user);
        System.out.println(
                ANSI_GREEN + "Аккаунт " + enteredLogin + " успешно зарегистрирован в системе. Присвоен ID: " +
                        user.getId() + ANSI_RESET + "\n"
        );
    }

    // Показать аккаунты пользователей
    public void showUserAccounts() {
        System.out.println(ANSI_GREEN + "\nАккаунты банка:" + ANSI_RESET);
        for (User user : users) {
            if (user.getAdmin()) {
                System.out.printf("%s | %s | Администратор\n", user.getId(), user.getLogin());
            } else {
                System.out.printf("%s | %s | Пользователь\n", user.getId(), user.getLogin());
            }
        }
        System.out.println();
    }

    // Удалить аккаунт
    public void deleteAccount(String login) {
        User user = findUser(login);
        if (user != null) {
            users.remove(user);
            System.out.println(
                    ANSI_GREEN + "Аккаунт " + login + " успешно удалён!" + ANSI_RESET + "\n"
            );
        } else {
            System.out.println(
                    ANSI_RED + "Аккаунт " + login + " не зарегистрирован в системе" + ANSI_RESET + "\n"
            );
        }
    }

    // Изменение прав доступа для пользователя
    public void setUserStatus() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_GREEN + "\nВведите имя аккаунта:" + ANSI_RESET);
        String enteredLogin = scanner.nextLine();
        System.out.println(
                ANSI_GREEN + "Введите права доступа (0-Пользователь, 1-Администратор):" + ANSI_RESET
        );

        int status = scanner.nextInt();

        User selUser = findUser(enteredLogin);
        if (selUser != null) {
            selUser.setAdmin((status == 1));
            System.out.println(ANSI_GREEN + "Операция проведена успешно!" + ANSI_RESET + "\n");
        } else {
            System.out.println(
                    ANSI_RED + "Аккаунт " + enteredLogin + " не зарегистрирован в системе" + ANSI_RESET + "\n"
            );
        }

    }

    // Найти пользователя
    public User findUser(String username) {
        for (User user : users) {
            if (user.getLogin().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }


}