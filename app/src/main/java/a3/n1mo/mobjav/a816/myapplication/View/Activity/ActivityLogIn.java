package a3.n1mo.mobjav.a816.myapplication.View.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;

import static com.twitter.sdk.android.Twitter.getSessionManager;


public class ActivityLogIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LoginButton loginButton;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "key de twitter";
    private static final String TWITTER_SECRET = "secret de twitter";

    private TwitterLoginButton twitterLoginButton;


    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        // ACA INICIALIZO EL SDK DE FACEBOOK PARA PODER UTILIZARLO
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        if(AccessToken.getCurrentAccessToken() != null || Twitter.getSessionManager().getActiveSession() != null){
            DispararActivity();
        }



        setContentView(R.layout.activity_activity_log_in);

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token, session.getAuthToken().secret);
                mAuth.signInWithCredential(credential).addOnCompleteListener(ActivityLogIn.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d("Firebase TWT", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("Firebase TWT", "signInWithCredential", task.getException());
                        }


                    }
                });


                DispararActivity();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        //Aca creo un callbackManager que es el que va a escuchar el resultado del logueo
        callbackManager = CallbackManager.Factory.create();

        //Aca obtengo el boton de login y le seteo los permisos que le voy a pedir al usuario al conectarme con facabook



        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");


        // Al boton le doy un callbackManager y le seteo el listener de cuando el usuario se loguea
        // Este codigo se va a ejecutar si es que logra loguearse, si falla o si el mismo usuario cancelo el logueo.
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(ActivityLogIn.this, "Se logueo", Toast.LENGTH_SHORT).show();

                handleFacebookAccessToken(loginResult.getAccessToken());
                DispararActivity();

            }
            private void handleFacebookAccessToken(AccessToken token) {
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(ActivityLogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("Facebook", "signInWithCredential", task.getException());
                                    Toast.makeText(ActivityLogIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancel() {
                Toast.makeText(ActivityLogIn.this, "Cancelado", Toast.LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(ActivityLogIn.this, "ERROR", Toast.LENGTH_SHORT).show();
                // App code
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
         if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void invitadosKoalaNews (View view) {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(ActivityLogIn.this, "Ingreso Invitado Autorizado.", Toast.LENGTH_SHORT).show();
                        DispararActivity();

                        if (!task.isSuccessful()) {
                            Log.w("Firebase", "signInAnonymously", task.getException());
                            Toast.makeText(ActivityLogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void DispararActivity (){
        DAOSQLite DAOSQLite = new DAOSQLite(this);
        List<Source> listaCanales = DAOSQLite.getListCanales();
        if(listaCanales.size()==0){
            Intent unIntent = new Intent(ActivityLogIn.this, Seleccion_Canal_Activity.class);
            startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else {
            Intent unIntent = new Intent(ActivityLogIn.this, MainActivity.class);
            startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    //Aca armo el pedido para consular la api de facebook para poder pedirle informacion del usuario
    public void requestGraph(View view){
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        //Aca es donde me llega la respuesta a la consulta y obtengo la inforrmacion del object de json
                        Toast.makeText(ActivityLogIn.this   , response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            String name = object.getString("name");
                            String link = object.getString("link");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Application code
                    }
                });
        //Aca armo un bundle con la informacion que le voy a pedir
        Bundle parameters = new Bundle();

        //Agrego "fields" para indicarle que quiero los campos "id", "name" y "link"
        parameters.putString("fields", "id, name, link");

        //Agrego el bundle de informacion del request al request
        request.setParameters(parameters);

        //Lo ejecuto, una vez finalizado se va a ejecutar el codigo del onCompleted
        request.executeAsync();

    }


}