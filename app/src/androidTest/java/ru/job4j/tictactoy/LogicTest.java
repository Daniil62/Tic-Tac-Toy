package ru.job4j.tictactoy;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class LogicTest {
    @Test
    public void checkTryMark() {
        Logic logic = new Logic();
        logic.tryMark(1);
        logic.tryMark(3);
        String expectedX = "X";
        String expectedO = "O";
        Assert.assertThat(logic.getField().get(1), is(expectedX));
        Assert.assertThat(logic.getField().get(3), is(expectedO));
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
    public void whenCheckFreeFalse() {
        Logic logic = new Logic();
        for (int i = 0; i < logic.getField().size(); ++i) {
            logic.tryMark(i);
        }
        Assert.assertThat(logic.hasFree(), is(false));
    }
    @Test
    public void checkReset() {
        Logic logic = new Logic();
        for (int i = 0; i < logic.getField().size(); ++i) {
            logic.tryMark(i);
        }
        logic.reset();
        Assert.assertThat(logic.hasFree(), is(true));
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
    public void whenIsWinFalse() {
        Logic logic = new Logic();
        Assert.assertThat(logic.isWin("O"), is(false));
    }
    @Test
    public void whenIsWinTrueAndWinnerIsObyReverseDiagonal() {
        Logic logic = new Logic();
        logic.reset();
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
        logic.reset();
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
        logic.reset();
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
        logic.reset();
        logic.tryMark(7);
        logic.tryMark(1);
        logic.tryMark(6);
        logic.tryMark(0);
        logic.tryMark(8);
        Assert.assertThat(logic.isWin("X"), is(true));
    }
}
