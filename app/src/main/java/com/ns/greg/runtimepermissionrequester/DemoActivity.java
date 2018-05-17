package com.ns.greg.runtimepermissionrequester;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ns.greg.library.rt_permissionrequester.PermissionRequester;
import com.ns.greg.library.rt_permissionrequester.external.RationaleOption;
import com.ns.greg.library.rt_permissionrequester.external.RequestingPermission;
import com.ns.greg.library.rt_permissionrequester.external.SimplePermissionListener;
import java.util.List;

/**
 * Created by Gregory on 2017/6/29.
 */

public class DemoActivity extends Activity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_demo);
    new PermissionRequester.Builder(getApplicationContext()).setPermissions(RequestingPermission.CAMERA, RequestingPermission.READ_CALENDAR)
        .setRationaleOption(new RationaleOption("RTPR APP", "I really need your permissions."))
        .setListener(new SimplePermissionListener() {
          @Override public void onGranted(List<String> permissions) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Granted: ");
            for (String permission: permissions) {
              stringBuilder.append(permission).append(" ");
            }

            System.out.println(stringBuilder.toString());
          }

          @Override public void onDenied(List<String> permissions) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Denied: ");
            for (String permission: permissions) {
              stringBuilder.append(permission).append(" ");
            }

            System.out.println(stringBuilder.toString());
          }
        })
        .build()
        .request();
  }

  @Override protected void onResume() {
    super.onResume();
  }
}

