import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionario.model.database.dao.PerguntaDao
import kotlinx.coroutines.launch
import com.example.questionario.model.entity.Pergunta
import kotlinx.coroutines.Dispatchers


class PerguntaViewModel(private val dao: PerguntaDao) : ViewModel() {

    var listaPerguntas = mutableStateOf(listOf<Pergunta>())
        private set

    private fun carregarPerguntas() {
        viewModelScope.launch {
            listaPerguntas.value = dao.buscarTodos()
        }
    }

    init {
        carregarPerguntas()
    }

    fun adicionarPergunta(
        enunciado: String, respostaCorreta: String, respostaIncorreta1: String,
        respostaIncorreta2: String, respostaIncorreta3: String, categoria: String
    ): String {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                dao.inserirPergunta(
                    Pergunta(
                        enunciado = enunciado,
                        respostaCorreta = respostaCorreta,
                        respostaIncorreta1 = respostaIncorreta1,
                        respostaIncorreta2 = respostaIncorreta2,
                        respostaIncorreta3 = respostaIncorreta3,
                        categoria = categoria
                    )
                )
            }
            "Pergunta salva com sucesso!"
        } catch (e: Exception) {
            Log.e("ViewModel", "Erro ao salvar pergunta: ${e.message}", e)
            "Erro ao salvar pergunta"
        }
    }

}
