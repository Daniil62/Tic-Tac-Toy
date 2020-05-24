package ru.job4j.tictactoy;

public abstract class LogicInterface {
    public abstract boolean tryMark(int id);
    public abstract boolean hasFree();
    public abstract boolean isWin(String symbol);
}
