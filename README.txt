	-------------------------------------------
	| Projet JAVA 2 L3 - Groupe C - MORA - TO |
	-------------------------------------------
	
	Ce fichier est à lire de préférence sous Notepad++
	
	Auteurs
	-------
	
		Benjamin MORA
		John-Michaël TO
	
	Dates du projet
	---------------
	
		Début 	:	28/03/2014
		Fin 	:	30/05/2014
	
	Préambule
	---------
	
	Dans le cadre du projet de JAVA (2), nous avons créé une application
	serveur et client permettant la gestion de la scolarité au sein d'une
	école d'ingénieurs. Nous avons créé nos applications de la façon la 
	plus modulaire possible.
	
	Compilation & Exécution
	-----------------------

	Plusieurs méthodes sont possibles, la plus simple étant de passer par l'IDE Eclipse.
	
	Côté serveur
	--
	1) Au préalable, configurer le fichier server.config
	   en n'oubliant pas d'ouvrir le port que vous indiquerez dans votre pare-feu
	  
	2) Importer le fichier BDD.sql dans votre base de données
	
	3) Exécuter le fichier MainS.java afin de lancer le serveur
	
	Côté client
	--
	1) Au préalable, configurer le fichier client.config en indiquant l'adresse IP
	   et le port du serveur distant
	
	2) Exécuter le fichier MainC.java afin de lancer l'application client
	
	Fonctionnalités optionnelles
	----------------------------
	
	- Interface administrateur
	- Affichage des moyennes calculées d'un module
	- Système d'authentification amélioré
	- Gestion et création des types de notes de manière dynamique
	- Pondération des coefficients pour les notes
	- Utilisation d'une base de données MySQL permettant d'être déployé sur tout type de serveur