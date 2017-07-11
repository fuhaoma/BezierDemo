package mfh.com.bezierdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static mfh.com.bezierdemo.R.id.advance;
import static mfh.com.bezierdemo.R.id.expert;

/**
 * 贝塞尔曲线，一阶二阶三阶。
 */
public class MainActivity extends AppCompatActivity {
    private View advanceView;
    private View basicView;
    private View expertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        advanceView = findViewById(R.id.advanceview);
        basicView = findViewById(R.id.basicview);
        expertView = findViewById(R.id.expertview);
    }

    public void MyClick(View view) {
        basicView.setVisibility(View.GONE);
        advanceView.setVisibility(View.GONE);
        expertView.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.basic:
                basicView.setVisibility(View.VISIBLE);
                break;
            case advance:
                advanceView.setVisibility(View.VISIBLE);
                break;
            case expert:
                expertView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
