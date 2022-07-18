# AssemblyEmulator
CPU emulator, it's own assembly commands and compiler.

# CPU architecture
Explanation of inner structure of processor.

* `Bit depth`: 16 bit
* `Architecture type`: Von Neumann architecture
* `Commands`: 1 address
* `Implemented program`: Convolution of two arrays

## Convolution of two arrays:
Arrays have the same lengths, consist of numbers
* `a` - first array
* `b` - second array
* `i` - index of array
* `n` - length of array

> `array_convolution = (a[0] * b[0]) + (a[1] * b[1]) + ... + (a[n] * b[n])`

## Registers
|Register               |Register Address |Depth, bit |Description            |
|-----------------------|-----------------|-----------|-----------------------|
| `AX`                  | 0 | 16 | Stores the result of operations |
| `BX`                  | 1 | 16 | - |
| `CX`                  | 2 | 16 | Loop counter |
| `DX`                  | 3 | 16 | Stores data memory addresses, values |
| `DH`                  | 4 | 8  | Stores the address of the element of the 1st array |
| `DL`                  | 5 | 8  | Stores the address of the element of the 2nd array |

## Command format
| Name      | Bits | Abbreviation | Description |
|-----------|------|--------------|-------------|
| `cmdtype` | 0-7  | `C`            | Assembly command |
| `op1`     | 8-15 | `O`            | First operand |

16 bit command representation:
| `Range` |   15-8   |    7-0   |
|---------|----------|----------|
| `Bits`  | OOOOOOOO | CCCCCCCC |

# Assembly language
| CmdType | Assembly Command | Arguments | Description |
|---------|------------------|-----------|-------------|
| 0       | `RET`            | - | Program completion command |
| 1       | `LDA1 ch1`       | `ch1` - data memory address | Loads into the AX register the value from the data memory at the address.|
| 2       | `MRA reg`        | `reg` - processor register |  Copies the value of the AX register to the specified register.|
| 3       | `LDA2 op1`       | `op1` - integer | Loads the specified integer into register AX.|
| 4       | `ADD reg`        | `reg` - processor register | Adds the numbers from the AX register and the numbers from the memory, the address of which is written in the specified register. The result is stored in AH.|
| 5       | `INC reg`        | `reg` – processor register | The value of the specified register is incremented.|
| 6       | `LOOP op1`       | `op1` - instruction memory address | Decreases the value in the CX register in real mode. If the value in CX is non-zero, then the LOOP instruction jumps to the instruction specified as an argument.|
| 7       | `DEC reg`        | `reg` – processor register | The value of the specified register is decremented.
| 8       | `MUL reg`        | `reg` - processor register | We multiply the number in AX by the number from the data memory at the address written in the specified register. The result is recorded in AH. |
| 9       | `ADC reg`        | `reg` – processor register | Addition with carry.|
| 10       | `ADD2 ch1`      | `ch1` – data memory address | Adding the number in AX and the number from the data memory at the specified address. The result is stored at the specified address in MEMORY!|
| 11       | `LDA3 reg`      | `reg` - processor register | Copies the value from the specified register to the AX register.|
| 12       | `LDA4 reg`      | `reg` - processor register | Copy the value from the specified register to the AX register|

## How does it work?
Assembly commands consist of 2 parts: 
  1. Command 
  2. Operand

When receiving an assembler text instruction, it breaks down into two parts: the type of the command and the operand.

Table of comparisons is used to translate the command type and operand into machine code. Assembler String is being converted to binary number.

When command type and operand are converted to binary the resulting binary instruction is obtained by placing cmdType bits in the upper 8 bits (15-8) and the operand bits in the lower 8 bits (7-0). For convenient reading the resulting command is presented in hexadecimal system.

There is also an integrated development environment. IDE can run compiler on assembly commands and run code on a processor emulator.

| IDE element      | Description |
|------------------|-------------|
| Text field       | Allows you to write programs without the use of third-party text editors.|
| “Compile” button | Translates assembler instructions into machine code. The result of the work is displayed in the console.|
| “Run” button     | Translates assembler commands and transfers the machine code to the emulator processor for execution.|
| Line count field | Allows you to see the size of the resulting program. |
