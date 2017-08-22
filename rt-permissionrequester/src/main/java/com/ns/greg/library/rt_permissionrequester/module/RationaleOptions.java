package com.ns.greg.library.rt_permissionrequester.module;

/**
 * @author Gregory
 * @since 2017/6/30
 */
public class RationaleOptions {

  private String title;
  private String message;

  public RationaleOptions(String title, String message) {

    this.title = title;
    this.message = message;
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }
}
