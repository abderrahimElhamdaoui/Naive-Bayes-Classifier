# Naive Bayes Text Classification

Ce projet implémente un classificateur de texte utilisant l'algorithme **Naïve Bayes**, l'un des algorithmes les plus populaires pour la classification de texte. Le classificateur est conçu pour prédire la catégorie d'un texte donné, en l'entraînant sur un ensemble de données contenant des exemples étiquetés.

L'algorithme de Naïve Bayes repose sur le théorème de Bayes et l'hypothèse d'indépendance conditionnelle entre les caractéristiques (mots) d'un document donné.

## Fonctionnalités principales

### 1. **Prétraitement des données** :
   - **Suppression des mots vides (stop words)** : Les mots communs comme "et", "le", "de", "à", etc. sont retirés pour améliorer l'efficacité du modèle.
   - **Stemming** : Réduction des mots à leur racine à l'aide de l'algorithme **Porter Stemmer** ou autre méthode de stemming.
   - **Transformation en minuscules** : Conversion de tous les mots en minuscules pour éviter la duplication des mots due à la casse.
   - **Tokenisation** : Découpage du texte en mots ou tokens.

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
2. Téléchargez ou clonez ce dépôt sur votre machine locale.

### Clonez le dépôt :
```bash
git clone https://github.com/username/NaiveBayesClassifier.git
