# Questions Fréquentes sur le cours

## Je n'arrive pas à avoir la bonne version de Java, mon .bashrc n'est pas lu

Selon comment le shell est lancé, c'est soit le `~/.bashrc` soit le `~/.bash_profile` qui est lu. Une bonne idée est donc de charger le `~/.bashrc` depuis le `~/.bash_profile`, en mettant ceci dans le `~/.bash_profile` :

```
# Fichier ~/.bash_profile
. ~/.bashrc
```

Si vous voulez plus d'explications, cherchez dans vos souvenirs ou vos cours de programmation concurrente si vous étiez en L3 à Lyon 1 !

# Maven ne marche pas avec Java 17

Il arrive que la version de Maven fournie avec la distribution soit incompatible avec la version de Java (c'est le cas sous Ubuntu 20.04 par exemple), et donne l'erreur :

```
$ mvn compile                                                             
[ERROR] Error executing Maven.
[ERROR] java.lang.IllegalStateException: Unable to load cache item
[ERROR] Caused by: Unable to load cache item
[ERROR] Caused by: Could not initialize class com.google.inject.internal.cglib.core.$MethodWrapper
```

La solution est d'installer la dernière version de Maven, cf. https://keepgrowing.in/java/how-to-fix-error-executing-maven-issue-after-updating-to-java-17/

# VSCode n'a pas les fonctionnalités intelligentes pour Java (complétion, navigation, ...)

Il faut :

* Installer le bon plugin. Le plus simple est d'installer [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

* Positionner correctement son `JAVA_HOME`, que l'on peut faire avec <kbd>Control</kbd> + <kbd>,</kbd>, puis chercher `java.home` et « edit in settings.json ». Au Nautibus, la configuration est :
<pre>
"java.home" : "/home/tpetu/m1if01/jdk-11.0.4/"
</pre>


### Problèmes fréquents et solutions avec Eclipse

Il est possible qu'Eclipse signale une erreur ou un warning de
restriction d'accès lors de l'utilisation de javafx. Ce problème peut
être résolu en [modifiant les propriétés du projet pour autoriser
javafx](http://stackoverflow.com/questions/22812488/using-javafx-in-jre-8).
En résumé : clic droit sur le projet dans le project / package
explorer > Properties > Section Java Build Path > Onglet Libraries >
Selectionner Access Rules > Cliquer sur le bouton Edit > Cliquer sur
le bouton Add > Changer l'option "Forbbiden" en "Accessible" et
saisir `javafx/**` comme Rule pattern.

Si vous obtenez le message

> Error: JavaFX runtime components are missing, and are required to
> run this application

La solution se trouve ici : [https://stackoverflow.com/q/57159440/4830165](https://stackoverflow.com/q/57159440/4830165).

Des explications similaires sont disponibles sur ce [lien](https://gist.github.com/stevenliebregt/bc62a382fc43064136b662ee62172ab3).
