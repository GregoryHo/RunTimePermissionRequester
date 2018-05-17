package com.ns.greg.library.rt_permissionrequester.external;

import java.util.List;

/**
 * @author gregho
 * @since 2018/5/16
 */
public interface SimplePermissionListener {

  void onGranted(List<String> permissions);

  void onDenied(List<String> permissions);
}
