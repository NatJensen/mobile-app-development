package com.natjen.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_CHEATED = "cheater";

    private static final int REQUEST_CODE_CHEAT = 0;

    private static final int QUESTION_GRADE = 1;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    private boolean[] mQuestionStatus = new boolean[mQuestionBank.length];
    private boolean[] mCheated = new boolean[mQuestionBank.length];
    private int[] mScore = new int[mQuestionBank.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(KEY_INDEX)) {
                mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            }

            if (savedInstanceState.containsKey(KEY_ANSWERED)) {
                mQuestionStatus = savedInstanceState.getBooleanArray(KEY_ANSWERED);
            }

            if (savedInstanceState.containsKey(KEY_SCORE)) {
                mScore = savedInstanceState.getIntArray(KEY_SCORE);
            }

            if (savedInstanceState.containsKey(KEY_CHEATED)) {
                mCheated = savedInstanceState.getBooleanArray(KEY_CHEATED);
            }
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                activateAnswerButton();
            }
        });

        // Cheat button
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent =
                        CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mCheated[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        // Mark question as answered
        mQuestionStatus[mCurrentIndex] = true;

        deactivateAnswerButton();

        int toastResId;
        if (mCheated[mCurrentIndex]) {
            toastResId = R.string.judgment_toast;
            mScore[mCurrentIndex] = 0;
        } else {
            boolean isCorrectAnswer = userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue();
            if (isCorrectAnswer) {
                mScore[mCurrentIndex] = QUESTION_GRADE;
                toastResId = R.string.correct_toast;
            } else {
                // when user answered again, report his inccorect answeres(depend on business logic)
                mScore[mCurrentIndex] = 0;
                toastResId = R.string.incorrect_toast;
            }
        }

        Toast toast = Toast.makeText(QuizActivity.this, toastResId,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();


        if (allAnswered()) {
            DisplayScore();
            reset();
        }
    }

    /*
     * Checks whether the user has answered all the questions
     * @return true if the user has answered all questions
     */
    private boolean allAnswered() {
        for (boolean status : mQuestionStatus) {
            if (!status) {
                return false;
            }
        }
        return true;
    }

    /*
     * Reset score and answered questions
     */
    private void reset() {

        // set all questions unanswered
        for (int i = 0; i < mQuestionStatus.length; i++) {
            mQuestionStatus[i] = false;
        }

        // reset cheating record**
        for (int i = 0; i < mCheated.length; i++) {
            mCheated[i] = false;
        }

        // set all scores to zero
        for (int i = 0; i < mScore.length; i++) {
            mScore[i] = 0;
        }
    }

    /*
     * Displays the score
     */
    private void DisplayScore() {
        Toast.makeText(this,
                "Score: " + getScore() + "%",
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Gets the score as a percentage
     * @return The score as a double
     */
    private double getScore() {
        double score = 0;
        for (int i = 0; i < mScore.length; i++) {
            score += mScore[i];
        }
        return (score / mQuestionBank.length) * 100;
    }

    /*
     * Proceeds to next question
     */
    private void nextQuestion() {
        // keep looping forward in the questions
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }

    /*
     * Deactivates True and False buttons
     */
    private void deactivateAnswerButton() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    /*
     * Activates True and False buttons
     */
    private void activateAnswerButton() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }
}
