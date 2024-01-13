import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class OnlineTest extends JFrame implements ActionListener {
    JLabel nameLabel, l, timerLabel;
    JTextField nameField;
    JRadioButton jb[] = new JRadioButton[5];
    JButton startButton, b1, b2, b3;
    ButtonGroup bg;
    int count = 0, current = 0, x = 1, y = 1, now = 0;
    int m[] = new int[10];
    Timer timer;
    int seconds;
    int timeRemaining = 300;
    String playerName;

    OnlineTest(String s) {
        super(s);

        nameLabel = new JLabel("Nama: ");
        nameLabel.setBounds(30, 10, 60, 20);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(80, 10, 150, 20);
        add(nameField);

        startButton = new JButton("Mulai Ujian");
        styleButton(startButton);
        startButton.setBounds(250, 10, 120, 20);
        startButton.addActionListener(this);
        add(startButton);

        l = new JLabel();
        add(l);

        bg = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            jb[i] = new JRadioButton();
            jb[i].setFont(new Font("Arial", Font.PLAIN, 14));
            add(jb[i]);
            bg.add(jb[i]);
        }

        b1 = new JButton("Next");
        b2 = new JButton("Bookmark");
        b3 = new JButton("Back");

        styleButton(b1);
        styleButton(b2);
        styleButton(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        add(b1);
        add(b2);
        add(b3);

        timerLabel = new JLabel();
        timerLabel.setBounds(400, 40, 150, 20);
        add(timerLabel);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                setTitle("Online Test - Time: " + seconds + " seconds");
            }
        });

        setLayout(null);
        setLocation(250, 100);
        getContentPane().setBackground(new Color(220, 240, 255));
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            playerName = nameField.getText();
            startButton.setEnabled(false);
            nameField.setEditable(false);
            set();
            timer.start();
        } else if (e.getSource() == b1) {
            if (check())
                count = count + 1;
            current++;
            set();
            if (current == 9) {
                b1.setEnabled(false);
                styleButton(b2);
                b2.setText("Result");
            }
        } else if (e.getActionCommand().equals("Bookmark")) {
            JButton bk = new JButton("Bookmark" + x);
            styleButton(bk);
            bk.setBounds(480, 20 + 30 * x, 100, 30);
            add(bk);
            bk.addActionListener(this);
            m[x] = current;
            x++;
            current++;
            set();
            if (current == 9)
                b2.setText("Result");
            setVisible(false);
            setVisible(true);
        } else if (e.getActionCommand().equals("Result")) {
            if (check())
                count = count + 1;
            timer.stop();
            showResult();
        } else if (e.getSource() == timer) {
            timeRemaining--;
            if (timeRemaining <= 0) {
                timer.stop();
                showResult();
            }
            updateTimerLabel();
        } else if (e.getSource() == b3) {
            if (current > 0) {
                current--;
                set();
                if (current == 0) {
                    b3.setEnabled(false);
                }
            }
        } else {
            for (int i = 0, y = 1; i < x; i++, y++) {
                if (e.getActionCommand().equals("Bookmark" + y)) {
                    if (check())
                        count = count + 1;
                    now = current;
                    current = m[y];
                    set();
                    ((JButton) e.getSource()).setEnabled(false);
                    current = now;
                }
            }
        }
    }

    void set() {
        if (current == 0) {
            l.setText("1. PBO adalah mata kuliah yang menggunakan bahasa?");
            jb[0].setText("Java");
            jb[1].setText("C++");
            jb[2].setText("C");
            jb[3].setText("Inggris");
        }
        if (current == 1) {
            l.setText("2. Abstraksi dari sesuatu yang mewakili dunia nyata adalah pengertian…");
            jb[0].setText("Abstraksi");
            jb[1].setText("Class");
            jb[2].setText("Objek");
            jb[3].setText("Atribut");
        }
        if (current == 2) {
            l.setText("3. Kumpulan dari obyek-obyek dengan karakteristik yang sama adalah pengertian...");
            jb[0].setText("Abstraksi");
            jb[1].setText("Class");
            jb[2].setText("Objek");
            jb[3].setText("Atribut");
        }
        if (current == 3) {
            l.setText("4. Nama lain dari kata pewarisan dalam java adalah …");
            jb[0].setText("Inheritance");
            jb[1].setText("Interface");
            jb[2].setText("Overriding");
            jb[3].setText("Overloading");
        }
        if (current == 4) {
            l.setText("5. Class dalam java yang mempunyai satu abstact metode disebut... ");
            jb[0].setText("Abstract class");
            jb[1].setText("Class");
            jb[2].setText("Multiple interface");
            jb[3].setText("Implementasi");
        }
        if (current == 5) {
            l.setText("6. Class dalam javayang digunakan menggunakan array dan character disebut …");
            jb[0].setText("Class math");
            jb[1].setText("Class string");
            jb[2].setText("Class string buffer");
            jb[3].setText("Class wrapper");
        }
        if (current == 6) {
            l.setText("7. Berikut ini yang bukan merupakan tipe data wrapper adalah...");
            jb[0].setText("Boolean");
            jb[1].setText("Byte");
            jb[2].setText("Double");
            jb[3].setText("Int");
        }
        if (current == 7) {
            l.setText("8. Dalam Pemrograman java telah memiliki 2 kategori tipe data, yaitu :");
            jb[0].setText("Primitive, Reference");
            jb[1].setText("Variabel, Contructor");
            jb[2].setText("Double, Int");
            jb[3].setText("Primitive, Int");
        }
        if (current == 8) {
            l.setText("9. Kesatuan antara data dan fungsi, disebut..");
            jb[0].setText("Class");
            jb[1].setText("Fungsi");
            jb[2].setText("Variabel");
            jb[3].setText("Objek");
        }
        if (current == 9) {
            l.setText("10. Modifier yang hanya dikenal oleh dirinya dan kelas turunannya adalah…");
            jb[0].setText("Class");
            jb[1].setText("Protected");
            jb[2].setText("Private");
            jb[3].setText("Public");
            timer.stop();
            int timeInSeconds = seconds;
            updateTimerLabel();
            saveResultToFile(playerName, count, timeInSeconds);
        }

        l.setBounds(30, 40, 450, 20);
        for (int i = 0, j = 0; i <= 90; i += 30, j++)
            jb[j].setBounds(50, 80 + i, 200, 20);

        b1.setBounds(100, 240, 100, 30);
        b2.setBounds(270, 240, 100, 30);
        b3.setBounds(170, 240, 100, 30);

        if (timerLabel == null) {
            timerLabel = new JLabel();
            timerLabel.setBounds(400, 40, 150, 20);
            add(timerLabel);
        }

        if (timer == null) {
            timer = new Timer(1000, this);
            timer.start();
        }
    }

    boolean check() {
        if (current == 0)
            return jb[0].isSelected();

        else if (current == 1)
            return jb[2].isSelected();

        else if (current == 2)
            return jb[1].isSelected();

        else if (current == 3)
            return jb[0].isSelected();

        else if (current == 4)
            return jb[0].isSelected();

        else if (current == 5)
            return jb[1].isSelected();

        else if (current == 6)
            return jb[3].isSelected();

        else if (current == 7)
            return jb[0].isSelected();

        else if (current == 8)
            return jb[3].isSelected();

        else if (current == 9)
            return jb[1].isSelected();

        return false;
    }

    void showResult() {
        int finalScore = count;
        String resultMessage = "Hasil akhir " + playerName + ": " + finalScore + "/10";
        resultMessage += "\nWaktu Pengerjaan: " + seconds + " detik";
        JOptionPane.showMessageDialog(this, resultMessage, "Hasil", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    void updateTimerLabel() {
        if (timerLabel != null) {
            timerLabel.setText("\n\n" + "Waktu Tersisa: " + timeRemaining + " detik");
        }
    }

    void saveResultToFile(String playerName, int finalScore, int timeInSeconds) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("hasil_ujian.txt", true))) {
            writer.write("Hasil akhir " + playerName + ": " + finalScore + "/10\n");
            writer.write("Waktu Pengerjaan: " + timeInSeconds + " detik\n");
            System.out.println("Hasil disimpan ke hasil_ujian.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String s[]) {
        SwingUtilities.invokeLater(() -> new OnlineTest("Ujian Online Java"));
    }
}
