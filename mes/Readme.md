
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

> Si vous voyez que la partie 5 du build (mvn clean install compile) reste bloqué n'hésitez pas à arrêter le build via Control + C et relancer la command docker build.

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
