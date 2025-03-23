package com.rogger.myapplication.ui.terms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.rogger.myapplication.R;

public class TermsActivity extends AppCompatActivity {
    private WebView webView;
    private WebAppInterface webAppInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        webView = findViewById(R.id.web_view);
        ActionBar actionBar = getSupportActionBar();

        // Verifica se a barra de ação existe (pode ser nula em alguns casos).
        if (actionBar != null) {
            // Habilita o botão "Up" (seta para a esquerda).
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Termos de Uso");
        }

        webView.loadUrl("file:///android_asset/terms2.html");
        webAppInterface = new WebAppInterface(this, webView, this.getClass());
        webView.addJavascriptInterface(webAppInterface, "Android"); // "Android" is the name you'll use in JavaScript

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shearch, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_print) {
            webView.post(() -> webAppInterface.createWebPrintJob(webView));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // JavaScript Interface
    public static class WebAppInterface {
        private final Context context;
        private final WebView webView;
        private final Class<?> targetActivity;

        public WebAppInterface(Context context, WebView webView, Class<?> targetActivity) {
            this.context = context;
            this.webView = webView;
            this.targetActivity = targetActivity;
        }

        @JavascriptInterface
        public void print() {
            // Run on the UI thread because we're interacting with UI elements
            webView.post(() -> createWebPrintJob(webView));
        }

        @JavascriptInterface
        public void gotohome() {
            Intent intent = new Intent(context, targetActivity);
            if (context instanceof Activity) {
                ((Activity) context).finish(); // Finish the current activity
            }
            context.startActivity(intent);
        }

        private void createWebPrintJob(WebView webView) {
            PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
            String jobName = context.getString(R.string.app_name) + " Document";
            android.print.PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
            PrintAttributes printAttributes = new PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .build();
            printManager.print(jobName, printAdapter, printAttributes);
        }
    }
}
