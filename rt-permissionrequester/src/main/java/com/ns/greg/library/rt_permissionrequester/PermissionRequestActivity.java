package com.ns.greg.library.rt_permissionrequester;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import com.ns.greg.library.fastdialogfragment.FastDialog;
import com.ns.greg.library.fastdialogfragment.listener.SimpleDialogListener;
import com.ns.greg.library.rt_permissionrequester.external.RationaleOption;
import com.ns.greg.library.rt_permissionrequester.external.SimplePermissionListener;
import java.util.ArrayList;
import java.util.List;

import static com.ns.greg.library.rt_permissionrequester.PermissionConstants.KEY_BUNDLE_REQUEST;
import static com.ns.greg.library.rt_permissionrequester.PermissionConstants.KEY_PERMISSIONS;
import static com.ns.greg.library.rt_permissionrequester.PermissionConstants.KEY_RATIONALE_OPTIONS;

/**
 * @author Gregory
 * @since 2017/6/30
 */
public final class PermissionRequestActivity extends Activity {

  public static final int PERMISSION_REQUEST_CODE = 0xBC;

  private Bundle bundle;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    bundle = getIntent().getBundleExtra(KEY_BUNDLE_REQUEST);
    if (bundle == null) {
      finish();
      Log.e(getClass().getSimpleName(), "the request bundle is null.");
    } else {
      request();
    }
  }

  private void request() {
    List<Permission> permissionList = new ArrayList<>();
    String[] permissions = bundle.getStringArray(KEY_PERMISSIONS);
    if (permissions == null) {
      finish();
      Log.e(getClass().getSimpleName(), "the request permissions is null.");
    } else {
      for (String permission : permissions) {
        permissionList.add(new Permission(permission));
      }

      // Check self permission
      for (Permission permission : permissionList) {
        permission.setSelfPermission(
            ContextCompat.checkSelfPermission(getApplicationContext(), permission.getPermission()));
      }

      // Generate the requests that is consist of permission that is not granted
      List<String> requests = new ArrayList<>();
      for (Permission permission : permissionList) {
        if (permission.getSelfPermission() != PackageManager.PERMISSION_GRANTED) {
          requests.add(permission.toString());
        }
      }

      // Request if the requests is not empty
      if (!requests.isEmpty()) {
        RationaleOption rationaleOption = bundle.getParcelable(KEY_RATIONALE_OPTIONS);
        final String[] requestArray = requests.toArray(new String[requests.size()]);
        // If permission rationale is needed
        if (rationaleOption != null) {
          boolean shouldShow = false;
          for (String request : requests) {
            shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, request);
            if (shouldShow) {
              break;
            }
          }

          if (shouldShow) {
            new FastDialog.Builder().setTitle(rationaleOption.getTitle())
                .setMessage(rationaleOption.getMessage())
                .setPositiveButtonLabel("OK")
                .setNegativeButtonLabel("CANCEL")
                .setSimpleDialogListener(new SimpleDialogListener() {
                  @Override public void onPositiveClick(String tag, Dialog dialog) {
                    requestPermissions(requestArray);
                    dialog.dismiss();
                  }
                })
                .build(getFragmentManager(), "RATIONALE");
          } else {
            requestPermissions(requestArray);
          }
        } else {
          requestPermissions(requestArray);
        }
      } else {
        /* nothing needs to request, just fire the callback */
        SimplePermissionListener listener = PermissionRequester.listener;
        if (listener != null) {
          List<String> list = new ArrayList<>(permissionList.size());
          for (Permission permission : permissionList) {
            list.add(permission.getPermission());
          }

          listener.onGranted(list);
        }

        finish();
      }
    }
  }

  private void requestPermissions(String[] requestArray) {
    ActivityCompat.requestPermissions(this, requestArray, PERMISSION_REQUEST_CODE);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_CODE:
        SimplePermissionListener listener = PermissionRequester.listener;
        if (listener != null) {
          List<String> granted = new ArrayList<>();
          List<String> denied = new ArrayList<>();
          int size = permissions.length;
          for (int i = 0; i < size; i++) {
            String permission = permissions[i];
            int result = grantResults[i];
            if (result == PackageManager.PERMISSION_GRANTED) {
              granted.add(permission);
            } else {
              denied.add(permission);
            }
          }

          listener.onGranted(granted);
          listener.onDenied(denied);
        }

        finish();
        break;

      default:
        break;
    }
  }
}
