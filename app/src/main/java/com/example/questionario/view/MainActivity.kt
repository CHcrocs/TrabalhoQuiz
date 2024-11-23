package com.example.questionario.view

import PerguntaViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.questionario.model.AppDatabase
import com.example.questionario.model.entity.Pergunta
import com.example.questionario.viewmodel.PerguntaViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(applicationContext).perguntaDao()
        val viewModelFactory = PerguntaViewModelFactory(dao) // Criação correta da fábrica
        val perguntaViewModel: PerguntaViewModel by viewModels { viewModelFactory } // Uso correto do delegate

        setContent {
            //CriarPerguntaLayout(perguntaViewModel)
            Jogolayout(perguntaViewModel = perguntaViewModel, categoria = "Matematica")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarPerguntaLayout(perguntaViewModel: PerguntaViewModel) {

    var enunciado by remember { mutableStateOf("") }
    var respostaCorreta by remember { mutableStateOf("") }
    var respostaIncorreta1 by remember { mutableStateOf("") }
    var respostaIncorreta2 by remember { mutableStateOf("") }
    var respostaIncorreta3 by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }


    var listaPergunta by perguntaViewModel.listaPerguntas


    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(

    ) {
        TextField(
            value = enunciado,
            onValueChange = { enunciado = it },
            label = { Text(text = "Enunciado da Pergunta") }
        )
        TextField(
            value = respostaCorreta,
            onValueChange = { respostaCorreta = it },
            label = { Text(text = "Resposta Correta") }
        )
        TextField(
            value = respostaIncorreta1,
            onValueChange = { respostaIncorreta1 = it },
            label = { Text(text = "Resposta Incorreta 1") }
        )
        TextField(
            value = respostaIncorreta2,
            onValueChange = { respostaIncorreta2 = it },
            label = { Text(text = "Resposta Incorreta 2") }
        )
        TextField(
            value = respostaIncorreta3,
            onValueChange = { respostaIncorreta3 = it },
            label = { Text(text = "Resposta Incorreta 3") }
        )
        TextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text(text = "Categoria") }
        )
        Button(
            onClick = {
                val retorno = perguntaViewModel.adicionarPergunta(
                    enunciado,
                    respostaCorreta,
                    respostaIncorreta1,
                    respostaIncorreta2,
                    respostaIncorreta3,
                    categoria
                )
                Toast.makeText(context, retorno, Toast.LENGTH_LONG).show()

                // Limpar campos e foco
                enunciado = ""
                respostaCorreta = ""
                respostaIncorreta1 = ""
                respostaIncorreta2 = ""
                respostaIncorreta3 = ""
                categoria = ""
                focusManager.clearFocus()
            }
        ) {
            Text(text = "Salvar")
        }
    }
}

@Composable
fun Jogolayout(perguntaViewModel: PerguntaViewModel, categoria: String) {

    // Estado para controlar a pergunta atual
    var perguntaAtual by remember { mutableStateOf<Pergunta?>(null) }
    var respostasMisturadas by remember { mutableStateOf<List<String>>(emptyList()) }
    var perguntaRespondida by remember { mutableStateOf(false) }

    val contexto = LocalContext.current

    // Lista de perguntas filtradas pela categoria
    val perguntas = perguntaViewModel.listaPerguntas.value.filter { it.categoria == categoria }
    val perguntasDisponiveis = remember { perguntas.toMutableList() }

    // Função para obter uma pergunta aleatória
    fun carregarNovaPergunta() {
        if (perguntasDisponiveis.isNotEmpty()) {
            val novaPergunta = perguntasDisponiveis.random()
            perguntasDisponiveis.remove(novaPergunta)

            // Atualizar o estado com a nova pergunta e misturar as respostas
            perguntaAtual = novaPergunta
            respostasMisturadas = listOf(
                novaPergunta.respostaIncorreta1,
                novaPergunta.respostaIncorreta2,
                novaPergunta.respostaIncorreta3,
                novaPergunta.respostaCorreta
            ).shuffled()
            perguntaRespondida = false
        } else {
            // Exibir mensagem ao fim do quiz
            Toast.makeText(
                contexto,
                "Você respondeu todas as perguntas desta categoria!",
                Toast.LENGTH_LONG
            ).show()
            perguntaAtual = null
        }
    }

    // Carregar a primeira pergunta ao entrar na tela
    LaunchedEffect(Unit) {
        carregarNovaPergunta()
    }

    // Layout principal do quiz
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (perguntaAtual != null) {
            Text(
                text = perguntaAtual!!.enunciado,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Mostrar as respostas como botões
            respostasMisturadas.forEach { resposta ->
                Button(
                    onClick = {
                        perguntaRespondida = true
                        val mensagem = if (resposta == perguntaAtual!!.respostaCorreta) {
                            "Resposta correta!"
                        } else {
                            "Resposta incorreta!"
                        }
                        Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    enabled = !perguntaRespondida // Desabilitar os botões após uma resposta
                ) {
                    Text(text = resposta)
                }
            }

            // Botão para próxima pergunta
            if (perguntaRespondida) {
                Button(
                    onClick = { carregarNovaPergunta() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Próxima pergunta")
                }
            }
        } else {
            Text(
                text = "Parabéns! Você respondeu todas as perguntas!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
