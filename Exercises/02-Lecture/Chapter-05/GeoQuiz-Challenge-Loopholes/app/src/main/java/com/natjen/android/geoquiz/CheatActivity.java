package com.natjen.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.natjen.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.natjen.android.geoquiz.answer_shown";
    private static final String KEY_ANSWER_SHOWN = "answer is shown";

    private boolean mAnswerIsTrue;
    private boolean mAnswerIsShown;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_ANSWER_SHOWN)) {
                mAnswerIsShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN);
            }
        }

        // Answer TextView
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        // Show answer Button
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer();
                mAnswerIsShown = true;
                setAnswerShownResult(mAnswerIsShown);
                mShowAnswerButton.setEnabled(false);
            }
        });

        if (mAnswerIsShown) {
            showAnswer();
            mShowAnswerButton.setEnabled(false);
            setAnswerShownResult(mAnswerIsShown);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_SHOWN, mAnswerIsShown);
    }

    /*
     * Display the answer
     */
    private void showAnswer() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    /*
     * Infrom parent Activity whether the user has seen the answer
     * @param isAnswerShown A flag to indicate whether the user has seen the answer
     */
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
