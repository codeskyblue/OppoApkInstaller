package sogou.mobile.explorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import sogou.mobile.explorer.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "ApkInstaller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvPath = (TextView) findViewById(R.id.apk_path);
                String apkPath = tvPath.getText().toString();
                Log.i(TAG, "apkPath: " + apkPath);
                Boolean success = installApkByPath(apkPath);
                Toast.makeText(getApplicationContext(), success ? "Success" : "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean installApkByPath(File apkFile) {
        Context context = getApplicationContext();
        try {
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE); // ACTION_VIEW
            Uri uri;
            if (Build.VERSION.SDK_INT < 24) {
                uri = Uri.fromFile(apkFile);
            } else {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean installApkByPath(String apkPath) {
        return installApkByPath(new File(apkPath));
    }
}