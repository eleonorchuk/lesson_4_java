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
        int[] pos = artificialIntelligence("X");
        int x = pos[0];
        int y = pos[1];
        while (isNotFinishMove) {
            Random rnd = new Random();

            if (!field[x][y].equals("X") && !field[x][y].equals("O")) {
                field[x][y] = "O";

                isNotFinishMove = false;
                System.out.println("Компьютер сделал ход:" + (pos[0] + 1) + ", " + (pos[1]+1));
            }
            x = rnd.nextInt(field.length - 1);
            y = rnd.nextInt(field.length - 1);
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

    public static int[] artificialIntelligence(String s)
    {
        int[] position = new int[]{0,0};
        int max = 0;
        int startDiag1 = 0;
        int diag1 = 0;
        int startDiag2 = 0;
        int diag2 = 0;

        for(int i = 0;i < field.length; i++) {
            int startLine = 0;
            int line = 0;
            int startRaw = 0;
            int raw = 0;

            for (int j = 0; j < field.length; j++) {
                if (field[i][j].equals(s)) {
                    line++;
                } else {
                    if (line > max) {
                        int endLine = 0;
                        for (int k = j; k < field.length; k++) {
                            if (field[i][k].equals("."))
                                endLine++;
                            else
                                break;
                        }
                        if ((startLine + line + endLine) >= 4) {
                            max = line;
                            position[0] = i;
                            if (startLine > 0)
                                position[1] = j - line - 1;
                            else
                                position[1] = j;
                        }
                    }
                    line = 0;
                    if ((field[i][j].equals(".")))
                        startLine++;
                    else
                        startLine = 0;
                }

                if (field[j][i].equals(s)) {
                    raw++;
                } else {
                    if (raw > max) {
                        int endRaw = 0;
                        for (int k = j; k < field.length; k++) {
                            if (field[k][i].equals("."))
                                endRaw++;
                            else
                                break;
                        }
                        if ((startRaw + raw + endRaw) >= 4) {
                            max = raw;
                            position[1] = i;
                            if (startRaw > 0)
                                position[0] = j - raw - 1;
                            else
                                position[0] = j;
                        }
                    }
                    raw = 0;
                    if ((field[j][i].equals(".")))
                        startRaw++;
                    else
                        startRaw = 0;
                }

            }
            if (line > max) {
                if (((startLine + line) >= 4) && (startLine > 0)) {
                    max = line;
                    position[0] = i;
                    position[1] = field.length - line - 1;
                }
            }
            if (raw > max) {
                if (((startRaw + raw) >= 4) && (startRaw > 0)) {
                    max = raw;
                    position[1] = i;
                    position[0] = field.length - raw - 1;
                }
            }

            if (field[i][i].equals(s)) {
                diag1++;
            } else {
                if (diag1 > max) {
                    int endDiag1 = 0;
                    for (int k = i; k < field.length; k++) {
                        if (field[k][k].equals("."))
                            endDiag1++;
                        else
                            break;
                    }
                    if ((startDiag1 + diag1 + endDiag1) >= 4) {
                        max = diag1;
                        if (startDiag1 > 0) {
                            position[0] = position[1] = i - diag1 - 1;
                        }
                        else
                            position[0] = position[1] = i;
                    }
                }
                diag1 = 0;
                if ((field[i][i].equals(".")))
                    startDiag1++;
                else
                    startDiag1 = 0;
            }

///
            if (field[i][field.length - 1 - i].equals(s)) {
                diag2++;
            } else {
                if (diag2 > max) {
                    int endDiag2 = 0;
                    for (int k = i; k < field.length; k++) {
                        if (field[k][field.length - k - 1].equals("."))
                            endDiag2++;
                        else
                            break;
                    }
                    if ((startDiag2 + diag2 + endDiag2) >= 4) {
                        max = diag2;
                        if (startDiag2 > 0) {
                            position[0] =  i - diag2 - 1;
                            position[1] = field.length - i + diag2;
                        }
                        else {
                            position[0] = i;
                            position[1] = field.length - i - 1;
                        }
                    }
                }
                diag2 = 0;
                if ((field[i][field.length - i - 1].equals(".")))
                    startDiag2++;
                else
                    startDiag2 = 0;
            }
        }
        if (diag1 > max) {
            if (((startDiag1 + diag1) >= 4) && (startDiag1 > 0)) {
                max = diag1;
                position[0] = position[1] = field.length - diag1 - 1;
            }
        }
        if (diag2 > max) {
            if (((startDiag2 + diag2) >= 4) && (startDiag2 > 0)) {
                max = diag2;
                position[0] = field.length - diag2 - 1;;
                position[1] = diag2;
            }
        }
        return position;
    }
}