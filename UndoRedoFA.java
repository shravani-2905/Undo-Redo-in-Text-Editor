import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

/*<applet code="UndoRedoFA" width=1000 height=1000></applet>*/
public class UndoRedoFA extends Applet {
    private TextArea textArea;
    private Button undoButton;
    private Button redoButton;
    private Button exitButton;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public void init() {
        textArea = new TextArea();
        undoButton = new Button("Undo");
        redoButton = new Button("Redo");
        exitButton = new Button("Exit");

        undoStack = new Stack();
        redoStack = new Stack();

        // Add components to the applet
        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);

        Panel buttonPanel = new Panel();
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add TextListener to textArea
        textArea.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent e) {
                // Push current content to the undo stack
                if (!undoStack.isEmpty() && !textArea.getText().equals(undoStack.peek())) {
                    undoStack.push(textArea.getText());
                } else if (undoStack.isEmpty()) {
                    undoStack.push(textArea.getText());
                }
            }
        });

        // Add action listener for undo button
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoStack.size() > 1) {
                    redoStack.push(undoStack.pop());
                    textArea.setText(undoStack.peek());
                }
            }
        });

        // Add action listener for redo button
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!redoStack.isEmpty()) {
                    textArea.setText(redoStack.peek());
                    undoStack.push(redoStack.pop());
                }
            }
        });

        // Add action listener for exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(600, 500);
    }
}
