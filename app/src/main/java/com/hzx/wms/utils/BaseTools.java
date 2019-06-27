package com.hzx.wms.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by linhu on 2019/6/24.
 */

public class BaseTools {

    public static void openLink(Context context, String content) {
        Uri issuesUrl = Uri.parse(content);
        Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
        context.startActivity(intent);
    }
}
