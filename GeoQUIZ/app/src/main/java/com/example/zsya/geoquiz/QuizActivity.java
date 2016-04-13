package com.example.zsya.geoquiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private Button mTrueButton;
    private Button mFalsButton;
    private Button mPrevButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank = new TrueFalse[]{ //数组
            new TrueFalse(R.string.question_oceans,true),
            new TrueFalse(R.string.question_mideast,true),
            new TrueFalse(R.string.question_africa,true),
            new TrueFalse(R.string.question_americas,true),
            new TrueFalse(R.string.question_asia,true),
    };
    private int mCurrentIndex = 0;

    private boolean mIsCheater;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getmQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();

        int messageResId = 0;
        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }else {
            if(userPressedTrue != answerIsTrue){
                messageResId = R.string.incorrect_toast;
            }else {
                messageResId = R.string.correct_toast;
            }
        }


        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data == null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getmQuestion();
        mQuestionTextView.setText(question);


        mTrueButton = (Button)findViewById(R.id.true_button); //引用组件
        mTrueButton.setOnClickListener(new View.OnClickListener() { //设置监听器,匿名内部类，事件监听器一般只在一处使用一次，用匿名内部类即可。
            @Override
            public void onClick(View v){
                checkAswer(true);
            }
        });
        mFalsButton = (Button)findViewById(R.id.false_button);
        mFalsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAswer(false);
            }
        });


        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mCurrentIndex == 0){
                    mCurrentIndex = (mCurrentIndex + mQuestionBank.length) % mQuestionBank.length;
                }else{
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;//通过这个来控制第几个 问题
                }
                mIsCheater = false;
                updateQuestion();
            }
        });
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;//通过这个来控制第几个 问题
                mIsCheater = false;
                updateQuestion();
            }
        });

       /* mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,answerIsTrue);
                startActivityForResult(i,0);

            }
        });*/
        updateQuestion();

    }

    @Override
    public void  onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void  onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void  onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }
    @Override
    public void  onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void  onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
