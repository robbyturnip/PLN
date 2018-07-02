package robbyturnip333.gmail.com.pln;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String url = "http://agungbudiprasetyo.atspace.com/xml/daftarSkripsi.xml";
    private TextView isi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isi = (TextView) findViewById(R.id.isi);
        new GetData(this, url).execute();
    }

    public void callBackData(StringBuilder result) {
        isi.setText(result);

    }
}