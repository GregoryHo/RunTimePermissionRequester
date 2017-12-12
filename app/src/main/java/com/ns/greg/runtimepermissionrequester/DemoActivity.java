package com.ns.greg.runtimepermissionrequester;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ns.greg.library.rt_permissionrequester.PermissionRequestActivity;
import com.ns.greg.library.rt_permissionrequester.external.PermissionRequester;
import com.ns.greg.library.rt_permissionrequester.module.RationaleOptions;
import com.ns.greg.library.rt_permissionrequester.module.RequestingPermission;

/**
 * Created by Gregory on 2017/6/29.
 */

public class DemoActivity extends PermissionRequestActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    new PermissionRequester.Builder().setPermissions(RequestingPermission.CAMERA,
        RequestingPermission.READ_CALENDAR)
        .setRationaleOptions(new RationaleOptions("RTPR APP", "I really need your permissions."))
        .build(this)
        .request();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onRequest(String permission, boolean granted) {
    System.out.println(
        "onRequest - " + "permission = [" + permission + "], granted = [" + granted + "]");
  }
}

