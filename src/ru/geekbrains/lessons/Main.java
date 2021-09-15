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
        field = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = ".";
    }

    public static void showField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

    public static boolean isFinishedGame() {
        int countFreespace = 0;
        for (String[] arr : field)
            for (String elem : arr)
                if (elem.equals("."))
                    countFreespace++;

        if ((field[0][0].equals("X") && field[0][1].equals("X") && field[0][2].equals("X"))
                || (field[1][0].equals("X") && field[1][1].equals("X") && field[1][2].equals("X"))
                || (field[2][0].equals("X") && field[2][1].equals("X") && field[2][2].equals("X"))
                || (field[0][0].equals("X") && field[1][0].equals("X") && field[2][0].equals("X"))
                || (field[0][1].equals("X") && field[1][1].equals("X") && field[2][1].equals("X"))
                || (field[0][2].equals("X") && field[1][2].equals("X") && field[2][2].equals("X"))
                || (field[0][0].equals("X") && field[1][1].equals("X") && field[2][2].equals("X"))
                || (field[0][2].equals("X") && field[1][1].equals("X") && field[2][0].equals("X"))
        ) {
            System.out.println("Игрок победил");
            return true;
        } else if(
                (field[0][0].equals("O") && field[0][1].equals("O") && field[0][2].equals("O"))
                || (field[1][0].equals("O") && field[1][1].equals("O") && field[1][2].equals("O"))
                || (field[2][0].equals("O") && field[2][1].equals("O") && field[2][2].equals("O"))
                || (field[0][0].equals("O") && field[1][0].equals("O") && field[2][0].equals("O"))
                || (field[0][1].equals("O") && field[1][1].equals("O") && field[2][1].equals("O"))
                || (field[0][2].equals("O") && field[1][2].equals("O") && field[2][2].equals("O"))
                || (field[0][0].equals("O") && field[1][1].equals("O") && field[2][2].equals("O"))
                || (field[0][2].equals("O") && field[1][1].equals("O") && field[2][0].equals("O"))
        ) {
            System.out.println("Компьютер победил");
            return true;
        } else if (countFreespace == 0) {
            System.out.println("Ничья");
            return true;
        }
        return false;
    }
}











