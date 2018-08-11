import java.util.ArrayList;
import java.util.List;

class Division {

    List<Numbers> divisionDetailsList;
    private String divisionResult;
    private String divisionResidueResult;
    private int dividend;
    private int divisor;

    private String lastLine;
    private int lastSubtraction;
    private int accuracy;

    public Division(int dividend, int divisor){

        this.divisionDetailsList = new ArrayList<>();
        this.dividend = dividend;
        this.divisor = divisor;
        this.accuracy = 10;
        this.divisionResult = "";

        divide(dividend, divisor);
    }

    public String getDivisionResult() {
        return divisionResult;
    }

    public void printDivision(){
        System.out.println(findDivisionDetails());
    }

    String findDivisionDetails(){

        String result = createTopPart();
        result += createMiddlePart();
        result += createBottomPart();

        return result;
    }

    private String createTopPart(){
        String result = "";
        if(dividend < 0){
            result += "-";
            dividend *= -1;
        } else {
            result += " ";
        }
        int firstTopNumber = divisionDetailsList.get(0).getTopNumber();
        int firstBottomNumber = divisionDetailsList.get(0).getBottomNumber();
        int differenceLengthTopVSDividend =
                String.valueOf(firstTopNumber).length() - String.valueOf(dividend).length();
        result += dividend;
        if (differenceLengthTopVSDividend > 0){
            result += repeat(differenceLengthTopVSDividend, ' ');
        }
        result += "|" + divisor;
        result += "\r\n";

        result += "-" + repeat(String.valueOf(dividend).length(), ' ');
        if (differenceLengthTopVSDividend > 0){
            result += repeat(differenceLengthTopVSDividend, ' ');
        }
        result += "|";

        int maxLengthResultDivisionVSDivisor;
        if(divisionResult.length() > String.valueOf(divisor).length()){
            maxLengthResultDivisionVSDivisor = divisionResult.length();
        } else {
            maxLengthResultDivisionVSDivisor = String.valueOf(divisor).length();
        }
        result += repeat(maxLengthResultDivisionVSDivisor, '_');
        result += "\r\n";

        result += " ";
        int differenceLengthTopVSBottom =
                String.valueOf(firstTopNumber).length() - String.valueOf(firstBottomNumber).length();
        result += repeat(differenceLengthTopVSBottom, ' ');
        result += firstBottomNumber;
        result += repeat(String.valueOf(dividend).length() - differenceLengthTopVSBottom
                - String.valueOf(firstBottomNumber).length(), ' ');
        result += "|";
        result += divisionResult;
        result += "\r\n";

        lastLine = " " + repeat(differenceLengthTopVSBottom, ' ')
                + repeat(String.valueOf(firstBottomNumber).length(), '_');
        result += lastLine;
        result += "\r\n";

        return result;
    }

    private String createMiddlePart(){
        String result = "";
        lastSubtraction = divisionDetailsList.get(0).getSubtraction();
        int spaceLength = String.valueOf(lastLine).length() - String.valueOf(lastSubtraction).length();

        if(divisionDetailsList.get(0).getSubtraction() == 0) spaceLength++;

        for(int i = 1; i < divisionDetailsList.size() - 1; i++){
            Numbers pairNumber = divisionDetailsList.get(i);

            if (pairNumber.getTopNumber() == 0 || pairNumber.getTopNumber() < divisor){
                if (pairNumber.getTopNumber() == 0){
                    spaceLength++;
                }
            } else {

                result += repeat(spaceLength, ' ');
                result += pairNumber.getTopNumber();
                result += "\r\n";

                result += repeat(spaceLength - 1, ' ');
                result += "-";
                result += "\r\n";

                spaceLength += String.valueOf(pairNumber.getTopNumber()).length() - String.valueOf(pairNumber.getBottomNumber()).length();
                result += repeat(spaceLength, ' ');
                result += pairNumber.getBottomNumber();
                result += "\r\n";

                lastLine = repeat(spaceLength, ' ');
                lastLine += repeat(String.valueOf(pairNumber.getBottomNumber()).length(), '_');
                result += lastLine;
                result += "\r\n";

                lastSubtraction = pairNumber.getSubtraction();

                if(pairNumber.getSubtraction() != 0){
                    spaceLength += String.valueOf(pairNumber.getBottomNumber()).length()
                            - String.valueOf(pairNumber.getSubtraction()).length();
                } else {
                    spaceLength += String.valueOf(pairNumber.getBottomNumber()).length();
                }

            }
        }

        return result;
    }

    private String createBottomPart(){
        String result = "";

        result += repeat(lastLine.length() - String.valueOf(lastSubtraction).length(), ' ');
        result += divisionDetailsList.get(divisionDetailsList.size() - 1).getTopNumber();
        result += "\r\n";

        return result;
    }

    private String repeat(int length, char symbol) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += symbol;
        }
        return result;
    }

    private void divide(int dividend, int divisor){

        boolean isResultNegative = false;
        if (dividend < 0){
            dividend *= -1;
            isResultNegative = true;
        }

        if (divisor < 0){
            divisor *= -1;
            isResultNegative = !isResultNegative;
        }

        if (isResultNegative){
            divisionResult += "-";
        }

        if (dividend >= divisor){
            divideEvently(dividend, divisor);

            int lastTopNumber = divisionDetailsList.get(divisionDetailsList.size() - 1).getTopNumber();
            if (lastTopNumber > 0){
                divisionResult += ".";
                divisionResidueResult = "";
                divideResidue(lastTopNumber, divisor);
                divisionResult += divisionResidueResult;
            }

        } else {
            divisionResult += "0.";
            divisionResidueResult = "";
            divideResidue(dividend, divisor);
            divisionResult += divisionResidueResult;
        }
    }

    private void divideEvently(int dividend, int divisor){

        if (divisor == 0) throw new ArithmeticException("Division by zero");

        String stringDididend = String.valueOf(dividend);
        int topTemp = Integer.parseInt(stringDididend.substring(0, 1));
        stringDididend = stringDididend.substring(1);
        int bottomTemp;

        while(topTemp < divisor && !stringDididend.isEmpty()){
            topTemp = Integer.parseInt(String.valueOf(topTemp) + stringDididend.substring(0, 1));
            stringDididend = stringDididend.substring(1);
        }

        int factor = 1;
        while(topTemp >= divisor * factor){
            factor++;
        }
        factor--;
        divisionResult = divisionResult + factor;
        bottomTemp = divisor * factor;

        Numbers tempNumbers = new Numbers(topTemp, bottomTemp);
        divisionDetailsList.add(tempNumbers);

        if(!stringDididend.isEmpty()){
            if(stringDididend.startsWith("0") && tempNumbers.getSubtraction() == 0){
                while(stringDididend.startsWith("0")){
                    divisionResult = divisionResult + stringDididend.substring(0, 1);
                    stringDididend = stringDididend.substring(1);

                    divisionDetailsList.add(new Numbers(0, 0));
                }
            }

            divisionResult += repeat(String.valueOf(divisor).length()
                    - String.valueOf(tempNumbers.getSubtraction()).length() - 1, '0');

            if(!stringDididend.isEmpty()){
                divideEvently(Integer.parseInt(tempNumbers.getSubtraction() + stringDididend), divisor);
            }

        } else {
            int subtraction = tempNumbers.getSubtraction();
            Numbers lastTempNumbers = new Numbers(subtraction, 0);
            divisionDetailsList.add(lastTempNumbers);
        }
    }

    private void divideResidue(int dividend, int divisor){

        if (accuracy > 0){
            int topTemp = dividend;
            int bottomTemp = 0;

            if (topTemp < divisor){
                topTemp *= 10;
                while(topTemp < divisor){
                    topTemp *= 10;
                    divisionResidueResult += "0";
                }
            }

            int factor = 1;
            while(topTemp >= divisor * factor){
                factor++;
            }
            factor--;

            if (findTopNumber(topTemp) >= 0 && accuracy < 10){
                Numbers tempNumbers = new Numbers(dividend, 0);
                divisionDetailsList.add(tempNumbers);

                if (!divisionResidueResult.contains(String.valueOf(factor))){
                    divisionResidueResult = divisionResidueResult + String.valueOf(factor);
                }
                divisionResidueResult = divisionResidueResult.substring(0, findTopNumber(topTemp))
                        + "(" + divisionResidueResult.substring(findTopNumber(topTemp)) + ")";

            } else {
                if (accuracy > 0){
                    accuracy--;
                    if (accuracy > 0){
                        divisionResidueResult += String.valueOf(factor);
                    }

                    bottomTemp = divisor * factor;
                    Numbers tempNumbers = new Numbers(topTemp, bottomTemp);
                    divisionDetailsList.add(tempNumbers);

                    if(tempNumbers.getSubtraction() == 0 || accuracy == 1){
                        Numbers lastTempPairNumber = new Numbers(tempNumbers.getSubtraction(), 0);
                        divisionDetailsList.add(lastTempPairNumber);
                    } else {
                        divideResidue(tempNumbers.getSubtraction(), divisor);
                    }
                }
            }

        }
    }

    private int findTopNumber(int topNumber){

        for(int i = 0; i < divisionDetailsList.size(); i++){
            if (divisionDetailsList.get(i).getTopNumber() == topNumber) return i;
        }
        return -1;
    }
}