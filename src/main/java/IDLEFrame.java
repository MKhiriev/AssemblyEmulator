import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IDLEFrame extends JFrame {

    JFrame frame = new JFrame("Assembly IDLE");
    JTextArea textArea = new JTextArea();
    JScrollPane scroller = new JScrollPane(textArea);
    JButton compileButton = new JButton("Compile");
    JButton runButton = new JButton("Run");
    JPanel buttonPanel = new JPanel();
    LineNumberModelImpl lineNumberModel = new LineNumberModelImpl();
    LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
    Compiler compiler = new Compiler();

    public IDLEFrame() {
        scroller.setRowHeaderView(lineNumberComponent);
        frame.getContentPane().add(scroller);

        runButton.addActionListener(new RunButtonListener());
        compileButton.addActionListener(new CompileButtonListener());

        buttonPanel.setBackground(Color.white);
        buttonPanel.add(compileButton);
        buttonPanel.add(runButton);

        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);

        lineNumberComponent.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);

        frame.pack();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setText("LDA1 ch1\n" +
                         "MRA cx\n" +
                         "DEC cx\n" +
                         "LDA2 20\n" +
                         "MRA dh\n" +
                         "LDA2 30\n" +
                         "MRA dl\n" +
                         "LDA2 0\n" +
                         "ADD dh\n" +
                         "MUL dl\n" +
                         "ADD2 41\n" +
                         "LDA2 0\n" +
                         "ADC ax\n" +
                         "ADD2 40\n" +
                         "INC dh\n" +
                         "INC dl\n" +
                         "DEC cx\n" +
                         "LOOP 7\n" +
                         "RET");

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }
        });

        frame.setVisible(true);
    }

    public String[] getCmds() {
        String cmd = textArea.getText();
        String[] cmds = cmd.split("\n");

        return cmds;
    }

    public void paintComponent(Graphics g) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                new IDLEFrame();
            }
        });
    }

    private class LineNumberModelImpl implements LineNumberModel {
        @Override
        public int getNumberLines() {
            return textArea.getLineCount();
        }


        @Override
        public Rectangle getLineRect(int line) {
            try {
                return textArea.modelToView(textArea.getLineStartOffset(line));
            } catch (BadLocationException e) {
                return new Rectangle();
            }
        }
    }

    public class CompileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String[] cmds = getCmds();

            int[] instructions = compiler.compileInstructions(cmds);
            System.out.print("commands = [ ");
            for (int i = 0; i < instructions.length; i++) {
                if (i == (instructions.length - 1)) System.out.print(String.format("0x%x", instructions[i]));
                else System.out.print(String.format("0x%x, ", instructions[i]));
            }
            System.out.println(" ]");
        }
    }

    public class RunButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String[] cmds = getCmds();
            int[] intCmds = new int[cmds.length];
            intCmds = compiler.compileInstructions(cmds);
            Processor processor = new Processor(6, 20, intCmds);
            processor.executeProgram();
        }
    }
}