package rgu.ac.uk.recordbox.OurActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import rgu.ac.uk.recordbox.R;

public class ContactActivity extends AppCompatActivity {

    //placeholder for a button that can be used in the entire scope of activity
    private Button send_button = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Logo Text colorSpan
        //select the word box and make it orange
        String app = getApplicationInfo().loadLabel(getPackageManager()).toString();
        SpannableString ss = new SpannableString(app);
        ForegroundColorSpan fcsOrange = new ForegroundColorSpan(getResources().getColor(R.color.colorOrange));
        ss.setSpan(fcsOrange,6,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(ss);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
