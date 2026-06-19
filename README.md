# PetMatch

PetMatch é um aplicativo mobile de adoção de pets desenvolvido em Kotlin com Jetpack Compose.
O projeto tem como objetivo conectar pessoas interessadas em adoção com tutores e instituições de apoio animal, oferecendo uma experiência simples, visual e funcional para encontrar, cadastrar e acompanhar pets disponíveis.

## Demonstração

O aplicativo conta com um fluxo completo de navegação, incluindo tela inicial, autenticação simulada, escolha de perfil, listagem de pets, detalhes, favoritos, cadastro de pets, instituições de ajuda e integração com mapa.

Principais telas implementadas:

* Boas-vindas
* Login
* Cadastro de usuário
* Escolha de perfil
* Lista de pets
* Detalhes do pet
* Cadastro de pet
* Perfil do usuário
* Ajude Pet
* Mapa/localização

## Funcionalidades

* Tela inicial com apresentação do app
* Modo visitante para explorar pets sem login
* Login e criação de conta simulados
* Escolha de perfil entre Tutor e Cliente
* Listagem de pets disponíveis para adoção
* Busca e filtros por tipo de pet
* Tela de detalhes com informações completas do animal
* Sistema de favoritos para usuários do perfil Cliente
* Cadastro de novos pets para usuários do perfil Tutor
* Perfil personalizado conforme o tipo de usuário
* Cadastro de instituições e locais de ajuda
* Notificações locais para ações importantes
* Integração com API pública de geolocalização
* Abertura da localização do pet no mapa
* Navegação inferior entre as principais áreas do app

## Tecnologias utilizadas

* Kotlin
* Jetpack Compose
* Material 3
* Android Studio
* Navigation Compose
* Retrofit
* Gson Converter
* Coil
* OpenStreetMap Nominatim API
* Local Notifications
* Gradle Kotlin DSL

## Integração com API

O projeto utiliza Retrofit para consumir a API pública Nominatim, do OpenStreetMap.

A integração permite transformar a localização textual cadastrada para um pet em dados de endereço, latitude e longitude. Com isso, o aplicativo consegue exibir informações de localização e abrir o local correspondente no mapa.

Arquivos relacionados:

* `RetrofitClient.kt`
* `PetApiService.kt`
* `TelaMapaPet.kt`

## Notificações

O app possui notificações locais para reforçar ações importantes da experiência do usuário, como:

* Login realizado
* Conta criada
* Pet favoritado
* Pet removido dos favoritos
* Pet cadastrado
* Localização consultada
* Instituição cadastrada

A lógica foi centralizada no arquivo `NotificationHelper.kt`, mantendo o código mais organizado e reutilizável.

## Estrutura do projeto

```txt
PetMatch/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/petmatch/
│           │   ├── MainActivity.kt
│           │   ├── Pet.kt
│           │   ├── DadosPets.kt
│           │   ├── BotaoPadrao.kt
│           │   ├── NotificationHelper.kt
│           │   ├── PetApiService.kt
│           │   ├── RetrofitClient.kt
│           │   ├── TelaBoasVindas.kt
│           │   ├── TelaLogin.kt
│           │   ├── TelaCadastroUsuario.kt
│           │   ├── TelaEscolhaPerfil.kt
│           │   ├── TelaListaPets.kt
│           │   ├── TelaDetalhesPet.kt
│           │   ├── TelaCadastroPet.kt
│           │   ├── TelaPerfilUsuario.kt
│           │   ├── TelaAjudePet.kt
│           │   └── TelaMapaPet.kt
│           └── res/
│               ├── drawable/
│               ├── mipmap/
│               ├── values/
│               └── xml/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
└── gradlew.bat
```

## Principais arquivos

* `MainActivity.kt`: controla a navegação, estados principais e fluxo do aplicativo.
* `Pet.kt`: define o modelo de dados dos pets.
* `DadosPets.kt`: armazena a base inicial de pets exibidos no app.
* `TelaListaPets.kt`: exibe pets, busca, filtros e cards.
* `TelaDetalhesPet.kt`: mostra informações completas de cada pet.
* `TelaCadastroPet.kt`: permite que tutores cadastrem novos pets.
* `TelaPerfilUsuario.kt`: exibe favoritos ou pets cadastrados, conforme o perfil.
* `TelaAjudePet.kt`: lista e cadastra instituições de apoio.
* `TelaMapaPet.kt`: consulta a API de localização e abre o mapa.
* `NotificationHelper.kt`: centraliza a criação e exibição de notificações locais.

## Observações técnicas

O projeto utiliza estados em memória para controlar usuários, favoritos, pets cadastrados e instituições durante a execução do aplicativo.

Como evolução futura, a persistência permanente pode ser implementada com Room ou DataStore, permitindo manter os dados salvos mesmo após o fechamento do app.

## Melhorias futuras

* Persistência local com Room
* Salvamento de preferências com DataStore
* Autenticação real
* Upload de imagens pela galeria
* Integração com banco de dados remoto
* Publicação na Play Store

## Autor

André Vinícius Branches Cunha
