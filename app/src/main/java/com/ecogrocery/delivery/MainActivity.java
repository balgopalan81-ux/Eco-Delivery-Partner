package com.ecogrocery.delivery;

import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content;
    final int green=Color.rgb(26,130,68);
    final ArrayList<Order> orders=new ArrayList<>();
    int accepted=0;
    static class Order {
        String id,name,address,otp,status; int fee;
        Order(String i,String n,String a,String o,int f,String s){id=i;name=n;address=a;otp=o;fee=f;status=s;}
    }
    @Override public void onCreate(Bundle b){super.onCreate(b);
        orders.add(new Order("ECO-1048","Aarav Sharma","Boring Road, Patna","4821",45,"Assigned"));
        orders.add(new Order("ECO-1049","Priya Kumari","Kankarbagh, Patna","7316",35,"Assigned"));
        orders.add(new Order("ECO-1050","Rahul Kumar","Patliputra Colony, Patna","2094",55,"Delivered"));
        showLogin();
    }
    TextView text(String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setPadding(0,6,0,6);return t;}
    void base(){root=new LinearLayout(this);root.setOrientation(1);root.setPadding(20,18,20,18);root.setBackgroundColor(Color.rgb(246,249,246));setContentView(root);}
    Button btn(String s){Button b=new Button(this);b.setText(s);return b;}
    void showLogin(){base();root.addView(text("ECO GROCERY",28,green));root.addView(text("DELIVERY PARTNER",18,Color.DKGRAY));
        root.addView(text("Demo partner login",16,Color.GRAY));EditText phone=new EditText(this);phone.setHint("Mobile number");phone.setInputType(3);phone.setText("9876543210");root.addView(phone);
        EditText pass=new EditText(this);pass.setHint("Password");pass.setInputType(129);pass.setText("123456");root.addView(pass);
        Button login=btn("LOGIN");root.addView(login);login.setOnClickListener(v->{if(phone.getText().length()==10&&pass.getText().length()>=4)showHome();else Toast.makeText(this,"Enter a 10-digit mobile and 4+ character password",0).show();});
        root.addView(text("Demo only: any 10-digit mobile and 4+ character password.",12,Color.GRAY));
    }
    void showHome(){base();root.addView(text("ECO Delivery Partner",25,green));root.addView(text("Welcome, Delivery Partner",15,Color.DKGRAY));
        Button online=btn("● ONLINE  |  Tap to go OFFLINE");root.addView(online);online.setOnClickListener(v->{online.setText(online.getText().toString().contains("ONLINE")?"○ OFFLINE  |  Tap to go ONLINE":"● ONLINE  |  Tap to go OFFLINE");});
        Button ordersBtn=btn("MY ORDERS");root.addView(ordersBtn);ordersBtn.setOnClickListener(v->showOrders());
        Button earnings=btn("EARNINGS & HISTORY");root.addView(earnings);earnings.setOnClickListener(v->showEarnings());
        Button profile=btn("PROFILE / LOG OUT");root.addView(profile);profile.setOnClickListener(v->showLogin());
        showOrderCards(root);
        root.addView(text("Demo app • Sample orders are stored on this device only.",12,Color.GRAY));
    }
    void showOrderCards(LinearLayout target){for(Order o:orders){LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setPadding(12,10,12,10);card.setBackgroundColor(Color.WHITE);
        card.addView(text(o.id+"  •  "+o.status,18,green));card.addView(text(o.name+"\n"+o.address+"\nDelivery fee: ₹"+o.fee,15,Color.DKGRAY));
        if(!o.status.equals("Delivered")&&!o.status.equals("Rejected")){
            Button action=btn(o.status.equals("Assigned")?"ACCEPT ORDER":o.status.equals("Accepted")?"MARK PICKED UP":o.status.equals("Picked Up")?"OUT FOR DELIVERY":"VERIFY OTP & DELIVER");card.addView(action);
            action.setOnClickListener(v->{if(o.status.equals("Assigned"))o.status="Accepted";else if(o.status.equals("Accepted"))o.status="Picked Up";else if(o.status.equals("Picked Up"))o.status="Out for Delivery";else {EditText code=new EditText(this);code.setHint("Enter demo OTP");new AlertDialog.Builder(this).setTitle("Delivery OTP").setView(code).setPositiveButton("Confirm",(d,w)->{if(code.getText().toString().equals(o.otp)){o.status="Delivered";Toast.makeText(this,"Order delivered!",0).show();}else Toast.makeText(this,"Incorrect OTP",0).show();showHome();}).setNegativeButton("Cancel",null).show();return;}showHome();});
            if(o.status.equals("Assigned")){Button reject=btn("REJECT");card.addView(reject);reject.setOnClickListener(v->{o.status="Rejected";showHome();});}
            Button map=btn("OPEN MAP");card.addView(map);map.setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_VIEW,android.net.Uri.parse("geo:0,0?q="+UriEncode(o.address)));i.setPackage("com.google.android.apps.maps");try{startActivity(i);}catch(Exception e){startActivity(new Intent(Intent.ACTION_VIEW,android.net.Uri.parse("geo:0,0?q="+UriEncode(o.address))));}});
        }
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,8,0,8);target.addView(card,lp);
    }}
    String UriEncode(String s){try{return java.net.URLEncoder.encode(s,"UTF-8");}catch(Exception e){return s;}}
    void showOrders(){base();root.addView(text("My Orders",25,green));showOrderCards(root);Button back=btn("BACK");root.addView(back);back.setOnClickListener(v->showHome());}
    void showEarnings(){base();int total=0,count=0;for(Order o:orders)if(o.status.equals("Delivered")){total+=o.fee;count++;}
        root.addView(text("Earnings & History",25,green));root.addView(text("Completed deliveries: "+count,18,Color.DKGRAY));root.addView(text("Demo earnings: ₹"+total,24,green));for(Order o:orders)root.addView(text(o.id+"  "+o.status+"  ₹"+(o.status.equals("Delivered")?o.fee:0),15,Color.DKGRAY));
        Button back=btn("BACK");root.addView(back);back.setOnClickListener(v->showHome());}
}
