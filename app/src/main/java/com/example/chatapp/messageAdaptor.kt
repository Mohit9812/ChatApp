package com.example.chatapp

import android.content.Context
import android.provider.Telephony.Mms.Sent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class messageAdaptor(val context : Context, val messageList : ArrayList<message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_receive = 1;
    val item_sent = 2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(view)
        }
        else{
            val view : View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currMsg = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java){

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currMsg.message
        }
        else{

            val viewHolder = holder as ReceiveViewHolder
            holder.receivedMessage.text = currMsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currMsg = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currMsg.senderId)){
            return item_sent
        }
        else{
            return item_receive
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sentMsg)
    }

    class ReceiveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.receiveMsg)
    }
}