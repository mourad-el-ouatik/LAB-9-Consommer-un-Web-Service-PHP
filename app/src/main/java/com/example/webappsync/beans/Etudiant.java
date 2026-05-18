package com.example.webappsync.beans;

/*
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  Etudiant.java  ≡  class Etudiant {} / interface / Entity   ║
 * ║  Rôle : Modèle de données — représente un étudiant.         ║
 * ║         Utilisé par Gson pour désérialiser le JSON reçu.    ║
 * ║                                                              ║
 * ║  ANALOGIE :                                                 ║
 * ║    Java Bean           → interface TypeScript / DTO         ║
 * ║    Champs privés       → propriétés de l'objet JS           ║
 * ║    Getters / Setters   → obj.nom / obj.getNom()             ║
 * ║    toString()          → JSON.stringify(etudiant)           ║
 * ║    Gson.fromJson(json) → JSON.parse(json) en JS             ║
 * ║                                                              ║
 * ║  Exemple JSON mappé :                                       ║
 * ║  { "id":1, "nom":"Alaoui", "prenom":"Sara",                ║
 * ║    "ville":"Rabat", "sexe":"femme" }                        ║
 * ╚══════════════════════════════════════════════════════════════╝
 */
public class Etudiant {

    // ── Champs (≡ propriétés d'un objet JS ou colonnes d'une table SQL)
    private int    id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;

    // ── Constructeur vide — requis par Gson pour l'instanciation
    // ≡ const etudiant = {}  (objet vide avant affectation)
    public Etudiant() {}

    // ── Constructeur complet — pour créer un Etudiant depuis le code
    public Etudiant(int id, String nom, String prenom, String ville, String sexe) {
        this.id     = id;
        this.nom    = nom;
        this.prenom = prenom;
        this.ville  = ville;
        this.sexe   = sexe;
    }

    // ── Getters (≡ obj.id, obj.nom ...)
    public int    getId()     { return id; }
    public String getNom()    { return nom; }
    public String getPrenom() { return prenom; }
    public String getVille()  { return ville; }
    public String getSexe()   { return sexe; }

    // ── Setters (≡ obj.nom = 'Alaoui')
    public void setId(int id)          { this.id = id; }
    public void setNom(String nom)     { this.nom = nom; }
    public void setPrenom(String p)    { this.prenom = p; }
    public void setVille(String v)     { this.ville = v; }
    public void setSexe(String s)      { this.sexe = s; }

    // ── toString() ≡ JSON.stringify(etudiant) pour le débogage
    @Override
    public String toString() {
        return "Etudiant{" +
                "id="      + id      +
                ", nom='"  + nom     + '\'' +
                ", prenom='"+ prenom + '\'' +
                ", ville='" + ville  + '\'' +
                ", sexe='"  + sexe   + '\'' +
                '}';
    }
}