package com.ns.greg.library.rt_permissionrequester.external;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Gregory
 * @since 2017/6/30
 */
public class RationaleOption implements Parcelable {

  private final String title;
  private final String message;

  public RationaleOption(String title, String message) {
    this.title = title;
    this.message = message;
  }

  private RationaleOption(Parcel in) {
    title = in.readString();
    message = in.readString();
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(message);
  }

  public static final Creator<RationaleOption> CREATOR = new Creator<RationaleOption>() {
    @Override public RationaleOption createFromParcel(Parcel in) {
      return new RationaleOption(in);
    }

    @Override public RationaleOption[] newArray(int size) {
      return new RationaleOption[size];
    }
  };
}
