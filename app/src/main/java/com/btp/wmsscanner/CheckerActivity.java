package com.btp.wmsscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.List;

public class CheckerActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;
    private ViewfinderView viewfinderView;
    private BeepManager beepManager;
    Button backToScanner;
    TextView resultData, stat, resultText;
    private String lastText;
    ImageView g, s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_checker);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        backToScanner = findViewById(R.id.bt_back);
        g = findViewById(R.id.stat_green);
        s = findViewById(R.id.stat_red);
        resultText = findViewById(R.id.scannerStat);
        viewfinderView = findViewById(R.id.zxing_viewfinder_view);
        barcodeView = findViewById(R.id.barcode_view);
        resultData = findViewById(R.id.tv_result);
        beepManager = new BeepManager(this);
        barcodeView.decodeContinuous(callback);
        backToScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckerActivity.this, ScannerActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    //======================================================================
    //=========================OVERRIDE CALLS===============================
    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
        //barcodeView2.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
        //barcodeView2.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {

        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    //======================================================================


    //-----------------------------------------
    //Zxing barcode decoder.
    //-----------------------------------------
    private BarcodeCallback callback = new BarcodeCallback() {

        @Override
        public void barcodeResult(BarcodeResult result) {
           /* if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }*/

            lastText = result.getText();
            //barcodeView.setStatusText(result.getText());
            resultText.setText("Latest Result \n" + lastText);
            resultData.setText(lastText);
            beepManager.playBeepSoundAndVibrate();

            //Added preview of scanned barcode
           /* ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));*/

            g.setVisibility(View.INVISIBLE);
            s.setVisibility(View.VISIBLE);
            stat.setText(R.string.stat_wait);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    g.setVisibility(View.VISIBLE);
                    s.setVisibility(View.INVISIBLE);
                    stat.setText(R.string.stat_ready_scan);
                }
            }, 300);
            barcodeView.pause();
            barcodeView.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

}