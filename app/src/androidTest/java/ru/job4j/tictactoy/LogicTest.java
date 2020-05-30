package ru.job4j.tictactoy;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class LogicTest {
    @Test
    public void checkTryMark() {
        Logic logic = new Logic();
        logic.tryMark(1);
        logic.tryMark(3);
        Assert.assertThat(logic.getField().get(1), is("X"));
        Assert.assertThat(logic.getField().get(3), is("O"));
    }
    @Test
    public void whenTryMarkImpossible() {
        Logic logic = new Logic();
        logic.tryMark(3);
        Assert.assertThat(logic.tryMark(3), is(false));
    }
    @Test
    public void whenHasFreeTrue() {
        Logic logic = new Logic();
        Assert.assertThat(logic.hasFree(), is(true));
    }
    @Test
    public void whenHasFreeFalse() {
        Logic logic = new Logic();
        for (int i = 0; i < logic.getField().size(); ++i) {
            logic.tryMark(i);
        }
        Assert.assertThat(logic.hasFree(), is(false));
    }

    @Test
    public void whenIsWinTrueAndWinnerIsXbyTopHorizontal() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(8);
        logic.tryMark(1);
        logic.tryMark(6);
        logic.tryMark(2);
        Assert.assertThat(logic.isWin("X"), is(true));
    }
    @Test
    public void whenIsWinTrueAndWinnerIsObyReverseDiagonal() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(2);
        logic.tryMark(8);
        logic.tryMark(4);
        logic.tryMark(3);
        logic.tryMark(6);
        Assert.assertThat(logic.isWin("O"), is(true));
    }
    @Test
    public void whenIsWinTrueAndWinnerIsXbyDiagonal() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(6);
        logic.tryMark(8);
        logic.tryMark(5);
        logic.tryMark(4);
        Assert.assertThat(logic.isWin("X"), is(true));
    }
    @Test
    public void whenIsWinTrueAndWinnerIsObySecondVertical() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(4);
        logic.tryMark(6);
        logic.tryMark(1);
        logic.tryMark(8);
        logic.tryMark(7);
        Assert.assertThat(logic.isWin("O"), is(true));
    }
    @Test
    public void whenIsWinTrueAndWinnerIsXbyThirdHorizontal() {
        Logic logic = new Logic();
        logic.tryMark(7);
        logic.tryMark(1);
        logic.tryMark(6);
        logic.tryMark(0);
        logic.tryMark(8);
        Assert.assertThat(logic.isWin("X"), is(true));
    }
    @Test
    public void whenIsWinFalse() {
        Logic logic = new Logic();
        logic.tryMark(5);
        logic.tryMark(1);
        logic.tryMark(0);
        logic.tryMark(3);
        logic.tryMark(2);
        logic.tryMark(4);
        logic.tryMark(7);
        logic.tryMark(8);
        logic.tryMark(6);
        Assert.assertThat(logic.isWin("O"), is(false));
        Assert.assertThat(logic.isWin("X"), is(false));
    }
    @Test
    public void checkAndroidLogicBySequence1FirstHorizontalX() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(4);
        logic.tryMark(1);
        Assert.assertThat(logic.androidAnalyse("X"), is(2));
    }
    @Test
    public void checkAndroidLogicBySequence2FirstVerticalX() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(4);
        logic.tryMark(6);
        Assert.assertThat(logic.androidAnalyse("X"), is(3));
    }
    @Test
    public void checkAndroidLogicBySequence3DiagonalX() {
        Logic logic = new Logic();
        logic.tryMark(4);
        logic.tryMark(2);
        logic.tryMark(8);
        Assert.assertThat(logic.androidAnalyse("X"), is(0));
    }
    @Test
    public void checkAndroidLogicBySequence2ReverseDiagonalO() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(2);
        logic.tryMark(8);
        logic.tryMark(6);
        Assert.assertThat(logic.androidAnalyse("O"), is(4));
    }
    @Test
    public void checkAndroidLogicWhenNoExplicitVariant() {
        Logic logic = new Logic();
        logic.tryMark(0);
        logic.tryMark(1);
        logic.tryMark(2);
        logic.tryMark(6);
        logic.tryMark(7);
        Assert.assertThat(logic.androidAnalyse("O"), is(-1));
    }
    @Test
    public void checkGetField() {
        Logic logic = new Logic();
        List<String> list = Arrays.asList(
                "\t", "X", "O",
                "\t", "\t", "X",
                "O", "X");
        logic.tryMark(1);
        logic.tryMark(2);
        logic.tryMark(5);
        logic.tryMark(6);
        logic.tryMark(7);
        Assert.assertThat(logic.getField().containsAll(list), is(true));
    }
    @Test
    public void checkGetCombination() {
        Logic logic = new Logic();
        List<Integer> combination = Arrays.asList(0, 4, 8);
        logic.tryMark(0);
        logic.tryMark(6);
        logic.tryMark(8);
        logic.tryMark(5);
        logic.tryMark(4);
        Assert.assertThat(logic.isWin("X"), is(true));
        Assert.assertThat(logic.getCombination().equals(combination), is(true));
    }
}
