
# Chiffrer des numéros de téléphone

## Procédure de mise en fonctionnement

- Ajouter un ou plusieurs numéros de téléphone (cf format), dans la classe `EncryptPN` et en particulier dans la liste construite dans la méthode `getInputPhoneNumberList()`.
- Lancer le programme.
- La sortie est (à ce jour) dans la sortie standard.

## Format

### Format des numéros de téléphone

Format valide : FR-NN.NN.NN.NN.NN

Avec :
- FR le préfixe utilisé pour générer un numéro pouvant être appeler.
- N des chiffres entre 0 et 9 inclus.

## Releases

### 1.0.0 (Version actuelle)

- Chiffrement avec un algorithme unique des numéros de téléphone.

### 2.0.0

- Lire les numéros de téléphone à chiffrer et le choix de l'algorithme de chiffrement depuis un fichier JSON.
- Ne pas afficher le résultat dans la sortie standard, mais générer un fichier JSON.
