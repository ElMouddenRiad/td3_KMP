# Plan de migration TD3 — PizzApp vers KMP

## Choix techniques retenus
- Repository actuel conservé
- Cible web : Compose Multiplatform **Wasm** (version récente)
- Approche incrémentale : ne pas casser le TD2 Android, puis extraire progressivement le code partagé

## Pourquoi Wasm plutôt que JS IR ?
- Wasm est la voie moderne de Compose Multiplatform pour le web (meilleures perfs à terme)
- JS IR reste une option historique/compatibilité
- Le sujet TD3 impose « navigateur web », pas explicitement JS IR : Wasm est cohérent avec « dernière version »

## Stratégie d’architecture
1. Créer un module `shared` en Kotlin Multiplatform
2. Déplacer dans `shared` :
   - modèles métier (Pizza, panier, commande)
   - logique de calcul total
   - logique ViewModel/UseCase (ou state holder) indépendante plateforme
3. Garder Android spécifique dans `app` :
   - Room (DAO, Database)
   - wiring Android / Compose Android
4. Ajouter implémentations Desktop/Web de la persistance (ou mémoire simple au départ)
5. Brancher UI Compose Multiplatform

## Point Room (important)
Room est Android-centric dans votre TD2 actuel.
Pour TD3, deux options propres :
- Option A (rapide) : Room sur Android uniquement + stockage en mémoire/fichier pour Desktop/Web
- Option B (plus complète) : remplacer le repository partagé par SQLDelight (multi-plateforme)

Recommandation TD3 : Option A d’abord (livrable rapide), Option B si temps.

## Checklist d’implémentation
- [x] Créer module `shared` (androidTarget, jvm("desktop"), wasmJs)
- [x] Exposer API commune du domaine (modèle Pizza + panier + calcul total + service)
- [x] Injecter repository par interface commune (Android Room + interface shared)
- [x] Conserver Android TD2 (écrans + Room) sans régression fonctionnelle
- [x] Créer launcher Desktop (`desktopApp`)
- [x] Créer launcher Web (Wasm) (`webApp`)
- [x] Vérifier flux commande + historique sur Desktop/Web (in-memory)
- [x] Aligner le flux utilisateur Android/Desktop/Web (Accueil, Menu, Détail, Panier, Historique)
- [ ] Compléter README (binôme, difficultés, vidéo)

## Écart restant (assumé)
- Persistance non totalement commune: Room Android, fichier JSON Desktop, localStorage Web.
- Ce point n'empêche pas la validation d'un TD3 KMP fonctionnel, mais une version "full shared persistence" demanderait SQLDelight (ou backend + synchronisation).

## Commandes de validation (à lancer dans Android Studio/terminal avec Gradle wrapper)
- `./gradlew :app:assembleDebug`
- `./gradlew :desktopApp:run`
- `./gradlew :webApp:wasmJsBrowserDevelopmentRun`

## Livrable attendu
- Projet multi-plateforme exécutable Android + Web + Desktop
- README documenté (difficultés + solutions)
- Lien de démo vidéo
