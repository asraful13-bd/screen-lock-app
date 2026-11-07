package com.asraful.screenlock;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLock = findViewById(R.id.btnLock);
        TextView tvDeveloper = findViewById(R.id.tvDeveloper);

        // Lock Screen Button Action
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAccessibilityServiceEnabled()) {
                    Intent intent = new Intent(MainActivity.this, LockAccessibilityService.class);
                    intent.setAction("ACTION_LOCK");
                    startService(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please enable 'Screen Lock' in Accessibility Settings", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                }
            }
        });

        // Clickable Developer Portfolio Action
        tvDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://asraful-portfolio-jet.vercel.app/"));
                startActivity(browserIntent);
            }
        });
    }

    private boolean isAccessibilityServiceEnabled() {
        ComponentName expectedComponent = new ComponentName(this, LockAccessibilityService.class);
        String enabledServices = Settings.Secure.getString(
            getContentResolver(),
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        );
        if (enabledServices == null) return false;

        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
        splitter.setString(enabledServices);
        while (splitter.hasNext()) {
            String component = splitter.next();
            ComponentName cn = ComponentName.unflattenFromString(component);
            if (cn != null && cn.equals(expectedComponent)) return true;
        }
        return false;
    }
}
