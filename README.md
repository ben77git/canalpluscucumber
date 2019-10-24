# canalpluscucumber
Implémentation d'un scénario Cucumber de modification d'adresse

## Description
Le projet **_canalpluscucumber_** implémente un scénario Cucumber de modification d'adresse d'un abonné Canal+ en
utilisant la plateforme **Spring Boot** avec **Java**, **Cucumber** et **Maven**.
Nous utilisons **WireMock** pour simuler l'api Canal+ de gestion d'un abonné proposant des fonctionnalités de 
création d'abonnés et de modification d'adresse d'abonnés.
**Serenity BDD** permet de décrire les actions effectuées sur notre api Canal+ en vue de la réalisation de notre scénario,
il produira également des rapports des resultats des tests ainsi qu'une documentation évolutive décrivant
les exigences et les comportements de l'application.

## Scénario Cucumber implémenté :
```
# language: fr
@adresse @modification
Fonctionnalité: Modifier l'adresse d'un abonné
	@TestsRecevabilité
	@scenarioTest
	Plan du scénario: Modification de l'adresse d'un abonné résidant en France sans ou avec date d'effet
		Etant donné un abonné avec une adresse principale <active> en <pays>
		Lorsque le conseiller connecté à <canal> modifie l'adresse de l'abonné <condition>
		Alors l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné
		Et un mouvement de modification d'adresse est créé
	Exemples:
		| canal | active | pays | condition |
		| FACE | inactive | France | sans date d’effet |
		| EC | active | Pologne | avec date d’effet |
```

## Installation :
Avant de commencer, vous aurez besoin d'installer :
- Java 8 ou supérieur
- Maven 3.3.1 ou supérieur
- Git

## Récupération du code du projet :
```
git clone https://github.com/ben77git/canalpluscucumber.git
cd canalpluscucumber
```

## Execution des tests :
`mvn test`

## Génération de la documentation :
`mvn clean verify`

## Spécifications techniques :
Java 8
Maven 3.3.1
Git
Spring boot
Cucumber 4
Serenity BDD 2
WireMock 2
Junit 4
Okhttp 3
Assertj 3

## Auteur :
[Benoît NJONKOU](mailto:benoit.n.pro@gmail.com)
