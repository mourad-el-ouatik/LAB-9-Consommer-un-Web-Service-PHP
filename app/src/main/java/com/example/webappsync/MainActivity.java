package com.example.webappsync;

/*
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  MainActivity.java  ≡  HomeController / index.php           ║
 * ║  Rôle : Contrôleur de l'écran principal (activity_main).   ║
 * ║                                                              ║
 * ║  ANALOGIE MVC :                                             ║
 * ║    onCreate()       → constructeur / méthode index()        ║
 * ║    setContentView() → charger la Vue (activity_main.xml)    ║
 * ║    Intent           → redirection (header Location: ...)    ║
 * ║    startActivity()  → res.redirect('/etudiant/add')         ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage plein écran bord à bord (gestion des barres système)
        EdgeToEdge.enable(this);

        // Charge la Vue XML → équivalent de render('activity_main.xml')
        setContentView(R.layout.activity_main);

        // Gestion des insets (padding autour des barres système)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Liaison des widgets déclarés dans activity_main.xml
        // ≡ document.getElementById('btnAjouter')
        Button btnAjouter = findViewById(R.id.btnAjouter);

        // Clic → naviguer vers AddEtudiant
        // ≡ window.location.href = '/etudiant/add'  OU  res.redirect('/etudiant/add')
        btnAjouter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEtudiant.class);
            startActivity(intent);
        });
    }
}