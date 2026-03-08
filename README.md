## TD3 : Application Kotlin Multiplateforme (KMP)

### Objectif
Faire évoluer PizzApp (TD2) en application multiplateforme pour :
- Android
- Navigateur Web (Wasm)
- Desktop (Windows / macOS / Linux)

### Auteur
- Étudiant : Riad El Moudden
- Travail réalisé en individuel (pas de binôme)

### État d'avancement TD3
- [x] Module shared KMP créé
- [x] Cible Android branchée sur le code partagé
- [x] Cible Desktop opérationnelle
- [x] Cible Web (Wasm) opérationnelle
- [x] UI graphique sur Android / Desktop / Web
- [x] Données/Repository partagés
- [x] Persistance adaptée hors-Android (fichier JSON Desktop + localStorage Web)

### Architecture finale
- Android conserve les écrans TD2 (Accueil, Menu, Détail, Panier, Historique) et la persistance Room.
- Desktop/Web utilisent une UI Compose Multiplatform avec le même flux fonctionnel (Accueil, Menu, Détail, Panier, Historique).
- La logique métier est partagée dans `shared` (`OrderService`, panier, calcul du total, historique).
- Le contrat de persistance est partagé via `OrdersRepository`.
- Implémentations de persistance:
	- Android: `OrderRepository` (Room)
	- Desktop: `DesktopFileOrdersRepository` (fichier JSON local)
	- Web: `WebLocalStorageOrdersRepository` (localStorage navigateur)

### Validation TD3
- Le projet correspond à l'objectif "même application multi-plateforme" au niveau fonctionnalités et parcours utilisateur.
- La différence principale restante est la technologie de persistance (Room Android vs fichier/localStorage), tout en conservant un contrat de repository commun.

### Difficultés rencontrées et solutions

- Difficulté 1 : `Room` n'est pas directement multi-plateforme (Android-centric).
	- Solution : création d'une interface commune `OrdersRepository` dans `shared`, avec implémentation `Room` sur Android et implémentations dédiées Desktop/Web.

- Difficulté 2 : éviter la duplication de logique panier/total entre plateformes.
	- Solution : extraction du domaine dans `shared` (`Pizza`, `CartManager`, `OrderPricing`, `OrderService`) et réutilisation depuis Android, Desktop et Web.

- Difficulté 3 : cible web KMP (choix techno + compatibilité).
	- Solution : choix de `wasmJs` (approche moderne Compose/Kotlin), adaptation du code pour éviter les API JVM-only et configuration d'une page d'entrée web compatible Compose.

- Difficulté 4 : verrouillage Yarn/Kotlin Wasm lors du run web.
	- Solution : exécution de la tâche `:kotlinWasmUpgradeYarnLock` pour synchroniser le lockfile et relancer le serveur web.

- Difficulté 5 : historique non persistant sur Desktop/Web après redémarrage.
	- Solution : implémentation de repositories persistants :
	  - `DesktopFileOrdersRepository` (fichier JSON local)
	  - `WebLocalStorageOrdersRepository` (localStorage navigateur)

### Démo vidéo
- Lien vidéo (YouTube) : (https://youtu.be/rbQUNSVh0lg)
- La vidéo montre :
	- le lancement Android
	- le lancement Desktop
	- le lancement Web

### Commandes utiles
- Android : exécution depuis Android Studio (`app`)
- Desktop : `./gradlew :desktopApp:run`
- Web (Wasm) : `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
