package ru.kazan.aes.calendar1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by AES on 13.04.2016.
 */

class MainFrame extends JFrame {
    private final JTextArea textMonth;
    private int yearI;
    private int monthInt;
    private int yearInt;

    public MainFrame() {
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel managePanel = new JPanel(); //панель со списком
        managePanel.setLayout(new GridLayout(1, 2)); //создание панели

        JComboBox<String> comboMonth = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {                 //наполнение списка месяцев значениями
            Locale loc = Locale.forLanguageTag("ru");   //локализация
            comboMonth.addItem(Month.of(i).getDisplayName(TextStyle.FULL_STANDALONE, loc));
        }

        comboMonth.setSelectedIndex(5); //значение по умолчанию
        monthInt = comboMonth.getSelectedIndex();
        managePanel.add(comboMonth); //расположение списка на панели

        JComboBox<Integer> comboYear = new JComboBox<>();
        for (int i = 1900; i <= 2100; i++)    //наполнение списка годов значениями
            comboYear.addItem(i);
        comboYear.setSelectedIndex(53); //значение по умолчанию
        yearI = (int) comboYear.getSelectedItem();
        yearInt = yearI;
        managePanel.add(comboYear); //раположение списка на панели

        // будем следить за нажатиями кнопки
        comboMonth.addActionListener(new comboMonthL());
        comboYear.addActionListener(new comboYearL());

        JPanel calendarPanel = new JPanel(); //панель с календарём
        calendarPanel.add(textMonth = new JTextArea(7, 14)); //добавление поля вывода календаря

        textMonth.setEditable(false);
        textMonth.setBackground(Color.LIGHT_GRAY);
        Font font = new Font("Courier New", Font.BOLD, 12);
        textMonth.setFont(font);

        textMonth.setText(outCalendar(comboMonth.getSelectedIndex(), yearI)); //вывод чисел календаря

        add(managePanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.SOUTH);
        managePanel.setVisible(true);
        calendarPanel.setVisible(true);

        pack();
    }

    /*
        Метод, создающий на выходе строку со списком чисел месяца с переносами по неделям
     */
    private String outCalendar(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int dow = cal.get(Calendar.DAY_OF_WEEK) - 1; //день недели, где 1 - понедельник
        int dom = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //число дней в месяце

        //создать накопительный список символов с разбиением на недели,
        //первая строка - названия дней недели
        StringBuilder calendar = new StringBuilder();

        //названия дней недели
        for (int i = 1; i <= 7; i++) {
            Locale loc = Locale.forLanguageTag("ru");   //локализация
            calendar.append(DayOfWeek.of(i).getDisplayName(TextStyle.SHORT_STANDALONE, loc).toUpperCase()).append(" ");
        }
        calendar.setLength(calendar.length() - 1); //удаление последнего пробела дней недели
        calendar.append("\n");

        //установка отступов для 1 числа
        int whiteSpacesBefore = 3 * (dow - 1);
        if (dow > 0)
            for (int i = 0; i < whiteSpacesBefore; i++)
                calendar.append(" ");
        else
            calendar.append("                  "); //отступ для воскресенья

        //вывод чисел
        int dow_marker = dow;
        if (dow == 0) dow_marker = 7;

        for (int i = 1; i <= dom; i++) {
            if (dow_marker < 7){
                if (i < 10)
                    calendar.append(" ").append(i).append(" ");
                else
                    calendar.append(i).append(" ");
                dow_marker++;
            }
            else
            {
                if (i < 10)
                    calendar.append(" ").append(i).append("\n");
                else
                    calendar.append(i).append("\n");
                dow_marker = 1;
            }
        }

        return calendar.toString();
    }

    // класс - слушатель списка месяцев
    private class comboMonthL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox month = (JComboBox) e.getSource();
            monthInt = month.getSelectedIndex();

            textMonth.setText(outCalendar(monthInt, yearInt)); //вызов метода отображения календаря
        }
    }

    // класс - слушатель списка годов
    private class comboYearL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox year = (JComboBox) e.getSource();
            yearI = (int) year.getSelectedItem();
            yearInt = yearI;

            textMonth.setText(outCalendar(monthInt, yearInt)); //вызов метода отображения календаря
        }
    }
}