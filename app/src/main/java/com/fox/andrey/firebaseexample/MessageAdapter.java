package com.fox.andrey.firebaseexample;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.message_item,parent,false);
        }
        TextView mUserName = convertView.findViewById(R.id.userName);
        TextView mTextMessage = convertView.findViewById(R.id.textMessage);

        Message message = getItem(position);

        mUserName.setText(message.getUserName());
        mTextMessage.setText(message.getTextMessage());

        return convertView;

    }
}
