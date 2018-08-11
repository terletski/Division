import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class LongDivisionTest {

    @Test(expectedExceptions=ArithmeticException.class)
    public void tstDivisionByZero() {

        int dividend = 10000;
        int divisor = 0;
        String expectedResultNumber = "Division by zero";
        Division division = new Division(dividend, divisor);
        String expectedResultLine = "Division by zero";
        assertEquals(expectedResultLine, divisionProcessListToString(division));
        assertEquals(expectedResultNumber, division.getDivisionResult());
    }

    @Test
    public void tstResultDivision() {

        int dividend = 78459;
        int divisor = 4;
        String expectedResultNumber = "19614.75";
        Division division = new Division(dividend, divisor);
        String expectedResultLine = "7-4, 38-36, 24-24, 5-4, 19-16, 3-0, 30-28, 20-20, 0-0, ";
        assertEquals(expectedResultLine, divisionProcessListToString(division));
        assertEquals(expectedResultNumber, division.getDivisionResult());
    }

    public String divisionProcessListToString(Division division) {
        String result = "";

        for (Numbers numbers : division.divisionDetailsList) {
            result += numbers.getTopNumber() + "-" + numbers.getBottomNumber() + ", ";
        }

        return result;
    }

    @Test
    public void tstShowDivisionResult() {

        int dividend = 78459;
        int divisor = 4;
        Division division = new Division(dividend, divisor);
        String expectedResultLine = " 78459|4\r\n"
                + "-     |________\r\n"
                + " 4    |19614.75\r\n"
                + " _\r\n"
                + " 38\r\n"
                + "-\r\n"
                + " 36\r\n"
                + " __\r\n"
                + "  24\r\n"
                + " -\r\n"
                + "  24\r\n"
                + "  __\r\n"
                + "    5\r\n"
                + "   -\r\n"
                + "    4\r\n"
                + "    _\r\n"
                + "    19\r\n"
                + "   -\r\n"
                + "    16\r\n"
                + "    __\r\n"
                + "     30\r\n"
                + "    -\r\n"
                + "     28\r\n"
                + "     __\r\n"
                + "      20\r\n"
                + "     -\r\n"
                + "      20\r\n"
                + "      __\r\n"
                + "       0\r\n";
        assertEquals(expectedResultLine, division.findDivisionDetails());
    }
}
