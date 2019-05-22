package com.bashar.salatreminder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TermsAndCondActivity extends AppCompatActivity {

    TextView pp_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        int itemNo = intent.getIntExtra("itemNo", 0);


        if(itemNo == 0) {
            setContentView(R.layout.layout_terms_condition);
            setTitle("Terms and Conditions");
        }

        else {
            setContentView(R.layout.layout_privacy_policy);
            //TextView textInfo = (TextView)findViewById(R.id.text_information);
            pp_info = (TextView)findViewById(R.id.pp_information);
            setTitle("Privacy Policy");

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Prevent CheckBox state from being toggled when link is clicked
                    widget.cancelPendingInputEvents();
                    // Do action for link text...
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy"));
                    startActivity(browserIntent);

                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    // Show links with underlines (optional)
                    ds.setUnderlineText(false);
                }
            };

            SpannableString linkText = new SpannableString(getResources().getString(R.string.pp_info_collection));
            linkText.setSpan(clickableSpan, 470, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            CharSequence cs = TextUtils.expandTemplate(
                    "^1", linkText);

            pp_info.setText(cs);
            pp_info.setLinkTextColor(getResources().getColor(R.color.colorPrimary));
// Finally, make links clickable
            pp_info.setMovementMethod(LinkMovementMethod.getInstance());
        }


    }
}
