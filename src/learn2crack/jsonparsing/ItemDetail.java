package learn2crack.jsonparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by yasird on 13.10.2015.
 */
public class ItemDetail extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        TextView myTextView = (TextView) findViewById(R.id.my_textview);
        myTextView.setText(title);

    }

}