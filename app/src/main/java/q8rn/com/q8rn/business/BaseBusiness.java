package q8rn.com.q8rn.business;

import android.content.Context;

/** Created by gabriel on 07/04/17. */

@SuppressWarnings("WeakerAccess")
public class BaseBusiness {

    protected Context mContext;

    protected BaseBusiness(Context context) {
        this.mContext = context;
    }
}
