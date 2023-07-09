import java.util.Scanner;


public class Main {
    public static int option;
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String ANSI_GREEN = "\u001B[32m"; // Зелёный цвет
    public static final String ANSI_RED = "\u001B[31m"; // Красный цвет
    public static final String ANSI_YELLOW = "\u001B[33m"; // Жёлтый цвет

    public static void main(String[] args) {
        Bank bank = new Bank();

        createAdmin(bank);
        mainMenu(bank);
    }

    // Создание главного админа (может изменять права других пользователей)
    private static void createAdmin(Bank bank) {
        User defaultAdmin = new User("admin", "admin");
        defaultAdmin.setAdmin(true);
        defaultAdmin.setBalance(200);
        bank.getUsers().add(defaultAdmin);
    }

    // Главное меню
    private static void mainMenu(Bank bank) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                ANSI_GREEN + "Добро пожаловать в " + bank.getBankName() + "!" + ANSI_RESET
        );

        // Меню 1 (Вход, регистрация ...)
        boolean exit = false;
        while (!exit) {
            System.out.println(
                    ANSI_GREEN + "Пожалуйста, выберите одну из предложенных операций:" + ANSI_RESET
            );
            System.out.println("[1]. Войти в личный кабинет");
            System.out.println("[2]. Регистрация аккаунта");
            System.out.println("[0]. Выйти из банковской системы");

            option = scanner.nextInt();
            scanner.nextLine(); // Решение проблемы со сканнером

            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    if (!bank.login()) {
                        option = 0;
                    } else {
                        System.out.println(
                                ANSI_GREEN + "Вы успешно вошли в свой личный кабинет!" + ANSI_RESET
                        );
                        System.out.println();
                    }
                    break;
                case 2:
                    bank.createUser();
                    break;
                default:
                    System.out.println(
                            ANSI_RED + "Неверная операция, попробуйте ещё раз!" + ANSI_RESET + "\n"
                    );
                    break;
            }

            // Меню 2 Пользователь и Администратор
            while (option == 1) {
                System.out.println(
                        "Ваш баланс: " + ANSI_YELLOW +
                                bank.getActiveUser().getBalance() + ANSI_RESET
                );
                System.out.println("Операции со счётом:");
                System.out.println("[1]. Пополнить счёт");
                System.out.println("[2]. Платёж");
                System.out.println("[3]. Перевод");
                System.out.println("[4]. Список транзакций");

                if (bank.getActiveUser().getAdmin()) {
                    System.out.println();
                    System.out.println("Операции администрирования:");
                    System.out.println("[5]. Список аккаунтов");
                    System.out.println("[6]. Добавить аккаунт");
                    System.out.println("[7]. Удалить аккаунт");
                    System.out.println("[8]. Изменить права аккаунта");
                }

                System.out.println();
                System.out.println("[0]. Выход");

                int userPanel = scanner.nextInt();
                scanner.nextLine();

                if ((!bank.getActiveUser().getAdmin()) && (userPanel > 4)) {
                    userPanel = 10;
                }

                Scanner scanner2 = new Scanner(System.in);
                switch (userPanel) {
                    case 0:
                        option = 0;
                        break;
                    case 1:
                        depositAccount(bank, scanner);
                        break;
                    case 2:
                        makePayment(bank, scanner, scanner2);
                        break;
                    case 3:
                        sendMoney(bank, scanner, scanner2);
                        break;
                    case 4:
                        bank.getActiveUser().listOfTransactions();
                        break;
                    case 5:
                        bank.showUserAccounts();
                        break;
                    case 6:
                        bank.createUser();
                        break;
                    case 7:
                        System.out.println(ANSI_GREEN + "Введите аккаунт для удаления:" + ANSI_RESET);
                        String deleteUsername = scanner.next();
                        bank.deleteAccount(deleteUsername);
                        break;
                    case 8:
                        bank.setUserStatus();
                        break;
                    default:
                        System.out.println(
                                ANSI_RED + "Неверная операция, попробуйте ещё раз!" + ANSI_RESET + "\n"
                        );
                        break;
                }
            }
        }
    }

    private static void depositAccount(Bank bank, Scanner scanner) {
        double money;
        Transaction transaction;

        System.out.println(ANSI_GREEN + "Введите сумму пополнения:" + ANSI_RESET);
        money = scanner.nextDouble();

        transaction = new Transaction(true, money, "");

        bank.getActiveUser().setBalance(
                transaction.performTransaction(bank.getActiveUser().getBalance())
        );

        bank.getActiveUser().getTransactions().add(transaction);

        System.out.println(ANSI_GREEN + "Счёт пополнен успешно!" + ANSI_RESET + "\n");
    }

    private static void makePayment(Bank bank, Scanner scanner, Scanner scanner2) {
        double money;
        Transaction transaction;
        String recipient;

        System.out.println(ANSI_GREEN + "Введите сумму платежа:" + ANSI_RESET);
        money = scanner2.nextDouble();

        System.out.println(ANSI_GREEN + "Введите интернет-магазин:" + ANSI_RESET);
        recipient = "*" + scanner.nextLine();
        transaction = new Transaction(false, money, recipient);

        bank.getActiveUser().setBalance(
                transaction.performTransaction(bank.getActiveUser().getBalance())
        );
        bank.getActiveUser().getTransactions().add(transaction);

        System.out.println(ANSI_GREEN + "Операция проведена успешно!" + ANSI_RESET + "\n");
    }

    private static void sendMoney(Bank bank, Scanner scanner, Scanner scanner2) {
        Transaction transaction;
        String recipient;
        double money;
        System.out.println(ANSI_GREEN + "Введите сумму перевода:" + ANSI_RESET);
        money = scanner2.nextDouble();

        System.out.println(ANSI_GREEN + "Введите получателя:" + ANSI_RESET);
        recipient = scanner.nextLine();

        User user = bank.findUser(recipient);
        if (user != null) {
            transaction = new Transaction(false, money, recipient);
            transaction.transferMoney(bank.getActiveUser(), user);
            bank.getActiveUser().getTransactions().add(transaction);
            System.out.println(
                    ANSI_GREEN + "Операция проведена успешно!" + ANSI_RESET + "\n"
            );
        } else {
            System.out.println(
                    ANSI_RED + "Аккаунт " + recipient + " не зарегистрирован в системе" +
                            ANSI_RESET + "\n"
            );
        }
    }

}
