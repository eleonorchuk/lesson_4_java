package ru.geekbrains.lessons;

import java.util.Scanner;
import java.util.Random;

public class Main {
    static String[][] field;

    public static void main(String[] args) {
        initField();
        showField();
        while (!isFinishedGame()) {
            movePlayer();
            showField();

            if (isFinishedGame())
                break;

            movePC();
            showField();
        }
        System.out.println("Game over");
    }

    public static void initField() {
        field = new String[5][5];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                field[i][j] = ".";
    }

    public static void showField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j] + "");
            }
            System.out.println();
        }
    }

    public static void movePlayer() {
        boolean isNotFinishMove = true;
        while (isNotFinishMove) {
            try {
                System.out.println("Выберите строчку и столбец куда нужно ходить:");
                Scanner sc = new Scanner(System.in);
                int x = sc.nextInt() - 1;
                int y = sc.nextInt() - 1;
                if (x >= 0 && x < field.length && y >= 0 && y < field.length) {
                    if (!field[x][y].equals("X") && !field[x][y].equals("O")) {
                        field[x][y] = "X";
                        isNotFinishMove = false;
                    } else {
                        System.out.println("Тут уже сделан ход");
                        return;
                    }
                } else {
                    System.out.println("Вы ушли за пределы поля");
                }
            }catch(Exception e)
            {
                System.out.println("Не корректный ввод данных");
            }
        }
    }

    public static void movePC() {
        boolean isNotFinishMove = true;
        while (isNotFinishMove) {
            Random rnd = new Random();
            int x = rnd.nextInt(field.length - 1);
            int y = rnd.nextInt(field.length - 1);
            if (!field[x][y].equals("X") && !field[x][y].equals("O")) {
                field[x][y] = "O";
                isNotFinishMove = false;
                System.out.println("Компьютер сделал ход");
            }
        }
    }
    public static boolean isWinner(String s)
            // s = "X" s = "O"
    {
        int diagonal1 = 0;
        int diagonal2 = 0;
        for(int i = 0;i < field.length; i++)
        {
            int line = 0;
            int row = 0;
            for(int j = 0;j < field.length; j++)
            {
                if (field[i][j].equals(s))
                    line++;
                else
                    line = 0;

                if (field[j][i].equals(s))
                    row++;
                else
                    row = 0;
                if ((line == 4) || (row == 4))
                    return true;
            }

            if (field[i][i].equals(s))
                diagonal1++;
            else
                diagonal1 = 0;

            if (diagonal1 == 4)
                return true;

            if (field[i][field.length - 1 - i].equals(s))
                diagonal2++;
            else
                diagonal2 = 0;
            if(diagonal2 == 4)
                return true;
        }
        return false;
    }

    public static boolean isFinishedGame() {
        int countFreespace = 0;
        for (String[] arr : field)
            for (String elem : arr)
                if (elem.equals("."))
                    countFreespace++;

        if (isWinner("X"))
        {
            System.out.println("Игрок победил");
            return true;
        } else if (isWinner("O"))
        {
            System.out.println("Компьютер победил");
            return true;
        } else if (countFreespace == 0) {
            System.out.println("Ничья");
            return true;
        }
        return false;
    }
}











