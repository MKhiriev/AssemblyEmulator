/* This class is fetching fields' values from Command */

public class Decipher {

    public static int getCmdType(int cmd) {
        return ((cmd >> 8) & 255);
    }

    public static String getCmd(int cmd) {
        String res = "";
        switch (getCmdType(cmd)) {
            case 0:
                res = "RET";
                break;
            case 1:
                res = "LDA1";
                break;
            case 2:
                res = "MRA";
                break;
            case 3:
                res = "LDA2";
                break;
            case 4:
                res = "ADD";
                break;
            case 5:
                res = "INC";
                break;
            case 6:
                res = "LOOP";
                break;
            case 7:
                res = "DEC";
                break;
            case 8:
                res = "MUL";
                break;
            case 9:
                res = "ADC";
                break;
            case 10:
                res = "ADD2";
                break;
            case 11:
                res = "LDA3";
                break;
            case 12:
                res = "LDA4";
                break;
            default:
                res = "undefied";
                break;
        }


        return res;
    }

    public static int getOp1(int cmd) {
        return (cmd & 255);
    }

    public static int getCh1(int cmd) {
        return (cmd & 255);
    }
}
