# Naive Bayes Text Classification

Ce projet implémente un classificateur de texte utilisant l'algorithme **Naïve Bayes**, l'un des algorithmes les plus populaires pour la classification de texte. Le classificateur est conçu pour prédire la catégorie d'un texte donné, en l'entraînant sur un ensemble de données contenant des exemples étiquetés.

L'algorithme de Naïve Bayes repose sur le théorème de Bayes et l'hypothèse d'indépendance conditionnelle entre les caractéristiques (mots) d'un document donné.

## Fonctionnalités principales

### 1. - **Entraînement d'un modèle Naive Bayes** avec des textes provenant de répertoires.
- **Prétraitement de texte**, y compris la suppression des mots vides (stop words) et le stemming des mots (réduction à leur racine).
- **Classification de documents texte** en catégories prédéfinies.
- **Sauvegarde et chargement du modèle Naive Bayes** pour réutilisation ultérieure.
- **Classification automatique de fichiers texte** et déplacement vers des répertoires correspondant à leur catégorie prédite.


### 2. **Entraînement du modèle** :
   - Le modèle Naïve Bayes est entraîné sur un jeu de données où chaque document appartient à une catégorie.
   - Le **lissage de Laplace** est appliqué pour éviter les probabilités nulles lors du calcul des distributions de probabilité des mots dans chaque catégorie.

### 3. **Prédiction** :
   - Le modèle prédit la catégorie d'un texte inconnu en se basant sur les probabilités calculées pendant l'entraînement.
   - Chaque texte est analysé et classé dans l'une des catégories prédéfinies.

### 4. **Sauvegarde et chargement du modèle** :
   - Après l'entraînement, le modèle peut être sauvegardé dans un fichier sérialisé et chargé à tout moment pour effectuer des prédictions sans nécessiter un réentraînement.

## Installation

### Prérequis :
1. **Java 8 ou supérieur** doit être installé.
2. Dépendances **Apache Lucene** pour le stemming des mots.