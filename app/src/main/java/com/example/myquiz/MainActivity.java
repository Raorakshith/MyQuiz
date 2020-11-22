package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
private TextView timerview,questionview,option1,option2,option3,option4,stat,questionno;
private CountDownTimer countDownTimer;
private DatabaseReference mRef,pRef,cRef;
private String qno,answer;
private CardView cardop1,cardop2,cardop3,cardop4;
private LinearLayout anstats;
private ImageView tickorcan;
    ProgressDialog mProgress;
    MediaPlayer mediaPlayer;
    CircleImageView imgprof;
    FirebaseAuth mAuth;
    private int count=0;
    private int questioncount=1;
    private int totcount;
    private Random r;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cRef=FirebaseDatabase.getInstance().getReference("Quiz1").child("Questions");
        mediaPlayer= new MediaPlayer();
        r= new Random();
        countDownTimer=new CountDownTimerClass(40000,1000);
        mProgress=new ProgressDialog(this);
        init();
        mProgress.setMessage("Loading question...");
        mProgress.show();
        cRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 totcount= (int) snapshot.getChildrenCount();
                int i1=r.nextInt(totcount);
                qno=String.valueOf(i1);
                Toast.makeText(MainActivity.this, ""+qno, Toast.LENGTH_SHORT).show();
                startquiz();
                Toast.makeText(MainActivity.this, "Child count is:"+totcount, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("profileimage")) {
                    String imgu = snapshot.child("profileimage").getValue().toString();
                    Picasso.with(MainActivity.this).load(imgu).into(imgprof);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Profile.class));
            }
        });
        cardop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardop1.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                String selectedans=option1.getText().toString();
                if(selectedans.equals(answer)){
                    anstats.setVisibility(View.VISIBLE);
                    stat.setText("Correct answer");
                    count = count+1;
                    stat.setTextColor(getResources().getColor(R.color.green));
                    tickorcan.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    cardop2.setEnabled(false);
                    cardop3.setEnabled(false);
                    cardop4.setEnabled(false);
                    delayexecution();
                }else {
                    anstats.setVisibility(View.VISIBLE);
                    if(Build.VERSION.SDK_INT >= 26){
                        vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                    }else {
                        vibrator.vibrate(200);
                    }
                    stat.setText("Wrong answer");
                    stat.setTextColor(getResources().getColor(R.color.red));
                    tickorcan.setImageResource(R.drawable.ic_baseline_cancel_24);
                    cardop2.setEnabled(false);
                    cardop3.setEnabled(false);
                    cardop4.setEnabled(false);
                    delayexecution();
                }
            }
        });
        cardop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardop2.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                String selectedans=option2.getText().toString();
                if(selectedans.equals(answer)){
                    anstats.setVisibility(View.VISIBLE);
                    stat.setText("Correct answer");
                    count = count+1;
                    stat.setTextColor(getResources().getColor(R.color.green));
                    tickorcan.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    cardop1.setEnabled(false);
                    cardop3.setEnabled(false);
                    cardop4.setEnabled(false);
                    delayexecution();
                }else {
                    anstats.setVisibility(View.VISIBLE);
                    if(Build.VERSION.SDK_INT >= 26){
                        vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                    }else {
                        vibrator.vibrate(200);
                    }
                    stat.setText("Wrong answer");
                    stat.setTextColor(getResources().getColor(R.color.red));
                    tickorcan.setImageResource(R.drawable.ic_baseline_cancel_24);
                    cardop1.setEnabled(false);
                    cardop3.setEnabled(false);
                    cardop4.setEnabled(false);
                    delayexecution();
                }
            }
        });
        cardop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardop3.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                String selectedans=option3.getText().toString();
                if(selectedans.equals(answer)){
                    anstats.setVisibility(View.VISIBLE);
                    stat.setText("Correct answer");
                    count = count+1;
                    stat.setTextColor(getResources().getColor(R.color.green));
                    tickorcan.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    cardop2.setEnabled(false);
                    cardop1.setEnabled(false);
                    cardop4.setEnabled(false);
                    delayexecution();
                }else {
                    anstats.setVisibility(View.VISIBLE);
                    if(Build.VERSION.SDK_INT >= 26){
                        vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                    }else {
                        vibrator.vibrate(200);
                    }
                    stat.setText("Wrong answer");
                    stat.setTextColor(getResources().getColor(R.color.red));
                    tickorcan.setImageResource(R.drawable.ic_baseline_cancel_24);
                    cardop2.setEnabled(false);
                    cardop1.setEnabled(false);
                    cardop4.setEnabled(false);
                    delayexecution();
                }
            }
        });
        cardop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardop4.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                String selectedans=option4.getText().toString();
                if(selectedans.equals(answer)){
                    anstats.setVisibility(View.VISIBLE);
                    stat.setText("Correct answer");
                    count = count+1;
                    stat.setTextColor(getResources().getColor(R.color.green));
                    tickorcan.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    cardop2.setEnabled(false);
                    cardop3.setEnabled(false);
                    cardop1.setEnabled(false);
                    delayexecution();
                }else {
                    anstats.setVisibility(View.VISIBLE);
                    if(Build.VERSION.SDK_INT >= 26){
                        vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                    }else {
                        vibrator.vibrate(200);
                    }
                    stat.setText("Wrong answer");
                    stat.setTextColor(getResources().getColor(R.color.red));
                    tickorcan.setImageResource(R.drawable.ic_baseline_cancel_24);
                    cardop2.setEnabled(false);
                    cardop3.setEnabled(false);
                    cardop1.setEnabled(false);
                    delayexecution();
                }
            }
        });
       // option1.setText(String.valueOf(i1));
    }
public class CountDownTimerClass extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown i=s done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerClass(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int progress= (int) (millisUntilFinished/1000);
        timerview.setText(String.valueOf(progress));
    }

    @Override
    public void onFinish() {
        timerview.setText("Time Up");
        delayexecution();
    }
}

    private void init() {
        timerview=findViewById(R.id.timertextview);
        questionview=findViewById(R.id.quiz_question);
        option1=findViewById(R.id.quiz_option1);
        option2=findViewById(R.id.quiz_option2);
        option3=findViewById(R.id.quiz_option3);
        option4=findViewById(R.id.quiz_option4);
        cardop1=findViewById(R.id.card1);
        cardop2=findViewById(R.id.card2);
        cardop3=findViewById(R.id.card3);
        cardop4=findViewById(R.id.card4);
        anstats=findViewById(R.id.answerstatus);
        stat=findViewById(R.id.ansst);
        tickorcan=findViewById(R.id.wrorrig);
        questionno=findViewById(R.id.quiz_questionno);
        imgprof=findViewById(R.id.circleimg);
        mAuth=FirebaseAuth.getInstance();
        pRef=FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid()).child("Profile");
        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }
private void startquiz(){
    questionno.setText("Q"+String.valueOf(questioncount)+".");
        mediaPlayer.start();
    mRef= FirebaseDatabase.getInstance().getReference("Quiz1").child("Questions").child(qno);
    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if(snapshot.hasChild("audiofile")){
                String audiourl=snapshot.child("audiofile").getValue().toString();

                try{
                    mediaPlayer.setDataSource(audiourl);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });

                    mediaPlayer.prepare();
                    countDownTimer.start();
                    mProgress.dismiss();
                }catch (IOException e){

                }
            }
            if(snapshot.hasChild("question")){
                String quest=snapshot.child("question").getValue().toString();
                questionview.setText(quest);
            }
            if(snapshot.hasChild("option1")){
                String opt1=snapshot.child("option1").getValue().toString();
                option1.setText(opt1);
            }
            if(snapshot.hasChild("option2")){
                String opt2=snapshot.child("option2").getValue().toString();
                option2.setText(opt2);
            }
            if(snapshot.hasChild("option3")){
                String opt3=snapshot.child("option3").getValue().toString();
                option3.setText(opt3);
            }
            if(snapshot.hasChild("option4")){
                String opt4=snapshot.child("option4").getValue().toString();
                option4.setText(opt4);
            }
            if(snapshot.hasChild("answer")){
                answer=snapshot.child("answer").getValue().toString();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
private void delayexecution(){
        questioncount=questioncount+1;
        countDownTimer.cancel();
        mediaPlayer.stop();
        if(questioncount>totcount){
            Intent intent= new Intent(MainActivity.this,ScoreDisplay.class);
            intent.putExtra("totalscore",String.valueOf(count));
            intent.putExtra("maximumscore",String.valueOf(totcount));
            startActivity(intent);
        }else{
            new CountDownTimer(5000,1000){
                @Override
                public void onFinish() {
                    //mProgress.dismiss();
                    anstats.setVisibility(View.GONE);
                    cardop4.setCardBackgroundColor(getResources().getColor(R.color.white));
                    cardop3.setCardBackgroundColor(getResources().getColor(R.color.white));
                    cardop2.setCardBackgroundColor(getResources().getColor(R.color.white));
                    cardop1.setCardBackgroundColor(getResources().getColor(R.color.white));
                    int i1=r.nextInt(totcount);
                    qno=String.valueOf(i1);
                    Toast.makeText(MainActivity.this, ""+qno, Toast.LENGTH_SHORT).show();
                    startquiz();
                    cardop2.setEnabled(true);
                    cardop3.setEnabled(true);
                    cardop1.setEnabled(true);
                    cardop4.setEnabled(true);
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    Toast.makeText(MainActivity.this, "Redirecting to next question", Toast.LENGTH_SHORT).show();
                    mProgress.setMessage("Loading Next Question...");
                    mProgress.show();
                }
            }.start();
        }

}
}