import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordCheckerTest {

    private PasswordChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PasswordChecker();
    }

    @Test
    void testPasswordOk() {
        int resultado = checker.check("Password123", "Password123");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado, "La contraseña debería ser válida.");
    }

    @Test
    void testPasswordBlank() {
        assertEquals(PasswordChecker.ERR_BLANK, checker.check("", ""));
        assertEquals(PasswordChecker.ERR_BLANK, checker.check("   ", "   "));
    }

    @Test
    void testPasswordLength() {
        checker.setMinLength(6);
        checker.setMaxLength(10);

        assertEquals(PasswordChecker.ERR_LENGTH, checker.check("Ab1", "Ab1"));
        assertEquals(PasswordChecker.ERR_LENGTH, checker.check("Abcdefghijk1", "Abcdefghijk1"));
    }

    @Test
    void testPasswordNoMatch() {
        assertEquals(PasswordChecker.ERR_NO_MATCH, checker.check("Password123", "Password321"));
    }

    @Test
    void testCheckDigits() {
        checker.setCheckDigits(true);
        assertEquals(PasswordChecker.ERR_DIGITS, checker.check("Password", "Password"));
    }

    @Test
    void testCheckUppers() {
        checker.setCheckUppers(true);
        assertEquals(PasswordChecker.ERR_UPPERS, checker.check("password123", "password123"));
    }

    @Test
    void testCheckLowers() {
        checker.setCheckLowers(true);
        assertEquals(PasswordChecker.ERR_LOWERS, checker.check("PASSWORD123", "PASSWORD123"));
    }

    @Test
    void testSettersExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            checker.setMinLength(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            checker.setMinLength(8);
            checker.setMaxLength(5);
        });
    }
}