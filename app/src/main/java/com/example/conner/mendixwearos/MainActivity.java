package com.example.conner.mendixwearos;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends WearableActivity {

    private Button button;
    private RequestQueue q;
    private LinearLayout root;
    private Context ctx;
//    protected final String urlBase = "http://10.104.109.109:8080/survey/v1";
    protected final String urlBase = "http://10.104.111.10:8080/rest/survey/v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        button = findViewById(R.id.button);
        q = Volley.newRequestQueue(this);
        ctx = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String url = urlBase + "/question/demo_user";

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray answers = response.getJSONArray("Answers");
                            root.removeAllViews();
                            button.setText("Here you go");
                            attachQuestionText(root, response, ctx);
                            attachAnswerButtons(root, answers, ctx);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                // Access the RequestQueue through your singleton class.
                 q.add(request);
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }

    protected void attachQuestionText(LinearLayout root, JSONObject question,
                                      Context ctx) throws JSONException {
        TextView tvQuestion = new TextView(ctx);
        String questionText = question.getString("Text");
        tvQuestion.setText(questionText);
        root.addView(tvQuestion);
    }

    protected void attachAnswerButtons(final LinearLayout root, JSONArray possibleAnswers,
                                       final Context ctx) throws JSONException {
        for (int i = 0; i<possibleAnswers.length(); i++){
            JSONObject answer = possibleAnswers.getJSONObject(i);
            Button btnAnswer = new Button(ctx);
            btnAnswer.setText(answer.getString("Text"));
            btnAnswer.setTag(answer.getInt("_id"));
            btnAnswer.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String url = urlBase + "/answer";
                    int answerId = Integer.parseInt(v.getTag().toString());
                    JSONObject body = new JSONObject();
                    try {
                        body.put("lookupId", answerId);
                        body.put("username", "demo_user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest answer = new JsonObjectRequest(
                            Request.Method.POST, url, body, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            button.setText("Another?");
                            root.removeAllViews();
                            TextView tv = new TextView(ctx);
                            tv.setText("Thanks!");
                            root.addView(tv);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            button.setText("Error");
                            button.setBackgroundColor(Color.RED);
                            error.printStackTrace();
                        }
                    });
                    q.add(answer);
                }
            });
            root.addView(btnAnswer);
        }
    }
}
