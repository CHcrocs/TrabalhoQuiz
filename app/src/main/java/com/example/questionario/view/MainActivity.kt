package com.example.questionario.view

import PerguntaViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.questionario.model.AppDatabase
import com.example.questionario.model.entity.Pergunta
import com.example.questionario.viewmodel.PerguntaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(applicationContext).perguntaDao()
        val viewModelFactory = PerguntaViewModelFactory(dao)
        val perguntaViewModel: PerguntaViewModel by viewModels { viewModelFactory }

        setContent {
            AppNavigation(perguntaViewModel)
        }
    }
}

@Composable
fun AppNavigation(perguntaViewModel: PerguntaViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainLayout(navController)
        }

        composable("escolherTema") {
            EscolherTemaLayout(navController)
        }

        composable("criarPergunta") {
            CriarPerguntaLayout(perguntaViewModel, navController)
        }
        composable("jogar/{categoria}") { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria")
            if (categoria != null) {
                JogoLayout(perguntaViewModel, categoria, navController)
            } else {
                Toast.makeText(
                    LocalContext.current,
                    "Categoria inválida.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Composable
fun MainLayout(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo ao QuestionÃ¡rio!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { navController.navigate("criarPergunta") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Criar Pergunta")
        }

        Button(
            onClick = { navController.navigate("escolherTema") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Escolher Tema")
        }
    }
}

@Composable
fun EscolherTemaLayout(navController: NavController) {
    var categoria by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                categoria = "Matematica"
                navController.navigate("jogar/$categoria")
            },
        ) {
            Text(text = "Matematica")
        }
        Button(
            onClick = {
                categoria = "Esporte"
                navController.navigate("jogar/$categoria")
            },
        ) {
            Text(text = "Esporte")
        }
        Button(
            onClick = {
                categoria = "Historia"
                navController.navigate("jogar/$categoria")
            },
        ) {
            Text(text = "Historia")
        }
        Button(
            onClick = {
                categoria = "Outros"
                navController.navigate("jogar/$categoria")
            },
        ) {
            Text(text = "Outros")
        }
    }
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "Voltar")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarPerguntaLayout(perguntaViewModel: PerguntaViewModel, navController: NavController) {
    var enunciado by remember { mutableStateOf("") }
    var respostaCorreta by remember { mutableStateOf("") }
    var respostaIncorreta1 by remember { mutableStateOf("") }
    var respostaIncorreta2 by remember { mutableStateOf("") }
    var respostaIncorreta3 by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }

    val categorias = listOf("Matematica", "Esporte", "Historia", "Outros")
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

        ExposedDropdownMenuBox(
            expanded = menuExpanded,
            onExpandedChange = { menuExpanded = !menuExpanded }
        ) {
            TextField(
                value = categoriaSelecionada,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Categoria") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(text = categoria) },
                        onClick = {
                            categoriaSelecionada = categoria
                            menuExpanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (enunciado.isBlank() || respostaCorreta.isBlank() || respostaIncorreta1.isBlank() ||
                    respostaIncorreta2.isBlank() || respostaIncorreta3.isBlank() || categoriaSelecionada.isBlank()
                ) {
                    Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
                } else {
                    try {
                        perguntaViewModel.adicionarPergunta(
                            enunciado,
                            respostaCorreta,
                            respostaIncorreta1,
                            respostaIncorreta2,
                            respostaIncorreta3,
                            categoriaSelecionada
                        )
                        Toast.makeText(context, "Pergunta salva com sucesso!", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro ao salvar pergunta: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.e("CriarPerguntaLayout", "Erro ao salvar pergunta", e)
                    }
                    // Limpa os campos após salvar
                    enunciado = ""
                    respostaCorreta = ""
                    respostaIncorreta1 = ""
                    respostaIncorreta2 = ""
                    respostaIncorreta3 = ""
                    categoriaSelecionada = ""
                }
            }
        ) {
            Text(text = "Salvar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Voltar")
        }
    }
}

@Composable
fun JogoLayout(
    perguntaViewModel: PerguntaViewModel,
    categoria: String,
    navController: NavController
) {
    var perguntaAtual by remember { mutableStateOf<Pergunta?>(null) }
    var respostasMisturadas by remember { mutableStateOf<List<String>>(emptyList()) }
    var perguntaRespondida by remember { mutableStateOf(false) }

    val contexto = LocalContext.current

    // Carregar perguntas da categoria e criar estado para perguntas disponíveis
    val perguntas = perguntaViewModel.listaPerguntas.value.filter { it.categoria == categoria }
    var perguntasDisponiveis by remember { mutableStateOf(perguntas.toMutableList()) }

    LaunchedEffect(categoria) {
        if (perguntas.isEmpty()) {
            Log.d("JogoLayout", "Nenhuma pergunta encontrada para a categoria: $categoria")
            Toast.makeText(contexto, "Nenhuma pergunta disponível!", Toast.LENGTH_LONG).show()
            navController.popBackStack()
        } else {
            Log.d("JogoLayout", "Perguntas carregadas: ${perguntas.size}")
        }
    }

    fun carregarNovaPergunta() {
        if (perguntasDisponiveis.isNotEmpty()) {
            val novaPergunta = perguntasDisponiveis.random()
            perguntasDisponiveis = perguntasDisponiveis.filter { it != novaPergunta }.toMutableList()

            perguntaAtual = novaPergunta
            respostasMisturadas = listOf(
                novaPergunta.respostaIncorreta1,
                novaPergunta.respostaIncorreta2,
                novaPergunta.respostaIncorreta3,
                novaPergunta.respostaCorreta
            ).shuffled()
            perguntaRespondida = false
        } else {
            Toast.makeText(
                contexto,
                "Você respondeu todas as perguntas desta categoria!",
                Toast.LENGTH_LONG
            ).show()
            navController.popBackStack()
        }
    }

    LaunchedEffect(perguntas) {
        Log.d("JogoLayout", "Perguntas carregadas: ${perguntas.size}")
        if (perguntas.isNotEmpty()) {
            carregarNovaPergunta()
        } else {
            Toast.makeText(contexto, "Nenhuma pergunta disponível na categoria!", Toast.LENGTH_LONG)
                .show()
            navController.popBackStack()
        }
    }

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
                    enabled = !perguntaRespondida
                ) {
                    Text(text = resposta)
                }
            }
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
        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Voltar")
        }
    }
}