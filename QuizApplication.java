// Java Quiz Application
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApplication extends JFrame implements ActionListener {
    JLabel questionLabel, questionCounterLabel, timerLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup bg;
    JButton nextButton, submitButton;

    // Quiz Data
    String[] questions = {
        "Which language runs in a web browser?",
        "What does HTML stand for?",
        "What year was Java released?",
        "Who is the founder of Microsoft?",
        "Which company developed Java?",
        "Which planet is known as the Red Planet?",
        "What is the capital of France?",
        "Which data structure uses LIFO order?",
        "What is the largest mammal?",
        "Which method is used to start a thread in Java?",
        "What is the boiling point of water in Celsius?",
        "Which keyword is used to inherit a class in Java?",
        "Who wrote 'Romeo and Juliet'?",
        "What is the square root of 64?",
        "Which ocean is the largest?"
    };
    String[][] choices = {
        {"Java", "C", "Python", "JavaScript"},
        {"HyperText Markup Language", "HyperText Markdown Language", "HyperTool Multi Language", "None"},
        {"1996", "1995", "2000", "1990"},
        {"Steve Jobs", "Bill Gates", "Larry Page", "Elon Musk"},
        {"Sun Microsystems", "Oracle", "Google", "Apple"},
        {"Mars", "Venus", "Jupiter", "Saturn"},
        {"Berlin", "Madrid", "Paris", "Rome"},
        {"Queue", "Array", "Stack", "LinkedList"},
        {"Elephant", "Blue Whale", "Giraffe", "Hippopotamus"},
        {"run()", "start()", "execute()", "init()"},
        {"90°C", "80°C", "100°C", "120°C"},
        {"implement", "extends", "inherits", "super"},
        {"Mark Twain", "William Shakespeare", "Jane Austen", "Charles Dickens"},
        {"6", "8", "10", "9"},
        {"Atlantic", "Indian", "Arctic", "Pacific"}
    };
    int[] answers = {
        3, 0, 1, 1, 0,
        0, 2, 2, 1, 1,
        2, 1, 1, 1, 3
    };

    int current = 0;
    int score = 0;
    int timeLeft = 15;
    Timer timer;
    JPanel centerPanel;

    QuizApplication() {
        setTitle("Java Quiz Application");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(60, 90, 153));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        questionCounterLabel = new JLabel("Question 1 of " + questions.length);
        questionCounterLabel.setForeground(Color.WHITE);
        questionCounterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(questionCounterLabel);

        questionLabel = new JLabel("Question here");
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Options
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        bg = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            options[i].setActionCommand(String.valueOf(i));
            options[i].addActionListener(this);
            bg.add(options[i]);
            centerPanel.add(options[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(this);

        submitButton = new JButton("Submit");
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);

        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time left: 15s", JLabel.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 20));
        bottomPanel.add(timerLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion(current);
        startTimer();

        setVisible(true);
    }

    void loadQuestion(int index) {
        bg.clearSelection();
        nextButton.setEnabled(false);
        questionLabel.setText("Q" + (index + 1) + ": " + questions[index]);
        questionCounterLabel.setText("Question " + (index + 1) + " of " + questions.length);
        for (int i = 0; i < 4; i++) {
            options[i].setText(choices[index][i]);
            options[i].setVisible(true);
        }
        timeLeft = 15;
        timerLabel.setText("Time left: " + timeLeft + "s");
    }

    boolean checkAnswer() {
        ButtonModel selected = bg.getSelection();
        if (selected != null) {
            int selectedIndex = Integer.parseInt(selected.getActionCommand());
            return selectedIndex == answers[current];
        }
        return false;
    }

    void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    timer.stop();
                    autoNext();
                }
            }
        });
        timer.start();
    }

    void autoNext() {
        if (checkAnswer()) score++;
        current++;
        if (current < questions.length) {
            loadQuestion(current);
            timer.restart();
        } else {
            finishQuiz();
        }
    }

    void finishQuiz() {
        timer.stop();
        questionLabel.setText("Quiz Completed!");
        questionCounterLabel.setText("");
        timerLabel.setText("");
        nextButton.setVisible(false);
        submitButton.setVisible(false);

        // Hide option buttons
        for (JRadioButton btn : options) {
            btn.setVisible(false);
        }

        // Remove center panel
        getContentPane().remove(centerPanel);

        // Score panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel = new JLabel("Your Score: " + score + " / " + questions.length, JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 22));
        resultLabel.setForeground(new Color(0, 128, 0));
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        JButton restart = new JButton("Restart Quiz");
        restart.setFont(new Font("Arial", Font.PLAIN, 16));
        restart.addActionListener(e -> {
            dispose();
            new QuizApplication();
        });

        JPanel restartPanel = new JPanel();
        restartPanel.add(restart);
        resultPanel.add(restartPanel, BorderLayout.SOUTH);

        add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JRadioButton) {
            nextButton.setEnabled(true);
        } else if (e.getSource() == nextButton) {
            if (checkAnswer()) score++;
            current++;
            if (current < questions.length) {
                loadQuestion(current);
                if (current == questions.length - 1) {
                    nextButton.setEnabled(false);
                    submitButton.setEnabled(true);
                }
            } else {
                finishQuiz();
            }
        } else if (e.getSource() == submitButton) {
            if (checkAnswer()) score++;
            finishQuiz();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApplication());
    }
}