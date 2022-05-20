package com.example.appser.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.*
import com.example.appser.data.resource.*
import com.example.appser.databinding.FragmentHomeBinding
import com.example.appser.databinding.FragmentRegisterBinding
import com.example.appser.presentation.*
import com.example.appser.repository.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class homeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var appDatabase: AppDatabase
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var job: Job

    val viewModelRol by viewModels<RolViewModel> {
        RolViewModelFactory(
            RolRepositoryImpl(
                RolDataSource(
                    appDatabase.rolDao()
                )
            )
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Home Fragment", "OnCreate")

        lifecycleScope.launch {
            val success = withContext(Dispatchers.IO){
                if(validarCargaBd()){
                    cargarRoles()
                    cargarCiclos()
                    cargarCategorias()
                    cargarEmociones()
                    cargarActividades()
                    cargarPreguntas()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Home Fragment", "onStart")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getDatabase(requireContext())
        Log.d("Home Fragment", "onViewCreated")

        binding = FragmentHomeBinding.bind(view)

        val btnLogin = binding.btnLogin
        val btnRegistrarse = binding.txtRegistrarse
        val btnLista = binding.txtLista

        btnLogin.setOnClickListener {
            login()
        }
        btnRegistrarse.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment2)
        }
        btnLista.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerListFragment2)
        }
    }

    fun login() {
        val viewModel by viewModels<UsuarioViewModel> {
            UsuarioViewModelFactory(
                UsuarioRepositoryImpl(
                    UsuarioDataSource(
                        appDatabase.usuarioDao()
                    )
                )
            )
        }
        val email = binding.txtUsuario.text
        if (email.isNotEmpty()) {
            viewModel.fetchUsuarioByEmail(email.toString())
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Toast.makeText(requireContext(), "Consultando..", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Success -> {
                            if (result.data != null) {
                                mainViewModel.setPersonaAndUsuario(result.data)
                                findNavController().navigate(R.id.action_homeFragment_to_dashboardFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "El correo no se encuentra en el sistema..",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                })

        } else {
            Toast.makeText(
                requireContext(),
                "Por favor Colocar el correo electronico",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    fun cargarPreguntas() {
        val viewModel by viewModels<PreguntasViewModel> {
            PreguntasViewModelFactory(
                PreguntasRepositoryImpl(
                    PreguntasDataSource(
                        appDatabase.preguntasDao()
                    )
                )
            )
        }

        val preguntas = listOf(
            PreguntasEntity(
                0,
                "Siento un nudo en la  garganta o presión en el pecho.",
                1,
                1,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(
                0,
                "Siento un nudo en la  garganta o presión en el pecho.",
                1,
                2,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(
                0,
                "Me duele la cabeza constantemente.",
                1,
                3,
                1,
                "SAdmin",
                "2022-05-03"
            ),

            PreguntasEntity(
                0,
                "Me siento cansado físicamente a pesar de  haber dormido bien / me cuesta mucho moverme",
                1,
                1,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(
                0,
                "Me duele la cabeza constantemente.",
                1,
                2,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(0, "Aprieto los dientes o las manos.", 1, 3, 1, "SAdmin", "2022-05-03"),
            PreguntasEntity(
                0,
                "Siento mucha energía en todo mi cuerpo",
                1,
                4,
                1,
                "SAdmin",
                "2022-05-03"
            ),


            PreguntasEntity(0, "Pienso que antes todo era mejor", 2, 1, 1, "SAdmin", "2022-05-03"),
            PreguntasEntity(
                0,
                "Creo que no seré capaz de hacer algo.",
                2,
                2,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(
                0,
                "Pienso que lo que me está pasando es injusto.",
                2,
                3,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(0, "Creo que todo estará bien.", 2, 4, 1, "SAdmin", "2022-05-03"),


            PreguntasEntity(0, "Siento que me hace falta algo.", 3, 1, 1, "SAdmin", "2022-05-03"),
            PreguntasEntity(0, "Me siento pequeño o frágil.", 3, 2, 1, "SAdmin", "2022-05-03"),
            PreguntasEntity(
                0,
                "Siento que lo que estoy viviendo no debería pasar.",
                3,
                3,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(
                0,
                "Quisiera que todos sintieran lo que yo estoy sintiendo en este momento.",
                3,
                4,
                1,
                "SAdmin",
                "2022-05-03"
            ),

            PreguntasEntity(0, "Quiero estar solo.", 3, 1, 1, "SAdmin", "2022-05-03"),
            PreguntasEntity(0, "Quiero estar solo.", 3, 2, 1, "SAdmin", "2022-05-03"),


            PreguntasEntity(
                0,
                "Quisiera que alguien viniera a decirme que todo estará bien.",
                4,
                1,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(0, "Quiero huir/esconderme.", 4, 2, 1, "SAdmin", "2022-05-03"),
            PreguntasEntity(
                0,
                "Quiero que alguien pague por algo que me ha hecho.",
                4,
                3,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            PreguntasEntity(
                0,
                "Quiero salir y compartir con las personas de mi vida.",
                4,
                4,
                1,
                "SAdmin",
                "2022-05-03"
            ),
        )

        preguntas.forEach {
            viewModel.fetchSavePregunta(it).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Save Pregunta exitoso..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Failure -> {
                        Log.d("Error LiveData", "${result.exception}")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }
    }

    fun cargarActividades() {
        val viewModel by viewModels<ActividadesViewModel> {
            ActividadesViewModelFactory(
                ActividadesRepositoryImpl(
                    ActividadesDataSource(
                        appDatabase.actividadesDao()
                    )
                )
            )
        }

        val actividades = listOf(

            ActividadesEntity(
                0, "Caras Dibujadas",
                " Los padres o acudientes dibujan caras que reflejen emociones como la tristeza y alegría (Pueden guiarse de las que ofrece SER). La idea es mostrar las caras al niño/a. Luego, los padres simularán, actuarán y gesticularán las emociones mencionadas y cada uno le pondrá en el rostro al otro la carita pintada correspondiente a la emoción. Esto pueden repetirlo un par de veces y expresar verbalmente cómo se sienten. Luego será el turno del/a niño/a. Se le entregan las caras y este deberá asignar la cara triste cuando el acudiente que refleje tristeza y la cara feliz al acudiente refleje alegría.",
                "", 1, 1, 1, "SAdmin", "2022-05-03"
            ),

            ActividadesEntity(
                0, "Caja Misteriosa",
                " adaptación de una caja de cartón como depósito de objetos que el/a niño/a desconozca. La caja puede pintarse de negro por dentro y por fuera para dar la sensación de oscuridad y hacerle un agujero en el frente en donde el niño puede meter su mano con cuidado y solo usando su sentido del tacto seleccionar y sacar el objeto desconocido. Los padres pudieran simular que ellos mismos sienten un poco de miedo de meter la mano en la caja para ver qué hay, pero animar al/a niño/a que se atreva a sacar algo de la caja. En ocasiones el/la acudiente podría sacar primero algo de la caja para que el/la niño/a se sienta más en confianza de meter la mano en la caja",
                "", 2, 1, 1, "SAdmin", "2022-05-03"
            ),

            ActividadesEntity(
                0, "Frecuencias",
                " el/la ñiño/a tenga que realizar una acción (ej: aplaudir, emitir un sonido, reír, toca un botón, etc) en un determinado ritmo o frecuencia que proporcionará la aplicación SER. Esta frecuencia corresponderá con la frecuencia cardiaca de una persona en reposo que puede ir de los 50 a 70 latidos por minuto.",
                "", 3, 1, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "¡Hagamos Plastilina!",
                " elaboración de plastilina casera con materiales de fácil acceso (500g gr de harina de trigo, una taza de aceite comestible, una taza de agua, y colorantes comestibles). Solo bastará mezclar los primeros tres ingredientes y dividir porciones, dependiendo del número de colores que se consigan. Luego agregar los colorantes. Cuando tengamos masas similares a la plastilina se pueden hacer diferentes figuras. La idea es que niños/as y acudientes participen en el proceso de amasado de las plastilinas, interactúen con las diferentes texturas y resultados de la experiencia.",
                "", 4, 1, 1, "SAdmin", "2022-05-03"
            ),


            ActividadesEntity(
                0, "Diccionario de emociones",
                " En familia  se colorean o se buscan imagenes  (se imprimen o se recortan de revistas) de personas que expresen tristeza. Una ves identifcadas las imagenes, se dialoga sobre cuando se siente la emocion de tristeza, en que situaciones y como se expresa la emocion de trsiteza. Los padres incluyen situaciones donde se da el analisis y se compara con otra emoción. ",
                "", 1, 2, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Compartir en familia",
                "Al estar el niño triste, como padres compartir una ocasiones cuando se sintio triste. Qué paso? cuando paso? que hizo? se quedo triste siempre",
                "", 1, 2, 1, "SAdmin", "2022-05-03"
            ),

            ActividadesEntity(
                0, "Caja de miedos",
                " Con ayuda de los padres construir una caja donde se depositen dibujos alusivos a los miedos de los niños. En familia, los integrantes dibujan sus miedos y explican el contenido de ello. Al final todos depositan los dibujos en la caja, es importante decorar la caja y buscar accesorio de seguridad; para que  se simbolice el echo que los miedos estan asegurados en esa caja. Si el niño vuelve a tener la sensacion de miedo. Se consulta a la caja identificando que los miedos se escaparon y hay que buscar una forma de proteger y asegurar mas la caja. Padres e hijos buscan un seguro más eficaz.",
                "", 2, 2, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Vamos a casar un oso",
                " Leer el cuento \"Vamos a cazar un oso\" (Michael Rosen y Helen oxenbury) ",
                "", 2, 2, 1, "SAdmin", "2022-05-03"
            ),

            ActividadesEntity(
                0, "Técnica de la Tortuga",
                " Leer el cuento de la tortuga tucker y llevar estos pasos con el infante:\n" +
                        "– Primera Etapa: Reconocer sus emociones. \n" +
                        "– Segunda Etapa: Pensar y “Parar”\n" +
                        "– Tercera Etapa: Meterse adentro de su caparazón y tomar tres profundas respiraciones para calmarse\n" +
                        "– Cuarto Etapa: Sacar{+}se de su caparazón y pensar en una solucion.  Enseña a los niños pasos para como controlar sus emociones y calmarse ( pensar como una tortuga)",
                "http://earlyliteracylearning.org/TACSEI_CELL/project_files/content/level_3/pdf/3_10b_TuckerTurtleStorySpanPP.pdf",
                3, 2, 1, "SAdmin", "2022-05-03"
            ),

            ActividadesEntity(
                0, "Veamos un video",
                " en familia ver el video y preguntar como se siente Teo, luego de las respuestas del video; pensar ¿cuándo me siento alegre?  cada integrante debe pensar una situación y contarla y asi compartir la experiencia. ",
                "https://www.youtube.com/watch?v=3wcc_oIxX10", 4, 2, 1, "SAdmin", "2022-05-03"
            ),


            ActividadesEntity(
                0, "Cuentos para usar",
                " Lagrimas bajo la cama (Ana Meilán), Ladron de Sonrisas (Susana Isern)",
                "", 1, 3, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "El bote de lagrimas",
                "en bote construido con una hoja de papel colocarlo en una valdesito con agua, asi mismo depositar  gotas de lagrimas de papel o cartulina las situaciones que nos generan tristeza, cuando este lleno el bote, debemos vaciar el bote para que las lagrimas no lo hundan, ayudados con el dialogo idear la forma de hablar de despedir la tristeza arrojando cada gota de lagrima de papel al agua. ",
                "", 1, 3, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "La pócima mágica de los miedos",
                " En familia tomar un recipiente o frasco  con tapa, el niño debera inventar una mezcla de ingredientes o materiales  y depositarlo en el tarro o recipiente, el  cual  al terminar y cerrar,  debera colocarla en un sitio especifico de su cuarto o de la casa. El  niño desde la fantasia identifica que la pocima mágica no permitira que se acerquen los miedos. . ",
                "", 2, 3, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "El semáforo",
                "  Rojo: Para, respira,  piensa antes de actuar. Amarillo:  Alerta, respira y  piensa en las  posibles consecuencias. Verde: Adelante con el buen comportamiento.",
                "", 3, 3, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Burbujas para la Ira",
                " El adulto estara atento cuando el niño este pasando por un momento de ira y este no logra calmarse, se le dara un burbujero el cual para funcionar necesita que el niño haga uso del aire de sus pulmones y asi logra respirar mas profundo y calmarse, luego promover el dialogo para analizar la situación.",
                "", 3, 3, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0,
                "Caja de Sonrisas",
                "En familia tomar una cajita de carton o plastico y decorarla. Esta se llamara la cajita de sonrisas en papeles de colores escribir cada integrante de la familia anecdotas, situaciones, recuerdos, chistes, que les cause risa, alegria, felicidad, etc.; en cada papelito al respaldo se dibujara una sonrisa o dos depende del efecto generado en la familia.",
                "",
                4,
                3,
                1,
                "SAdmin",
                "2022-05-03"
            ),
            ActividadesEntity(
                0,
                "Ruleta de la felicidad",
                "Con ayuda de los padres construir una ruleta con cartulina,  los integrantes de la familia identifican acciones o actividades que generen o expresen alegria (abrazar, dar un elogio, hacer ejercicio etc). estas seran las opciones de la ruleta. al finalizar jugar con la ruleta y el integrante de la familia debera realizar la acción o actividad que propueve la alegria. ",
                "",
                4,
                3,
                1,
                "SAdmin",
                "2022-05-03"
            ),


            ActividadesEntity(
                0, "Coloreando lo que siento (mándalas)",
                "Colorear es un ejercicio creativo que contribuye a la autoreflexión y calma. Se puede hacer de manera individual o coloreado a dos manos",
                "", 1, 4, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "AUTOVALORACIÓN Yo me reconozco por",
                "Llevar un diario de reconocimientos y al final del día escribir 5 cosas por las que te reconocer haber realizado",
                "", 1, 4, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Cosificación",
                "se trata de un ejercicio visual , donde se esculpen con materiales a libre elección el miedo de la persona, la idea es convertirlo en un objeto visible y manpulable; dotar de propiedades fisicas al miedo.",
                "", 2, 4, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Cambiar significados: Diccionario de palabras molestas",
                " En este ejercicio el joven debe identificar las palabras \"groseras\" y frases molestas que usa o que suelen usar las personas a su alrededor cuando se enojan . Colocarán a su lado su significado y con ayuda de los padres buscarán alternativas de palabras y frases asertivas para comunicar la molestia.",
                "", 3, 4, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Proyección: Mapa de sueños",
                " En un corcho o tablero , se seleccionan imágenes alusivas a los sueños , metas y propósitos de vida a cumplir , se debe pegar en un lugar visible del cuarto",
                "", 4, 4, 1, "SAdmin", "2022-05-03"
            ),


            ActividadesEntity(
                0, "Cuadros vivos collage",
                "  crear un cuadro con material y plantas muertas. Es un ejercicio que implica salir en busqueda de material a un lugar abierto o jardín; se puede realizar de manera grupal o individual, se trata de dar una segunda oportunidad a aquello que se cree inútil",
                "", 1, 5, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Meditación inversa",
                " es un ejercico de autorreflexión en donde e un espacio silencioso  y privado cerrarás los ojos intentando focalizarte en ltu propio cuerpo. identificaras todas las imágenes y pensamientos que se te vengan a la mente, los revisaras y aceptaras y luego uno a uno irás dejando la mente en blanco",
                "", 2, 5, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Cómo nos ven",
                "( ejercicio grupal- familiar) Cada persona tendra una silueta con su nombre , la idea es rotar entre el grupo las siluetas y que cada integrante escriba como ve a la otra persona ; al final se hace la retroalimentación siempre diciendo \"yo te veo....\" Evita usar las frases : tu eres, tu siempre , tu nunca. ",
                "", 3, 5, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Limpieza de espacios",
                "para mantener las relaciones afectivas sanas estás deben limpiarse tal cual se limpia la casa, en este ejercicio tendrás dos recipientes uno llamado \"lo que no me funciona \" y otro \"lo que sí me funciona \". En caso que tu rabia sea con una persona , deberán reunirse en un espacio privado y leerán en voz alta las cosas que no le funcionan en primer lugar, con el fin de facilitar el dialogo sobre los puntos contrarios , al final se leen las cosas que si les funcionan. Evita usar las frases : tu eres, tu siempre , tu nunca ",
                "", 3, 5, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Ruta de metas",
                "se elabora con camino donde hay un punto de inicio y un punto final de la meta a cumplir por ejemplo: \"graduarme de la médico\" . En el punto de inicio se responde a la pregunta ¿ dónde estoy ahora? se debe colocar el momento de vida en el que esta actualmente ( acción puntual desempeñada por ejemplo \"no incrito en la universidad)\", en la ruta intermedia se colocan las acciones coherentes a desempeñar para poder alcanzar la meta deseada. Es importante contar con la ayuda de los padres para identificar los recursos a necesitar en cada acción.",
                "", 4, 5, 1, "SAdmin", "2022-05-03"
            ),


            ActividadesEntity(
                0, "Escribir un cuento",
                " A traves de la expresión verbal se puede metaforizar la experiencia que ha producido la emoción o bien darle a la experiencia alguna forma de resolución distinta a la ocurrida. El sujeto puede simplemente narrar los hechos que le acontecieron y cómo los vivencia o bien, crear historias fantásticas, acorde con su estilo narrativo",
                "", 1, 6, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Escuchar un cuento",
                "Escuchar el cuento: el círculo de los 99, Jorge Bucay",
                "https://www.youtube.com/watch?v=Kx24nncU2ZE", 1, 6, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "La ruta",
                " esquematizar en un diagrama el origen del miedo, las posibles consecuencias del objeto u evento temido (positivas y negativas). Evaluar las áreas y la magnitud en las que percibe la amenaza.  Si quiere, dele a cada aspecto del esquema un color que le ayude a representar cómo se siente a ese respecto. Plantee las posibles herramientas que necesitará para enfrentar la amenaza. ¿no las tiene? ¡es posible que alguien cerca de usted si las tenga? ¿correría el riesgo de pedir ayuda? si no es así ¿es mas valioso para usted onservar una imagen frente a otros o enfrentar ese miedo?",
                "", 2, 6, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Mirarse al espejo",
                " Tome un espejo en el que pueda ver de cerca sus expresiones faciales e incluso corporales. Tómese el tiempo para ver cada parte de su rostro, lineas, colores, formas.  Dese el espacio para ver desde afuera lo que está ocurriendo con su cuerpo en ese momento. la expresión de la ira es un evento que puede llegar a tornarse complejo por la experiencia fisica que se vive; verlo desde afuera puede darle elementos para el autocontrol así como la posibilidad de tener un factor distractor, que no lo va a desconectar de la emoción pero si le va a permitir conocerse mas de cerca",
                "", 3, 6, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Organizar",
                "Puede ser una habitación desordenada, una caja de objetos varios o un juego de organización como un rompecabezas. Dar orden a los objetos materiales puede contribuir a reorganizar las ideas y dar lugar tanto a la emoción como a los eventos desencadenantes de la misma",
                "", 3, 6, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Bailar",
                "Bailar musica acorde a la emoción del o los participantes, bailar, puede ser de manera libre o siguiendo una coreografía.  Utilizar el cuerpo como medio de expresión artistica que permita la manifestación de la emocion y que termina por generar mayor sensación de bienestar.  el baile puede ser tambien utilizado como canal de las relaciones sociales y ayuda a fortalecer este tipo de vínculos",
                "", 4, 6, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Karaoke",
                "Canciones nuevas o conocidas, explorar nuevos idiomas o ritmos poco acostumbrados",
                "", 4, 6, 1, "SAdmin", "2022-05-03"
            ),


            ActividadesEntity(
                0, "Caminata con un propósito",
                "Preferiblemente en un espacio que permita reconectar con la naturaleza. Los sentimientos de tristeza pueden aparecer por una situación particular o sin ninguna razon aparente. En ambos casos es necesario darle el espacio y la atención a cada pensamiento o sensación fisica o emocional que tenga lugar. Por ello, una caminata que tenga un fin básico, como hallar 5 tipos de flores, o 10 tipos de aves, permite a la vez que enfocarse en la tarea, darle al individuo el tiempo para estar solo con sus pensamientos y volcar la emoción sobre una actividad placentera que conecte la situación con el aquí y el ahora",
                "", 1, 7, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Origami",
                "Ejercer sobre el papel el poder del que se siente desprovisto el adulto, puede ayudarle a recobrar la sensación de que tiene la capacidad de transformar aquellos elementos sobre los que decida tomar decisiones, de modo que focalizar la energia que proviene de una situación de amenaza en otra que genere oportunidad de aprendizaje puede resultar no solo calmante y relajante, sino tambien un entrenamiento de habilidades manuales",
                "", 2, 7, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Respiración con la mano",
                "La actividad consiste en delinear la forma de la mano con los dedos abiertos. Cada  ascenso desde el abajo hasta la punta del dedo es un inspiración, desde la punta del dedo hacia la primer falange es una exhalación. Repetir cuantas veces sea necesario hasta disminuir el conteo de lpm",
                "", 3, 7, 1, "SAdmin", "2022-05-03"
            ),
            ActividadesEntity(
                0, "Bolitas de colores",
                "amazando pedaciotos de papeles de colores, crear cientos de bolitas de colores. Luego pegarlas en una cartulina. Se pueden crear paisajes, figuras o imágenes abstractas llenas de colores.  Los paricipantes reparten su producto final con un compañero, que debe tratar de identificar las emociones que el otro plasmó en su imagen\n" +
                        "ejercicio para fortalecer los vínculos con la familia o los pares,  que utiliza la empatía como forma de conexión social",
                "", 4, 7, 1, "SAdmin", "2022-05-03"
            ),
        )

        actividades.forEach {
            viewModel.fetchSaveActividad(it).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Save Actividad exitoso..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Failure -> {
                        Log.d("Error LiveData", "${result.exception}")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            })
        }
    }

    fun cargarEmociones() {
        val viewModel by viewModels<EmocionesViewModel> {
            EmocionesViewModelFactory(
                EmocionesRepositoryImpl(
                    EmocionesDataSource(
                        appDatabase.emocionesDao()
                    )
                )
            )
        }

        val emociones = listOf(
            EmocionesEntity(0, "Tristeza", 1, "SAdmin", "2022-05-03"),
            EmocionesEntity(0, "Miedo", 1, "SAdmin", "2022-05-03"),
            EmocionesEntity(0, "Ira", 1, "SAdmin", "2022-05-03"),
            EmocionesEntity(0, "Alegria", 1, "SAdmin", "2022-05-03")
        )

        emociones.forEach {
            Log.d("Emociones", "emocion->${it}")
            viewModel.fetchSaveEmocion(it).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Save Emociones exitoso..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Failure -> {
                        Log.d("Error LiveData", "${result.exception}")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            })
        }
    }

    fun cargarCategorias() {
        val viewModel by viewModels<CategoriasViewModel> {
            CategoriasViewModelFactory(
                CategoriasRepositoryImpl(
                    CategoriasDataSource(
                        appDatabase.categoriasDao()
                    )
                )
            )
        }

        val categorias = listOf(
            CategoriasEntity(0, "Fisico/Corporal", 1, "SAdmin", "2022-05-03"),
            CategoriasEntity(0, "Cognitivo", 1, "SAdmin", "2022-05-03"),
            CategoriasEntity(0, "Emocional", 1, "SAdmin", "2022-05-03"),
            CategoriasEntity(0, "Social", 1, "SAdmin", "2022-05-03"),

            )

        categorias.forEach {
            viewModel.fetchSaveCategoria(it).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Save CicloVital exitoso..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Failure -> {
                        Log.d("Error LiveData", "${result.exception}")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }
    }

    fun cargarCiclos() {
        val viewModel by viewModels<CicloVitalViewModel> {
            CicloVitalViewModelFactory(
                CicloVitalRepositoryImpl(
                    CicloVitalDataResource(
                        appDatabase.ciclovitalDao()
                    )
                )
            )
        }

        val ciclosVital = listOf(
            CicloVitalEntity(0, "2-3 AÑOS", 2, 3, 1, "SAdmin", "2022-05-03"),
            CicloVitalEntity(0, "Infancia 4-6", 4, 6, 1, "SAdmin", "2022-05-03"),
            CicloVitalEntity(0, "T. Infancia", 7, 12, 1, "SAdmin", "2022-05-03"),
            CicloVitalEntity(0, "Adolescencia", 13, 19, 1, "SAdmin", "2022-05-03"),
            CicloVitalEntity(0, "Adultez Temprana", 20, 39, 1, "SAdmin", "2022-05-03"),
            CicloVitalEntity(0, "Adultez Media", 40, 59, 1, "SAdmin", "2022-05-03"),
            CicloVitalEntity(0, "Adultez Tardía", 60, 140, 1, "SAdmin", "2022-05-03"),

            )

        ciclosVital.forEach {
            viewModel.fetchSaveCicloVital(it).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Save CicloVital exitoso..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Failure -> {
                        Log.d("Error LiveData", "${result.exception}")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }
    }

     fun validarCargaBd(): Boolean {
        var cargaBd: Boolean = false
        viewModelRol.fetchCountRol().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {

                    cargaBd = (result.data > 0)
                    Log.d("validarCargaBd", "result.data :${result.data} / ")
                    Log.d("validarCargaBd", "validar Carga  Bd:${cargaBd} / ")
                }
                is Resource.Failure -> {
                    Log.d("Error LiveData", "${result.exception}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })
        Log.d("validarCargaBd", "validarCargaBd:${cargaBd} / ")
        return cargaBd
    }

    fun cargarRoles() {


        val roles = listOf(
            RolEntity(0, "Admin", 1, "SAdmin", "2022-04-28"),
            RolEntity(0, "Usuario", 1, "SAdmin", "2022-04-28")
        )


        roles.forEach {
            viewModelRol.fetchSaveRol(it).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Save Rol exitoso..", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Resource.Failure -> {
                        Log.d("Error LiveData", "${result.exception}")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }
    }

}