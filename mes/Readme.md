# Installation

- Java 11
- openjfx `sudo apt install openjfx`

Si vous avez installé JavaFX via votre distribution et que Java ne trouve pas les classes JavaFX, ajoutez explicitement les fichiers JAR concernés à votre classpath, avec quelque chose comme :

```bash
CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/ext/jfxrt.jar
CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/jfxswt.jar
export CLASSPATH
```

Vérifiez que vous avez bien git et mvn installés :

```bash
$ git --version
git version 2.17.0
$ mvn --version
Apache Maven 3.5.2
[...]
```

(Vous pouvez bien sûr avoir des versions plus récentes)
Si ce n'est pas le cas installez-les. Sous Ubuntu, faire :

```bash
apt install git maven
```

# Lancer l'application dans un container Docker

## MacOs

### Pré-requis

- Avoir Docker !

- Pour pouvoir lancer une interface graphique depuis docker il va falloir avoir un système de fenêtrage d'installer sur votre OS.
  - x11
  - xquartz

#### Installation de XQuartz

Sur MacOs il faut installer le paquet suivant

```bash
brew install xquartz
```

Avant de lancer l'application, quitter votre session MacOs et re-logger vous.

#### Lancement de XQuartz

Pour lancer l'application vous pouvez passer par votre terminal via

```bash
open -a Xquartz
```

ou via Spotligh/Alfred : XQuartz

Un terminal blanc doit s'ouvrir ne le fermez jamais.

Pour vérifier que l'application est bien lancé regardez dans le dock qu'un icône avec un X est affiché.

#### Settings à définir

1. Dans le dock cliquez sur l'icone avec le X.
2. Diriger votre souris en haut à gauche de l'écran.
3. Cliquez sur XQuartz.
4. Puis sur "Preferences...".
5. Cliquez sur la tab "Security"
6. Cochez "Allow connections from network clients"
7. Quittez la fenêtre.
8. fermez XQuartz via Command + Q ou dans le dock en cliquant droit dessus puis quittez.
9. Rouvrez ce dernier.

### Build de l'image Docker

1. Rendez-vous dans le dossier où est stocké le fichier Dockerfile.
2. Lancez la commande suivante :

```bash
docker build -t mes
```

3. Cette commande risque de prendre du temps.

> Si vous voyez que la partie 5 du build (`mvn clean compile`) reste bloqué, n'hésitez pas à arrêter le build via `Control + C` et relancer la commande `docker build -t mes`.

4. A la fin vous devriez avoir un message qui vous indique que le build a bien fonctionné.

### Run du container Docker `mes`

- Vérifiez que vous avez le script `run-container.sh` et éxécutez `chmod +x ./run-container.sh`.

Si ce n'est pas le cas voici le script :

```bash
#!/bin/bash

# allow access from localhost
xhost + 127.0.0.1

# Run the image builded.
docker run -e DISPLAY=host.docker.internal:0 --privileged mes
```

- Enfin, veuillez vérifier que XQuartz est bien lancé.

1. lancer le script via : `./run-container.sh`
2. Si tous se passe bien vous devriez avoir une fenêtre qui s'affiche avec votre application :D .

## Sources

<https://learnwell.medium.com/how-to-dockerize-a-java-gui-application-bce560abf62a>

<https://cntnr.io/running-guis-with-docker-on-mac-os-x-a14df6a76efc>

<https://medium.com/@mreichelt/how-to-show-x11-windows-within-docker-on-mac-50759f4b65cb>

<https://medium.com/@SaravSun/running-gui-applications-inside-docker-containers-83d65c0db110>

---
## Linux

Le projet utilise l'outil Maven pour la compilation.

Le projet est décrit dans le fichier pom.xml. Vous pouvez regarder
le contenu de ce fichier mais vous n'avez pas besoin de le modifier
pour ce TP.

`mv compile` compile le projet

`mvn exec:java` lance le programme compilé

# Structure de l'application

## Racine

| Dossiers/fichiers  | Explications |
|---|---|
| `src/`  | Contient tous les fichiers sources du Projet. |
| `target/` | Contient tous les fichiers compilés et build par Maven. |
| `ressources/data/`  | Données statiques qui font offices de base de données. |
| `Dockerfile`  | Permet de générer une image Docker et faire tourner l'application dans un conteneur Linux. |
| `pom.xml`  | Contient toutes les dépendances et les règles de lancement de l'application.  |

## Fichiers sources `src/`

| Dossiers/fichiers  | Explications |
|---|---|
| `main/config`  | Contient un fichier `checkstyle.xml`, c'est un outil de contrôle de code qui vérifie que le style est conforme à certaines règles de formattage. |
| `main/java`  | Code Source de l'application.  |
| `test/` | Tous les fichiers de tests unitaires JUnit. |

### Quelques remarques sur le Code Source

> Pour faciliter la lecture et l'observation des designes pattern utilisés pour cette application nous avons décidés de faire un dossier par design pattern.

- `view.JfxView` : une classe mère gérant l'interface graphique de la fenêtre principale de l'application.

- Des classes `model.*` : Où nous définissons la représentation des données utilisés par l'application (Patients, Professionnels de santé, Prescriptions).

- `App` : la principale, qui gère la création de l'application (AppStartup permet de configurer et spécifier les traitements qui doivent être faits au lancement).

# Documentation

## Générer la documentation de l'application

Bien que incomplète pour le moment, vous pouvez générer grâce à javadoc une documentation de l'application via `mvn javadoc:javadoc`, pour plus de commandes repotez-vous à la documentation suivante [Apache Maven Javadoc Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/usage.html).

## Java et JavaFX

Consultez la documentation de [Java 11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html) et de [JavaFX Graphics](https://openjfx.io/javadoc/11/)
(la bibliothèque graphique utilisée).

## En savoir plus sur les designs pattern

Durant le développement nous nous sommes énormément appuyés sur [Refactoring Guru](https://refactoring.guru/design-patterns/catalog) qui est un site de vulgarisation des designs patterns et des bonnes pratiques en matière de refactoring. Si vous voulez en savoir plus sur ces sujets nous vous invitons à aller jeter un oeil à ce dernier.
