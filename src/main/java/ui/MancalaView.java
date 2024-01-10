package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.Font;

import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mancala.MancalaGame;

public class MancalaView extends JFrame {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;
    private int indexOfMove = -1;

    private Container contentPane;

    public MancalaView() {
        super();
        setDefaults();
        setMainContainer();
    }

    private void setDefaults() {
        setSize(WIDTH, HEIGHT);
        setTitle("Mancala Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setMainContainer() {
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JLabel("Carsten Singh\n\n"), BorderLayout.SOUTH);
    }

    public void addMenuButtons(Consumer<ActionEvent> startGameAction, Consumer<ActionEvent> exitAction) {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        Dimension buttonSize = new Dimension(200, 50);
        createButton("Start Game", buttonPanel, startGameAction, buttonSize);
        createButton("Exit", buttonPanel, exitAction, buttonSize);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
    }

    public void addSaveButton(Consumer<ActionEvent> saveGameAction, Consumer<ActionEvent> backHomeAction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        createButtonOptions("Save Game", panel, saveGameAction, new Dimension(200, 50));
        createButtonOptions("Back To Home", panel, backHomeAction, new Dimension(200, 50));
    
    
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalGlue()); 
        contentPane.add(panel);
        contentPane.add(Box.createVerticalGlue()); 
    }
    
    
    public void addStartButtons(
        Consumer<ActionEvent> startKalahGameAction,
        Consumer<ActionEvent> startAyoGameAction,
        Consumer<ActionEvent> loadGameAction,
        Consumer<ActionEvent> backToHomeAction
    ) {
        JPanel outerPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(200, 50);

        createButton("Start New Kalah Game", buttonPanel, startKalahGameAction, buttonSize);
        buttonPanel.add(Box.createVerticalStrut(10));

        createButton("Start New Ayo Game", buttonPanel, startAyoGameAction, buttonSize);
        buttonPanel.add(Box.createVerticalStrut(10));

        createButton("Load Previous Game", buttonPanel, loadGameAction, buttonSize);
        buttonPanel.add(Box.createVerticalStrut(10));

        createButton("Back To Home", buttonPanel, backToHomeAction, buttonSize);
        buttonPanel.add(Box.createVerticalGlue());

        // Create a new JPanel with GridBagLayout.
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.add(buttonPanel);

        outerPanel.add(gridPanel, BorderLayout.CENTER);
        contentPane.add(outerPanel, BorderLayout.CENTER);
    }

    public int showConfirmationDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_OPTION);
    }

    public String getPlayerName(String prompt) {
        return JOptionPane.showInputDialog(this, prompt);
    }

    public void addPanel(JPanel panel){

        contentPane.add(panel, BorderLayout.CENTER);
        
    }


    public void addText(String text){
        JLabel label = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), labelFont.getStyle(), labelFont.getSize() * 2));
    
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        textPanel.add(label, BorderLayout.SOUTH);
        
        if (!(contentPane.getLayout() instanceof BoxLayout)) {
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        }
        contentPane.add(textPanel);
    }
    

    public int getChoice(){
        return this.indexOfMove;

    }
    private void createButton(String buttonText, JPanel panel, Consumer<ActionEvent> action, Dimension size) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> action.accept(e));
        button.setMaximumSize(size); // Set the size from the parameter
        button.setMinimumSize(size);
        panel.add(button);
    }

    private void createButtonOptions(String buttonText, JPanel panel, Consumer<ActionEvent> action, Dimension size) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> action.accept(e));
        button.setPreferredSize(size);
        panel.add(button);
    }

    public void clearScreen() {
        contentPane.removeAll();
        setMainContainer();
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void showGui() {
        setVisible(true);
    }
}
