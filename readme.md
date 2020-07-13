## Usuários

**GET /usuarios**

Exibe lista de usuários cadastrados.

**Response 200**

```json
[
    {
        "id": 0,
        "nome": "Joao Silva",
        "cpf": "186.186.440-05",
        "email": "joao.silva@gmail.com",
        "dataCadastro": "2020-07-10"
    },
    {
        "id": 1,
        "nome": "Maria Pereira",
        "cpf": "050.345.780-93",
        "email": "maria@teste.com",
        "dataCadastro": "2020-05-20"
    },
    {
        "id": 2,
        "nome": "Ana Souza",
        "cpf": "642.577.970-53",
        "email": "ana.souza@outlook.com.br",
        "dataCadastro": "2020-06-05"
    }
]
```
**GET /usuarios/{id}**

Exibe os dados de um usuário de acordo com id informado.

**Response 200**

```json
{
    "id": 1,
    "nome": "Maria Pereira",
    "cpf": "050.345.780-93",
    "email": "maria@teste.com",
    "dataCadastro": "2020-05-20"
}
```

**POST /usuarios**

Cadastra um novo usuário.

**Request body**

```json
{
    "nome": "Ana Souza",
    "cpf": "642.577.970-53",
    "email": "ana.souza@outlook.com.br",
    "dataCadastro": "2020-06-05"

}
```
**Response 201**

```json
{
    "id": 2,
    "nome": "Ana Souza",
    "cpf": "642.577.970-53",
    "email": "ana.souza@outlook.com.br",
    "dataCadastro": "2020-06-05"
}
```

**PATCH /usuarios/{id}**

Edita dados de um usuário (id e data de cadastro não são editáveis)

**Request body**

```json
{
    "nome":"Ana Souza Lopes",
    "cpf": "961.050.430-23",
    "email": "ana.souza@gmail.com"
}
```

**Response 200**

```json
{
    "id": 2,
    "nome": "Ana Souza Lopes",
    "cpf": "961.050.430-23",
    "email": "ana.souza@gmail.com",
    "dataCadastro": "2020-06-05"
}
```

## Batidas de Ponto

**GET /batidas-ponto/{idUsuario}**

Exibe lista de batidas de ponto e total de horas trabalhadas de um usuário de acordo com o id informado.

**Response 200**

```json
{
    "listagemPonto": [
        {
            "id": 1,
            "usuario": {
                "id": 0,
                "nome": "Joao Silva",
                "cpf": "186.186.440-05",
                "email": "joao.silva@gmail.com",
                "dataCadastro": "2020-07-10"
            },
            "dataHoraBatida": "2020-06-18T09:15:00",
            "tipoBatida": "ENTRADA"
        },
        {
            "id": 2,
            "usuario": {
                "id": 0,
                "nome": "Joao Silva",
                "cpf": "186.186.440-05",
                "email": "joao.silva@gmail.com",
                "dataCadastro": "2020-07-10"
            },
            "dataHoraBatida": "2020-06-18T18:37:00",
            "tipoBatida": "SAIDA"
        }
    ],
    "horasTrabalhadas": "9:22:00"
}
```

**POST /batidas-ponto/{idUsuario}**

Cadastro uma batida de ponto (seja entrada ou saída) para um usuário de
acordo com o id informado.

**Request body**

```json
{
    "dataHoraBatida":"2020-06-18T18:37",
    "tipoBatida":"saida"
}
```

**Response 201**

```json
{
    "id": 2,
    "usuario": {
        "id": 0,
        "nome": "Joao Silva",
        "cpf": "186.186.440-05",
        "email": "joao.silva@gmail.com",
        "dataCadastro": "2020-07-10"
    },
    "dataHoraBatida": "2020-06-18T18:37:00",
    "tipoBatida": "SAIDA"
}
```