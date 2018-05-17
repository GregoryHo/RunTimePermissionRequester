package com.ns.greg.library.rt_permissionrequester;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ns.greg.library.rt_permissionrequester.external.RationaleOption;
import com.ns.greg.library.rt_permissionrequester.external.RequestingPermission;
import com.ns.greg.library.rt_permissionrequester.external.SimplePermissionListener;

import static com.ns.greg.library.rt_permissionrequester.PermissionConstants.KEY_BUNDLE_REQUEST;
import static com.ns.greg.library.rt_permissionrequester.PermissionConstants.KEY_PERMISSIONS;
import static com.ns.greg.library.rt_permissionrequester.PermissionConstants.KEY_RATIONALE_OPTIONS;

/**
 * @author Gregory
 * @since 2017/6/29
 */
public class PermissionRequester {

  private final Context context;
  private final String[] permissions;
  private final RationaleOption rationaleOption;
  // static listener for communicate between activities
  static SimplePermissionListener listener;

  private PermissionRequester(Context context, String[] permissions,
      RationaleOption rationaleOption, SimplePermissionListener listener) {
    this.context = context;
    this.permissions = permissions;
    this.rationaleOption = rationaleOption;
    PermissionRequester.listener = listener;
  }

  public void request() {
    Intent intent = new Intent(context, PermissionRequestActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Bundle bundle = new Bundle();
    bundle.putStringArray(KEY_PERMISSIONS, permissions);
    bundle.putParcelable(KEY_RATIONALE_OPTIONS, rationaleOption);
    intent.putExtra(KEY_BUNDLE_REQUEST, bundle);
    context.startActivity(intent);
  }

  public static final class Builder {

    private Context context;
    private String[] permissions;
    private RationaleOption rationaleOption;
    private SimplePermissionListener listener;

    public Builder(Context context) {
      this.context = context;
      this.permissions = null;
      this.rationaleOption = null;
      this.listener = null;
    }

    public Builder setPermissions(@RequestingPermission.RequestPermission String... permissions) {
      this.permissions = permissions;
      return this;
    }

    public Builder setRationaleOption(RationaleOption rationaleOption) {
      this.rationaleOption = rationaleOption;
      return this;
    }

    public Builder setListener(SimplePermissionListener listener) {
      this.listener = listener;
      return this;
    }

    public PermissionRequester build() {
      if (permissions == null || permissions.length == 0) {
        throw new NullPointerException("The request permissions is empty.");
      }

      return new PermissionRequester(context, permissions, rationaleOption, listener);
    }
  }
}
