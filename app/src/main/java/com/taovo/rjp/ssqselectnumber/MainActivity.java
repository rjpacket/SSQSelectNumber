package com.taovo.rjp.ssqselectnumber;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SelectBallsView view1;
    private SelectBallsView view2;
    private SelectBallsView view3;
    private SelectBallsView view4;
    private SelectBallsView view5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view1 = (SelectBallsView) findViewById(R.id.view_1);

        view2 = (SelectBallsView) findViewById(R.id.view_2);

        view3 = (SelectBallsView) findViewById(R.id.view_3);

        view4 = (SelectBallsView) findViewById(R.id.view_4);

        view5 = (SelectBallsView) findViewById(R.id.view_5);

    }

    public void showMiss(View view){
        view1.setShowMissValue(!view1.getShowMissValue());
        view2.setShowMissValue(!view2.getShowMissValue());
        view3.setShowMissValue(!view3.getShowMissValue());
        view4.setShowMissValue(!view4.getShowMissValue());
        view5.setShowMissValue(!view5.getShowMissValue());
    }

    public void confirm(View view){
        String str1 = view1.getSelectBallsString();
        String str2 = view2.getSelectBallsString();
        String str3 = view3.getSelectBallsString();
        String str4 = view4.getSelectBallsString();
        String str5 = view5.getSelectBallsString();
        Toast.makeText(this, "选中的号码是:\n" + str1 + "\n" + str2 + "\n" + str3 + "\n" + str4 + "\n" + str5, Toast.LENGTH_SHORT).show();
    }
}
