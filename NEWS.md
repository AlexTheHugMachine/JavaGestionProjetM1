Ce fichier contiendra les nouvelles du cours. Un mail sera envoyé quand le fichier est mis à jour.


# 08/09/2022 : chat-info, et encore des typos

## Encore des typos dans l'énoncé !

Avec toutes mes excuses, décidément moi qui étais persuadé d'avoir lancé le `git
grep 2021` qui va bien ...

Il restait une instance de `2021` qui aurait dû être `2022` dans l'énoncé, au
moment où on vous propose de faire un `git remote add`. Si vous essayez les
commandes telles quelles, vous allez essayer de fusionner votre dépôt (de cette
année) avec le dépôt de l'an passé. Git n'aime pas, il trouve que les deux
dépôts n'ont aucun commit commun, et il a raison. Pas de panique, on peut
toujours réparer cela :

```
git remote rm moy
git remote add moy https://forge.univ-lyon1.fr/matthieu.moy/mif01-2022.git
git fetch moy
```

Et tant qu'on y est, cette année la branche principale s'appelle `main` et pas
`master` (explication du pourquoi un peu plus tard dans le cours). Donc la
commande pour récupérer les changements est :

```
git pull moy main
```

Vous pouvez expérimenter cela grandeur nature, en vérifiant que vous récupérez
bien la correction de typo. Une fois tout cela fait, vous devriez voir cette
entrée de `NEWS.md` apparaître dans votre dépôt local, et `TP2-tools/README.md`
ne devrait contenir que des URL en `2022`.

## Messagerie instantanée via chat-info

Les nouveaux inscrits ne pouvaient pas utiliser https://chat-info.univ-lyon1.fr/
jusqu'à hier, mais c'est maintenant réparé. Ce système de tchat est le moyen
privilégié de communiquer avec les enseignants en dehors des séances, et pour
l'instant vous ne l'avez pas vraiment utilisé. C'est dommage (ou alors c'est que
personne n'a eu aucune difficulté ?). Vous avez toutes les informations pour
rejoindre les bons canaux dans la page d'accueil du cours, section  «
Enseignement présentiel, vidéos et messagerie instantanée ».

# 06/09/2022 : Mise en route du cours et petits bugs

## Typo dans l'énoncé

L'énoncé du TP1 vous disait « Ouvrez la page
-[https://forge.univ-lyon1.fr/matthieu.moy/mif01-2021](https://forge.univ-lyon1.fr/matthieu.moy/mif01-2021)
 ». Le 2021 était bien entendu une typo de votre serviteur, la bonne URL est
 https://forge.univ-lyon1.fr/matthieu.moy/mif01-2022. Vérifiez bien que vous
 avez fait un fork du projet de cette année (vous devriez avoir un répertoire
 `mes` et pas de répertoire `cv-search`).

## Problème de quota

Certains étudiants ont des problème pour faire les TPs car leur quota disque est
plein, donc toutes les commandes qui cherchent à écrire sur le disque échouent.

Quelques commandes pour vous aider, sous Linux :

Connaître l'espace disque (`du` = disk usage) utilisé par votre compte :

```
cd
du -sh .
```

(le quota semble être de 2 ou 3 Go par étudiant, selon les étudiants)

Trouver les plus gros répertoires :

```
du -sh * .[^.]* ..?* | sort -h
```

Souvent, le coupable est le répertoire `.cache` qui contient le cache des
applications, c'est à dire des données que l'application peut re-calculer ou
re-télécharger si besoin et que l'on peut en général supprimer sans risque.
Firefox et VSCode sont particulièrement gourmands dans ce répertoire.

## Champ URL sur TOMUSS

Vous avez pu remarquer que vous n'avez pas de case « URL_Projet » sur TOMUSS,
tout simplement parce que vous n'êtes pas encore inscrits officiellement à l'UE.
La situation devrait revenir à la normale d'ici quelques jours.

## Alternants et fiche de présence

Le système officiel de relevé d'absences n'est pas encore en route, là aussi
parce que les inscriptions ne sont pas terminées. Je ferai donc l'appel « à la
main » pour garder des traces de votre présence. Manifestez-vous explicitement
auprès de moi si j'oublie de le faire.
