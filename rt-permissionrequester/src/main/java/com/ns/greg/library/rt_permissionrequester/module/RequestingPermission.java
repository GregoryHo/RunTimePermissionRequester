package com.ns.greg.library.rt_permissionrequester.module;

import android.Manifest;
import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gregory
 * @since 2017/6/30
 */
public class RequestingPermission {

  /*--------------------------------
   * Calendar
   *-------------------------------*/

  public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
  public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;

  /*--------------------------------
   * Camera
   *-------------------------------*/

  public static final String CAMERA = Manifest.permission.CAMERA;

  /*--------------------------------
   * CONTACTS
   *-------------------------------*/

  public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
  public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
  public static final String GET_ACCOUNT = Manifest.permission.GET_ACCOUNTS;

  /*--------------------------------
   * Location
   *-------------------------------*/

  public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
  public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
  
  /*--------------------------------
   * Microphone
   *-------------------------------*/

  public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
  
  /*--------------------------------
   * Phone
   *-------------------------------*/

  public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
  public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
  public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
  public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
  public static final String ADD_VOICE_MAIL = Manifest.permission.ADD_VOICEMAIL;
  public static final String USE_SIP = Manifest.permission.USE_SIP;
  public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
  
  /*--------------------------------
   * Sensors
   *-------------------------------*/

  public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
  
  /*--------------------------------
   * SMS
   *-------------------------------*/

  public static final String SEND_SMS = Manifest.permission.SEND_SMS;
  public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
  public static final String READ_SMS = Manifest.permission.READ_SMS;
  public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
  public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;

  /*--------------------------------
   * Storage
   *-------------------------------*/

  public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
  public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

  @StringDef({
      READ_CALENDAR, WRITE_CALENDAR, CAMERA, READ_CONTACTS, WRITE_CONTACTS, GET_ACCOUNT,
      ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, RECORD_AUDIO, READ_PHONE_STATE, CALL_PHONE,
      READ_CALL_LOG, WRITE_CALL_LOG, ADD_VOICE_MAIL, USE_SIP, PROCESS_OUTGOING_CALLS, BODY_SENSORS,
      SEND_SMS, RECEIVE_SMS, READ_SMS, RECEIVE_WAP_PUSH, RECEIVE_MMS, READ_EXTERNAL_STORAGE,
      WRITE_EXTERNAL_STORAGE
  }) @Retention(RetentionPolicy.SOURCE) public @interface RequestPermission {

  }
}
