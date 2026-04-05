# project.pdf
Pages: 7

---


## Page 1

CONNECT'IN V2_
< MIGRATION DU BACK-END VERS JAVA
SPRING BOOT />

## Page 2

CONNECT'IN V2
Contexte professionnel
VousavezterminélapremièreversiondeConnect'In,leréseausocialinternedel'entreprise. Voscollab-
orateursensonttrèssatisfaits,maisladirectiontechniquesouhaitefaireévoluerlasolution.
MêmesilapremièreversiondéveloppéeavecLaravelfonctionnecorrectement,l'entreprisesouhaitedé-
sormaisharmonisersestechnologiesback-endautourdeJavaafindefaciliterlamaintenance,améliorer
la robustesse globale des applications et s'aligner sur les standards techniques utilisés dans ses autres
projets.
Dans ce contexte, votre équipe reçoit la mission de remplacer entièrement le back-end existant par
unenouvelleversiondéveloppéeavecSpringBoot,toutenconservantlefront-enddéjàenplace. Cette
contrainteimpliquequelanouvelleAPIdevrarestercompatibleavecl'applicationclienteexistante,sans
modificationdecelle-ci.
VousêtesdoncchargédeconcevoiretdévelopperConnect'Inv2,unenouvelleAPIreposantsurl'écosys-
tèmeJava.
Présentation du projet
Le projet consiste à recréer le back-end de l'application sous forme d'une API RESTful Spring Boot, en
reproduisantfidèlementlecomportementdel'APIprécédente.
L’équipepédagogiquelocalesélectionneralesdeuxmeilleursfront-endréaliséslorsdelapremière
éditiondeConnect’In. Cesdeuxprojetsservirontdebasecommunepourl’ensembledesgroupes.
Chaquegroupeseraensuiteaffectéàl’undecesfront-end,enveillantàcequ’aucunparticipantne
travaillesurceluiqu’ilalui-mêmedéveloppé.
Le front-end ayant déjà été conçu et validé, il ne devra faire l’objet d’aucune modification. Cela
impliquenotammentdeconserverl’intégralitédelalogiquemétier,lesroutesAPIexistantesainsi
que les formats de réponses JSON attendus. L’objectif principal n’est donc pas d’ajouter de nou-
vellesfonctionnalités,maisdedémontrervotrecapacitéàcomprendreunearchitectureexistante
etàlareproduirefidèlementdansuneautretechnologie.
L'applicationreposerasurunebasededonnéesMySQLetdevrasuivreànouveauuneorganisationprofes-
sionnelleencouchesséparantclairementlesresponsabilités(contrôleurs,services,accèsauxdonnées,
sécurité,etc.).
1

## Page 3

Objectifs pédagogiques
Ce projet vise à vous confronter à une situation réaliste de migration technologique telle qu'on peut en
rencontrerenentreprise. Ilvouspermettranotammentde:
(cid:51) comprendreetanalyseruneAPIexistante
(cid:51) concevoiruneAPIRESTavecSpringBoot
(cid:51) utiliserSpringDataJPAetHibernatepourlapersistance
(cid:51) mettreenplaceuneauthentificationsécuriséepartoken(JWT)
(cid:51) structureruneapplicationJavaselonunearchitectureencouches
(cid:51) maintenirlacompatibilitéentreunfront-endetunback-end
(cid:51) appliquerlesbonnespratiquesprofessionnellesdudéveloppementJava
(cid:51) déployeruneapplicationconteneuriséeavecDocker
Au-delàdel'aspecttechnique,ceprojetmetégalementl'accentsurlacapacitéàlire,comprendreetre-
produireunsystèmeexistant.
Fonctionnalités attendues
Les fonctionnalités implémentées devront être équivalentes à celles de la première version du projet
Connect'In. Pourrappel,lesfonctionnalitésattenduessontlessuivantes:
L'applicationdevrapermettrelagestioncomplètedesutilisateurs: inscription,authentification,consul-
tationetmodificationduprofil,changementdemotdepasseainsiquesuppressionducompte. Lorsqu'un
utilisateursupprimesoncompte,ildevrapouvoirchoisirentresupprimerl'ensembledesescontenusou
lesconserversousformeanonymisée. Danstouslescas,lesinformationspersonnellesetleslikesasso-
ciésdevrontêtresupprimés.
Lagestiondespublicationsconstituelecœurduréseausocial. Unutilisateurconnectépourracréerun
postcontenantdutexteetéventuellementuneimage,modifiersesproprespublicationsetlessupprimer.
Touslesutilisateursauthentifiéspourrontconsulterlespublicationsdesautrescollaborateursetinteragir
avec celles-ci via un système de likes. Chaque utilisateur ne pourra liker un post qu'une seule fois. La
suppressiond'unpostentraîneraautomatiquementlasuppressiondescommentairesassociés.
Lesystèmedevraégalementpermettrelagestiondescommentaires. Chaquepublicationpourrarecevoir
plusieurscommentairesetchaqueutilisateurdevrapouvoirmodifierousupprimeruniquementsespro-
presmessages.
Commedanslapremièreversion,touteslesinteractionsdevrontfonctionnerdemanièredynamiquegrâce
auxappelsAPIréalisésparlefront-endexistant. LesréponsesenvoyéesparvotreAPIdevrontdoncêtre
immédiatementexploitablessansnécessiterderechargementdepage.
2

## Page 4

Contraintes techniques
Leprojetdevraêtreréaliséaveclestechnologiessuivantes:
(cid:51) Java25(ouversionplusrécente)
(cid:51) SpringBoot
(cid:51) SpringWeb
(cid:51) SpringDataJPA
(cid:51) SpringSecurity
(cid:51) JWTpourl'authentification
(cid:51) MySQL
(cid:51) MavenouGradle
Conteneurisation(Docker)
Leprojetdevraêtreentièrementdockerisé.
Celaimplique:
-unDockerfilepourl'applicationSpringBoot
-undocker-compose.ymlpermettantdelancer:
-l'application
-labasededonnéesMySQL
-laconfigurationdesvariablesd'environnement(BDD,JWT,etc.)
-undémarragefonctionnelduprojetvialacommande:
docker compose up --build
L'objectifestdefournirunenvironnementreproductibleetprofessionnel,similaireàuncontexteréeld'en-
treprise.
Architectureattendue
Une architecture claire et structurée est attendue, avec une séparation logique entre les différentes
couchesdel'application(contrôleurs, services, repositories, entités, configuration, sécurité, gestiondes
exceptions,etc.).
Lasécuritédevraêtrecorrectementimplémentée. Touteslesroutesnécessitantuneauthentificationde-
vrontêtreprotégées, etlesrèglesd'autorisationdevrontgarantirqu'unutilisateurnepuissemodifierou
supprimerquesespropresressources.
3

## Page 5

Base de données
Vousavezlapossibilitéderéutiliserlemodèledebasededonnéesexistantoudeleredéfinirsinécessaire,
àconditionderespecterlesfonctionnalitésattenduesetdeconserverlemêmed'entrées/sortiesdevos
routes.
Undiagrammedebasededonnéesdevraêtrefournidansleslivrablesafindejustifiervoschoixdecon-
ceptionencasdechangements.
Bonus (facultatif)
Unefoistouteslesfonctionnalitésprincipalesimplémentées,vouspourrezenrichirvotreprojetavec:
(cid:51) destestsunitairesetd'intégration
(cid:51) l'optimisationdesimagesuploadées
(cid:51) lamiseencachedecertainesdonnées
Livrables attendus
Lesélémentssuivantsdevrontêtrefournis:
(cid:51) lecodesourcecompletduprojet
(cid:51) unREADMEdétaillantl'installation,laconfigurationetlelancement
(cid:51) undiagrammedebasededonnées
(cid:51) unedocumentationdel'API(PDF,collectionPostman,interfacewebouautre)
Ressources utiles
Framework
SpringBoot—https://spring.io/projects/spring-boot
SpringSecurity—https://spring.io/projects/spring-security
SpringDataJPA—https://spring.io/projects/spring-data-jpa
JWT—https://jwt.io
MySQL—https://www.mysql.com
Hibernate—https://hibernate.org
Maven—https://maven.apache.org
4

## Page 6

Tests
SpringBootTest—https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.test-
ing
JUnit5—https://junit.org/junit5/
Mockito—https://site.mockito.org/
Testcontainers—https://www.testcontainers.org/
Conteneurisation
Docker—https://www.docker.com/
DockerCompose—https://docs.docker.com/compose/
5

## Page 7

v1.0.1