package com.atman.wysq.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by tangbingliang on 17/2/24.
 */

public class SelectVideoUtil {

    public static Uri geturi(Context context, android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                        Log.i("urishi", uri.toString());
                    }
                }
            }
        }
        return uri;
    }

    public static String getPath(Activity context, Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    public static String queryImageThumbnailByPath(Context context, String path) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] { MediaStore.Images.Media._ID };
        String selection = MediaStore.Images.Media.DATA + " = ? ";
        String[] selectionArgs = new String[] { path };

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        }
        cursor.close();
        if (id == -1) {
            return null;
        }

        uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[] { MediaStore.Images.Thumbnails.DATA };
        selection = MediaStore.Images.Thumbnails.IMAGE_ID + " = ? ";
        selectionArgs = new String[] { String.valueOf(id) };

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    public static String queryVideoThumbnailByPath(Context context, String path) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] { MediaStore.Video.Media._ID };
        String selection = MediaStore.Video.Media.DATA + " = ? ";
        String[] selectionArgs = new String[] { path };

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int mediaId = -1;
        if (cursor.moveToFirst()) {
            int idxId = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            mediaId = cursor.getInt(idxId);
        }
        cursor.close();
        if (mediaId == -1) {
            return null;
        }

        uri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[] { MediaStore.Video.Thumbnails.DATA };
        selection = MediaStore.Video.Thumbnails.VIDEO_ID + " =  ? ";
        selectionArgs = new String[] { String.valueOf(mediaId) };

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    private static Cursor query(Context context, Uri uri, String[] projection,
                                String selection, String[] selectionArgs) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uri, projection, selection, selectionArgs,
                null);
        return cursor;
    }
}
