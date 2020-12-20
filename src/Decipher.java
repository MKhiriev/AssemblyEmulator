/* This class is fetching fields' values from Command */

public class Decipher {

    public static int getCmdType(int cmd) {
        return ((cmd >> 8) & 255);
    }

    public static String getCmd(int cmd) {
        String res = "";
        switch (getCmdType(cmd)) {
            case 0: res = "RET"; break;
            case 1: res = "LDA1"; break;  //LDA1 - loads into AX data from <dmem address> OK
            case 2: res = "MRA"; break;   //MRA - copies from AX to <register> OK
            case 3: res = "LDA2"; break;       //LDA2 - loads into AX number OK
            case 4: res = "ADD"; break;   //ADD - returns sum of AX and given <register> OK
            case 5: res = "INC";  break;       //INC - increments <register>
            case 6: res = "LOOP"; break;
            case 7: res = "DEC"; break; //DEC - Retires from program
            //case 7: res = "RET"; break;
            case 8: res = "MUL"; break;
            case 9: res = "ADC"; break;
            case 10: res = "ADD2"; break;
            case 11: res = "LDA3"; break;
            case 12: res = "LDA4"; break;
            default: res = "undefied";break;
        }


        return res;
    }


    /*public static short getRegDest(short cmd) {
        return (short) ((cmd >> 8) & 15);
    }*/

    public static int getOp1(int cmd) {
        return (cmd & 255);
    }

    /*public static short getOp2(short cmd) {
        return (short) (cmd & 15);
    }*/

    public static int getCh1(int cmd) {
        return (cmd & 255);
    }

    public static void double8(int cmd, String message) {
        int[] resInt = new int[8];

        int count = 0;
        for (int i = 0; i < 8; i++) resInt[i] = 0;

        while (cmd / 2 != 0) {
            resInt[resInt.length - count - 1] = cmd % 2;
            cmd = cmd / 2;
            count++;
        }

        System.out.print("Двоичное число " + message + " : ");
        for (int i = 0; i < 8; i++) System.out.print(resInt[i]);
    }
}
