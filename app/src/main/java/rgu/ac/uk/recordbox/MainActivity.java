package rgu.ac.uk.recordbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logo Text colorSpan
        //select the word box and make it orange
        String app = getApplicationInfo().loadLabel(getPackageManager()).toString();
        SpannableString ss = new SpannableString(app);
        ForegroundColorSpan fcsOrange = new ForegroundColorSpan(getResources().getColor(R.color.colorOrange));
        ss.setSpan(fcsOrange,6,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(ss);

    }
}
