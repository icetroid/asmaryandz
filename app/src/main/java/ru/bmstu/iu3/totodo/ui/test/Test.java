package ru.bmstu.iu3.totodo.ui.test;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.bmstu.iu3.totodo.R;

public class Test extends AppCompatActivity {

    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //addTest();
    }

    private void addTest()
    {
        LinearLayout testRoot = findViewById(R.id.test_root);
        List<Question> questions = getQuestions();
        for(Question question : questions)
        {
            String questionText = question.getQuestion();
            TextView questionTextView = new TextView(this);
            questionTextView.setText(questionText);
            testRoot.addView(questionTextView);
            RadioGroup radioGroup = new RadioGroup(this);
            for(String option : question.getOptions().keySet())
            {
                final boolean correct = question.getOptions().get(option);
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option);
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(correct)
                        {
                            Toast.makeText(Test.this, "Правильно", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Test.this, "Неправильно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                radioGroup.addView(radioButton);
            }
            testRoot.addView(radioGroup);
        }
    }
    private List<Question> getQuestions()
    {
        List<Question> questions = new LinkedList<>();

        Question question = new Question();
        question.setQuestion(getString(R.string.test_question_1));
        Map<String, Boolean> options = new HashMap<>();
        options.put(getString(R.string.important), true);
        options.put(getString(R.string.urgent), false);
        question.setOptions(options);
        questions.add(question);

        question = new Question();
        question.setQuestion(getString(R.string.test_question_2));
        options = new HashMap<>();
        options.put(getString(R.string.important), false);
        options.put(getString(R.string.urgent), true);
        question.setOptions(options);
        questions.add(question);

        question = new Question();
        question.setQuestion(getString(R.string.test_question_3));
        options = new HashMap<>();
        options.put(getString(R.string.important), true);
        options.put(getString(R.string.urgent), false);
        question.setOptions(options);
        questions.add(question);

        return questions;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Log.i(TAG, "home");
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
