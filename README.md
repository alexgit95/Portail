# Portail

## Generation du certificat 

```
keytool -genkeypair -alias baeldung -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore baeldung.p12 -validity 3650
```

## Gestion des fichiers de conf

Les fichier suivants doient etre présent pour le bon fonctionnement de l'appli :

```
/properties/configuration-portail.properties
/properties/configuration-portail-ssl.properties
/properties/configuration-portail-secrets.properties
```

/properties/configuration-portail.properties :

```
ip.geoposition=localhost
port.geoposition=8888
ip.myphotos=localhost
port.myphotos=8899
```


/properties/configuration-portail-ssl.properties


```
server.ssl.key-store-type=PKCS12
server.ssl.key-store=file:/certificats/mycertif.p12
server.ssl.key-store-password=motdepasse
server.ssl.key-alias=baeldung
```


/properties/configuration-portail-secrets.properties


```
secure.admin.login=admin
secure.admin.password=admin
secure.guest.login=guest
secure.guest.password=guest
secure.nbjour.rememberme=10
secure.secret.rememberme=unsecret
```


## Utilisation avec Docker

```
docker build -t portail .
```

```
docker run -p 7777:7777 -v /c/Users/<USER WINDOWS>/dev/properties:/properties/:ro -v /c/Users/<USER WINDOWS>/dev/certificats:/certificats/:ro portail
```
