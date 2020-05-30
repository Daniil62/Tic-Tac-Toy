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
    private int[] field;
    private Logic logic;
    private final String X = "X";
    private final String O = "O";
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
        outState.putIntArray("fieldState", field);
        Button one = findViewById(field[0]);
        Button two = findViewById(field[1]);
        Button three = findViewById(field[2]);
        Button four = findViewById(field[3]);
        Button five = findViewById(field[4]);
        Button six = findViewById(field[5]);
        Button seven = findViewById(field[6]);
        Button eight = findViewById(field[7]);
        Button nine = findViewById(field[8]);
        outState.putString("oneText", one.getText().toString());
        outState.putString("twoText", two.getText().toString());
        outState.putString("threeText", three.getText().toString());
        outState.putString("fourText", four.getText().toString());
        outState.putString("fiveText", five.getText().toString());
        outState.putString("sixText", six.getText().toString());
        outState.putString("sevenText", seven.getText().toString());
        outState.putString("eightText", eight.getText().toString());
        outState.putString("nineText", nine.getText().toString());
        outState.putBoolean("queue", queue);
        outState.putBoolean("opSelect", oppSelect);
        outState.putBoolean("logicQueue", logic.getQueue());
        outState.putStringArrayList("symbols", new ArrayList<String> (logic.getField()));
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        field = savedInstanceState.getIntArray("fieldState");
        assert field != null;
        Button one = findViewById(field[0]);
        Button two = findViewById(field[1]);
        Button three = findViewById(field[2]);
        Button four = findViewById(field[3]);
        Button five = findViewById(field[4]);
        Button six = findViewById(field[5]);
        Button seven = findViewById(field[6]);
        Button eight = findViewById(field[7]);
        Button nine = findViewById(field[8]);
        one.setText(savedInstanceState.getString("oneText"));
        two.setText(savedInstanceState.getString("twoText"));
        three.setText(savedInstanceState.getString("threeText"));
        four.setText(savedInstanceState.getString("fourText"));
        five.setText(savedInstanceState.getString("fiveText"));
        six.setText(savedInstanceState.getString("sixText"));
        seven.setText(savedInstanceState.getString("sevenText"));
        eight.setText(savedInstanceState.getString("eightText"));
        nine.setText(savedInstanceState.getString("nineText"));
        queue = savedInstanceState.getBoolean("queue");
        logic.setQueue(savedInstanceState.getBoolean("logicQueue"));
        oppSelect = savedInstanceState.getBoolean("opSelect");
        List<String> list = savedInstanceState.getStringArrayList("symbols");
        logic.setField(list);
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
        logic = new Logic();
        SharedPreferences preferences = getSharedPreferences(
                "selector", Context.MODE_PRIVATE);
        boolean oppSelectState = preferences.getBoolean("oppSelector", true);
        opponentSelector.setChecked(oppSelectState);
        opponentSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oppSelect = opponentSelector.isChecked();
                queuePriority.setEnabled(oppSelect);
                if (oppSelect && queue) {
                    androidInGame();
                }
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
                if (logic.checkCleanField() && firstStep) {
                    logic.setQueue(!logic.getQueue());
                    androidInGame();
                }
            }
        });
        this.field = new int[]{
                one.getId(), two.getId(), three.getId(),
                four.getId(), five.getId(), six.getId(),
                seven.getId(), eight.getId(), nine.getId()
        };
        loadField();
    }
    private void loadField() {
        Button cell;
        for (int i = 0; i < field.length; ++i) {
            cell = findViewById(field[i]);
            cell.setText(logic.getField().get(i));
            cell.setId(i);
            cell.setText(logic.getField().get(i));
            field[i] = i;
        }
        Switch opponentSelector = findViewById(R.id.opponentSelector);
        Switch queuePriority = findViewById(R.id.queueSelector);
        oppSelect = opponentSelector.isChecked();
        queuePriority.setEnabled(oppSelect);
        if (logic.checkCleanField() && firstStep) {
            logic.setQueue(!logic.getQueue());
            androidInGame();
        }
    }
    public void hasWinner() {
        boolean result = false;
        char symbol;
        boolean winX = logic.isWin(X);
        boolean winO = logic.isWin(O);
        if (winX || winO) {
            paint(logic.getCombination());
            if (winX) {
                symbol = 'X';
            } else {
                symbol = 'O';
            }
            String text = "";
            text += symbol;
            if (oppSelect) {
                if (logic.getQueue()) {
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
        if (!result || !logic.hasFree()) {
            Toast.makeText(this, getString(R.string.draw),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void cellMark(View view) {
        int id = view.getId();
        Button cell = findViewById(id);
        if (logic.tryMark(id) && !cell.getText().toString().equals(X)
                && !cell.getText().toString().equals(O)) {
            cell.setText(logic.getField().get(id));
            queue = logic.getQueue();
        }
        boolean winX = logic.isWin(X);
        boolean winO = logic.isWin(O);
        if (oppSelect && queue && !(winX || winO)) {
            androidInGame();
        }
        if (winX || winO || !logic.hasFree()) {
            hasWinner();
            Intent intent = new Intent(this, this.getClass());
            finish();
            this.startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    public boolean androidAction(String symbol) {
        boolean result = false;
        Button button;
        int cell = logic.androidAnalyse(symbol);
        if (cell != -1) {
            button = findViewById(cell);
            button.performClick();
            result = true;
        }
        return result;
    }
    public void androidInGame() {
        Button cell;
        if (!androidAction(O)) {
            if (!androidAction(X) && logic.hasFree()) {
                List<Integer> freeCells = new ArrayList<>();
                for (int i = 0; i < field.length; ++i) {
                    if (logic.getField().get(i).equals("\t")) {
                        freeCells.add(i);
                    }
                }
                cell = findViewById(freeCells.get((int) (Math.random() * freeCells.size())));
                cell.performClick();
            }
        }
    }
    private void paint(List<Integer> array) {
        for (int index : array) {
            Button button = findViewById(index);
            button.setTextColor(Color.parseColor("#00EE00"));}
    }
}
