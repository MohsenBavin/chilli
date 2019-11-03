/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bavin.mohsen.backnardeban.Classes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.SelectLessonStudyActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.hawk.Hawk;

import java.util.Map;
import java.util.Objects;

//import androidx.work.OneTimeWorkRequest;
//import androidx.work.WorkManager;

/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 *
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 *
 * <intent-filter>
 *   <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
private static final String DESTINATION_KEY="destination_key";
private static final String DESTINATION="destination";
private static final String DESTINATION_KEY_URL="url";
private static final String DESTINATION_KEY_ACTIVITY="activity";
private static final String TAG = "MyFirebaseMsgService";

private static final String ACTIVITY_MAIN="activity_main";
private static final String ACTIVITY_THIRD="activity_third";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Hawk.init(getBaseContext()).build();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String,String> data=remoteMessage.getData();
            switch (Objects.requireNonNull( data.get( DESTINATION_KEY ) )){
                case DESTINATION_KEY_ACTIVITY:
                    String activity=data.get( DESTINATION );
                    assert activity != null;
                    switch (activity){
                        case ACTIVITY_MAIN:
                            Intent showMain=new Intent( this,MainActivity.class );
                            showMain.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );//برای رفتن از کلاس به اکتیویتی حتما این متد باید فرا خوانی شود
                            sendNotification( Objects.requireNonNull( remoteMessage.getNotification() ).getBody(),showMain );
                            break;
                        case ACTIVITY_THIRD:
                            Intent showThird=new Intent( this, SelectLessonStudyActivity.class );
                            showThird.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );//برای رفتن از کلاس به اکتیویتی حتما این متد باید فرا خوانی شود
                            sendNotification( remoteMessage.getNotification().getBody(),showThird );
                            break;
                    }
                    break;
                case DESTINATION_KEY_URL:
                    String url=data.get( DESTINATION );
                    Hawk.put( "pointh",url );
                  //  Intent showUrl=new Intent( Intent.ACTION_VIEW ,Uri.parse(url));
                  //  sendNotification( remoteMessage.getNotification().getBody(),showUrl );

                    break;
            }
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.

    private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }*/

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody,Intent intent) {
        //Intent intent = new Intent(this, MainActivity.class);
        Log.d(TAG, "sendNotification: " + messageBody);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0
                /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon( R.mipmap.ic_launcher) // notification icon
                .setLargeIcon( BitmapFactory.decodeResource(getResources(), R.mipmap.chilli_icon ) )
                //.setContentTitle("chilli") // title for notification
                .setContentText(messageBody)// message for notification
                .setAutoCancel(true); // clear notification after click
        //Intent intent = new Intent(getApplicationContext(), ACTIVITY_NAME.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
/*
        //String channelId = getString(R.string.default_notification_channel_id);
       // String channelId = "notif";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
               // new NotificationCompat.Builder(this, channelId)
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.chilli)
                        //.setContentTitle(getString(R.string.fcm_message))
                        .setContentTitle("Chilli")
                        .setLargeIcon( BitmapFactory.decodeResource(getResources(), R.mipmap.chilli_icon ) )
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);*/

        // Since android Oreo notification channel is needed.
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }*/

        //notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}