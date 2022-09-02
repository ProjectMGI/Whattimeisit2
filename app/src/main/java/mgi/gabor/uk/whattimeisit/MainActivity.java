package mgi.gabor.uk.whattimeisit;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //        Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
    //        My app ID ca-app-pub-9551363432365771~5935139532

    //        Test Ad ID: ca-app-pub-3940256099942544/1033173712
    //        My Ad ID: ca-app-pub-9551363432365771/3241967200


    private ImageView res1;
    private ImageView res2;
    private ImageView res3;
    private ImageView res4;
    private ImageView res5;
    private ImageView res6;
    private ImageView res7;
    private ImageView res8;
    private ImageView res9;
    private ImageView res10;
    private ImageView res11;
    private ImageView res12;
    private ImageView answerCenter;
    private ImageView gameClock;
    private ImageView gameBigH;
    private ImageView gameSmallH;
    private GridLayout gridLayoutMiddle;

    private int answerCount = 0;
    private int correctCount = 0;

    private final Answers answer1 = new Answers();
    private final Answers answer2 = new Answers();
    private final Answers answer3 = new Answers();
    private final Answers answerCorrect = new Answers();

    private final Random rnd = new Random();

    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;

    private ImageView thumb;

    private SoundPool soundPool;
    private int g1, g2, gw, w1;
    private Random random;

    private ImageView btnSound;
    private Boolean sound;

    private SharedPreferences sharedPreferences;


    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        final String TEST_ADMOB = "ca-app-pub-3940256099942544~3347511713";
        final String MY_ADMOB = "ca-app-pub-9551363432365771~5935139532";

        final String TEST_AD_ID = "ca-app-pub-3940256099942544/1033173712";
        final String MY_AD_ID = "ca-app-pub-9551363432365771/3241967200";

        MobileAds.initialize(this, MY_ADMOB);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(MY_AD_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().addKeyword("game").addKeyword("kid").build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().addKeyword("game").addKeyword("kid").build());
            }
        });


        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(11)
                .setAudioAttributes(audioAttributes)
                .build();
        g1 = soundPool.load(this, R.raw.owow, 1);
        g2 = soundPool.load(this, R.raw.wow, 1);
        gw = soundPool.load(this, R.raw.mmm, 1);
        w1 = soundPool.load(this, R.raw.oohh, 1);



        res1 = findViewById(R.id.ivResult1);
        res1.setVisibility(View.INVISIBLE);
        res2 = findViewById(R.id.ivResult2);
        res2.setVisibility(View.INVISIBLE);
        res3 = findViewById(R.id.ivResult3);
        res3.setVisibility(View.INVISIBLE);
        res4 = findViewById(R.id.ivResult4);
        res4.setVisibility(View.INVISIBLE);
        res5 = findViewById(R.id.ivResult5);
        res5.setVisibility(View.INVISIBLE);
        res6 = findViewById(R.id.ivResult6);
        res6.setVisibility(View.INVISIBLE);
        res7 = findViewById(R.id.ivResult7);
        res7.setVisibility(View.INVISIBLE);
        res8 = findViewById(R.id.ivResult8);
        res8.setVisibility(View.INVISIBLE);
        res9 = findViewById(R.id.ivResult9);
        res9.setVisibility(View.INVISIBLE);
        res10 = findViewById(R.id.ivResult10);
        res10.setVisibility(View.INVISIBLE);
        res11 = findViewById(R.id.ivResult11);
        res11.setVisibility(View.INVISIBLE);
        res12 = findViewById(R.id.ivResult12);
        res12.setVisibility(View.INVISIBLE);
        answerCenter = findViewById(R.id.ivClockCenter);
        answerCenter.setVisibility(View.INVISIBLE);

        gameClock = findViewById(R.id.ivMainClock);
        gameBigH = findViewById(R.id.ivBigHand);
        gameSmallH = findViewById(R.id.ivSmallHand);

        gridLayoutMiddle = findViewById(R.id.gridlyMiddle);
        gridLayoutMiddle.setVisibility(View.INVISIBLE);

        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);

        ImageView btnExit = findViewById(R.id.ivExit);
        btnSound = findViewById(R.id.ivSound);
        ImageView btnHelp = findViewById(R.id.ivHelp);

        thumb = findViewById(R.id.thumbup);

        SetDefUI();
        random = new Random();

        LoadSharedPreferences();

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickedSound();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickedExit();
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickedHelp();
            }
        });


    }


    private void SetDefUI() {


        final Animation animres1 = AnimationUtils.loadAnimation(this, R.anim.result1strartanim);
        final Animation animres2 = AnimationUtils.loadAnimation(this, R.anim.result2strartanim);
        final Animation animres3 = AnimationUtils.loadAnimation(this, R.anim.result3strartanim);
        final Animation animres4 = AnimationUtils.loadAnimation(this, R.anim.result4strartanim);
        final Animation animres5 = AnimationUtils.loadAnimation(this, R.anim.result5strartanim);
        final Animation animres6 = AnimationUtils.loadAnimation(this, R.anim.result6strartanim);
        final Animation animres7 = AnimationUtils.loadAnimation(this, R.anim.result7strartanim);
        final Animation animres8 = AnimationUtils.loadAnimation(this, R.anim.result8strartanim);
        final Animation animres9 = AnimationUtils.loadAnimation(this, R.anim.result9strartanim);
        final Animation animres10 = AnimationUtils.loadAnimation(this, R.anim.result10strartanim);
        final Animation animres11 = AnimationUtils.loadAnimation(this, R.anim.result11strartanim);
        final Animation animres12 = AnimationUtils.loadAnimation(this, R.anim.result12strartanim);


        res1.startAnimation(animres1);
        res2.startAnimation(animres2);
        res3.startAnimation(animres3);
        res4.startAnimation(animres4);
        res5.startAnimation(animres5);
        res6.startAnimation(animres6);
        res7.startAnimation(animres7);
        res8.startAnimation(animres8);
        res9.startAnimation(animres9);
        res10.startAnimation(animres10);
        res11.startAnimation(animres11);
        res12.startAnimation(animres12);

        gameClock.animate().rotationY(360f).alpha(1).setDuration(3000).start();
        gameBigH.animate().rotationY(360f).alpha(1).rotation(1080).setDuration(3000).withEndAction(new Runnable() {
            @Override
            public void run() {
                gameBigH.setRotation(0f);
                gameSmallH.setRotation(0f);
                LetsGame();
            }
        }).start();
        gameSmallH.animate().rotationY(360f).alpha(1).rotation(-1080).setDuration(3000).start();

        gridLayoutMiddle.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInDown)
                .duration(3000)
                .playOn(gridLayoutMiddle);

        answerCenter.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.RollIn)
                .duration(3000)
                .pivot(0.5f, 0.5f)
                .playOn(answerCenter);


    }

    private void ClickedHelp() {

        Log.d("helpbutton", "ClickedHelp: click");
        final Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.helpdialog, null);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.dialogbackground);

        Button dialogOk = view.findViewById(R.id.btn_dialogHelpOk);
        TextView dialogEmail = view.findViewById(R.id.tv_email);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailintent = new Intent(Intent.ACTION_SEND);
                emailintent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.email)});
                emailintent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback));
                emailintent.setType("message/rfc822");
                startActivity(emailintent);
            }
        });
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout((int) (Splash.screenWidth * 0.75), (int) (Splash.screenHeight * 0.75));
    }

    private void ClickedExit() {

        final Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.exitdialog, null);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.dialogbackground);


        Button dialogExit = view.findViewById(R.id.btn_dialogExitYES);
        Button dialogNo = view.findViewById(R.id.btn_dialogExitNO);

        dialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(Splash.screenWidth / 2, Splash.screenHeight / 2);

    }

    private void FinalDialog() {

        final Dialog after12dialog = new Dialog(this);
        after12dialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.scoring, null);
        after12dialog.setContentView(view);
        Objects.requireNonNull(after12dialog.getWindow()).setBackgroundDrawableResource(R.drawable.dialogbackground);

        Button ok = view.findViewById(R.id.btn_dialog12Ok);
        TextView textScore = view.findViewById(R.id.textView12);

        textScore.setText(String.format("%s%s%s", textScore.getText(), " ", String.valueOf(correctCount)));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                after12dialog.dismiss();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                LetsGame();
            }
        });
        after12dialog.show();

        Objects.requireNonNull(after12dialog.getWindow()).setLayout(Splash.screenWidth / 2, Splash.screenHeight / 2);
    }

    private void LetsGame() {

        answerCount = 0;
        correctCount = 0;

        res1.setImageResource(R.drawable.clockpiece_blue);
        res2.setImageResource(R.drawable.clockpiece_blue);
        res3.setImageResource(R.drawable.clockpiece_blue);
        res4.setImageResource(R.drawable.clockpiece_blue);
        res5.setImageResource(R.drawable.clockpiece_blue);
        res6.setImageResource(R.drawable.clockpiece_blue);
        res7.setImageResource(R.drawable.clockpiece_blue);
        res8.setImageResource(R.drawable.clockpiece_blue);
        res9.setImageResource(R.drawable.clockpiece_blue);
        res10.setImageResource(R.drawable.clockpiece_blue);
        res11.setImageResource(R.drawable.clockpiece_blue);
        res12.setImageResource(R.drawable.clockpiece_blue);

        GenerateAnswersAndCorrect();
    }

    private void GenerateAnswersAndCorrect() {

        if (answerCount == 12) {
            FinalDialog();
        } else {
            btnAnswer1.setTag("notcorrect");
            btnAnswer2.setTag("notcorrect");
            btnAnswer3.setTag("notcorrect");

            answer1.setHour(rnd.nextInt(12) + 1);
            answer1.setMinute(rnd.nextInt(12) * 5);

            answer2.setHour(rnd.nextInt(12) + 1);
            answer2.setMinute(rnd.nextInt(12) * 5);

            while (answer2.getHour() == answer1.getHour()) {
                answer2.setHour(rnd.nextInt(12) + 1);
                answer2.setMinute(rnd.nextInt(12) * 5);
            }

            answer3.setHour(rnd.nextInt(12) + 1);
            answer3.setMinute(rnd.nextInt(12) * 5);

            while ((answer3.getHour() == answer1.getHour()) || (answer3.getHour() == answer2.getHour())) {
                answer3.setHour(rnd.nextInt(12) + 1);
                answer3.setMinute(rnd.nextInt(12) * 5);
            }

            int correct = rnd.nextInt(3) + 1;

            switch (correct) {
                case 1:
                    answerCorrect.setHour(answer1.getHour());
                    answerCorrect.setMinute(answer1.getMinute());
                    btnAnswer1.setTag("correct");
                    break;
                case 2:
                    answerCorrect.setHour(answer2.getHour());
                    answerCorrect.setMinute(answer2.getMinute());
                    btnAnswer2.setTag("correct");
                    break;
                case 3:
                    answerCorrect.setHour(answer3.getHour());
                    answerCorrect.setMinute(answer3.getMinute());
                    btnAnswer3.setTag("correct");
                    break;
            }

            String answer1text = answer1.makeText();
            String answer2text = answer2.makeText();
            String answer3text = answer3.makeText();

            btnAnswer1.setText(answer1text);
            btnAnswer2.setText(answer2text);
            btnAnswer3.setText(answer3text);

            gameBigH.animate().rotation(answerCorrect.getDegreeM() - 1440).setDuration(2000).setInterpolator(new FastOutSlowInInterpolator()).start();

            gameSmallH.animate().rotation(answerCorrect.getDegreeH() + 360).setDuration(2000).setInterpolator(new FastOutSlowInInterpolator()).start();
        }
    }


    public void CheckAnswer(View view) {

        answerCount++;
        if (view.getTag() == "correct") {
            correctCount++;
            switch (answerCount) {
                case 1:
                    res1.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 2:
                    res2.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 3:
                    res3.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 4:
                    res4.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 5:
                    res5.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 6:
                    res6.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 7:
                    res7.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 8:
                    res8.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 9:
                    res9.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 10:
                    res10.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 11:
                    res11.setImageResource(R.drawable.clockpiece_green);
                    break;
                case 12:
                    res12.setImageResource(R.drawable.clockpiece_green);
                    break;
            }
            ThumbAnim(true);
        } else {
            switch (answerCount) {
                case 1:
                    res1.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 2:
                    res2.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 3:
                    res3.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 4:
                    res4.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 5:
                    res5.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 6:
                    res6.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 7:
                    res7.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 8:
                    res8.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 9:
                    res9.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 10:
                    res10.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 11:
                    res11.setImageResource(R.drawable.clockpiece_red);
                    break;
                case 12:
                    res12.setImageResource(R.drawable.clockpiece_red);
                    break;
            }
            ThumbAnim(false);
        }
    }

    private void ThumbAnim(boolean correct) {

        if (correct) {
            thumb.setImageResource(R.drawable.smileup);

            if (sound) {
                int winEffect = random.nextInt(3);
                switch (winEffect) {
                    case 0:
                        soundPool.play(g1, 1, 1, 0, 0, 1);
                        break;
                    case 1:
                        soundPool.play(g2, 1, 1, 0, 0, 1);
                        break;
                    case 2:
                        soundPool.play(gw, 1, 1, 0, 0, 1);
                        break;
                }

            }

        } else {
            thumb.setImageResource(R.drawable.smiledown);

            if (sound) {

                int looseEffect = random.nextInt(2);
                switch (looseEffect) {
                    case 0:
                        soundPool.play(gw, 1, 1, 0, 0, 1);
                        break;
                    case 1:
                        soundPool.play(w1, 1, 1, 0, 0, 1);
                        break;
                }

            }

        }
        thumb.setVisibility(View.VISIBLE);
        btnAnswer1.setClickable(false);
        btnAnswer2.setClickable(false);
        btnAnswer3.setClickable(false);
        YoYo.with(Techniques.RollIn)
                .duration(1000)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        GenerateAnswersAndCorrect();
                        YoYo.with(Techniques.RollOut)
                                .duration(1000)
                                .delay(800)
                                .withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        btnAnswer1.setClickable(true);
                                        btnAnswer2.setClickable(true);
                                        btnAnswer3.setClickable(true);
                                        thumb.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .playOn(thumb);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })

                .playOn(thumb);
    }

    private void ClickedSound() {

        sound = !sound;
        SaveSharedPreferences();
        if (sound) {
            btnSound.setImageResource(R.drawable.soundonicon);
        } else {
            btnSound.setImageResource(R.drawable.soundofficon);
        }

    }

    private void LoadSharedPreferences() {

        sound = sharedPreferences.getBoolean("soundonoff", true);
        if (sound) {
            btnSound.setImageResource(R.drawable.soundonicon);
        } else {
            btnSound.setImageResource(R.drawable.soundofficon);
        }

    }

    private void SaveSharedPreferences() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("soundonoff", sound);

        editor.apply();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}


