package com.ns.greg.library.rt_permissionrequester;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.ns.greg.library.fastdialogfragment.FastDialogBuilder;
import com.ns.greg.library.fastdialogfragment.listener.SimpleDialogListener;
import com.ns.greg.library.rt_permissionrequester.module.Permission;
import com.ns.greg.library.rt_permissionrequester.module.RationaleOptions;
import com.ns.greg.library.rt_permissionrequester.module.RequestingPermission;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.ns.greg.library.rt_permissionrequester.PermissionRequestActivity.PERMISSION_REQUEST_CODE;

/**
 * Created by Gregory on 2017/6/29.
 */

public class PermissionRequester {

  private PermissionRequestActivity activity;

  private List<Permission> permissions;

  private RationaleOptions rationaleOptions;

  private PermissionRequester(PermissionRequestActivity referenceActivity, List<Permission> permissions,
      RationaleOptions rationaleOptions) {
    WeakReference<PermissionRequestActivity> weakReference = new WeakReference<>(referenceActivity);
    this.activity = weakReference.get();
    this.permissions = permissions;
    this.rationaleOptions = rationaleOptions;
  }

  public void request() {
    // Check self permission
    for (Permission permission : permissions) {
      permission.setSelfPermission(
          ContextCompat.checkSelfPermission(activity, permission.getPermission()));
    }

    // Generate the requests that is consist of permission that is not granted
    List<String> requests = new ArrayList<>();
    for (Permission permission : permissions) {
      if (permission.getSelfPermission() != PackageManager.PERMISSION_GRANTED) {
        requests.add(permission.toString());
      }
    }

    // Request if the requests is not empty
    if (!requests.isEmpty()) {
      final String[] requestArray = requests.toArray(new String[requests.size()]);
      // If permission rationale is needed
      if (rationaleOptions != null) {
        boolean shouldShow = false;
        for (String request : requests) {
          shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, request);
          if (shouldShow) {
            break;
          }
        }

        if (shouldShow) {
          FastDialogBuilder.builder()
              .setTitle(rationaleOptions.getTitle())
              .setMessage(rationaleOptions.getMessage())
              .setPositiveButtonLabel("OK")
              .setNegativeButtonLabel("CANCEL")
              .setListener(new SimpleDialogListener() {
                @Override public void onPositiveClick(String tag, Dialog dialog) {
                  requestPermissions(requestArray);
                  dialog.dismiss();
                }
              })
              .build(activity.getFragmentManager(), "RATIONALE");
        } else {
          requestPermissions(requestArray);
        }
      } else {
        // Just do the request
        requestPermissions(requestArray);
      }
    } else {
      for (Permission permission : permissions) {
        activity.onRequest(permission.getPermission(), true);
      }
    }
  }

  private void requestPermissions(String[] requestArray) {
    ActivityCompat.requestPermissions(activity, requestArray, PERMISSION_REQUEST_CODE);
  }

  public static final class Builder {

    private List<Permission> permissions = new ArrayList<>();
    private RationaleOptions rationaleOptions;

    public Builder setPermissions(
        @RequestingPermission.RequestPermission @NonNull String... permissions) {
      for (String permission : permissions) {
        this.permissions.add(new Permission(permission));
      }

      return this;
    }

    public Builder setRationaleOptions(RationaleOptions rationaleOptions) {
      this.rationaleOptions = rationaleOptions;

      return this;
    }

    public PermissionRequester build(@NonNull PermissionRequestActivity activity) {
      if (permissions.isEmpty()) {
        throw new NullPointerException("The request permissions is empty.");
      }

      return new PermissionRequester(activity, permissions, rationaleOptions);
    }
  }
}
