# Rendu du Mini Projet "Mon espace santé"

**Votre travail devra être rendu sous forme d’un projet déposé sur la Forge Lyon 1, au plus tard le dimanche 11 décembre 2022 à 23h59. Malus si rendu après la deadline, aucun projet accepté après le 13 décembre.**

Le mini-projet noté est le fil rouge de tous les TPs. Vous commencez à
travailler sur la base de code au [TP1](TP1-java/), et vous ajoutez
des fonctionnalités tout en améliorant la qualité du code dans la
suite.

Les consignes ci-dessous sont à respecter **impérativement** pour le rendu.

## Rapport

Votre rendu inclura un rapport, au format PDF (consignes pour le rendu
ci-dessous), qui doit comprendre obligatoirement :

- une présentation globale du projet (rapide : ne répétez pas
  l'énoncé),

- Une section « design patterns », donnant une motivation des choix
  d’architecture (et des patterns choisis), et leur explication en s’aidant de
  diagrammes appropriés et adaptés au degré de précision et au type
  d’explication. Donc des diagrammes de classe, mais pas que cela, et pas de
  plats de spaghettis générés automatiquement représentant tout le code.
  
* Une section « éthique ». Cette section devra discuter de la problématique de
  la gestion des données personnelles de santé, en utilisant l'exemple de « Mon
  Espace Santé » (le vrai https://www.monespacesante.fr/, et votre TP). Quels
  sont les enjeux ? Quelles sont les mesures, légales et techniques, pour
  limiter ou éliminer les risques ? Lesquels sont mis en œuvre dans le vrai site
  « Mon espace santé » ? En avez-vous mis en place dans votre TP, si oui,
  lesquelles (il s'agit d'un petit projet scolaire, on ne vous demande pas une
  application vraiment sécurisée, mais vous devriez être capable de discuter des
  limites de votre implémentation. Vous pouvez aussi mettre en place des mesures
  simplistes et discuter de ce qu'il faudrait faire dans une vraie application)
  ?

  L'objectif n'est pas de donner un avis subjectif (la question « le site
  https://www.monespacesante.fr/ est-il bien ? » est hors-sujet ici), mais de
  présenter les questions importantes et les éléments objectifs de réponse
  autour de la gestion de données médicales. Appuyez-vous autant que possible
  sur des articles existants, en citant vos sources. Il s'agit donc avant tout
  d'un travail de bibliographie de votre part.

  Pour vous aider, voici quelques références intéressantes sur le sujet :

  * [Mon espace santé, un nouveau service numérique personnel et sécurisé](https://www.ameli.fr/rhone/assure/sante/mon-espace-sante) sur ameli.fr.

  * [Décollage imminent pour découvrir un nouvel espace](https://esante.gouv.fr/sites/default/files/media_entity/documents/dp-mes_-03-02-2022.pdf), dossier de presse sur esante.gouv.fr.

  * [Comprendre "Mon espace santé", le carnet de santé numérique, en trois questions](https://www.usine-digitale.fr/article/comprendre-mon-espace-sante-le-carnet-de-sante-numerique-en-trois-questions.N1780347) sur l'Usine Digitale.

  * [Un thread twitter](https://twitter.com/GuillaumeRozier/status/1527587327165579266) par Guillaume Rozier, le créateur de CovidTracker.fr.

  * [Pourquoi s’opposer à la création de Mon Espace Santé ?](https://blogs.mediapart.fr/la-quadrature-du-net/blog/280322/pourquoi-s-opposer-la-creation-de-mon-espace-sante) sur Mediapart.

  * [Doctolib adopte le chiffrement de bout en bout, nouvelle étape dans la sécurisation des données de santé](https://info.doctolib.fr/blog/doctolib-adopte-le-chiffrement-de-bout-en-bout-nouvelle-etape-dans-la-securisation-des-donnees-de-sante/) sur Doctolib, un autre acteur majeur de gestion de données de santé en france. Voir par exemple [Chiffrement de bout en bout](https://fr.wikipedia.org/wiki/Chiffrement_de_bout_en_bout) sur Wikipedia si vous ne savez pas à quoi sert le chiffrement de bout en bout. <!-- Et mauvaise nouvelle, on ne trouve pas grand chose sur le web sur la question « Mon espace santé utilise-t-il le chiffrement de bout en bout -->

  * Un autre exemple sur l'importance des données liées à la santé (même indirectement) [Google va exclure les cliniques pratiquant l’avortement de l’historique de géolocalisation](https://www.lemonde.fr/pixels/article/2022/07/04/google-va-exclure-les-cliniques-pratiquant-l-avortement-de-l-historique-de-geolocalisation_6133271_4408996.html)

  La liste n'est bien entendu pas exhaustive. Pensez à vos enseignants qui
  liront des dizaines de rapports, surprenez-nous, apprenez-nous des choses ! Si
  votre relecteur se dit « Ah tiens, je ne savais pas » ou « Ah tiens, je n'y
  avais pas pensé » en lisant votre rapport, vous avez atteint l'objectif !
  
* Une section « tests » où vous décrirez les tests manuels que vous avez
  réalisés. Vos tests automatiques (le code Java des tests et les commentaires
  associés) devraient se suffire à eux-mêmes, il n'est pas nécessaire de les
  re-documenter dans le rapport (sauf si vous avez fait des choses
  extraordinaires qui méritent une documentation externe).

## Qualité du code

### Style

Assurez-vous que votre programme respecte toujours le style imposé
(`mvn test`, qui doit lancer checkstyle).

Bien sûr, respecter le style doit se faire en corrigeant (si besoin)
votre code, mais *pas* en modifiant le fichier de configuration de
checkstyle.

### Design-pattern

Assurez-vous d'avoir appliqué toutes les consignes du
[TP 3](TP3-patterns/).

### Tests et intégration continue

Vérifiez que l'intégration continue mise en place au
[TP 2](TP2-outils/) fonctionne toujours.

Les tests automatisés tels que décrits au [TP 4](TP4-tests/) doivent
être lancés automatiquement par `mvn test`, et doivent tous passer
avec succès.

### Portabilité

Clonez, compilez et exécutez votre code **sur une machine vierge**
(c'est-à-dire sur laquelle vous n'avez installé aucune dépendance, ni
configuré le compte utilisateur de façon particulière). Une grande
partie du barème est liée à l'exécution de votre travail. Il est
important que nous arrivions à l'exécuter **directement**. "Ça marche
chez moi" n'est pas une excuse et une démo *a posteriori* ne permet
pas de remonter une note de TP.

## Projet Forge et TOMUSS

Les projets seront rendus en binômes. La date limite est indiquée sur
la page d'accueil du cours.

**Ajoutez les utilisateurs @matthieu.moy, @LIONEL.MEDINI,
@EMMANUEL.COQUERY, @vac_lucien.ndjie, avec le niveau de privilège
"reporter" (ou plus, mais *pas* "guest") à votre projet sur la forge**

Dans la feuille TOMUSS M1 Informatique, indiquez l'URL de votre projet dans la case URL_Projet_MIF01 (l'URL doit ressembler à
`https://forge.univ-lyon1.fr/<login>/mif01-2022`). Il faut impérativement
**que la commande `git clone <url>` fonctionne, et que cette commande récupère la dernière version de votre projet.**
Autrement dit la branche par défaut doit contenir la dernière version du projet si vous avez
plusieurs branches. Tous les membres du binôme doivent entrer **exactement** la même URL (au caractère près).

Pensez à remplir dès à présent TOMUSS indiquant votre URL.
Le dépôt ne sera relevé qu’après la date de rendu.

Votre dépôt sur la Forge devra contenir :

- un répertoire `mes/` (le répertoire doit impérativement avoir exactement ce nom)
- un fichier maven (`mes/pom.xml`) pour le build du projet
- les sources (fichiers Java)
- le rapport en PDF (6 pages maximum, format libre. La limitation de pages est indicative, si vous avez vraiment besoin de plus vous pouvez dépasser un peu mais restez raisonnables et concis), dans un fichier qui doit impérativement s'appeler `rapport.pdf` à la racine du dépôt Git.

Vous pouvez laisser les autres fichiers et répertoires.

## Barème indicatif (le barème sera ramené à 20), à utiliser comme checklist pour vérifier que vous avez tout fait

| Points | Critère |
|--------|---------|
| malus -3 si absent | Présence des bons fichiers (rapport.pdf, mes/pom.xml, .gitlab-ci.yml) |
| 1 | Le projet est compilable (mvn compile) |
| 1 | Au moins une issue dans GitLab |
| 1 | Au moins une merge-request fermée |
| 1 | L'intégration continue est OK sur Gitlab |
| 1 | Les tests passent (mvn test -Dcheckstyle.skip) |
| 2 | Checkstyle |
| 1 | Fichier .gitignore (pas de "untracked files" après "mvn test") |
| Malus si retard | Rendu avant la deadline |
| Malus jusqu'à -5 | Malus éventuel pour non-respect des consignes (en plus de la note automatique) |
| 1 | Le programme se lance correctement (mvn exec:java) |
| 1 | Qualité et structure globale du code, utilisation de Packages |
| 2 | Interface (UI) propre y compris pour les extensions (pas de point si l'UI du squelette est rendue, note max si l'interface est belle et ergonomique) |
| 1 | Extension : au moins 4 types de professionnels de santé |
| 1 | Extension : ajout dynamique de patient |
| 1 | Extension : suppression d'une ordonnance par un patient |
| 1 | Extension : recherche de patient par nom |
| 1 | Extension : troisième stratégie de recherche |
| 1 | Extension : création des utilisateurs |
| 1 | Extension : modification de l'interface pour les ordonnances pré-définies |
| 3 | Autres extensions |
| 3 | Tests automatiques |
| 1 | Principes GRASP bien appliqués |
| 1 | Design pattern MVC : la deuxième vue fonctionne, les listes se mettent à jour automatiquement (plus de bouton « refresh ») |
| 3 | Design pattern MVC : qualité et organisation du code |
| 5 | Design-patterns (création, structure, SOLID, ...) : au moins 3 autres patterns que MVC |
| 6 | Rapport : partie « design patterns » |
| 4 | Rapport : partie « éthique » |
| 1 | Rapport : partie « tests » |
| 3 | Rapport : qualité globale des explications |
| Malus jusqu'à 5 | Rapport : malus éventuel pour mauvaise forme et orthographe (0 si la forme est OK, pas de note positive) |
| Malus jusqu'à +3 | Bonus éventuel pour choses en plus (pas de note négative) |
