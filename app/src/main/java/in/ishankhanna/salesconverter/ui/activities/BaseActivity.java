package in.ishankhanna.salesconverter.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by ishan on 03/04/16.
 */
public class BaseActivity extends AppCompatActivity {

    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
