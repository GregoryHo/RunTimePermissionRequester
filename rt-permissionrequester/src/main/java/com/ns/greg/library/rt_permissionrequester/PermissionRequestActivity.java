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
  private final List<String> granted = new ArrayList<>();
  private final List<String> denied = new ArrayList<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    bundle = getIntent().getBundleExtra(KEY_BUNDLE_REQUEST);
    if (bundle == null) {
      finish();
      Log.e(getClass().getSimpleName(), "the request bundle is null.");
    } else {
      checkBundle();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_CODE:
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
          String permission = permissions[i];
          int result = grantResults[i];
          if (result == PackageManager.PERMISSION_GRANTED) {
            granted.add(permission);
            denied.remove(permission);
          }
        }

        notifyRequestResult();
        break;

      default:
        break;
    }
  }

  private void checkBundle() {
    String[] permissions = bundle.getStringArray(KEY_PERMISSIONS);
    if (permissions == null) {
      Log.e(getClass().getSimpleName(), "the request permissions is null.");
      notifyRequestResult();
    } else {
      requestPermission(permissions);
    }
  }

  private void requestPermission(String[] permissions) {
    final List<Permission> permissionList = new ArrayList<>();
    for (String permission : permissions) {
      permissionList.add(new Permission(permission));
    }
    /* check self permission */
    for (Permission permission : permissionList) {
      permission.setSelfPermission(
          ContextCompat.checkSelfPermission(getApplicationContext(), permission.getPermission()));
    }
    /* collect granted and denied */
    for (Permission permission : permissionList) {
      String value = permission.toString();
      if (permission.getSelfPermission() == PackageManager.PERMISSION_GRANTED) {
        granted.add(value);
      } else {
        denied.add(value);
      }
    }

    /* request denied list */
    if (!denied.isEmpty()) {
      RationaleOption rationaleOption = bundle.getParcelable(KEY_RATIONALE_OPTIONS);
      final String[] requestArray = denied.toArray(new String[0]);
      // If permission rationale is needed
      if (rationaleOption != null) {
        boolean shouldShow = false;
        for (String permission : denied) {
          shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
          if (shouldShow) {
            break;
          }
        }

        if (shouldShow) {
          new FastDialog.Builder().setTitle(rationaleOption.getTitle())
              .setMessage(rationaleOption.getMessage())
              .setNegativeButtonLabel(rationaleOption.getNegativeLabel())
              .setPositiveButtonLabel(rationaleOption.getPositiveLabel())
              .setSimpleDialogListener(new SimpleDialogListener() {
                @Override public void onNegativeClick(String tag, Dialog dialog) {
                  super.onNegativeClick(tag, dialog);
                  notifyRequestResult();
                }

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
      /* nothing needs to request */
      notifyRequestResult();
    }
  }

  private void requestPermissions(String[] requestArray) {
    ActivityCompat.requestPermissions(this, requestArray, PERMISSION_REQUEST_CODE);
  }

  private void notifyRequestResult() {
    if (PermissionRequester.listener != null) {
      PermissionRequester.listener.onGranted(granted);
      PermissionRequester.listener.onDenied(denied);
    }

    finish();
  }
}
