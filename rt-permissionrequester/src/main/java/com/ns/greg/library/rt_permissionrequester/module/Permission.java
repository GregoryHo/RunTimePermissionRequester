package com.ns.greg.library.rt_permissionrequester.module;

import android.support.v4.content.PermissionChecker;

/**
 * @author Gregory
 * @since 2017/6/30
 */
public class Permission {

  private String permission;

  private int selfPermission;

  public Permission(String permission) {
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
