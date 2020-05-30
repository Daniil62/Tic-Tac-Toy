package ru.job4j.tictactoy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logic extends LogicInterface {
    private List<String> field = Arrays.asList(
            "\t", "\t", "\t",
            "\t", "\t", "\t",
            "\t", "\t", "\t");
    private List<Integer> combination = new ArrayList<>();
    private boolean queue = false;
    List<String> getField() {
        return field;
    }
    void setField (List<String> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                field.set(i, list.get(i));
            }
        }
    }
    List<Integer> getCombination() {
        return combination;
    }
    boolean getQueue() {
        return queue;
    }
    void setQueue(boolean value) {
        this.queue = value;
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
    boolean checkCleanField() {
        boolean result = true;
        for (String symbol : field) {
            if (!symbol.equals("\t")) {
                result = false;
            }
        }
        return result;
    }
    @Override
    public boolean tryMark(int id) {
        boolean result = false;
        String symbol = "\t";
        if (id < field.size() && id > -1 && hasFree()) {
            String cell = field.get(id);
            if (!queue && cell.equals("\t")) {
                symbol = "X";
                result = true;
            }
            else if (queue && cell.equals("\t")){
                symbol = "O";
                result = true;
            }
            queue = !queue;
            field.set(id, symbol);
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
        List<Integer> temp = new ArrayList<>();
        int size = field.size();
        String s = "";
        boolean result = false;
        for (int i = 0; i < size; ++i) {
            if (field.get(i).equals(symbol)) {
                temp.add(i);
                s += symbol;
            }
            if (s.equals(symbol + symbol + symbol)) {
                combination = temp;
                result = true;
                break;
            }
            else if (s.length() != size / 3 && (i + 1) % 3 == 0) {
                temp.clear();
                s = "";
            }
        }
        return result;
    }
    private boolean checkVertical(String symbol) {
        List<Integer> temp = new ArrayList<>();
        int size = field.size();
        boolean result = false;
        for (int i = 0; i < size / 3; ++i) {
            String s = "";
            for (int j = i; j < size; j += size / 3) {
                if (field.get(j).equals(symbol)) {
                    temp.add(j);
                    s += symbol;
                }
                if (s.equals(symbol + symbol + symbol)) {
                    combination = temp;
                    result = true;
                }
            }
            if (result) {
                break;
            } else {
                temp.clear();
            }
        }
        return result;
    }
    private boolean checkDiagonal(String symbol) {
        List<Integer> temp = new ArrayList<>();
        int size = field.size();
        boolean result = false;
        String s = "";
        for (int i = 0; i < size; i += 4) {
            if (!field.get(i).equals(symbol)) {
                break;
            }
            if (field.get(i).equals(symbol)) {
                temp.add(i);
                s += symbol;
            }
            if (s.equals(symbol + symbol + symbol)) {
                combination = temp;
                result = true;
                break;
            }
        }
        if (!result) {
            temp.clear();
            s = "";
            for (int i = size / 3 - 1; i < size; i += 2) {
                if (!field.get(i).equals(symbol)) {
                    break;
                }
                if (field.get(i).equals(symbol)) {
                    temp.add(i);
                    s += symbol;
                }
                if (s.equals(symbol + symbol + symbol)) {
                    combination = temp;
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    int androidAnalyse(String symbol) {
        String sequence1 = symbol + symbol + "\t";
        String sequence2 = symbol + "\t" + symbol;
        String sequence3 = "\t" + symbol + symbol;
        boolean suitable = false;
        int size = field.size();
        int count;
        int result = -1;
        String str = "";
        for (int i = 0; i < size; ++i) {
            str += field.get(i);
            if (str.length() == 3) {
                if (str.equals(sequence1)) {
                    result = i;
                    suitable = true;
                    break;
                }
                if (str.equals(sequence2)) {
                    result = --i;
                    suitable = true;
                    break;
                }
                if (str.equals(sequence3)) {
                    result = (i - 2);
                    suitable = true;
                    break;
                }
            }
            if ((i + 1) % 3 == 0) {
                str = "";
            }
        }
        if (!suitable) {
            str = "";
            for (int i = 0; i < size / 3; ++i) {
                for (int j = i; j < size; j += 3) {
                    str += field.get(j);
                    if (str.length() == 3) {
                        if (str.equals(sequence1)) {
                            result = j;
                            suitable = true;
                            break;
                        }
                        if (str.equals(sequence2)) {
                        result = (j - 3);
                        suitable = true;
                        break;
                        }
                        if (str.equals(sequence3)) {
                            result = (i);
                            suitable = true;
                            break;
                        }
                        if (!str.equals(sequence1) && !str.equals(sequence2)
                            && !str.equals(sequence3)) {
                            str = "";
                        }
                    }
                }
            }
        }
        if (!suitable) {
            str = "";
            count = 4;
            for (int i = 0; i < size; i += count) {
                str += field.get(i);
                if (str.length() == 3) {
                    if (str.equals(sequence1)) {
                        result = i;
                        break;
                    }
                    if (str.equals(sequence2)) {
                        result = (i - count);
                        break;
                    }
                    if (str.equals(sequence3)) {
                        result = (i - count * 2);
                        break;
                    }
                    if (!str.equals(sequence1) && !str.equals(sequence2)
                            && !str.equals(sequence3) && !suitable) {
                        suitable = true;
                        count = 2;
                        i = 0;
                        str = "";
                    }
                }
            }
        }
        return result;
    }
}
