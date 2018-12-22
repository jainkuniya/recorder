import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class Test extends BroadcastReceiver {
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;

    private MediaRecorder mRecorder = null;

    public void onReceive(Context context , Intent intent) {
        // Record to the external cache directory for visibility
        mFileName = context.getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        String action = intent.getAction();

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // Do something when power connected
            startRecording();
            Toast.makeText(context, "Starting recording", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            // Do something when power disconnected
            stopRecording();
            Toast.makeText(context, "Stopping recording", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        //TODO Send audio file to server
    }
}
