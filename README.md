# Dockerisation of a Spring-boot + MariaDB application

## Author
KLEIN Arthur  
ING3 ICC  
22208208

## Description
The current repository represents my work for the project of the module Cloud Infrastructure. It is based on a previously build project (referred as inner project) which contained a JEE application that used Spring-boot and MariaDB to create a website to find available tennis matches. The previous Readme of the inner project is at the end of this one.

## Additions to the inner project

### src/main/resources/application.properties
The host localhost had to be changed to mariadb to allow the connection between the two containers. The username and password were also changed to meet sql security norms.

### Dockerfile
The dockerfile of this project has been written using the exemple of the [official documentation](https://docs.docker.com/language/java/build-images/) as a base to create the containers that runs the webpage.

### docker-compose.yml
The docker-compose.yml uses the concepts presented in the module to create a second container for the mariaDB database with the correct login informations, and allow this container and the JEE container to communicate with each other and with the host.

## What I did to verify my work?

### Download the project
``` shell
git clone https://github.com/arthur2klein/projetJEE_MaghraouiMighisKlein
```

### Launch the application
``` shell
cd <project_path>
docker-compose up --build
```
Docker should download and create the images, and run the container. The webpage can be accessed by connecting your preferred web browser with localhost:8080 .

### Test the communication with the database
When launching the application, no user is created. Clicking on the "Identification" button and entering:  
- Identifiant: login
- Mot de passe: password
should result in a label "Veuillez vous identifier." displayed in red on the top of the webpage, showing that this user does not exist.  

Clicking on the "Création Compte" button and entering:
- Nom: nom
- Prenom: prenom
- eMail: test@test.com
- Login: login
- Mot de passe: password
will create the user. Switching back to the Identification page and entering the same login and password will redirect the user to the Accueil with the user logged in.

Switching then to Gérer mon compte and entering:
- Login: newlogin
- Mot de passe: newpassword
will modify the login and password of the same user. Re-doing the Identification with the previous login and password will show the error message. But the new ones will word.

## Known Issues

### DNS issues with Docker
During the first tests of the application, spring could not download the dependencies. The issue was that the DNS used by my docker could not find them. Changing the dns of my /etc/docker/daemon.json file to   
"dns": ["8.8.8.8"],  
solved the issue.

### Port usage
I had to change the port used by the mariadb to 3307 (see docker-compose.yml). The following command will list the port in used for your host:
```shell
sudo netstat -tulpn
```
This will allow you to find unused port or idenitfy the application that was using a port (using a grep command).

### Other issues
- [Submit an issue on this repository](https://github.com/arthur2klein/projetJEE_MaghraouiMighisKlein/issues),
- Contact me: kleinarthu@cy-tech.fr .

## Special thanks
Thanks to Maghraoui Yahya and Mighis Assia for allowing me to use and modified the project they participated for in a previous module.

---
# Inner project
## Classe
ING2 GSI Groupe 1
## Groupe
Maghraoui Yahya (modele)  
Mighis Assia (vue)  
Klein Arthur (controllers)

## Notes d'installation
Pour installer le projet :
- télécharger le projet
- dans eclipse JEE, choisissez import-> projet from folder or archive -> entrez le chemin vers le projet en choisissant archive
- si besoin, modifier le contenu de src/main/ressources/application.properties, puis créez la base de donnée ayant le nom indiqué dans ce fichier
## Notes de lancement
Pour lancer le projet :
- faîtes un clic droit sur src/main/java/fr/cytech/projetJEE/ProjetJEEApplication.java et choisissez run As -> Java application
- ouvrez votre navigateur sur le port indiqué par eclipse (normalement localhost:8080)
- pour le premier lancement, entrez localhost:8080/createTerrain
## Notes d'utilisation
Les fonctionnalités de l'application sont les suivantes :
- Accédez à un aperçu de l'application par l'accueil.
- Créez votre ou vos comptes, vous n'êtes pas autorisés à créer plusieurs comptes avec des logins similaires.
- Si vous avez un compte, authentifiez-vous.
- Entrez vos disponibilités dans l'onglet dédié, après validation, l'application vous indiquera si un match a été trouvé.
- Pour tester l'application, utilisez un second compte et choisissez une disponibilité du premier compte.
- Consultez les matchs à venir par l'agenda.
- Visualiser et entrez vos résultats dans l'onglet dédié, l'application n'enregistrera pas de score invalide.
- Déconnectez-vous et modifiez votre compte par l'onglet dédié
---
