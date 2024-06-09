package com.trex.markdown;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.io.File;
import java.util.ArrayList;

public class FileArrayAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final ArrayList<String> mFilePaths;

    public FileArrayAdapter(@NonNull Context context, @NonNull ArrayList<String> filePaths) {
        super(context, R.layout.list_item_layout, filePaths);
        this.mContext = context;
        this.mFilePaths = filePaths;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iconImageView = convertView.findViewById(R.id.iconImageView);
            viewHolder.fileNameTextView = convertView.findViewById(R.id.fileNameTextView);
            viewHolder.fileSizeTextView = convertView.findViewById(R.id.fileSizeTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String filePath = mFilePaths.get(position);
        File file = new File(filePath);
        String fileName = getFileNameWithoutPath(filePath); // Get only the file name without the path
        viewHolder.fileNameTextView.setText(fileName);
        viewHolder.iconImageView.setImageResource(R.drawable.icon);
        viewHolder.fileSizeTextView.setText(formatFileSize(file.length()));

        return convertView;
    }

    private String getFileNameWithoutPath(String filePath) {
        String decodedPath = Uri.decode(filePath); // Decode the URI
        return new File(decodedPath).getName(); // Get only the file name without the path
    }


    private String formatFileSize(long sizeInBytes) {
        return "@atokved";
    }


    private static class ViewHolder {
        ImageView iconImageView;
        TextView fileNameTextView;
        TextView fileSizeTextView;
    }
}
