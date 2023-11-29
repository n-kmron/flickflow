package g58008.mobg5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import g58008.mobg5.ui.theme.AppTheme

private const val TAG = "MainActivity"

/**
 * @author Cameron Noupoué
 * @version 1.0
 */
class MainActivity : ComponentActivity() {

    /**
     * When the system create the application
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreated called")
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }

    /**
     * Makes the application visible on the screen, but no user interaction is possible yet.
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    /**
     * Brings the app to the foreground and the user can now interact.
     */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    /**
     * Called when the activity is about to be restarted after stopping.
     * This method is typically called after `onStop()` and before `onStart()`.
     */
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    /**
     * Called when the activity is no longer visible to the user
     * Used for cleanup or saving state
     */
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    /**
     * Called when the activity is no longer visible to the user and is not in the foreground.
     */
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    /**
     * Called when the activity is about to be destroyed. This is the final callback
     * that the activity will receive.
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}