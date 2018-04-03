package com.fox.andrey.firebaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private MessageAdapter messageAdapter;
    private Button mButton;

    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /*private class MessageHolder extends RecyclerView.ViewHolder{
        private TextView mMessageText;
        private TextView mUserName;


        private MessageHolder(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.textMessage);
            mUserName = itemView.findViewById(R.id.userName);
        }

        private void bind(Message message){
            mMessageText.setText(message.getTextMessage());
            mUserName.setText(message.getUserName());
        }

    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder>{
        private List<Message> itemsList;

        MessageAdapter(List<Message> itemsList) {
            this.itemsList = itemsList;
        }

        @NonNull
        @Override
        public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.message_item,parent,false);
            return new MessageHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
            Message message = itemsList.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemCount() {
            return itemsList.size();
        }
    }*/

    private void initialize(){

        // retrieve an instance of database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //get reference
        mRef = database.getReference("message");

        if (mChildEventListener == null){
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messageAdapter.add(message);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mRef.addChildEventListener(mChildEventListener);
        }

        List<Message> messageItems = new ArrayList<>();
        messageAdapter = new MessageAdapter(MainActivity.this, R.layout.message_item, messageItems);

        ListView mListView = findViewById(R.id.listView);
        mListView.setAdapter(messageAdapter);

        mEditText = findViewById(R.id.editText);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().trim().length() > 0) {
                    mButton.setEnabled(true);
                } else {
                    mButton.setEnabled(false);
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {}
        });

        mButton = findViewById(R.id.send_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(mEditText.getText().toString());
                mRef.push().setValue(message);
                mEditText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mChildEventListener != null)
            mRef.removeEventListener(mChildEventListener);
        mChildEventListener = null;
        super.onDestroy();
    }
}
