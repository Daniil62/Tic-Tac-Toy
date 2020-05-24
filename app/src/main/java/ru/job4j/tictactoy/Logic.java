package ru.job4j.tictactoy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logic extends LogicInterface {
    private int[] combination;
    private List<String> field = Arrays.asList(
            "\t", "\t", "\t",
            "\t", "\t", "\t",
            "\t", "\t", "\t");
    private boolean queue = false;
    List<String> getField() {
        return field;
    }
    @Override
    public boolean hasFree() {
        boolean result = false;
        for (String symbol : field) {
            if (symbol.equals("\t")) {
                result = true;
                break;
            }
        }
        return result;
    }
    @Override
    public boolean tryMark(int id) {
        boolean result = false;
        String symbol;
        if (id < field.size() && id >= 0) {
            String cell = field.get(id);
            if (!queue && cell.equals("\t")) {
                symbol = "X";
                field.set(id, symbol);
                queue = true;
                result = true;
            } else if (queue && cell.equals("\t")){
                symbol = "O";
                field.set(id, symbol);
                queue = false;
                result = true;
            }
        }
        return result;
    }
    @Override
    public boolean isWin(String symbol) {
        boolean result = false;
        if (checkHorizontal(symbol) || checkVertical(symbol)
                || checkDiagonal(symbol)) {
            result = true;
        }
        return result;
    }
    private boolean checkHorizontal(String symbol) {
        int size = field.size();
        combination = new int[size / 3];
        boolean result = false;
        int count = 0;
        for (int i = 0; i < size; ++i) {
            String s = field.get(i);
            if (s.equals(symbol)) {
                combination[count] = field.indexOf(s);
                ++count;
            }
            if (count == size / 3) {
                result = true;
                break;
            }
        }
        return result;
    }
    private boolean checkVertical(String symbol) {
        int size = field.size();
        combination = new int[size / 3];
        boolean result = false;
        int count = 0;
        int c = 0;
        for (int i = 0; i < combination.length; ++i) {
            for (int j = 0; j < size; j += 3) {
                c++;
                String s = field.get(j);
                if (s.equals(symbol)) {
                    combination[count] = field.indexOf(s);
                    ++count;
                }
                if (count == size / 3) {
                    result = true;
                    break;
                }
                if (c == size) {
                    j += i;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
    private boolean checkDiagonal(String symbol) {
        int size = field.size();
        combination = new int[size / 3];
        boolean result = false;
        int count = 0;
        for (int i = 0; i < size; i += 4) {
            String s = field.get(i);
            if (s.equals(symbol)) {
                ++count;
                combination[count - 1] = field.indexOf(s);
            }
        }
        if (count == size / 3) {
            result = true;
        }
        if (!result) {
            count = 0;
            for (int i = size - 1; i < size; i += 2) {
                String s = field.get(i);
                if (s.equals(symbol)) {
                    ++count;
                    combination[count - 1] = field.indexOf(s);
                }
            }
            if (count == size / 3) {
                result = true;
            }
        }
        return result;
    }
    void reset() {
        queue = false;
        for (int i = 0; i < field.size(); ++i) {
            field.set(i, "\t");
        }
    }
}
