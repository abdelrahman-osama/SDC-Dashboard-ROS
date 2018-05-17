/*
 * Copyright (C) 2014 Oliver Degener.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ollide.rosandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class MainActivity extends RosActivity {

    public MainActivity() {
        super("RosAndroidExample", "RosAndroidExample");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                FirebaseInstanceId.getInstance().getToken();
            }
        }, 5000);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, AppStart.class);
        this.startActivity(intent);

//        this.setTitle(Html.fromHtml("<font color='#000000'>Please choose your drop-off point</font>"));
//        MapsFragment MapsFragment = new MapsFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_2, MapsFragment, "").commit();
        //getFragmentManager().beginTransaction().replace(R.id.fragment_container_2, MapsFragment).commit();
        //getFragmentManager().beginTransaction().rep
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
//        NodeMain node = new SimplePublisherNode();
//
//        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
//        nodeConfiguration.setMasterUri(getMasterUri());
//
//        nodeMainExecutor.execute(node, nodeConfiguration);

        Talker talker = new Talker();
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(), getMasterUri());
        nodeConfiguration.setNodeName("talker");
        nodeMainExecutor.execute(talker, nodeConfiguration);

        Listener listener = new Listener();
        nodeConfiguration.setNodeName("listener");
        nodeMainExecutor.execute(listener, nodeConfiguration);
    }
}
