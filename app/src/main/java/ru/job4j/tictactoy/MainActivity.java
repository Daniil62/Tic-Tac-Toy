package ru.job4j.tictactoy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private boolean queue;
    private boolean oppSelect;
    private boolean firstStep;
    private int[][] field;
    private int[] combination;

    @Override
    protected void onPause() {
        super.onPause();
        Switch opponentSelector = findViewById(R.id.opponentSelector);
        Switch queuePriority = findViewById(R.id.queueSelector);
        SharedPreferences.Editor editor = getSharedPreferences(
                "selector", Context.MODE_PRIVATE).edit();
        editor.putBoolean("oppSelector", opponentSelector.isChecked());
        editor.apply();
        editor = getSharedPreferences(
                "selector2", Context.MODE_PRIVATE).edit();
        editor.putBoolean("queueSelector", queuePriority.isChecked());
        editor.apply();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Button one = findViewById(R.id.button1_1);
        Button two = findViewById(R.id.button1_2);
        Button three = findViewById(R.id.button1_3);
        Button four = findViewById(R.id.button2_1);
        Button five = findViewById(R.id.button2_2);
        Button six = findViewById(R.id.button2_3);
        Button seven = findViewById(R.id.button3_1);
        Button eight = findViewById(R.id.button3_2);
        Button nine = findViewById(R.id.button3_3);
        outState.putString("oneText", one.getText().toString());
        outState.putString("twoText", two.getText().toString());
        outState.putString("threeText", three.getText().toString());
        outState.putString("fourText", four.getText().toString());
        outState.putString("fiveText", five.getText().toString());
        outState.putString("sixText", six.getText().toString());
        outState.putString("sevenText", seven.getText().toString());
        outState.putString("eightText", eight.getText().toString());
        outState.putString("nineText", nine.getText().toString());
        outState.getIntArray("combination");
        outState.putBoolean("queue", queue);
        outState.putBoolean("opSelect", oppSelect);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Button one = findViewById(R.id.button1_1);
        Button two = findViewById(R.id.button1_2);
        Button three = findViewById(R.id.button1_3);
        Button four = findViewById(R.id.button2_1);
        Button five = findViewById(R.id.button2_2);
        Button six = findViewById(R.id.button2_3);
        Button seven = findViewById(R.id.button3_1);
        Button eight = findViewById(R.id.button3_2);
        Button nine = findViewById(R.id.button3_3);
        one.setText(savedInstanceState.getString("oneText"));
        two.setText(savedInstanceState.getString("twoText"));
        three.setText(savedInstanceState.getString("threeText"));
        four.setText(savedInstanceState.getString("fourText"));
        five.setText(savedInstanceState.getString("fiveText"));
        six.setText(savedInstanceState.getString("sixText"));
        seven.setText(savedInstanceState.getString("sevenText"));
        eight.setText(savedInstanceState.getString("eightText"));
        nine.setText(savedInstanceState.getString("nineText"));
        combination = savedInstanceState.getIntArray("combination");
        queue = savedInstanceState.getBoolean("queue");
        oppSelect = savedInstanceState.getBoolean("opSelect");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button one = findViewById(R.id.button1_1);
        Button two = findViewById(R.id.button1_2);
        Button three = findViewById(R.id.button1_3);
        Button four = findViewById(R.id.button2_1);
        Button five = findViewById(R.id.button2_2);
        Button six = findViewById(R.id.button2_3);
        Button seven = findViewById(R.id.button3_1);
        Button eight = findViewById(R.id.button3_2);
        Button nine = findViewById(R.id.button3_3);
        Switch opponentSelector = findViewById(R.id.opponentSelector);
        Switch queuePriority = findViewById(R.id.queueSelector);
        one.setOnClickListener(this::cellMark);
        two.setOnClickListener(this::cellMark);
        three.setOnClickListener(this::cellMark);
        four.setOnClickListener(this::cellMark);
        five.setOnClickListener(this::cellMark);
        six.setOnClickListener(this::cellMark);
        seven.setOnClickListener(this::cellMark);
        eight.setOnClickListener(this::cellMark);
        nine.setOnClickListener(this::cellMark);
        SharedPreferences preferences = getSharedPreferences(
                "selector", Context.MODE_PRIVATE);
        boolean oppSelectState = preferences.getBoolean("oppSelector", true);
        opponentSelector.setChecked(oppSelectState);
        opponentSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oppSelect = opponentSelector.isChecked();
                queuePriority.setEnabled(oppSelect);
                if (checkCleanField()) {
                    androidInit();
                }
                androidInGame();
            }
        });
        preferences = getSharedPreferences(
                "selector2", Context.MODE_PRIVATE);
        boolean queueSelectState = preferences.getBoolean("queueSelector", true);
        queuePriority.setChecked(queueSelectState);
        firstStep = queuePriority.isChecked();
        queuePriority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                firstStep = queuePriority.isChecked();
                if (checkCleanField()) {
                    queue = firstStep;
                    androidInGame();
                }
            }
        });
        this.field = new int[][]{
                {one.getId(), two.getId(), three.getId()},
                {four.getId(), five.getId(), six.getId()},
                {seven.getId(), eight.getId(), nine.getId()}
        };
        oppSelect = opponentSelector.isChecked();
        queuePriority.setEnabled(oppSelect);
        androidInit();
    }
    private void androidInit() {
        Switch queuePriority = findViewById(R.id.queueSelector);
        if (queuePriority.isChecked()) {
            queue = true;
            androidInGame();
        }
        queue = false;
    }
    private boolean checkCleanField() {
        boolean result = true;
        Button button;
        for (int[] array : field) {
            for (int id : array) {
                button = findViewById(id);
                if (!button.getText().equals("\t")) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
    private boolean isWin() {
        boolean result = false;
        char symbol;
        if (checkHorizontal("O") || checkVertical("O")
                || checkDiagonal("O") || checkHorizontal("X")
                || checkVertical("X") || checkDiagonal("X")) {
            if (queue) {
                symbol = 'X';
            } else {
                symbol = 'O';
            }
            String text = "";
            text += symbol;
            if (oppSelect) {
                if (queue) {
                    text = getString(R.string.youWin);
                } else {
                    text = getString(R.string.androidWin);
                }
            }
            if (!oppSelect) {
                text += " " + getString(R.string.win);
            }
            Toast.makeText(this, text,
                    Toast.LENGTH_SHORT).show();
            result = true;
        }
        if (!result) {
            int count = 0;
            Button button;
            for (int[] arr : field) {
                for (int id : arr) {
                    button = findViewById(id);
                    if (!button.getText().equals("\t")) {
                        ++count;
                    } else {
                        break;
                    }
                }
                if (count == 9) {
                    Toast.makeText(this, getString(R.string.draw),
                            Toast.LENGTH_SHORT).show();
                    result = true;
                }
            }
        }
        return result;
    }
    private void cellMark(View view) {
        Button cell = findViewById(view.getId());
        if (!queue && cell.getText().toString().equals("\t")) {
            queue = true;
            cell.setText("X");
        } else if (queue && cell.getText().toString().equals("\t")) {
            queue = false;
            cell.setText("O");
        }
        if (oppSelect && queue && !isWin()) {
            androidInGame();
        }
        if (isWin()) {
            Intent intent = new Intent(this, this.getClass());
            finish();
            this.startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private boolean androidAnalyse(String sequence1, String sequence2, String sequence3) {
        boolean result = false;
        Button cell;
        String str;
        for (int i = 0; i < field[0].length; ++i) {
            str = "";
            for (int j = 0; j < field[i].length; ++j) {
                cell = findViewById(field[i][j]);
                str += cell.getText().toString();
                if (str.equals(sequence1)) {
                    cell = findViewById(field[i][j]);
                    result = true;
                    cell.performClick();
                    break;
                }
                if (str.equals(sequence2)) {
                    cell = findViewById(field[i][--j]);
                    result = true;
                    cell.performClick();
                    break;
                }
                if (str.equals(sequence3)) {
                    cell = findViewById(field[i][j - 2]);
                    result = true;
                    cell.performClick();
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        if (!result) {
            for (int i = 0; i < field[0].length; ++i) {
                str = "";
                for (int j = 0; j < field[0].length; ++j) {
                    cell = findViewById(field[j][i]);
                    str += cell.getText().toString();
                    if (str.equals(sequence1)) {
                        cell = findViewById(field[j][i]);
                        result = true;
                        cell.performClick();
                        break;
                    }
                    if (str.equals(sequence2)) {
                        cell = findViewById(field[--j][i]);
                        result = true;
                        cell.performClick();
                        break;
                    }
                    if (str.equals(sequence3)) {
                        cell = findViewById(field[j - 2][i]);
                        result = true;
                        cell.performClick();
                        break;
                    }
                }
                if (result) {
                    break;
                }
            }
        }
        if (!result) {
            str = "";
            for (int i = 0; i < field[0].length; ++i) {
                cell = findViewById(field[i][i]);
                str += cell.getText().toString();
                if (str.equals(sequence1)) {
                    cell = findViewById(field[i][i]);
                    result = true;
                    cell.performClick();
                    break;
                }
                if (str.equals(sequence2)) {
                    --i;
                    cell = findViewById(field[i][i]);
                    result = true;
                    cell.performClick();
                    break;
                }
                if (str.equals(sequence3)) {
                    i -= 2;
                    cell = findViewById(field[i][i]);
                    result = true;
                    cell.performClick();
                    break;
                }
            }
        }
        if (!result) {
            int j = 0;
            str = "";
            for (int i = field[0].length - 1; i >= 0; --i) {
                cell = findViewById(field[j][i]);
                str += cell.getText().toString();
                if (str.equals(sequence1)) {
                    cell = findViewById(field[j][i]);
                    result = true;
                    cell.performClick();
                    break;
                }
                if (str.equals(sequence2)) {
                    ++i;
                    --j;
                    cell = findViewById(field[j][i]);
                    result = true;
                    cell.performClick();
                    break;
                }
                if (str.equals(sequence3)) {
                    i += 2;
                    j -= 2;
                    cell = findViewById(field[j][i]);
                    result = true;
                    cell.performClick();
                    break;
                }
                ++j;
            }
        }
        return result;
    }
    private boolean androidAttack() {
        boolean result = false;
        String sequence1 = "OO\t";
        String sequence2 = "O\tO";
        String sequence3 = "\tOO";
        if (androidAnalyse(sequence1, sequence2, sequence3)) {
            result = true;
        }
        return result;
    }
    private boolean androidDefence() {
        boolean result = false;
        String sequence1 = "XX\t";
        String sequence2 = "X\tX";
        String sequence3 = "\tXX";
        if (androidAnalyse(sequence1, sequence2, sequence3)) {
            result = true;
        }
        return result;
    }
    private void androidInGame() {
        Button cell;
        boolean result = false;
        if (oppSelect && queue && !isWin()) {
            if (!androidAttack()) {
                if (androidDefence()) {
                    result = true;
                }
                if (!result) {
                    List<Integer> emptyCells = new ArrayList<>();
                    for (int[] array : field) {
                        for (int id : array) {
                            cell = findViewById(id);
                            if (cell.getText().equals("\t")) {
                                emptyCells.add(cell.getId());
                            }
                        }
                    }
                    int x = (int) (Math.random() * emptyCells.size());
                    while (!result) {
                        cell = findViewById(emptyCells.get(x));
                        cell.performClick();
                        result = true;
                    }
                }
            }
        }
    }
    private void paint(int[] array) {
        for (int anArray : array) {
            Button button = findViewById(anArray);
            button.setTextColor(Color.parseColor("#00EE00"));}
    }
    private boolean checkHorizontal(String symbol) {
        combination = new int[field[0].length];
        boolean result = false;
        Button cell;
        for (int i = 0; i < field[0].length; ++i) {
            int count = 0;
            for (int j = 0; j < field[i].length; ++j) {
                cell = this.findViewById(field[i][j]);
                if (cell.getText().equals(symbol)) {
                    ++count;
                    combination[count - 1] = field[i][j];
                }
            }
            if (count == field[0].length) {
                paint(combination);
                result = true;
                break;
            }
        }
        return result;
    }
    private boolean checkVertical(String symbol) {
        combination = new int[field[0].length];
        boolean result = false;
        Button cell;
        for (int i = 0; i < field[0].length; ++i) {
            int count = 0;
            for (int[] aField : field) {
                cell = this.findViewById(aField[i]);
                if (cell.getText().equals(symbol)) {
                    ++count;
                    combination[count - 1] = aField[i];
                }
            }
            if (count == field[0].length) {
                paint(combination);
                result = true;
                break;
            }
        }
        return result;
    }
    private boolean checkDiagonal(String symbol) {
        combination = new int[field[0].length];
        boolean result = false;
        Button cell;
        int count = 0;
        for (int i = 0; i < field[0].length; ++i) {
            cell = this.findViewById(field[i][i]);
            if (cell.getText().equals(symbol)) {
                ++count;
                combination[count - 1] = field[i][i];
            }
        }
        if (count == field[0].length) {
            result = true;
            paint(combination);
        }
        if (!result) {
            int j = 0;
            count = 0;
            for (int i = field[0].length - 1; i >= 0; --i) {
                cell = this.findViewById(field[j][i]);
                if (cell.getText().equals(symbol)) {
                    ++count;
                    combination[count - 1] = field[j][i];
                }
                j++;
            }
            if (count == field[0].length) {
                result = true;
                paint(combination);
            }
        }
        return result;
    }
}
