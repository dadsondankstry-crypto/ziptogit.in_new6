package com.exemplo.personagem3d;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import java.io.OutputStream;

public class MainActivity extends Activity {
    private static final int EXPORTAR_GLB = 1202;
    private byte[] glbPendente;
    private WebView webView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface public void salvarGLB(String base64) {
                try {
                    final byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
                    runOnUiThread(() -> abrirSeletor(bytes));
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao exportar GLB", Toast.LENGTH_LONG).show());
                }
            }
        }, "AndroidBridge");
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void abrirSeletor(byte[] bytes) {
        glbPendente = bytes;
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("model/gltf-binary");
        intent.putExtra(Intent.EXTRA_TITLE, "personagem-175cm.glb");
        startActivityForResult(intent, EXPORTAR_GLB);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EXPORTAR_GLB) {
            if (resultCode == RESULT_OK && data != null && glbPendente != null) {
                Uri uri = data.getData();
                try (OutputStream output = getContentResolver().openOutputStream(uri)) {
                    if (output == null) throw new IllegalStateException("Sem acesso ao arquivo");
                    output.write(glbPendente);
                    Toast.makeText(this, "GLB exportado com sucesso", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Erro ao gravar arquivo", Toast.LENGTH_LONG).show();
                }
            }
            glbPendente = null;
        }
    }

    @Override public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}
