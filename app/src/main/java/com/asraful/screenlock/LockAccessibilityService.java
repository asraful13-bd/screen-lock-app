package com.asraful.screenlock;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

public class LockAccessibilityService extends AccessibilityService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && "ACTION_LOCK".equals(intent.getAction())) {
            // Android 9+ (Android 11) অফিশিয়াল পাওয়ার বাটন লক মেথড
            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}
}
