public class Compiler {
    /*
     * This class is written to perform translation of Human Written Commands into Machine Code
     * */

    private int firstDataMemoryAddress;

    /*
     * Converts Array of String instructions into Array of 16 bit Integer programs for the CPU
     *
     * In: instructionsString array - e.g. {"LDA3 cx", "LDA4 dh", ...}
     * Out: instructions - e.g. {2818, 3076}
     * Where: LDA3 cx : 0000101100000010 : 2818
     *        LDA4 dh : 0000110000000100 : 3076
     * */
    public int[] compileInstructions(String[] instructionsString) {
        int[] instructions = new int[instructionsString.length];
        String instruction;
        String instructionBinaryString;

        firstDataMemoryAddress = instructions.length;  // NEW COMMAND

        for (int i = 0; i < instructions.length; i++) {
            instruction = instructionsString[i];
            instructionBinaryString = instructionStringToBinaryString(instruction);
            instructions[i] = instructionBinaryStringToBinary(instructionBinaryString);
        }

        return instructions;
    }

    /*
     * Converts String Command into Integer Command Type
     * In: command in String form - e.g. "MUL"
     * Out: command Type in Integer - e.g. 8
     *
     * */
    public int commandStringToType(String commandString) {
        int commandType = 0;
        switch (commandString) {
            case "RET":
                commandType = 0;
                break;
            case "LDA1":
                commandType = 1;
                break;
            case "MRA":
                commandType = 2;
                break;
            case "LDA2":
                commandType = 3;
                break;
            case "ADD":
                commandType = 4;
                break;
            case "INC":
                commandType = 5;
                break;
            case "LOOP":
                commandType = 6;
                break;
            case "DEC":
                commandType = 7;
                break;
            case "MUL":
                commandType = 8;
                break;
            case "ADC":
                commandType = 9;
                break;
            case "ADD2":
                commandType = 10;
                break;
            case "LDA3":
                commandType = 11;
                break;
            case "LDA4":
                commandType = 12;
                break;
            default:
                System.out.println(commandString + ": Not Found.");
                break;
        }

        return commandType;
    }

    /*
     * Converts String operand into Integer Operand Address
     * In: operand in String form - e.g. "dl"
     * Out: operand Address in Integer - e.g. 8
     *
     * */
    public int operandStringToAddress(String operandString) {
        int operandAddress = 0;

        switch (operandString) {
            case "ax":
                operandAddress = 0;
                break;
            case "bx":
                operandAddress = 1;
                break;
            case "cx":
                operandAddress = 2;
                break;
            case "dx":
                operandAddress = 3;
                break;
            case "dh":
                operandAddress = 4;
                break;
            case "dl":
                operandAddress = 5;
                break;
            case "ch1":
                operandAddress = firstDataMemoryAddress;
                break;
            case "":
                operandAddress = 0;
                break;
            default:
                operandAddress = Integer.parseInt(operandString);
        }

        return operandAddress;
    }

    /*
     * Converts binary String instructions into Integer program for the CPU
     *
     * In: binary Instruction String - e.g. "0000101100000010", "0000110000000100"
     * Out: Integer instruction - e.g. 2818, 3076
     * */
    public int instructionBinaryStringToBinary(String binaryStringInstruction) {
        int binaryInstruction;

        binaryInstruction = Integer.parseInt(binaryStringInstruction, 2);

        return binaryInstruction;
    }

    /*
     * Converts String instruction into Binary String program
     *
     * In: binary Instruction String array - "LDA3 cx", "LDA4 dh"
     * Out: instructions - e.g. "0000101100000010", "0000110000000100"
     * */
    public String instructionStringToBinaryString(String instruction) {

        String binaryStringInstruction = "";

        String commandString = "";
        String operandString = "";

        String[] instructionParts = instruction.split(" ");

        for (int i = 0; i < instructionParts.length; i++) {
            if (i == 0) commandString = instructionParts[i];
            if (i == 1) operandString = instructionParts[i];
        }

        binaryStringInstruction = commandStringToBinaryString(commandString) + operandStringToBinaryString(operandString);

        return binaryStringInstruction;
    }

    /*
     * Converts String command into Binary String command Type
     *
     * In: String command - "LDA3", "LDA4"
     * Out: Binary String command Type - e.g. "00001011", "00001100"
     * */
    public String commandStringToBinaryString(String commandString) {
        String commandBinaryString = "";

        int commandType = commandStringToType(commandString);
        String commandBinary = Integer.toBinaryString(commandType);

        commandBinaryString = commandBinary;

        for (int i = 0; i < 8 - commandBinary.length(); i++) {
            commandBinaryString = "0" + commandBinaryString;
        }

        return commandBinaryString;
    }

    /*
     * Converts String operand into Binary String operand Address
     *
     * In: String operand - "cx", "dh"
     * Out: Binary String operand Address - e.g. "00000010", "00000100"
     * Where: cx = 2; dh = 4;
     * */
    public String operandStringToBinaryString(String operandString) {
        String operandBinaryString = "";

        int operandAddress = operandStringToAddress(operandString);
        String operandBinary = Integer.toBinaryString(operandAddress);

        operandBinaryString = operandBinary;

        for (int i = 0; i < 8 - operandBinary.length(); i++) {
            operandBinaryString = "0" + operandBinaryString;
        }

        return operandBinaryString;
    }
}
