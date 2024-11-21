import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalhoquiz.model.database.dao.PerguntaDao
import kotlinx.coroutines.launch


class PerguntaViewModel(private val perguntaDao: PerguntaDao) : ViewModel() {

    var listaPerguntas = mutableStateOf(listOf<Pergunta>())
        private set

    private fun carregarPerguntas() {
        viewModelScope.launch {
            listaPerguntas.value = perguntaDao.buscarTodos()
        }
    }

    fun adicionarPergunta(enunciado: String, respostaCorreta: String,
                          respostaIncorreta1: String, respostaIncorreta2: String,
                          respostaIncorreta3: String, categoria: String) : String {


        val pergunta = Pergunta(
            enunciado = enunciado,
            respostaCorreta = respostaCorreta,
            respostaIncorreta1 = respostaIncorreta1,
            respostaIncorreta2 = respostaIncorreta2,
            respostaIncorreta3 = respostaIncorreta3,
            categoria = categoria
        )

        viewModelScope.launch {
            perguntaDao.inserirPergunta(pergunta)
            carregarPerguntas()
        }
        return "Salvo com sucesso"
    }
}
