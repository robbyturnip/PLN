package robbyturnip333.gmail.com.pln;

/**
 * Created by robby on 02/07/18.
 */


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class GetData extends AsyncTask<StringBuilder, Void, StringBuilder> {

    private MainActivity activity;
    private String url;
    private XmlPullParserFactory xmlFactoryObject;
    private ProgressDialog pDialog;

    public GetData(MainActivity activity, String url) {
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setTitle("Get Data Information from XML");
        pDialog.setMessage("Loading...");
        pDialog.show();
    }


    protected StringBuilder doInBackground(StringBuilder... params) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(20000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream stream = connection.getInputStream();

            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            myParser.setInput(stream, null);
            StringBuilder result = parseXML(myParser);
            stream.close();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }
    }

    public StringBuilder parseXML(XmlPullParser myParser) {
        int event;
        String text = null;
        StringBuilder sb = new StringBuilder();
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG)
                    sb.append("\n<" + myParser.getName() + ">");
                else if (event == XmlPullParser.END_TAG)
                    sb.append("</" + myParser.getName() + ">");
                else if (event == XmlPullParser.TEXT)
                    sb.append(myParser.getText());
                event = myParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    protected void onPostExecute(StringBuilder result) {
        //call back data to main thread
        pDialog.dismiss();
        activity.callBackData(result);

    }
}
