package com.ns.greg.library.rt_permissionrequester;

import android.support.v4.content.PermissionChecker;
import com.ns.greg.library.rt_permissionrequester.external.RequestingPermission;

/**
 * @author Gregory
 * @since 2017/6/30
 */
class Permission {

  private final String permission;
  private int selfPermission;

  Permission(String permission) {
    this.permission = permission;
  }

  @Override public String toString() {
    return permission;
  }

  @RequestingPermission.RequestPermission public String getPermission() {
    return permission;
  }

  public void setSelfPermission(int selfPermission) {
    this.selfPermission = selfPermission;
  }

  @PermissionChecker.PermissionResult public int getSelfPermission() {
    return selfPermission;
  }
}
