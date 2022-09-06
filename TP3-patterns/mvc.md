# Mettre en place le pattern MVC

Il existe beaucoup de variantes du pattern MVC. 
Certains mettent la logique métier dans le contrôleur, d'autres dans le modèle. 
Parfois les entrées de l'utilisateur arrivent via le contrôleur (c'est
le cas quand on passe par un serveur Web par exemple) parfois c'est la
vue qui en est chargée et les transmet au modèle via le contrôleur.

Nous utiliserons le MVC tel que vu en CM, où le contrôleur se limite aux tâches
de contrôle et de coordination (pas de logique métier). Cela vous laisse
beaucoup de choix possibles sur la manière de mettre en place le MVC, il est
important de justifier vos choix dans le rapport.

## Les questions à se poser pour mettre en oeuvre le pattern MVC

En revanche, vous ne pouvez pas vous contenter de dire (ni d'écrire dans votre rapport) « nous faisons du MVC » ! 
Il y a bien plus de questions à vous poser, par exemple :

* Comment faire communiquer Modèle, Vue et Contrôleur ? Des appels de
  méthodes directement sur les classes ? Un patron
  « [Observateur](https://en.wikipedia.org/wiki/Observer_pattern) » ?
  Un appel de méthode en passant par une classe abstraite ou une
  interface pour faire une inversion de dépendance (le « D » de
  [SOLID](https://en.wikipedia.org/wiki/SOLID)) ?
  
* Comment découper Modèle, Vue et Contrôleur ? Par exemple, mettre
  tout le modèle dans une classe violerait le
  [SRP](https://en.wikipedia.org/wiki/Single_responsibility_principle),
  mais comment le découper correctement ? Comment faire circuler
  l'information d'une classe à l'autre
  ([delegation](https://en.wikipedia.org/wiki/Delegation_pattern) ?
  Observateur ? ...) ? L'API exposée au reste du programme doit-elle
  refléter la structure de nos classes, ou bien est-ce pertinent
  d'utiliser une
  [facade](https://en.wikipedia.org/wiki/Facade_pattern) pour en
  exposer une plus simple via des
  [indirections](https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)#Indirection) ?
  
* ...

## Découpage et responsabilités du M, V, C.

Nous proposons ici d'appliquer le MVC tel qu'il est présenté dans les
transparents, donc appliquer le découpage suivant :

* Le modèle définit les données (gestion des candidats, de leurs CV, requête entrée par l'utilisateur, ...) et les méthodes qui les
  manipulent (filtrer les candidats selon, ...). Tout doit être pensé en termes de données
  logiques, indépendamment du rendu graphique, donc aucune dépendance
  vers le toolkit graphique (JavaFX). Le modèle doit pouvoir être
  réutilisable sans aucune modification pour une application en mode
  texte ou une interface web par exemple.

  <!-- - Aucune référence à un nombre de pixels. Un poney peut par exemple -->
  <!--   être défini par sa rangée (`int`) et par sa progression dans un -->
  <!--   tour (`double` compris entre `0.0` et `1.0`). -->
  <!-- - Aucune référence à des noms de touches au clavier. Par exemple, le -->
  <!--   modèle doit recevoir l'information « l'utilisateur a demandé à -->
  <!--   passer le Poney numéro 2 en mode boost », mais pas « l'utilisateur -->
  <!--   a appuyé sur la touche ... » (qui est du domaine de la vue). -->

* La vue s'occupe de gérer l'affichage et les entrées clavier via
  JavaFX. Certaines entrées sont gérées en interne par la vue (par
  exemple entrer un caractère dans un champ texte sans le valider),
  d'autres sont transmises au modèle, via le contrôleur.

* Le contrôleur s'occupe de transmettre l'information.

## Mise en place

Nous allons implémenter Modèle, Vue et Contrôleur avec 3 packages
java (`fr.univ_lyon1.info.m1.mes.model`, et idem pour `view`
et `controller`), contenant chacun une ou plusieurs classes.

On vous fournit un package `model` contenant le nécessaire pour gérer patients
et professionnels de santé. Réfléchissez aux autres classes dont vous allez
avoir besoin, par exemple :

* Pour représenter la stratégie de recherche (par numéro de sécurité sociale, par nom, etc.).

* Pour représenter les spécificités de chaque professionnel de santé.

* ...

<!-- * Dès que la requête devient non-triviale, il peut être nécessaire de
  la découper, par exemple via le pattern
  [Composite](https://en.wikipedia.org/wiki/Composite_pattern). -->

* ...

Rien n'est fourni pour le `controller`. Pour notre projet il peut être
très simple (une classe peut suffire). Réfléchissez aux messages que
la vue peut envoyer au modèle : chaque message peut être par exemple
une méthode.

Attention, le package `view` fourni dans le squelette contient
<!-- **beaucoup** --> trop de choses, il ne faut y garder que ce qui concerne
l'interface graphique.

La quasi-totalité du code doit se trouver dans les packages `model`,
`view` et `controller`. La fonction principale `start` doit uniquement
instancier les classes de ces packages (et leur fournir des pointeurs
sur les classes qu'elles vont utiliser).
