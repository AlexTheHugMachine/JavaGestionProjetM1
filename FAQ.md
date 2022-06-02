# Questions Fréquentes sur le cours

## Je n'arrive pas à avoir la bonne version de Java, mon .bashrc n'est pas lu

Selon comment le shell est lancé, c'est soit le `~/.bashrc` soit le `~/.bash_profile` qui est lu. Une bonne idée est donc de charger le `~/.bashrc` depuis le `~/.bash_profile`, en mettant ceci dans le `~/.bash_profile` :

```
# Fichier ~/.bash_profile
. ~/.bashrc
```

Si vous voulez plus d'explications, cherchez dans vos souvenirs ou vos cours de programmation concurrente si vous étiez en L3 à Lyon 1 !

# VSCode n'a pas les fonctionnalités intelligentes pour Java (complétion, navigation, ...)

Il faut :

* Installer le bon plugin. Le plus simple est d'installer [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

* Positionner correctement son `JAVA_HOME`, que l'on peut faire avec <kbd>Control</kbd> + <kbd>,</kbd>, puis chercher `java.home` et « edit in settings.json ». Au Nautibus, la configuration est :
<pre>
"java.home" : "/home/tpetu/m1if01/jdk-11.0.4/"
</pre>
