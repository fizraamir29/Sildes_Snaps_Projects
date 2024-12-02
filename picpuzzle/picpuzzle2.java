import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*; // Import java.util package for collections

class PicPuzzle extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, sample, restart, levelSelect;
    Icon star;
    JLabel timerLabel, levelLabel;
    java.util.Timer timer; // Explicitly using java.util.Timer
    int timeLeft = 60; // Timer countdown in seconds
    String currentLevel = "Easy";

    // Define the Icons for the puzzle pieces
    Icon ic0 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\blank.jpg");
    Icon ic1 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\1.jpg");
    Icon ic2 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\5.jpg");
    Icon ic3 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\2.jpg");
    Icon ic4 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\7.jpg");
    Icon ic5 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\4.jpg");
    Icon ic6 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\6.jpg");
    Icon ic7 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\8.jpg");
    Icon ic8 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\9.jpg");
    Icon ic9 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\3.jpg");
    Icon samicon1 = new ImageIcon("C:\\Users\\LEnovo\\Downloads\\PicPuzzle\\picpuzzle\\pic\\main.jpg");

    java.util.List<JButton> buttons = new java.util.ArrayList<>(); // Explicitly using java.util.List and java.util.ArrayList

    PicPuzzle() {
        super("Slides Snap");

        // Initialize buttons with corresponding icons
        b1 = new JButton(ic1);
        b2 = new JButton(ic2);
        b3 = new JButton(ic3);
        b4 = new JButton(ic4);
        b5 = new JButton(ic5);
        b6 = new JButton(ic6);
        b7 = new JButton(ic7);
        b8 = new JButton(ic8);
        b9 = new JButton(ic0); // Start with empty space

        // Add buttons to the list
        buttons.addAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9));

        // Set bounds for buttons
        setButtonBounds();

        sample = new JButton(samicon1);
        sample.setBounds(380, 100, 300, 300);  // Increased size

        restart = new JButton("Restart");
        restart.setBounds(10, 400, 100, 30);

        levelSelect = new JButton("Change Level");
        levelSelect.setBounds(120, 400, 150, 30);

        timerLabel = new JLabel("Time Left: 60s");
        timerLabel.setBounds(330, 20, 150, 30);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        levelLabel = new JLabel("Level: Easy");
        levelLabel.setBounds(330, 60, 150, 30);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Initialize with the empty space icon
        star = b9.getIcon();

        // Add buttons to the frame
        for (JButton button : buttons) {
            add(button);
            button.addActionListener(this);
        }
        add(sample);
        add(restart);
        add(levelSelect);
        add(timerLabel);
        add(levelLabel);

        restart.addActionListener(this);
        levelSelect.addActionListener(this);

        // Set layout and size
        setLayout(null);
        setSize(700, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startTimer(); // Start the timer
    }

    private void setButtonBounds() {
        int x = 10, y = 80;
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setBounds(x, y, 100, 100);
            x += 100;
            if ((i + 1) % 3 == 0) {
                x = 10;
                y += 100;
            }
        }
    }

    private void startTimer() {
        timer = new java.util.Timer(); // Explicitly using java.util.Timer
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time Left: " + timeLeft + "s");
                } else {
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "Time's up! Restart the game.");
                }
            }
        }, 1000, 1000);
    }

    private void swapIcons(JButton button1, JButton button2) {
        Icon temp = button1.getIcon();
        button1.setIcon(button2.getIcon());
        button2.setIcon(temp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
            resetGame();
        } else if (e.getSource() == levelSelect) {
            shuffleIcons();
        } else {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getIcon() == star) return;

            for (JButton button : buttons) {
                if (button.getIcon() == star && isAdjacent(clickedButton, button)) {
                    swapIcons(clickedButton, button);
                    break;
                }
            }
        }
    }

    private boolean isAdjacent(JButton b1, JButton b2) {
        int index1 = buttons.indexOf(b1);
        int index2 = buttons.indexOf(b2);

        int row1 = index1 / 3, col1 = index1 % 3;
        int row2 = index2 / 3, col2 = index2 % 3;

        return (Math.abs(row1 - row2) + Math.abs(col1 - col2)) == 1;
    }

    private void resetGame() {
        timeLeft = 60;
        timer.cancel();
        startTimer();

        // Reset all buttons to initial positions
        buttons.clear();
        buttons.addAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9));

        b1.setIcon(ic1);
        b2.setIcon(ic2);
        b3.setIcon(ic3);
        b4.setIcon(ic4);
        b5.setIcon(ic5);
        b6.setIcon(ic6);
        b7.setIcon(ic7);
        b8.setIcon(ic8);
        b9.setIcon(ic0); // Empty space
        setButtonBounds();

        levelLabel.setText("Level: " + currentLevel);
    }

    private void shuffleIcons() {
        java.util.List<Icon> icons = new java.util.ArrayList<>(); // Explicitly using java.util.List
        for (JButton button : buttons) {
            icons.add(button.getIcon());
        }
        Collections.shuffle(icons);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setIcon(icons.get(i));
        }
        if (currentLevel.equals("Easy")) {
            currentLevel = "Medium";
        } else if (currentLevel.equals("Medium")) {
            currentLevel = "Hard";
        } else {
            currentLevel = "Easy";
        }
        levelLabel.setText("Level: " + currentLevel);
    }

    public static void main(String[] args) {
        new PicPuzzle();
    }
}
