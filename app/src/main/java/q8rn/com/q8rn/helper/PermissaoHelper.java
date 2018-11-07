package q8rn.com.q8rn.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class PermissaoHelper {
    
    private static final String PERMISSION_IS_GRANTED = "Permission is granted";
    private static final String PERMISSION_IS_REVOKED = "Permission is revoked";
    private static final String TAG = "PermissaoHelper";
    private Context context;

    public PermissaoHelper(Context context) {
        this.context = context;
    }

    public boolean temPermissaoArmazenamento() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, PERMISSION_IS_GRANTED);
                return true;
            } else {

                Log.v(TAG, PERMISSION_IS_REVOKED);
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            Log.v(TAG,PERMISSION_IS_GRANTED);
            return true;
        }
    }
}
