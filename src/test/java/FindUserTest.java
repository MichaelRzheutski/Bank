import org.testng.Assert;
import org.testng.annotations.Test;

public class FindUserTest {
    Bank bank = new Bank();
    public static final String EXPECTED_USER_TO_FIND = "admin";

    @Test
    public void findUserTest() {
        Main.createAdmin(bank);
        Assert.assertNotNull(bank.findUser(EXPECTED_USER_TO_FIND));
    }
}
