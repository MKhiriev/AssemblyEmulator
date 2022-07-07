import java.util.Scanner;

public class Processor {

    private final int ax = 0; // Index of ax register in reg[] Array
    private final int bx = 1; // Index of bx register in reg[] Array
    private final int cx = 2; // Index of cx register in reg[] Array
    private final int dx = 3; // Index of dx register in reg[] Array
    private final int dh = 4; // Index of dh register in reg[] Array
    private final int dl = 5; // Index of dh register in reg[] Array
    private final int ch1; // Address of the first value in Data Memory
    private int pc; // Program Counter
    private int CF; //CF flag
    private int[] reg; // All Registers in Processor
    private int[] memory; // Command Memory and Data Memory
    private int commandMemorySize; // Command Memory Size

    /*Creation and getting ready Processor for work*/
    public Processor(int numOfRegisters, int dMemorySize, int[] programCommands) {

        System.out.println("Starting Initialization... Done!");
        System.out.print("Preparing PC, Registers, Memory for work... ");

        /*Setting Number of Commands in Memory*/
        this.commandMemorySize = programCommands.length;
        this.ch1 = commandMemorySize;
        initializeMemory(dMemorySize);
        initializeRegisters(numOfRegisters);

        System.out.println("Done!");

        System.out.print("Loading Commands for the Processor... ");
        System.out.print("Loading Random Numbers in Data Memory... ");

        /*Setting up Dmem and Registers*/
        loadMemory(programCommands);

        System.out.println("Done!");
        System.out.println("Processor is ready to go!");
    }


    /*
     * This method executes preloaded instructions
     * */
    public void executeProgram() {
        /*
         * Created auxiliary variables for convenience
         * */
        int cmdtype;
        int operand;
        int ch1;
        int cmd;
        int loop;

        Scanner scan = new Scanner(System.in); // Scanner is created for stopping program on each iteration

        showAll(); // get Starting state of Processor

        System.out.println("Programme begins!");

        while (pc != commandMemorySize) {
            /*Copying command from Command Memory*/
            cmd = memory[pc];

            /*Getting FIELDS' values*/
            cmdtype = Decipher.getCmdType(cmd);
            operand = Decipher.getOp1(cmd);
            ch1 = Decipher.getCh1(cmd);
            loop = Decipher.getOp1(cmd);

            System.out.println(String.format("\nCMDType[%d]: %s %d", pc, Decipher.getCmd(cmd), operand));

            /*Executing given command depending on its type*/
            switch (cmdtype) {
                case 0:
                    RET();
                    pc++;
                    break;
                case 1:
                    LDA1(ch1);
                    pc++;
                    break;
                case 2:
                    MRA(operand);
                    pc++;
                    break;
                case 3:
                    LDA2(operand);
                    pc++;
                    break;
                case 4:
                    ADD(operand);
                    pc++;
                    break;
                case 5:
                    INC(operand);
                    pc++;
                    break;
                case 6:
                    LOOP(loop);
                    break;
                case 7:
                    DEC(operand);
                    pc++;
                    break;
                case 8:
                    MUL(operand);
                    pc++;
                    break;
                case 9:
                    ADC(operand);
                    pc++;
                    break;
                case 10:
                    ADD2(ch1);
                    pc++;
                    break;
                case 11:
                    LDA3(operand);
                    pc++;
                    break;
                case 12:
                    LDA4(operand);
                    pc++;
                    break;
            }

            /*Showing results after execution*/
            showRegisters();
        }
        System.out.println("\nProgram ended...\nResults\nMemH (HighBits): " + memory[memory.length - 2] + "; MemL (LowBits): " + memory[memory.length - 1]);
        System.out.println("#########################################################");
        checkSvertka();
    }

    /*
     * This methods outputs in Console view Registers' Values
     * Out[]: "pc: 0
     *         Registers: ax: 0 bx: 0 cx: 0 dx: 0"
     * */
    public void showRegisters() {

        System.out.print("pc: " + pc + "\n");
        System.out.println("Registers: " + "ax: " + reg[ax] + " bx: " + reg[bx] + " cx: " + reg[cx] + " dx: " + reg[dx] + " dh: " + reg[dh] + " dl: " + reg[dl]);
        System.out.println("MemH[" + (memory.length - 2) + "] = " + memory[memory.length - 2] + "; MemL[" + (memory.length - 1) + "] = " + memory[memory.length - 1]);

    }

    /*
     * This methods outputs in Console view Command Memory Values
     * Out[]: "Cmem: [ 9 258 522 259 512 771 1027 1285 1536 ]"
     * */
    public void showCommandMemory() {

        System.out.print("Cmem: " + "[ ");
        for (int i = 0; i < commandMemorySize; i++) System.out.print(memory[i] + " ");
        System.out.println("]");

    }

    /*
     * This methods outputs in Console view Data Values
     * Out[]: "Dmem: [ 11 60 30 3 31 95 56 4 97 60 80 11 3 ]"
     * */
    public void showDataMemory() {

        System.out.print("Dmem: " + "[ ");
        int dMemSum = 0;
        for (int i = commandMemorySize; i < memory.length; i++) {
            System.out.print(memory[i] + " ");
            if (i != commandMemorySize) {
                dMemSum += memory[i];
            }
        }
        System.out.println("]");

        int offSet = memory[commandMemorySize] / 2 + 1;
        int addressOfFirstNum = commandMemorySize + 1;
        System.out.println(String.format("1: Address: %d ; Num[%<d] = %d", addressOfFirstNum, memory[addressOfFirstNum]));

        int addressOfSecondNum = commandMemorySize + offSet;
        System.out.println(String.format("2: Address: %d ; Num[%<d] = %d", addressOfSecondNum, memory[addressOfSecondNum]));
    }

    /*
     * showAll() - method for showing info of all components: PC, Registers, Command Memory, Data Memory
     * Out[]:    Showing all info.
     *           ==============================================================
     *           pc: 0
     *           Registers: ax: 0 bx: 0 cx: 0 dx: 0
     *           Cmem: [ 9 258 522 259 512 771 1027 1285 1536 ]
     *           Dmem: [ 11 60 30 3 31 95 56 4 97 60 80 11 3 ]
     *           Data Memory Sum = 530
     *           ==============================================================
     * */
    private void showAll() {
        System.out.println("\nShowing all info.");
        System.out.println("==============================================================");

        showRegisters();
        showCommandMemory();
        showDataMemory();

        calculateSvertka();

        System.out.println("==============================================================\n");
    }

    /*
     * This Method Loads commands to execute for Processor
     * int[] commands - array with commands
     * */
    public void loadCommands(int[] programCommands) {

        System.arraycopy(programCommands, 0, memory, 0, programCommands.length);

    }


    /*Initializing Data Memory Numbers
     * First Number is a size of a Data Memory
     * Other Numbers are operands
     * */
    public void loadData(int memorySize) {

        int numOfData = (memorySize - commandMemorySize - 1) * 2;

        memory = new int[commandMemorySize + numOfData + 1];
        memory[commandMemorySize] = (memory.length - commandMemorySize - 1 - 2);
        for (int i = commandMemorySize; i < memory.length - 1 - 2; i++) {
            memory[i + 1] = (int) (Math.random() * 100);
        }
    }

    /*Initializing Data Memory Numbers
     * First Number is a size of a Data Memory
     * Other Numbers are operands
     * */
    public void loadRegisters(int numOfRegisters) {
        pc = 0;
        //checks if we have at least 4 registers: ax, bx, cx, dx.
        if (numOfRegisters < 4) numOfRegisters = 4;
        reg = new int[numOfRegisters];
    }

    /*
     * Loads in AX value by address in Data Memory
     * int dataMemoryAddress - 16 bit address of number in Memory
     * Result is stored in ax
     * cmdType = 0;
     * */
    public void LDA1(int dataMemoryAddress) {
        reg[ax] = memory[dataMemoryAddress];
    }

    /*
     * Loads in AX value by pure Value(not address)
     * int dataMemoryAddress - 16 bit address of number in Memory
     * Result is stored in ax
     * cmdType = 2;
     * */
    public void LDA2(int operandValue) {
        reg[ax] = operandValue;
    }

    /*
     * Copies AX value into chosen register
     * int register - 16 bit address of register in Register Memory
     *                i.e. ax, bx, cx, dx.
     * Result is stored in <register>
     * cmdType = 1;
     * */
    public void MRA(int register) {
        reg[register] = reg[ax];
    }

    /*
     * Adds two values: ax-value and value from address stored in dx
     * Result is stored in ax
     * int dataMemoryAddress - 16 bit address of number in Memory
     * Result is stored in ax
     * cmdType = 3;
     * */
    public void ADD(int register) {
        reg[ax] = reg[ax] + memory[reg[register]];
    }

    /*
     * Increments value of chosen register
     * Result is stored in chosen register
     * int op1 - 16 bit address of number in Memory
     * Result is stored in ax
     * cmdType = 4;
     * */
    public void INC(int op1) {
        reg[op1]++;
    }

    /*
     * Decrements value in a given register
     * register - address of a register in reg[] array
     * cmdType = 6;
     * */
    public void DEC(int register) {
        reg[register]--;
    }


    /*
     * Creates for loop
     * Number of iterations is stored in cx register
     * Each iteration cx decrements by one
     * If cx != 0 -> Jumps to given command in Command Memory
     * When cx == 0 -> pc increments by 1
     * cmdType = 5;
     * */
    public void LOOP(int commandToJump) {
        if (reg[cx] == 0) {
            pc++;
        } else {
            reg[cx]--;
            pc = commandToJump;
        }
    }

    /*
     * Multiplies two Numbers: from AX reg and Data Memory address stored in chosen reg
     *
     * register - chosen register
     * Result is stored in AX reg
     * */
    public void MUL(int register) {
        reg[ax] = memory[reg[register]] * reg[ax];
    }

    /*
     * Adds CF value to a chosen register
     * */
    public void ADC(int register) {
        reg[register] = reg[register] + CF;
    }

    /*
     * Adds number stored in AX register and Data Memory number accessed by address
     * dataMemoryAddress - chosen address from Data Memory
     *
     * Result is stored in chosen Data Memory address
     * */
    public void ADD2(int dataMemoryAddress) {
        int limit = 65535;
        CF = 0;

        if (reg[ax] + memory[dataMemoryAddress] > limit) {
            CF = 1;
            memory[dataMemoryAddress] = (reg[ax] + memory[dataMemoryAddress]) - limit;
        } else {
            memory[dataMemoryAddress] = reg[ax] + memory[dataMemoryAddress];
        }

    }

    /*
     * Copies value from chosen register to AX register
     * */
    public void LDA3(int register) {
        reg[ax] = reg[register];
    }

    /*
     * Loads in AX register value from Data Memory accessed by address stored in DH register
     * */
    public void LDA4(int register) {
        reg[ax] = memory[reg[register]];
    }

    public void initializeMemory(int dMemorySize) {
        int emptySlots = 2;
        int memorySize = commandMemorySize + dMemorySize + emptySlots + 1;
        memory = new int[memorySize];
    }

    /*
     * This method is ending execution of the program
     * */
    public void RET() {
        String ret = "RET";
    }


    public void initializeRegisters(int numOfRegisters) {
        pc = 0;
        //checks if we have at least 4 registers: ax, bx, cx, dx.
        if (numOfRegisters < 6) numOfRegisters = 6;
        reg = new int[numOfRegisters];
    }

    public void loadMemory(int[] programCommands) {
        loadCommands(programCommands);

        loadRandomNumbers();
    }

    public void loadRandomNumbers() {
        int emptySlots = 2;

        memory[commandMemorySize] = (memory.length - commandMemorySize - emptySlots - 1);
        for (int i = commandMemorySize; i < memory.length - emptySlots - 1; i++) {
            memory[i + 1] = (int) (Math.random() * 256);
        }
    }

    public void calculateSvertka() {
        int result = 0;

        int offSet = memory[commandMemorySize] / 2;
        for (int i = commandMemorySize + 1; i < commandMemorySize + offSet + 1; i++) {
            result += memory[i] * memory[i + offSet];
        }
        System.out.println(String.format("Svertka Sum = %d", result));
    }


    public void checkSvertka() {
        System.out.println("Checking results...");

        int MemH = memory[memory.length - 2];
        int MemL = memory[memory.length - 1];

        String highBits = "";
        String lowBits = "";
        int bitsInNumber = 16;

        highBits = Integer.toBinaryString(MemH);
        String MemHString = highBits;
        for (int i = 0; i < bitsInNumber - highBits.length(); i++) {
            MemHString = "0" + MemHString;
        }
        System.out.println("High Bits: " + MemHString + " | length = " + MemHString.length());
        lowBits = Integer.toBinaryString(MemL);
        String MemLString = lowBits;
        for (int i = 0; i < bitsInNumber - lowBits.length(); i++) {
            MemLString = "0" + MemLString;
        }
        System.out.println("Low Bits:  " + MemLString + " | length = " + MemLString.length());

        System.out.println("Low + High: BIN = " + MemHString + MemLString + "\nLow + High: DEC = " + Integer.parseInt(MemHString + MemLString, 2));
    }


    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int[] getReg() {
        return reg;
    }

    public void setReg(int[] reg) {
        this.reg = reg;
    }

    public int[] getMemory() {
        return memory;
    }

    public void setMemory(int[] memory) {
        this.memory = memory;
    }

    public int getCommandMemorySize() {
        return commandMemorySize;
    }

    public void setCommandMemorySize(int commandMemorySize) {
        this.commandMemorySize = commandMemorySize;
    }

}
