package mx.gob.cdmx.semanal20200822;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Bienvenida extends AppCompatActivity {

    private static final String TAG = Bienvenida.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);

        Executor newExecutor = Executors.newSingleThreadExecutor();


        FragmentActivity activity = this;

        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                } else {
                    Log.d(TAG, "A ocurrido un error");
                }
            }



            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Reconocimiento exitoso");
                Intent intent = new Intent(getApplicationContext(), Entrada.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d(TAG, "Huella no reconocida");
            }


        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Bloquear aplicación con biometría")
                .setSubtitle("toca el sensor de huellas digitales")
                //.setDescription("This is the description")
                .setNegativeButtonText("Cancelar")
                .build();


         myBiometricPrompt.authenticate(promptInfo);

//        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myBiometricPrompt.authenticate(promptInfo);
//            }
//        });

    }
}
