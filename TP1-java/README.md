# MIF01 - TP Remise en route JAVA

**Objectif :**

Il vous est demandé de mettre en place quelques classes pour vous remettre en
tête les grands principes de la programmation orientée objet : messages et
collaboration entre objets, attributs et méthodes, constructeurs, héritage, etc.
Pour cela, vous manipulerez un outil de gestion de données de santés, inspiré du
service https://www.monespacesante.fr/ déployé par l'assurance maladie française
en 2022.

Votre travail servira de base aux TPs suivants qui feront l'objet d'une note
globale (voir le fichier [projet-note.md](projet-note.md) pour les consignes sur
l'ensemble du projet). Le travail se fait en binômes (malus sur la note pour les
étudiants travaillant seul). Si vous ne trouvez pas de binôme, utilisez le canal
de discussion
[#mif01-2022/cherche-binome](https://go.rocket.chat/invite?host=chat-info.univ-lyon1.fr&path=invite%2F5pcnoj)
sur chat-info (si le lien ne marche pas, identifiez-vous d'abord sur [chat-info](https://chat-info.univ-lyon1.fr)).

Ce TP devrait vous prendre environ une séance.

## Environnement

Pour développer en Java durant le TP, il est fortement recommandé d'utiliser, un
environnement de développement intégré (comme VSCode, Eclipse, Netbeans ou
IntelliJ IDEA, etc) qui permet de compiler, de générer un projet, de débugger et
d'exécuter. 

Vous pouvez aussi choisir d'utiliser n'importe quel éditeur de texte avec
coloration syntaxique, mais les outils que nous utilisons dans l'UE sont en
général plus agréables à utiliser via l'intégration IDE.

Le TP n'a été testé que sous Linux. Sur les machines de l'université le TP fonctionne sous Linux, mais les IDE Java n'ont pas l'air installés sous Windows. Sur vos machines personnelles, vous pouvez utiliser l'OS de votre choix.

### Sur les machines du Nautibus sous Linux

Sur les machines du Nautibus, les TPs ont été testés sous Linux, en
utilisant l'environnement Java par défaut (sans rien configurer).

#### En cas de problème avec l'environnement par défaut

En cas de besoin, il y a aussi une version installé pour vous dans
`/home/tpetu/m1if01/` (à une époque où la version par défaut posait
problème). Pour l'utiliser (en cas de soucis avec la version de base),
avant de démarrer le TP, ajoutez ceci dans votre fichier `~/.bashrc`:

    PATH=/home/tpetu/m1if01/bin:"$PATH"

Puis rechargez le fichier (`exec bash` par exemple). Vérifiez que vous
obtenez bien :

    $ which java
    /home/tpetu/m1if01/bin/java
    $ which javac
    /home/tpetu/m1if01/bin/javac
    $ which mvn
    /home/tpetu/m1if01/bin/mvn

### Sur vos machines personnelles

Sur vos machines personnelles, en cas de problème sous Linux, il peut
être nécessaire d'installer JavaFX explicitement (`sudo apt install
openjfx` sous Ubuntu, ou bien téléchargement depuis
[openjfx.io](https://openjfx.io)). Le TP a été testé avec Java 11, il
ne marchera probablement pas sans adaptation avec des versions plus récentes
(il faudra peut-être version de JavaFX dans `pom.xml`).

Si vous avez installé JavaFX via votre distribution et que Java ne
trouve pas les classes JavaFX, ajoutez explicitement les fichiers JAR
concernés à votre classpath, avec quelque chose comme :

    CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/ext/jfxrt.jar
    CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/jfxswt.jar
    export CLASSPATH

Sur machines personnelles, vérifiez que vous avez bien `git` et `mvn`
installés :

    $ git --version
    git version 2.17.0
    $ mvn --version
    Apache Maven 3.5.2
    [...]

(Vous pouvez bien sûr avoir des versions plus récentes)

Si ce n'est pas le cas installez-les. Sous Ubuntu, faire :

    apt install git maven

Si quelque chose ne marche pas, regardez si la solution n'est pas documentée dans [FAQ.md](../FAQ.md).

## Création d'un projet sur la forge et récupération du code

Ouvrez dans votre navigateur
[forge.univ-lyon1.fr](http://forge.univ-lyon1.fr). Si vous vous
connectez pour la première fois, le système vous permettra de
vérifier/modifier les informations qui vous concernent, idem pour
votre éventuel binôme.

Nous allons utiliser le dépôt Git du cours comme base pour votre
projet. Ouvrez la page
[https://forge.univ-lyon1.fr/matthieu.moy/mif01-2022](https://forge.univ-lyon1.fr/matthieu.moy/mif01-2022),
et cliquez sur le bouton « fork ». Ce bouton vous permet de récupérer
une copie du projet sur votre espace de la forge.

**IMPORTANT**: pour l'instant, le fork de votre projet est public sur
la forge. Nous vous demandons **impérativement de passer ce projet en
« privé »** pour que vos collègues ne puissent pas recopier votre code.
En cas de copie, nous sanctionnerons sévèrement les étudiants ayant copié **et**
ceux ayant laissé copier leur code. Pour rendre votre projet privé,
rendez-vous dans « settings → general » en bas de la barre latérale de
gauche, puis « Permissions ». Le premier réglage est « Project
visibility ». Dans le menu, choisissez « private », puis cliquez sur
le bouton « save changes ».

Pour vérifier que votre projet est bien privé (indispensable),
retournez à la page d'accueil de votre projet
(`https://forge.univ-lyon1.fr/votre.nom/mif01-2022`) et copiez l'URL.
Ouvrez une fenêtre de navigation privée
(<kbd>Control</kbd>+<kbd>Shift</kbd>+<kbd>P</kbd> sous Firefox,
<kbd>Control</kbd>+<kbd>Shift</kbd>+<kbd>N</kbd> sous Chrom{e,ium}), et collez l'URL de votre projet
dans la barre d'URL. Comme le projet est privé, vous devez avoir le
message « You need to sign in or sign up before continuing. ». Si ce
n'est pas le cas, vous avez raté quelque chose, recommencez la
manipulation.

Bien sûr, il faudra donner les droits à vos enseignants, cf.
[projet-note.md](projet-note.md), et à votre binôme. Tout cela se fait dans
Configuration → Membres.

Pour vos projets futurs, vous pourrez aussi créer des projets à partir
de zéro. Pour cela, vous pourrez faire simplement « new project »
(bouton **+** en haut de l'écran).

Récupérez une copie locale du code avec la commande (l'URL est à
ajuster et vous est donnée par la forge quand vous ouvrez votre projet
avec votre navigateur) :

```
git clone https://forge.univ-lyon1.fr/votrelogin/mif01-2022.git
cd mif01-2022
```

[En cas de problème, voir la FAQ de la forge](https://forge.univ-lyon1.fr/EMMANUEL.COQUERY/forge/wikis/FAQ).

Une fois le clone fait, indiquez l'URL de votre projet (la même pour les deux
membres du binôme) sur TOMUSS. Voir consignes détaillées dans
[projet-note.md](projet-note.md).

Ce fork est votre version du projet, c'est là que vous ferez votre développement.
En cas de mise à jour du dépôt du cours, votre dépôt ne recevra pas
automatiquement les mises à jour. Nous verrons au TP2 comment récupérer facilement les mises à jour depuis le dépôt enseignant.

## Premier contact avec le projet

On vous fournit un squelette [mes/](../mes/).

Le projet utilise l'outil Maven pour la compilation. Nous en parlerons
plus en détails en CM, pour l'instant vous devez seulement savoir :

- Le projet est décrit dans le fichier `pom.xml`. Vous pouvez regarder
  le contenu de ce fichier mais vous n'avez pas besoin de le modifier
  pour ce TP.
  
- `mvn compile` compile le projet

- `mvn exec:java` lance le programme compilé

Vérifiez que vous pouvez lancer le programme depuis la ligne de
commande.

Le squelette contient ces classes :

-   `view.JfxView` : une classe gérant l'interface graphique. Vous pouvez jeter
    un œil au code source de cette classe et des classes qu'elle utilise
    (`PatientView`, `HealthProfessionalView`). Si vous trouvez le code propre,
    ce cours est là pour vous montrer que ce n'est pas le cas, il y a beaucoup
    de choses dans ce squelette qu'un bon programmeur ne fait *jamais*.

-   Des classes `model.*` : pour gérer pour vous les données manipulées par
    votre programme. Dans la vraie vie, il y aurait une vraie base de données,
    mais pour simplifier, tout est géré en RAM. Notre application manipule des
    patients (`Patient`), des professionnels de santé (`HealthProfessional`), et
    des ordonnances (`Prescription`), émises par des professionnels de santé
    pour des patients.

-   `App` : la principale, qui gère la création de l'application.

Le squelette de code fait une première séparation entre l'interface
graphique (package `view`) et la logique métier (package `model`).
Nous verrons plus tard que cette séparation est encore plus
qu'imparfaite et vous demanderons de refactorer le code.

### Exemple d'utilisation

Lancez l'interface graphique (`mvn exec:java`). Vous devriez voir s'afficher à
gauche la liste des patients, et à droite la liste des professionnels de santé.
Bien sûr, dans la vraie vie chaque utilisateur aurait son interface et ne verrai
pas les informations des autres utilisateurs, mais pour simplifier nous
affichons tout au même endroit. Chaque patient voit la liste des ordonnances qui
le concernent, et chaque professionnel de santé peut chercher un patient en
utilisant son numéro de sécurité sociale (pour simplifier, un bouton 📋 permet
de copier le numéro de sécurité sociale d'un patient, et vous pouvez le coller
avec <kbd>Control</kbd>+<kbd>v</kbd> dans le champ recherche. Avec un peu
d'imagination ce système est équivalent au lecteur de carte vitale de votre
médecin ...). Une fois un patient sélectionné, le professionnel de santé peut
ajouter ou supprimer des ordonnances. Une ordonnance est ici simplement une chaîne
de caractères, et le professionnel de santé a une série de boutons pour faire
une ordonnance en 1 clic plutôt que de devoir saisir l'intitulé exact au
clavier.

Dans le vrai https://www.monespacesante.fr/, c'est bien entendu beaucoup plus
complexe. En réalité, le professionnel de santé crée son ordonnance dans un
autre logiciel, qui lui permet de l'envoyer à l'espace santé automatiquement.

Cliquez sur le bouton 📋 de l'utilisatrice Alice, collez le numéro dans le champ
recherche de Dr. Who et validez. Vous devriez voir les ordonnances d'Alice
s'afficher. Essayez d'entrer « Prendre la pilule rouge » dans le champ «
prescribe » puis validez. Vous devriez voir apparaître cette ordonnance dans la
liste du Dr. Who, mais vous aurez besoin d'un clic sur 🗘 pour mettre à jour la
liste du côté d'Alice.

(Hmm, hmm, non-seulement ce n'est pas très ergonomique, mais c'est révélateur
d'un problème d'architecture de notre squelette de code, la liste aurait du se
mettre à jour toute seule ...)

Cliquez sur un bouton « x » en face d'une ordonnance : l'ordonnance est
supprimée.

### Documentation de Java et JavaFX

Consultez la documentation de [Java
11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html) et de
[JavaFX Graphics](https://openjfx.io/javadoc/11/)
(la bibliothèque graphique utilisée).

### Chargement du projet dans un IDE (VS Code, Eclipse, IntelliJ, Netbeans ..)

Si vous souhaitez utiliser un IDE, votre IDE favori propose
probablement une prise en charge de Maven, et configurera donc le
projet automatiquement depuis le `pom.xml` :

- VS Code : installer le plugin [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) qui apporte le support du langage Java, de Maven, ... Faire menu File → Open Folder (<kbd>Controle</kbd>+<kbd>k</kbd> <kbd>Controle</kbd>+<kbd>o</kbd>) puis choisir le répertoire. Une section « Maven Projects » doit s'ajouter à la barre latérale de gauche, et vous pourrez sélectionner les actions à effectuer (`exec:java` dans la section `exec` pour lancer l'application). Si vous n'avez pas la complétion intelligente et la navigation dans le code, vous devrez sans doute positionner votre variable de configuration `java.home` : faire avec <kbd>Control</kbd> + <kbd>,</kbd>, puis chercher `java.home` et « edit in settings.json ». Au Nautibus, la configuration est :
<pre>
"java.home" : "/home/tpetu/m1if01/jdk-11.0.4/"
</pre>

- Eclipse : installer le plugin [m2e](http://www.eclipse.org/m2e/), puis importer le projet en
  temps que projet Maven (File → Import... → Maven → Existing Maven Projects). Au Nautibus, Eclipse
  est installé dans `/home/tpetu/m1if01/bin/eclipse` avec m2e installé. Pour lancer l'application,
  on peut au choix utiliser « Run as ... » → « Java application » (mais cf. ci-dessous pour une
  erreur fréquente), ou « Run as ... » → « Maven Build... » puis choisir le goal « exec:java ».

- IntelliJ et Netbeans : le support de Maven est inclus de base dans l'outil. Il
  suffit d'ouvrir le répertoire contenant le `pom.xml`.

Il n'est pas rare qu'il y ait de mauvaises interactions entre un IDE et JavaFX, nous avons essayé de documenter les problèmes et solutions dans [FAQ.md](../FAQ.md).

## Travail demandé

Dans un premier temps, conservez l'architecture (répartition en
classes et packages) fournie. Nous verrons bientôt comment réorganiser
le tout.

### Un autre type de professionnel de santé

Pour l'instant, un professionnel de santé peut être soit non-spécialisé, soit
Homéopathe, soit Dentiste (dans le modèle, ceci est reflété par de l'héritage,
les classes `Homeopath` et `Dentist` dérivent de `HealthProfessionnal`). Ajoutez
un autre type de soignant en créant une classe similaire à `Homeopath` et
`Dentist`. Instanciez cette classe dans
`model.MES.createExampleConfiguration()`, et proposez des boutons pour ajouter
les prescriptions en un clic correspondant à ce type de soignant (il n'est pas
demandé que ce soit réaliste médicalement !).

Si vous ne trouvez pas les endroits où faire les modifications dans la base de
code, une astuce : cherchez les chaînes de caractères qui vous intéressent, par
exemple `git grep Natrum` vous indiquera où se trouve la prescription prédéfinie
de l'Homéopathe.

### Ajout dynamique de patient

En bas de la liste des patients, vous avez un bouton qui devrait permettre de
créer un patient. Dans le squelette, cette fonctionnalité n'est pas implémentée
(il y a presque tout ce qu'il faut dans `MES.java`, mais ce n'est pas exposé
dans l'interface graphique). Ajoutez le nécessaire pour que ce bouton
fonctionne.

### Suppression d'une ordonnance par un patient

Pour des raisons de confidentialité, un patient pourrait vouloir supprimer une ordonnance. Réfléchissez à ce qu'il faudrait faire pour avoir un bouton « x » dans la liste des ordonnances d'un patient, analogue au bouton « x » déjà présent chez les professionnels de santé. Si vous pensez pouvoir le faire facilement, codez-le, mais vous pouvez aussi décider que cette modification est trop horrible pour être faite sur la base de code en l'état, et attendre d'avoir fait un refactoring MVC correct pour implémenter cette fonctionnalité.

### Problèmes avec la base de code fournie

Réfléchissez maintenant à l'architecture du code. Posez-vous les
questions (rhétoriques ?) suivantes :

- Est-ce facile de modifier l'interface graphique sans changer la
  fonctionnalité ?
  
- À l'inverse, des fonctionnalités comme les ordonnances pré-définies devraient
  être indépendantes, ou presque, de l'interface graphique utilisée. Quand vous
  avez ajouté un type de professionnel de santé, avez-vous pu le faire sans
  modifier la vue ?
  
- Si vous deviez avoir plusieurs interfaces possibles (par exemple un
  mode « expert » et un mode « débutant » avec moins de boutons, ou
  une interface web et une interface graphique JavaFX), pourriez-vous
  le faire facilement sans dupliquer le code de la logique métier ?
  
- Quelles sont les responsabilités de la classe `JfxView` ? Est-ce
  compatible avec le [Principe de Responsabilité
  Unique](https://en.wikipedia.org/wiki/Single_responsibility_principle) ?

Quelques éléments de réponses sont disponibles dans le fichier
[architecture-et-dependances.md](architecture-et-dependances.md). Ne
les lisez pas avant d'y avoir réfléchi vous-mêmes.

## Si vous avez fini ...

Passez au [TP2](../TP2-outils) !
