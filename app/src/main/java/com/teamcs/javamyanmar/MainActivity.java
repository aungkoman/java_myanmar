package com.teamcs.javamyanmar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static android.view.View.INVISIBLE;



import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity  implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener{



    private static final String TAG = MainActivity.class.getSimpleName();


    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

    public static final String SAMPLE_FILE = "pwd.pdf";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

    PDFView pdfView;


    Uri uri;


    Integer pageNumber = 1;

    String pdfFileName;
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
        //show_text(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());
        //show_text("pdf loaded");
        //printBookmarksTree(pdfView.getTableOfContents(), "-");


    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
        //show_text("Cannot load page " + page);
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        Integer bookmark_lenght = 0 ;
        for (PdfDocument.Bookmark b : tree) {
            bookmark_lenght++;
            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            //show_text(String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            /*
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
            */

        }
        page_list = new Integer[bookmark_lenght];

        int index_old = 0 ;
        for (PdfDocument.Bookmark b : tree) {
            page_list[index_old] =(int) b.getPageIdx();
            //titles[index_old] = b.getTitle();
            index_old++;
            /*
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
            */
            //titles = new String[bookmark_lenght];
            //titles = new String[] {"မာတိကာ","ဒုတိယအၾကိမ္ အမွာစာ","ပထမအၾကိမ္ အမွာစာ","ေက်းဇူးတင္လြႊာ (ဒုတိယအၾကိမ္)","ေက်းဇူးတင္လြႊာ (ဒုတိယအၾကိမ္)","ေက်းဇူးတင္လြႊာ (ပထမအၾကိမ္)","စာေရးသူ အမွာ","မိတ္ဆက္"," အခန္း (၁) Web Standards","အခန္း (၂) HyperText Markup Language","အခန္း (၃) Cascading Style Sheets - CSS ","အခန္း(၄) JavaScript","အခန္း (၅) Java Script Frameworks","အခန္း(၆) jQuery","အခန္း (၇) Server-side Programming Languages"," အခန္း (၈) PHP Basic","အခန္း (၉) PHP and MySQL","အခန္း (၁၀) Controlling the Enviroment","အခန္း (၁၁) Ajax with jQuery","အခန္း (၁၂) Content Management System - CMS","အခန္း(၁၃) Model-View-Controller","အခန္း (၁၄) HTML5","အခန္း (၁၅) Web Service API","အခန္း (၁၆) RESTful URL","အခန္း (၁၇) Mobile Web","အခန္း (၁၈) Web Application Security","အခန္း (၁၉) Performance Optimization","အခန္း (၂၀) Tools and Utilities","အက်ဥ္းခ်ဳပ္","ေနာက္ဆက္တြဲ(က)","ေနာက္ဆက္တြဲ(ခ)","က်မ္းကိုးစာရင္း"};
            titles = new String[] {"Java မိတ်ဆက်", "JAVA နဲ့ C++"," JAVA ပရိုဂရမ်းမင်း","APPLETS ပရိုဂရမ်းမင်း","GRAPHICS USING APPLETS","OBJECT Orients Programming : OOP","နောက်ထပ်ဘာတွေ ရှိသေးလဲ"};
        }

        // we have to notifiy that list view adapter data is chagned

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getApplicationContext(), titles);
        listView.setAdapter(adapter);

    }


    private void show_text(String str){
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
    }
    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    /*
        class MyTask extends AsyncTask<Integer, Integer, String> {
            @Override
            protected String doInBackground(Integer... params) {
                for (; count <= params[0]; count++) {
                    try {
                        Thread.sleep(1000);
                        publishProgress(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "Task Completed.";
            }
            @Override
            protected void onPostExecute(String result) {
                progressBar.setVisibility(View.GONE);
                txt.setText(result);
                btn.setText("Restart");
            }
            @Override
            protected void onPreExecute() {
                txt.setText("Task Starting...");
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
                txt.setText("Running..."+ values[0]);
                progressBar.setProgress(values[0]);
            }
        }
    */
    Button btn;
    private ProgressBar progressBar;
    TextView txt;
    Integer count =1;


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.simple_listview_layout_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.text1);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.listview_image);
            Typeface tf= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/pyidaungsu.ttf");
            textView.setTypeface(tf);
            textView.setText(values[position]);
            imageView.setImageResource(R.mipmap.ic_launcher);
            return rowView;
        }
    }


    ArrayAdapter<String> arrAdapter;
    ListView listView;
    static String[] titles;
    static Integer[] page_list;

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String FILE_NAME = "filename";
    public static final String TITLES_FILE = "titles";
    public static final String CONTENT_LIST_FILE = "content_list";

    private String log_tag = "AKM";
    private Menu menu;


    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        //show_text("Welcome to PDF Viewer");

        //titles = new String[] { "Welcome to Professional Web Developer" ,"Tap to Read"};
        //page_list = new Integer[] {8,88};

        titles = new String[] {"Java မိတ်ဆက်", "JAVA နဲ့ C++"," JAVA ပရိုဂရမ်းမင်း","APPLETS ပရိုဂရမ်းမင်း","GRAPHICS USING APPLETS","OBJECT Orients Programming : OOP","နောက်ထပ်ဘာတွေ ရှိသေးလဲ"};
        page_list = new Integer[] {3,9,20,31,42,51,62};


        listView = findViewById(R.id.listView);
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, titles);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // load pdf to default page
                // show pdf view
                // hide list view
                // show/ hide loading indicator
                //listView.setVisibility(View.INVISIBLE);

                //listView.setLayoutParams(new ViewGroup.LayoutParams(, "0px"));
                // we have to translte position from page_number

                LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutBookmarkList);
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) layout.getLayoutParams();
                lp.height = 0;
                layout.setLayoutParams(lp);

                LinearLayout layout2 = (LinearLayout)findViewById(R.id.linearLayoutPdf);
                ViewGroup.LayoutParams lp2 = (ViewGroup.LayoutParams) layout2.getLayoutParams();
                lp2.height = ViewGroup.LayoutParams.MATCH_PARENT;;
                layout2.setLayoutParams(lp2);

                Integer page_number = page_list[position];
                //show_text("Clicked on "+position+ " => "+page_number);
                fab.show();
                load_pdf(page_number);
            }

        });

        final PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.setVisibility(INVISIBLE);
//
//        String pdf_name = getResources().getString(R.string.pdf_url);
//        pdfView.fromAsset(pdf_name)
//                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
//                .enableSwipe(true) // allows to block changing pages using swipe
//                .swipeHorizontal(false)
//                .enableDoubletap(true)
//                .defaultPage(0)
//                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
//                .password(null)
//                .scrollHandle(null)
//                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
//                // spacing between pages in dp. To define spacing color, set view background
//                .spacing(0)
//                .onLoad(new OnLoadCompleteListener() {
//                    @Override
//                    public void loadComplete(int nbPages) {
//                        //show_text("loadComplete");
//                        printBookmarksTree(pdfView.getTableOfContents(), "-");
//                    }
//                })
//                .load();



        //load_pdf(10);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutBookmarkList);
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) layout.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                layout.setLayoutParams(lp);

                LinearLayout layout2 = (LinearLayout)findViewById(R.id.linearLayoutPdf);
                ViewGroup.LayoutParams lp2 = (ViewGroup.LayoutParams) layout2.getLayoutParams();
                lp2.height = 0;
                layout2.setLayoutParams(lp2);

                fab.hide();

            }
        });
        fab.hide();


        // intrestial ads

        // interstitial ads
        // ca-app-pub-3940256099942544/1033173712
        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        String intersialCode = getString(R.string.interstitial1);
        mInterstitialAd.setAdUnitId(intersialCode);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("TAG", "The interstitial onAdFailedToLoad errorCode " + errorCode);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d("TAG", "The interstitial onAdOpened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("TAG", "The interstitial onAdClicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("TAG", "The interstitial onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                // Load the next interstitial.
                Log.d("TAG", "The interstitial onAdClosed");
                // mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        mAdView = findViewById(R.id.adView);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //load_pdf(90);
            // call to another activity or just show haha
            haha();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void load_pdf(int PageNo){

        final PDFView pdfView = findViewById(R.id.pdfView);

        String pdf_name = getResources().getString(R.string.pdf_url);
        pdfView.fromAsset(pdf_name)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(PageNo)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        //show_text("loadComplete on Setting");
                        pdfView.setVisibility(View.VISIBLE);
                    }
                })
                .load();
    }


    private boolean _doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        // check we can go back

        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutBookmarkList);
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) layout.getLayoutParams();
        if(lp.height != ViewGroup.LayoutParams.MATCH_PARENT){
            //show_text("does not patrent");
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layout.setLayoutParams(lp);


            LinearLayout layout2 = (LinearLayout)findViewById(R.id.linearLayoutPdf);
            ViewGroup.LayoutParams lp2 = (ViewGroup.LayoutParams) layout2.getLayoutParams();
            lp2.height = 0;
            layout2.setLayoutParams(lp2);


            fab.hide();


            return;
        }
        //show_text("it parent");

        if (_doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }
        this._doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, getResources().getString(R.string.exit_greeting), Toast.LENGTH_SHORT).show();
        Snackbar.make(getWindow().getDecorView(), getResources().getString(R.string.exit_greeting), Snackbar.LENGTH_LONG).show();

        //WV.loadUrl("javascript:android_hello()");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                _doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void haha(){
        new AlertDialog.Builder(this)
                .setTitle("TEAM CS")
                .setMessage("Java Myanmar")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                        //Toast.makeText(getApplicationContext(),"ok clicked",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
