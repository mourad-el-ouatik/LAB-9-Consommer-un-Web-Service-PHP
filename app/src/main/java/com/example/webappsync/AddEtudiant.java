package com.example.webappsync;

/*
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  AddEtudiant.java  ≡  FormController / createEtudiant.js    ║
 * ║  Rôle : Contrôleur du formulaire d'ajout.                   ║
 * ║         Récupère les données, envoie en HTTP POST,          ║
 * ║         parse la réponse JSON.                              ║
 * ║                                                              ║
 * ║  ANALOGIE :                                                 ║
 * ║    Volley        → fetch() / axios en JavaScript            ║
 * ║    StringRequest → new Request(url, { method: 'POST' })     ║
 * ║    getParams()   → new FormData() / req.body en Express     ║
 * ║    Gson          → JSON.parse(response)                     ║
 * ║    onClick()     → addEventListener('submit', ...)          ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.webappsync.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {

    // ── Références aux widgets de la Vue (≡ const nom = document.getElementById('nom'))
    private EditText nom, prenom;
    private Spinner ville;
    private RadioButton m, f;
    private Button add;

    // ── File d'attente Volley (≡ instance axios / client fetch)
    private RequestQueue requestQueue;

    // ── URL du web service PHP (≡ const API_URL = 'http://...')
    // 10.0.2.2 = localhost de la machine hôte vu depuis l'émulateur
    private static final String INSERT_URL = "http://10.0.2.2/projet/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Charge la Vue → activity_add_etudiant.xml
        setContentView(R.layout.activity_add_etudiant);

        // Liaison widgets ↔ variables Java
        // ≡ const nom = document.getElementById('nom')
        nom    = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville  = findViewById(R.id.ville);
        m      = findViewById(R.id.m);
        f      = findViewById(R.id.f);
        add    = findViewById(R.id.add);

        // Initialisation de la file Volley (réseau)
        requestQueue = Volley.newRequestQueue(this);

        // Écoute du clic sur "Enregistrer"
        // ≡ add.addEventListener('click', envoyerEtudiant)
        add.setOnClickListener(this);
    }

    // ── Callback du clic (≡ addEventListener handler)
    @Override
    public void onClick(View v) {
        if (v == add) {
            // Validation simple avant envoi
            if (nom.getText().toString().trim().isEmpty()
                    || prenom.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            envoyerEtudiant();
        }
    }

    /*
     * Envoie les données du formulaire au serveur PHP via HTTP POST.
     *
     * ANALOGIE FETCH :
     *   fetch(INSERT_URL, {
     *     method: 'POST',
     *     body: new FormData(form)
     *   })
     *   .then(res => res.json())
     *   .then(data => data.forEach(e => console.log(e)))
     *   .catch(err => console.error(err));
     */
    private void envoyerEtudiant() {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                INSERT_URL,

                // ── Succès (≡ .then(response => ...))
                response -> {
                    Log.d("RESPONSE", response);

                    // Désérialisation JSON → Collection<Etudiant>
                    // ≡ const etudiants = JSON.parse(response)
                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);

                    for (Etudiant e : etudiants) {
                        Log.d("ETUDIANT", e.toString());
                    }

                    Toast.makeText(this, "✅ Étudiant ajouté !", Toast.LENGTH_SHORT).show();
                    finish(); // Retour à MainActivity
                },

                // ── Erreur (≡ .catch(error => ...))
                error -> {
                    Log.e("VOLLEY", "Erreur réseau : " + error.getMessage());
                    Toast.makeText(this, "❌ Erreur : " + error.getMessage(), Toast.LENGTH_LONG).show();
                }

        ) {
            /*
             * getParams() = corps de la requête POST
             * ≡ new FormData() ou req.body en Express/PHP $_POST
             */
            @Override
            protected Map<String, String> getParams() {
                // Récupération de la valeur du RadioButton sélectionné
                String sexe = m.isChecked() ? "homme" : "femme";

                Map<String, String> params = new HashMap<>();
                params.put("nom",    nom.getText().toString().trim());
                params.put("prenom", prenom.getText().toString().trim());
                params.put("ville",  ville.getSelectedItem().toString());
                params.put("sexe",   sexe);
                return params;
            }
        };

        // Ajout de la requête à la file d'attente Volley
        requestQueue.add(request);
    }
}