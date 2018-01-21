package com.example.android.verygotestas;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //Our health minister created a test. It evaluetes you if you have problem with alchohol or not. Questions and evaluation system if taken from https://www.facebook.com/DELFI.Lietuva/photos/a.173273562701715.41875.151962321499506/2148815425147509/?type=3&theater    and adapted
    // I guess there is a easer way to do this especialy with radio buttons like I have done in Quiz app by getting checked resource id not like here i listen if radio button was clicked...
    //And animation, back,forward managing is strange also.. But i couldin't think any better at the time
    //Application was made for different screen sizes and for English and Lithianian languages

    int nr = 0;//number that will record our progress in test
    DecimalFormat format = new DecimalFormat("##");
    int[] a5 = new int[4];int[] a4 = new int[4];int[] a3 = new int[4];int[] a2 = new int[4];int[] a1 = new int[4];int[] i = new int[4];
    double ratio;
    boolean toliau=true,pasikartojimas=false,is4=false;
    Button go,back;
    ImageView pic;
    RadioButton p5,p1,p2,p3,p4,man,woman;
    int maxbalai = 0;
    RelativeLayout share;
    RadioGroup lytisgroup;
    View radiogroup;
    TextView header, comment, question;
    Animation zoom,right,left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);//zoom in animation
        right=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_right);//slide from right animation
        left=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_left);//slide from left animation
        share =findViewById(R.id.sharelayout);//share button layout
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuotauka;
                if (ratio<=30) { //if you result is less or equal than 30 we set the specific image resourse. Etc..
                    nuotauka= getResources().getString(R.string.HTTP_G2_DCDN_LT_IMAGES_PIX_AURELIJUS_VERYGA_73762088_JPG);
                }
                else if (ratio<=50&&ratio>30){
                    nuotauka= getResources().getString(R.string.HTTP_LSVEIKATA_LT_UPLOAD_ARTICLES_IMAGES_6870_DEF_VER_JPG);
                }
                else if (ratio<=79&&ratio>51){
                    nuotauka=getResources().getString(R.string.percent51_79);
                }
                else
                { pic.setImageResource(R.drawable.v100);
                   nuotauka=getResources().getString(R.string.percent100);
                }
                Intent myIntent = new Intent(Intent.ACTION_SEND);//used to perform sharing function
                myIntent.setType(getResources().getString(R.string.TEXT_PLAIN));
                String shareBody = getResources().getString(R.string.PASAK_VERYGOS_MANYJE) + format.format(ratio)+getResources().getString(R.string.percent) + getResources().getString(R.string.ALKOHOLIKO) +"\n\n"+nuotauka;
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent, getResources().getString(R.string.DALINTIS_NAUDOJANT)));
            }
        });
        go = findViewById(R.id.go);//go is the main button for begining the test
        back =findViewById(R.id.back);//this is a back button
        header = findViewById(R.id.header);//this is header.
        p1 = findViewById(R.id.p1);//this is radio button 1
        p2 = findViewById(R.id.p2);//this is radio button 2
        p3 = findViewById(R.id.p3);//this is radio button 3
        p4 = findViewById(R.id.p4);//this is radio button 4
        p5 = findViewById(R.id.p5);//this is radio button 5
        man=findViewById(R.id.man);//this is radio to choose sex.
        woman = findViewById(R.id.woman);//this is radio to choose sex.
        pic =findViewById(R.id.pic);//this is the picture shown at all stages of application.
        radiogroup = findViewById(R.id.radiogroup);//this is a radiogroup of radiobuttons (1,2,3,4,5)
        comment = findViewById(R.id.comment);////this is the comment that says Complete VerygoTest now and find out more about your situation! and and percentage at the end
        question = findViewById(R.id.question);//this is questions textview and at the end it shows is it good, bad, normal, or awesome result
        lytisgroup = findViewById(R.id.radiogrouplytis);//this is radio group of radiobutton for choosing sex
        pic.startAnimation(zoom);//this zooms in picture when activity is created
        header.startAnimation(zoom);//this zooms in Header when activity is created
        comment.startAnimation(zoom);//this zooms in comment TextView when activity is created
        go.startAnimation(zoom);//this zooms in begin button when activity is created

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toliau=true;
            click(toliau);
            click(toliau);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toliau=false;
                if(nr==4)
                {
                    comment.clearAnimation();//this clears animation from comment TextView becouse without clearing animation set visibility won't work
                    comment.setVisibility(View.GONE);
                    radiogroup.setVisibility(View.VISIBLE);
                    pasikartojimas=true;//if user haven't choosen any radio button do not play any animation
                    header.clearAnimation();
                    pic.clearAnimation();
                    radiogroup.clearAnimation();
                    go.clearAnimation();
                    back.clearAnimation();
                    question.clearAnimation();

                    go.setText(getResources().getString(R.string.TOLIAU));
                    go.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toliau=true;
                            click(toliau);
                            click(toliau);
                        }
                    });
                }
                if (nr==1) {
                    radiogroup.clearAnimation();
                    radiogroup.setVisibility(View.GONE);
                }
                nr--;//if user clicks back we reduce the number that is responsible of changind texts and picture
                a1[nr]=0;//we set curent answer to be 0 becouse user hasin't selected anything yet
                a2[nr]=0;//we set curent answer to be 0 becouse user hasin't selected anything yet
                a3[nr]=0;//we set curent answer to be 0 becouse user hasin't selected anything yet
                a4[nr]=0;//we set curent answer to be 0 becouse user hasin't selected anything yet
                a5[nr]=0;//we set curent answer to be 0 becouse user hasin't selected anything yet
                i[nr]=0;//checks if any of the button was pressed and adds one to @nr, and sets pasikartojimas(responsible for playing animation) to true  i weren't able to set pasikartojimas to true right away when button is clicked...
                click(toliau);
            }
        });
    }
private void click(boolean toliau)
{
    switch (nr) {
        case 0:
            comment.clearAnimation();
            comment.setVisibility(View.GONE);
            if(!pasikartojimas) {//we check if it pasikartojimas (reapeading) is not true
                if (toliau) {//and if toliau(next) is true... if We can go forward
                    header.startAnimation(right);
                    pic.startAnimation(right);
                    lytisgroup.startAnimation(right);
                    go.startAnimation(right);
                    question.startAnimation(right);
                }
            }
            else if(!toliau) {//if we are going back toliau=false and we set animation to from left to right
                header.startAnimation(left);
                pic.startAnimation(left);
                lytisgroup.startAnimation(left);
                go.startAnimation(left);
                question.startAnimation(left);
            }

            lytisgroup.setVisibility(View.VISIBLE);
            pic.setImageResource(R.drawable.nulinis);
            header.setText(getResources().getString(R.string.KLAUSIMAS));

            go.setText(getResources().getString(R.string.TOLIAU));
            back.setVisibility(View.GONE);
            question.setVisibility(View.VISIBLE);
            lytisgroup.setVisibility(View.VISIBLE);
            question.setText(getResources().getString(R.string.JŪSŲ_LYTIS));
            man.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    maxbalai=4;
                    man.setChecked(true);
                    woman.setChecked(false);
                }
            });
            woman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    maxbalai=3;
                    man.setChecked(false);
                    woman.setChecked(true);
                }
            });
            if(maxbalai!=0) {
                pasikartojimas=false;
                nr++;
            return;
            }
            else {
            pasikartojimas = true;
            return;
            }
        case 1:
            lytisgroup.clearAnimation();
            lytisgroup.setVisibility(View.GONE);

            if(!pasikartojimas) {
                if (toliau) {
                    header.startAnimation(right);
                    pic.startAnimation(right);
                    radiogroup.startAnimation(right);
                    go.startAnimation(right);
                    back.startAnimation(right);
                    question.startAnimation(right);
                }
            }
            else if(!toliau) {
                header.startAnimation(left);
                pic.startAnimation(left);
                radiogroup.startAnimation(left);
                go.startAnimation(left);
                back.startAnimation(left);
                question.startAnimation(left);
            }
            pic.setImageResource(R.drawable.pirmas);
            p1.setText(getResources().getString(R.string.NIEKADA));
            p2.setText(getResources().getString(R.string.KARTĄ_PER_MĖN_AR_REČIAU));
            p3.setText(getResources().getString(R.string.KARTUS_PER_MĖN));
            p4.setText(getResources().getString(R.string.KARTUS_PER_SAV));
            back.setVisibility(View.VISIBLE);
            p5.setText(getResources().getString(R.string.AR_DAUGIAU_KARTUS_PER_SAV));
            radiogroup.setVisibility(View.VISIBLE);

            p1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[1]=100;
                    a1[1]=1;
                    p1.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[1]=100;
                    a2[1]=1;
                    p2.setChecked(true);
                    p1.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[1]=100;
                    a3[1]=1;
                    p3.setChecked(true);
                    p2.setChecked(false);
                    p1.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[1]=100;
                    a4[1]=1;
                    p4.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p1.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[1]=100;
                    a5[1]=1;
                    p5.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p1.setChecked(false);
                }
            });
            header.setText(getResources().getString(R.string.KLAUSIMAS2));
            question.setText(getResources().getString(R.string.KAIP_DAŽNAI_JŪS_GERIATE_ALKOHOLINIUS_GĖRIMUS));
            if(i[1]==100) {
                pasikartojimas=false;
                nr++;
                return;
            }
            else {
                pasikartojimas = true;
                return;
            }
        case 2:
            pic.setImageResource(R.drawable.antras);
            header.clearAnimation();
            pic.clearAnimation();
            radiogroup.clearAnimation();
            go.clearAnimation();
            back.clearAnimation();
            question.clearAnimation();
            if(!pasikartojimas) {
                if (toliau) {
                    header.startAnimation(right);
                    pic.startAnimation(right);
                    radiogroup.startAnimation(right);
                    go.startAnimation(right);
                    back.startAnimation(right);
                    question.startAnimation(right);
                }
            }
            else if(!toliau) {
                header.startAnimation(left);
                pic.startAnimation(left);
                radiogroup.startAnimation(left);
                go.startAnimation(left);
                back.startAnimation(left);
                question.startAnimation(left);
            }
            p1.setChecked(false);
            p2.setChecked(false);
            p3.setChecked(false);
            p4.setChecked(false);
            p5.setChecked(false);
            header.setText(getResources().getString(R.string.KLAUSIMAS3));
            question.setText(getResources().getString(R.string.KIEK_STANDANTINIŲ_ALKOHOLINIŲ_VIENETŲ_IŠGERIATE_EILINĘ_DIENĄ_KAI_GERIATE));
            p1.setText(getResources().getString(R.string.AR1_2));
            p2.setText(getResources().getString(R.string.AR3_4));
            p3.setText(getResources().getString(R.string.AR5_6));
            p4.setText(getResources().getString(R.string.AR7_9));
            p5.setText(getResources().getString(R.string.AR_DAUGIAU10));
            p1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[2]=100;
                    a1[2]=1;
                    p1.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[2]=100;
                    a2[2]=1;
                    p2.setChecked(true);
                    p1.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[2]=100;
                    a3[2]=1;
                    p3.setChecked(true);
                    p2.setChecked(false);
                    p1.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[2]=100;
                    a4[2]=1;
                    p4.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p1.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[2]=100;
                    a5[2]=1;
                    p5.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p1.setChecked(false);
                }
            });
            if(i[2]==100) {
                pasikartojimas=false;
                nr++;
                return;
            }
            else {
                pasikartojimas = true;
                return;
            }
        case 3:
            header.clearAnimation();
            pic.clearAnimation();
            radiogroup.clearAnimation();
            go.clearAnimation();
            back.clearAnimation();
            question.clearAnimation();

            pic.setImageResource(R.drawable.trecias);
            if(!pasikartojimas) {
                if (toliau) {
                    header.startAnimation(right);
                    pic.startAnimation(right);
                    radiogroup.startAnimation(right);
                    go.startAnimation(right);
                    back.startAnimation(right);
                    question.startAnimation(right);
                }
            }
            else if(!toliau) {
                header.startAnimation(left);
                pic.startAnimation(left);
                radiogroup.startAnimation(left);
                go.startAnimation(left);
                back.startAnimation(left);
                question.startAnimation(left);
            }
            share.setVisibility(View.GONE);
            p1.setChecked(false);
            p2.setChecked(false);
            p3.setChecked(false);
            p4.setChecked(false);
            p5.setChecked(false);
            header.setText(getResources().getString(R.string.KLAUSIMAS4));
            question.setText(getResources().getString(R.string.KAIP_DAŽNAI_PER_DIENĄ_IŠGERIATE_6_AR_DAUGIAU_STANDARTINIŲ_ALKOHOLIO_VIENETŲ));
            p1.setText(getResources().getString(R.string.NIEKADA));
            p2.setText(getResources().getString(R.string.REČIAU_NEI_KARTĄ_PER_MĖNESĮ));
            p3.setText(getResources().getString(R.string.KARTĄ_PER_MĖNESĮ));
            p4.setText(getResources().getString(R.string.KARTĄ_PER_SAVAITĘ));
            p5.setText(getResources().getString(R.string.KASDIEN_ARBA_BEVEIK_KASDIEN));
            p1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[3]=100;
                    a1[3]=1;
                    p1.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[3]=100;
                    a2[3]=1;
                    p2.setChecked(true);
                    p1.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[3]=100;
                    a3[3]=1;
                    p3.setChecked(true);
                    p2.setChecked(false);
                    p1.setChecked(false);
                    p4.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[3]=100;
                    a4[3]=1;
                    p4.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p1.setChecked(false);
                    p5.setChecked(false);
                }
            });
            p5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[3]=100;
                    a5[3]=1;
                    p5.setChecked(true);
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);
                    p1.setChecked(false);
                }
            });
            is4=false;
            if(i[3]==100) {
                pasikartojimas=false;
                nr++;
                return;
            }
            else {
                pasikartojimas = true;
                return;
            }
        case 4:
            share.setVisibility(View.VISIBLE);
            header.clearAnimation();
            pic.clearAnimation();
            radiogroup.clearAnimation();
            go.clearAnimation();
            back.clearAnimation();
            question.clearAnimation();

            radiogroup.setVisibility(View.GONE);
            header.startAnimation(right);
            pic.startAnimation(right);
            go.startAnimation(right);
            back.startAnimation(right);
            question.startAnimation(right);
            comment.startAnimation(right);
            share.startAnimation(right);
            double pereik=taskai();
            double maxa=maxbalai;
            ratio=(pereik/(maxa*3))*100;
            header.setText(getResources().getString(R.string.TAVYJE_ALKOHOLIKO));
            comment.setVisibility(View.VISIBLE);
            if(ratio>=100)
                ratio=100;

            comment.setText(format.format(ratio)+getResources().getString(R.string.percent));
            if (ratio<=30) {
                pic.setImageResource(R.drawable.v0);
                question.setText(getResources().getString(R.string.NEKAŽKĄ_NEKAŽKĄ));
            }
            else if (ratio<=50&&ratio>30){
                pic.setImageResource(R.drawable.v50);
                question.setText(getResources().getString(R.string.GALĖJAI_IR_GERIAU));
            }
            else if (ratio<=79&&ratio>51){
                pic.setImageResource(R.drawable.v75);
                question.setText(getResources().getString(R.string.LABAI_GERAI));
            }
            else if (ratio>=80)
            { pic.setImageResource(R.drawable.v100);
                question.setText(getResources().getString(R.string.KĄ_VAKARE));
            }
            go.setText(getResources().getString(R.string.IŠ_NAUJO));
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            break;
    }
}
    private double taskai()
    {
        int ans=0;
        if(a1[1]==1)
            ans+=1;
        for (int i=1; i<4; i++)
        {
            if(a2[i]==1)
                ans+=1;
            else if(a3[i]==1)
                ans+=2;
            else if (a4[i]==1)
                ans+=3;
            else if(a5[i]==1)
                ans+=4;
        }
        return ans;
    }
}
