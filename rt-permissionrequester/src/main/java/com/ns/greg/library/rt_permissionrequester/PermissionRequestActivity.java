package com.ns.greg.library.rt_permissionrequester;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gregory on 2017/6/30.
 */

public abstract class PermissionRequestActivity extends AppCompatActivity {

  public static final int PERMISSION_REQUEST_CODE = 7788;

  protected abstract void onRequest(@RequiresPermission String permission, boolean granted);

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_CODE:
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
          onRequest(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
        }

        break;
    }
  }
}
