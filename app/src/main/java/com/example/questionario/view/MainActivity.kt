package com.example.questionario.view

import com.example.questionario.viewmodel.PerguntaViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.example.questionario.model.AppDatabase
import com.example.questionario.viewmodel.CategoriaViewModel
import com.example.questionario.viewmodel.CategoriaViewModelFactory
import com.example.questionario.viewmodel.PerguntaViewModelFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(applicationContext).perguntaDao()
        val viewModelFactory = PerguntaViewModelFactory(dao) // Criação correta da fábrica
        val perguntaViewModel: PerguntaViewModel by viewModels { viewModelFactory } // Uso correto do delegate

        setContent {
            MainLayout(perguntaViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(perguntaViewModel: PerguntaViewModel) {

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
                    respostaId = 0,
                    categoriaId = 0
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
fun Jogolayout() {
    // Aguardando
}
