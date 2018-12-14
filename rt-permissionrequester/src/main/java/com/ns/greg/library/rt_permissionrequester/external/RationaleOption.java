package com.ns.greg.library.rt_permissionrequester.external;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Gregory
 * @since 2017/6/30
 */
public class RationaleOption implements Parcelable {

  private static final String CANCEL = "CANCEL";
  private static final String OK = "OK";

  private final String title;
  private final String message;
  private final String negativeLabel;
  private final String positiveLabel;

  public RationaleOption(String title, String message) {
    this(title, message, CANCEL, OK);
  }

  public RationaleOption(String title, String message, String negativeLabel, String positiveLabel) {
    this.title = title;
    this.message = message;
    this.negativeLabel = negativeLabel;
    this.positiveLabel = positiveLabel;
  }

  private RationaleOption(Parcel in) {
    title = in.readString();
    message = in.readString();
    negativeLabel = in.readString();
    positiveLabel = in.readString();
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }

  public String getNegativeLabel() {
    return negativeLabel;
  }

  public String getPositiveLabel() {
    return positiveLabel;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(message);
    dest.writeString(negativeLabel);
    dest.writeString(positiveLabel);
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
