[[_TOC_]]

---
# Introdução: 
Sistema de compartilhamento de recursos dos rebeldes.

---
# GitLab
[star-wars-resistence-social-network](https://github.com/brunoikeda/star-wars-resistence-social-network)

---
# Endpoints
## /rebelde
- Todos os endpoints possuem a mascara /rebelde. Ex: http://localhost:8080/rebelde/list

### GET /list
- Lista todos os rebeldes

### GET /{id}
- Busca um rebelde por ID

### POST /novo
- Cria um novo rebelde.
- Seguir o json de exemplo abaixo:
  
``` json
{
    "nome": "rebelde 1",
    "idade": 10,
    "genero": "masculino",
    "localizacao":{
        "latitude": 123456789,
        "longitude": 987654321,
        "nome": "casa"
    },
    "recursos": [
        {
            "nome": "Arma",
            "quantidade": 1
        },
        {
            "nome": "Municao",
            "quantidade": 2
        },
        {
            "nome": "Agua",
            "quantidade": 3
        },
        {
            "nome": "Comida",
            "quantidade": 4
        }
    ]
}

```

### POST /atualiza/localizacao
- Atualiza a localizacao do rebelde
- Recuperar o id pela operacao /list ou /{id}
- Seguir o json de exemplo abaixo:

```json
{
    "id": 1,
    "localizacao":{
        "latitude": 999999999,
        "longitude": 999999999,
        "nome": "trabalho"
    }
}

```

### GET /reportar/traidor/{idRebelde}
- Reporta um traidor pelo id
- Recuperar o id pela operacao /list ou /{id}

### POST /atualiza/traidor
- Atualiza um rebelde para traidor ou um traidor para rebelde novamente
- Recuperar o id pela operacao /list ou /{id}
- Seguir o json abaixo:

```json
{
    "id": 1,
    "traidor": true
}

```

### POST /negocia/item
- Realiza a troca de itens seguindo as especificações enviadas.
- Recuperar os ids dos rebeldes pela operacao /list ou /{id}
- Seguir o json abaixo:

```json
{
    "rebelde1" : {
        "id": 1,
        "recursos": [
            {
                "nome": "Arma",
                "quantidade": 1
            },
            {
                "nome": "Municao",
                "quantidade": 1
            },
            {
                "nome": "Agua",
                "quantidade": 1
            },
            {
                "nome": "Comida",
                "quantidade": 1
            }
        ]
    },
    "rebelde2":{
        "id": 2,
        "recursos": [
            {
                "nome": "Arma",
                "quantidade": 1
            },
            {
                "nome": "Municao",
                "quantidade": 1
            },
            {
                "nome": "Agua",
                "quantidade": 1
            },
            {
                "nome": "Comida",
                "quantidade": 1
            }
        ]
    }
}

```

## /relatorio
- Todos os endpoints possuem o prefixo /relatorio. Ex: http://localhost:8080/relatorio/list

### GET /porcentagem/traidores
- Retorna a porcentagem de rebeldes traidores

### GET /porcentagem/rebeldes
- Retorna a pocentagem de rebeldes

### GET /quantidade/media/recursos
- Retorna a quantidade media de recursos dentre todos os rebeldes

### GET /pontos/perdidos
- Retorna a quantidade de pontos perdidos por traidores

### GET /completo
- Retorna todos os outros relatorios