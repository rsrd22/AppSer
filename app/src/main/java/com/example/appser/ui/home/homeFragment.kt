package com.example.appser.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import androidx.navigation.findNavController

import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.*
import com.example.appser.data.preference.SerApplication.Companion.prefs
import com.example.appser.data.resource.*
import com.example.appser.databinding.FragmentHomeBinding
import com.example.appser.databinding.FragmentRegisterBinding
import com.example.appser.presentation.*
import com.example.appser.repository.*
import com.example.appser.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.coroutines.CoroutineContext


class homeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var appDatabase: AppDatabase
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var job: Job
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val currentdate = sdf.format(Date())

    val viewModelRol by viewModels<RolViewModel> {
        RolViewModelFactory(
            RolRepositoryImpl(
                RolDataSource(
                    appDatabase.rolDao()
                )
            )
        )
    }

    val viewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(
            UsuarioRepositoryImpl(
                UsuarioDataSource(
                    appDatabase.usuarioDao()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d("Home Fragment", "OnCreate")


    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Home Fragment", "onStart")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getDatabase(requireContext())
        Log.d("Home Fragment", "onViewCreated---${currentdate}")


        cargarCiclos()
        cargarCategorias()
        cargarEmociones()
        cargarActividades()
        cargarPreguntas()
        cargarRoles()


        binding = FragmentHomeBinding.bind(view)

        val btnLogin = binding.btnLogin
        val btnRegistrarse = binding.txtRegistrarse
//        val btnLista = binding.txtLista
        checkUserValues()

        btnLogin.setOnClickListener {
            login()
        }
        btnRegistrarse.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment2)
        }
//        btnLista.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_registerListFragment2)
//        }
    }

    fun checkUserValues(){
        if(prefs.getName().isNotEmpty() && prefs.getEmail().isNotEmpty()){
            viewModel.fetchUsuarioByEmail(prefs.getEmail().toString())
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Toast.makeText(requireContext(), "Consultando..", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Success -> {
                            mainViewModel.setPersonaAndUsuario(result.data)
                            findNavController().navigate(R.id.action_homeFragment_to_dashboardFragment)
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

    fun login() {

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
                            Log.d("Login", "ResourceSuccess")
                            if (result.data != null) {
                                Log.d("USUarioPersona", "User--${result.data}")
                                mainViewModel.setPersonaAndUsuario(result.data)
                                prefs.saveName(result.data.persona.nombre_completo.toString())
                                prefs.saveEmail(result.data.usuario.email)
                                prefs.saveIdUser(result.data.persona.id)
                                binding.txtUsuario.text.clear()



                                findNavController().navigate(R.id.action_homeFragment_to_dashboardFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "El correo no se encuentra en el sistema..",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
                1,
                "Siento un nudo en la  garganta o presión en el pecho.",
                1,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                2,
                "Siento un nudo en la  garganta o presión en el pecho.",
                1,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                3,
                "Me duele la cabeza constantemente.",
                1,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),

            PreguntasEntity(
                4,
                "Me siento cansado físicamente a pesar de  haber dormido bien / me cuesta mucho moverme",
                1,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                5,
                "Me duele la cabeza constantemente.",
                1,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                6,
                "Aprieto los dientes o las manos.",
                1,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                7,
                "Siento mucha energía en todo mi cuerpo",
                1,
                4,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            PreguntasEntity(
                8,
                "Pienso que antes todo era mejor",
                2,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                9,
                "Creo que no seré capaz de hacer algo.",
                2,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                10,
                "Pienso que lo que me está pasando es injusto.",
                2,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                11,
                "Creo que todo estará bien.",
                2,
                4,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            PreguntasEntity(
                12,
                "Siento que me hace falta algo.",
                3,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                13,
                "Me siento pequeño o frágil.",
                3,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                14,
                "Siento que lo que estoy viviendo no debería pasar.",
                3,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                15,
                "Quisiera que todos sintieran lo que yo estoy sintiendo en este momento.",
                3,
                4,
                1,
                "SAdmin",
                "${currentdate}"
            ),

            PreguntasEntity(
                16,
                "Quiero estar solo.",
                3,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                17,
                "Quiero estar solo.",
                3,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            PreguntasEntity(
                18,
                "Quisiera que alguien viniera a decirme que todo estará bien.",
                4,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                19,
                "Quiero huir/esconderme.",
                4,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                20,
                "Quiero que alguien pague por algo que me ha hecho.",
                4,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                21,
                "Quiero salir y compartir con las personas de mi vida.",
                4,
                4,
                1,
                "SAdmin",
                "${currentdate}"
            ),
        )

        for (pregunta in preguntas) {

            viewModel.fetchSavePregunta(pregunta).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        //Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Save Pregunta exitoso..",
//                            Toast.LENGTH_SHORT
//                        ).show()
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
                1, "Caras Dibujadas",
                "Los padres o acudientes dibujan caras que reflejen emociones como la tristeza y alegría (Pueden guiarse de las que ofrece SER). La idea es mostrar las caras al niño/a.\n" +
                        "Luego, los padres simularán, actuarán y gesticularán las emociones mencionadas y cada uno le pondrá en el rostro al otro la carita pintada correspondiente a la emoción. Esto pueden repetirlo un par de veces y expresar verbalmente cómo se sienten. \n" +
                        "Luego será el turno del/a niño/a. Se le entregan las caras y este deberá asignar la cara triste cuando el acudiente que refleje tristeza y la cara feliz al acudiente refleje alegría.\n",
                "", 1, 1, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                2, "Caja Misteriosa",
                "Se realizará la adaptación de una caja de cartón como depósito de objetos que el/a niño/a desconozca. \n" +
                        "La caja puede pintarse de negro por dentro y por fuera para dar la sensación de oscuridad y hacerle un agujero en el frente en donde el niño puede meter su mano con cuidado y solo usando su sentido del tacto seleccionar y sacar el objeto desconocido. \n" +
                        "Los padres pudieran simular que ellos mismos sienten un poco de miedo de meter la mano en la caja para ver qué hay, pero animar al/a niño/a que se atreva a sacar algo de la caja. En ocasiones el/la acudiente podría sacar primero algo de la caja para que el/la niño/a se sienta más en confianza de meter la mano en la caja",
                "", 2, 1, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                3, "Frecuencias",
                "El/la ñiño/a tiene que realizar una acción (ej: aplaudir, emitir un sonido, reír, toca un botón, etc) en un determinado ritmo o frecuencia que proporcionará la aplicación SER. Esta frecuencia corresponderá con la frecuencia cardiaca de una persona en reposo que puede ir de los 50 a 70 latidos por minuto.",
                "", 3, 1, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                4, "¡Hagamos Plastilina!",
                "Elaboración de plastilina casera con materiales de fácil acceso (500g gr de harina de trigo, una taza de aceite comestible, una taza de agua, y colorantes comestibles). \n" +
                        "Solo bastará mezclar los primeros tres ingredientes y dividir porciones, dependiendo del número de colores que se consigan. Luego agregar los colorantes. Cuando tengamos masas similares a la plastilina se pueden hacer diferentes figuras. La idea es que niños/as y acudientes participen en el proceso de amasado de las plastilinas, interactúen con las diferentes texturas y resultados de la experiencia.",
                "", 4, 1, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                5, "Diccionario de emociones",
                "En familia  se colorean o se buscan imagenes  (se imprimen o se recortan de revistas) de personas que expresen tristeza. Una ves identifcadas las imágenes, se dialoga sobre cuando se siente la emoción de tristeza, en que situaciones y como se expresa la emoción de trsiteza. Los padres incluyen situaciones donde se da el análisis y se compara con otra emoción. ",
                "", 1, 2, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                6, "Compartir en familia",
                "Al estar el niño triste, como padres compartir una ocasiones cuando se sintio triste. Qué pasó? cuando pasó? que hizo? se quedo triste siempre",
                "", 1, 2, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                7, "Caja de miedos",
                "Con ayuda de los padres, construir una caja donde se depositen dibujos alusivos a los miedos de los niños. \n" +
                        "En familia, los integrantes dibujan sus miedos y explican el contenido de ello. Al final todos depositan los dibujos en la caja, es importante decorar la caja y buscar accesorio de seguridad; para que  se simbolice el echo que los miedos estan asegurados en esa caja. Si el niño vuelve a tener la sensacion de miedo. Se consulta a la caja identificando que los miedos se escaparon y hay que buscar una forma de proteger y asegurar mas la caja. Padres e hijos buscan un seguro más eficaz.",
                "", 2, 2, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                8, "Vamos a casar un oso",
                "Leer el cuento \"Vamos a cazar un oso\" (Michael Rosen y Helen oxenbury) ",
                "", 2, 2, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                9, "Técnica de la Tortuga",
                "Leer el cuento de la tortuga tucker y llevar estos pasos con el infante:\n" +
                        "– Primera Etapa: Reconocer sus emociones. \n" +
                        "– Segunda Etapa: Pensar y “Parar”\n" +
                        "– Tercera Etapa: Meterse adentro de su caparazón y tomar tres profundas respiraciones para calmarse\n" +
                        "– Cuarto Etapa: Sacar{+}se de su caparazón y pensar en una solucion.  Enseña a los niños pasos para como controlar sus emociones y calmarse ( pensar como una tortuga)",
                "http://earlyliteracylearning.org/TACSEI_CELL/project_files/content/level_3/pdf/3_10b_TuckerTurtleStorySpanPP.pdf",
                3, 2, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                10,
                "Veamos un video",
                "En familia ver el video y preguntar como se siente Teo, luego de las respuestas del video; pensar ¿cuándo me siento alegre? \n" +
                        "Cada integrante debe pensar una situación y contarla y asi compartir la experiencia. ",
                "https://www.youtube.com/watch?v=3wcc_oIxX10",
                4,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            ActividadesEntity(
                11, "Cuentos para usar",
                "Leer alguno de los siguientes cuentos: lagrimas bajo la cama (Ana Meilán)\n" +
                        "Ladrón de Sonrisas (Susana Isern)",
                "", 1, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                12, "El bote de lagrimas",
                "Construir un bote con una hoja de papel, colocarlo en una valdesito con agua, asi mismo depositar gotas de lagrimas de papel o cartulina las situaciones que nos generan tristeza, cuando este lleno el bote, debemos vaciar el bote para que las lagrimas no lo hundan, ayudados con el dialogo idear la forma de hablar de despedir la tristeza arrojando cada gota de lagrima de papel al agua. ",
                "", 1, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                13, "La pócima mágica de los miedos",
                "En familia tomar un recipiente o frasco con tapa, el niño debera inventar una mezcla de ingredientes o materiales  y depositarlo en el tarro o recipiente, el  cual  al terminar y cerrar,  debera colocarla en un sitio especifico de su cuarto o de la casa. \n" +
                        "El  niño desde la fantasia identifica que la pocima mágica no permitira que se acerquen los miedos. . ",
                "", 2, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                14, "El semáforo",
                "Realizar un semáforo y asignar a los colores lo siguinte:\n" +
                        "Rojo: Para, respira,  piensa antes de actuar. \n" +
                        "Amarillo:  Alerta, respira y  piensa en las  posibles consecuencias. \n" +
                        "Verde: Adelante con el buen comportamiento.",
                "", 3, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                15, "Burbujas para la Ira",
                "El adulto estara atento cuando el niño este pasando por un momento de ira y este no logra calmarse, se le dara un burbujero el cual para funcionar necesita que el niño haga uso del aire de sus pulmones y asi logra respirar mas profundo y calmarse.\n" +
                        "Luego promover el dialogo para analizar la situación.",
                "", 3, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                16,
                "Caja de Sonrisas",
                "En familia tomar una cajita de carton o plastico y decorarla. Esta se llamara la cajita de sonrisas en papeles de colores escribir cada integrante de la familia anecdotas, situaciones, recuerdos, chistes, que les cause risa, alegria, felicidad, etc.; en cada papelito al respaldo se dibujara una sonrisa o dos depende del efecto generado en la familia.",
                "",
                4,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            ActividadesEntity(
                17,
                "Ruleta de la felicidad",
                "Con ayuda de los padres construir una ruleta con cartulina,  los integrantes de la familia identifican acciones o actividades que generen o expresen alegria (abrazar, dar un elogio, hacer ejercicio etc). Estas serán las opciones de la ruleta.\n" +
                        "Al finalizar jugar con la ruleta y el integrante de la familia debera realizar la acción o actividad que propueve la alegria.",
                "",
                4,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            ActividadesEntity(
                18, "Coloreando lo que siento (mándalas)",
                "Colorear es un ejercicio creativo que contribuye a la autoreflexión y calma. Se puede hacer de manera individual o coloreado a dos manos",
                "", 1, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                19, "AUTOVALORACIÓN. Yo me reconozco por",
                "Llevar un diario de reconocimientos y al final del día escribir 5 cosas por las que te reconocer haber realizado",
                "", 1, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                20, "Cosificación",
                "Se trata de un ejercicio visual, donde se esculpen con materiales a libre elección el miedo de la persona, la idea es convertirlo en un objeto visible y manpulable; dotar de propiedades fisicas al miedo.",
                "", 2, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                21, "Cambiar significados: Diccionario de palabras molestas",
                "En este ejercicio el joven debe identificar las palabras \"groseras\" y frases molestas que usa o que suelen usar las personas a su alrededor cuando se enojan . Colocarán a su lado su significado y con ayuda de los padres buscarán alternativas de palabras y frases asertivas para comunicar la molestia.",
                "", 3, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                22, "Proyección: Mapa de sueños",
                "En un corcho o tablero, se seleccionan imágenes alusivas a los sueños, metas y propósitos de vida a cumplir, se debe pegar en un lugar visible del cuarto",
                "", 4, 4, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                23, "Cuadros vivos collage",
                "Crear un cuadro con material y plantas muertas.\n " +
                        "Es un ejercicio que implica salir en búsqueda de material a un lugar abierto o jardín; se puede realizar de manera grupal o individual.\n" +
                        "Se trata de dar una segunda oportunidad a aquello que se cree inútil",
                "", 1, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                24, "Meditación inversa",
                "Este es un ejercicio de autorreflexión en donde en un espacio silencioso  y privado cerrarás los ojos intentando focalizarte en tu propio cuerpo.\n " +
                        "Identificaras todas las imágenes y pensamientos que se te vengan a la mente, los revisaras y aceptaras y luego uno a uno irás dejando la mente en blanco",
                "", 2, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                25, "Cómo nos ven",
                "Este es un ejercicio grupal - familiar.\n" +
                        "Cada persona tendra una silueta con su nombre, la idea es rotar entre el grupo las siluetas y que cada integrante escriba como ve a la otra persona; al final se hace la retroalimentación siempre diciendo \"yo te veo....\" \n" +
                        "Evita usar las frases: tu eres, tu siempre , tu nunca. ",
                "", 3, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                26, "Limpieza de espacios",
                "Para mantener las relaciones afectivas sanas estás deben limpiarse tal cual se limpia la casa.\n" +
                        "En este ejercicio tendrás dos recipientes uno llamado \"Lo que no me funciona \" y otro \"Lo que sí me funciona \". \n" +
                        "En caso que tu rabia sea con una persona, deberán reunirse en un espacio privado y leerán en voz alta las cosas que no le funcionan en primer lugar, con el fin de facilitar el dialogo sobre los puntos contrarios , al final se leen las cosas que si les funcionan.\n " +
                        "Evita usar las frases : tu eres, tu siempre , tu nunca ",
                "", 3, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                27, "Ruta de metas",
                "Se elabora un camino donde hay un punto de inicio y un punto final de la meta a cumplir, por ejemplo: \"Graduarme como médico\" . \n" +
                        "En el punto de inicio se responde a la pregunta ¿dónde estoy ahora? se debe colocar el momento de vida en el que esta actualmente (acción puntual desempeñada por ejemplo \"no incrito en la universidad)\". \n" +
                        "En la ruta intermedia se colocan las acciones coherentes a desempeñar para poder alcanzar la meta deseada. \n" +
                        "Es importante contar con la ayuda de los padres para identificar los recursos a necesitar en cada acción.",
                "", 4, 5, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                28, "Escribir un cuento",
                "A traves de la expresión verbal se puede metaforizar la experiencia que ha producido la emoción o bien darle a la experiencia alguna forma de resolución distinta a la ocurrida. \n" +
                        "El sujeto puede simplemente narrar los hechos que le acontecieron y cómo los vivencia o bien, crear historias fantásticas, acorde con su estilo narrativo",
                "", 1, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                29,
                "Escuchar un cuento",
                "Escuchar el cuento: el círculo de los 99, Jorge Bucay",
                "https://www.youtube.com/watch?v=Kx24nncU2ZE",
                1,
                6,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            ActividadesEntity(
                30, "La ruta",
                "Esquematizar en un diagrama el origen del miedo, las posibles consecuencias del objeto u evento temido (positivas y negativas). \n" +
                        "Evaluar las áreas y la magnitud en las que percibe la amenaza.  Si quiere, dele a cada aspecto del esquema un color que le ayude a representar cómo se siente a ese respecto.\n " +
                        "Plantee las posibles herramientas que necesitará para enfrentar la amenaza. ¿no las tiene? ¿es posible que alguien cerca de usted si las tenga? ¿correría el riesgo de pedir ayuda? si no es así ¿es mas valioso para usted onservar una imagen frente a otros o enfrentar ese miedo?",
                "", 2, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                31, "Mirarse al espejo",
                "Tome un espejo en el que pueda ver de cerca sus expresiones faciales e incluso corporales.\n " +
                        "Tómese el tiempo para ver cada parte de su rostro, lineas, colores, formas.  Dese el espacio para ver desde afuera lo que está ocurriendo con su cuerpo en ese momento.\n " +
                        "La expresión de la ira es un evento que puede llegar a tornarse complejo por la experiencia fisica que se vive; verlo desde afuera puede darle elementos para el autocontrol así como la posibilidad de tener un factor distractor, que no lo va a desconectar de la emoción pero si le va a permitir conocerse mas de cerca",
                "", 3, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                32, "Organizar",
                "Puede ser una habitación desordenada, una caja de objetos varios o un juego de organización como un rompecabezas.\n " +
                        "Dar orden a los objetos materiales puede contribuir a reorganizar las ideas y dar lugar tanto a la emoción como a los eventos desencadenantes de la misma",
                "", 3, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                33, "Bailar",
                "Bailar musica acorde a la emoción del o los participantes, bailar, puede ser de manera libre o siguiendo una coreografía.\n  " +
                        "Utilizar el cuerpo como medio de expresión artistica que permita la manifestación de la emoción y que termina por generar mayor sensación de bienestar.\n " +
                        "El baile puede ser tambien utilizado como canal de las relaciones sociales y ayuda a fortalecer este tipo de vínculos.",
                "", 4, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                34, "Karaoke",
                "Canciones nuevas o conocidas, explorar nuevos idiomas o ritmos poco acostumbrados",
                "", 4, 6, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                35, "Caminata con un propósito",
                "Preferiblemente en un espacio que permita reconectar con la naturaleza. \n" +
                        "Los sentimientos de tristeza pueden aparecer por una situación particular o sin ninguna razón aparente. En ambos casos es necesario darle el espacio y la atención a cada pensamiento o sensación fisica o emocional que tenga lugar.\n " +
                        "Por ello, una caminata que tenga un fin básico, como hallar 5 tipos de flores, o 10 tipos de aves, permite a la vez que enfocarse en la tarea, darle al individuo el tiempo para estar solo con sus pensamientos y volcar la emoción sobre una actividad placentera que conecte la situación con el aquí y el ahora",
                "", 1, 7, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                36, "Origami",
                "Ejercer sobre el papel el poder del que se siente desprovisto el adulto, puede ayudarle a recobrar la sensación de que tiene la capacidad de transformar aquellos elementos sobre los que decida tomar decisiones, de modo que focalizar la energia que proviene de una situación de amenaza en otra que genere oportunidad de aprendizaje puede resultar no solo calmante y relajante, sino tambien un entrenamiento de habilidades manuales",
                "", 2, 7, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                37, "Respiración con la mano",
                "La actividad consiste en delinear la forma de la mano con los dedos abiertos. \n" +
                        "Cada  ascenso desde el abajo hasta la punta del dedo es un inspiración.\n" +
                        "Desde la punta del dedo hacia la primer falange es una exhalación. \n" +
                        "Repetir cuantas veces sea necesario hasta disminuir el conteo de lpm",
                "", 3, 7, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                38, "Bolitas de colores",
                "Amazando pedaciotos de papeles de colores, crear cientos de bolitas de colores. Luego pegarlas en una cartulina. \n" +
                        "Se pueden crear paisajes, figuras o imágenes abstractas llenas de colores. \n " +
                        "Los participantes reparten su producto final con un compañero, que debe tratar de identificar las emociones que el otro plasmó en su imagen\n" +
                        "Este ejercicio sirve para fortalecer los vínculos con la familia o los pares, que utiliza la empatía como forma de conexión social",
                "", 4, 7, 1, "SAdmin", "${currentdate}"
            ),
        )

        for (actividad in actividades) {
            viewModel.fetchSaveActividad(actividad).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
//                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Save Actividad exitoso..",
//                            Toast.LENGTH_SHORT
//                        ).show()
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
            EmocionesEntity(
                1,
                "Tristeza",
                "Es una emoción que señala la necesidad de afiliación, siempre está extrañando algo, cuando estás triste quieres estar solo y sientes q tienes poca energía para hacer las cosas. En esos momentos es buscar apoyo en tu círculo cercano y hablar de ella.",
                1,
                "SAdmin",
                "${currentdate}"
            ),
            EmocionesEntity(
                2,
                "Miedo",
                "Siempre está huyendo de algo, cuando sientes miedo te sientes pequeñito y crees que no podras hacer lo que te propones. En esos momentos es bueno darnos un espacio para organizar nuestras ideas, hacer un plan. recuerda ir un paso a la vez y de lo más simple a lo más complejo.",
                1,
                "SAdmin",
                "${currentdate}"
            ),
            EmocionesEntity(
                3,
                "Ira",
                "Es una emoción que aparece cuando sientes que algo injusto ha pasado. Cuando sientes ira quieres que se te escuché y se resuelva la situación... En esos momentos es clave identificar qué te parece injusto  y qué quieres  para comunicarlo asertivamente . Muchas personas necesitan espacio para transitar su ira antes de hablarla.",
                1,
                "SAdmin",
                "${currentdate}"
            ),
            EmocionesEntity(
                4,
                "Alegria",
                "Es contagiosa, cuando te sientes alegre quieres compartirlo con todos.",
                1,
                "SAdmin",
                "${currentdate}"
            )
        )

        for (emocion in emociones) {
            Log.d("Emociones", "emocion->${emocion}")
            viewModel.fetchSaveEmocion(emocion).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
//                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Save Emociones exitoso..",
//                            Toast.LENGTH_SHORT
//                        ).show()
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
            CategoriasEntity(1, "Fisico/Corporal", 1, "SAdmin", "${currentdate}"),
            CategoriasEntity(2, "Cognitivo", 1, "SAdmin", "${currentdate}"),
            CategoriasEntity(3, "Emocional", 1, "SAdmin", "${currentdate}"),
            CategoriasEntity(4, "Social", 1, "SAdmin", "${currentdate}"),

            )

        for (categoria in categorias) {
            viewModel.fetchSaveCategoria(categoria).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
//                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Save CicloVital exitoso..",
//                            Toast.LENGTH_SHORT
//                        ).show()
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

        val ciclosVital = arrayOf(
            CicloVitalEntity(1, "2-3 AÑOS", 2, 3, 1, "SAdmin", "${currentdate}"),
            CicloVitalEntity(2, "Infancia 4-6", 4, 6, 1, "SAdmin", "${currentdate}"),
            CicloVitalEntity(3, "T. Infancia", 7, 12, 1, "SAdmin", "${currentdate}"),
            CicloVitalEntity(4, "Adolescencia", 13, 19, 1, "SAdmin", "${currentdate}"),
            CicloVitalEntity(
                5,
                "Adultez Temprana",
                20,
                39,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            CicloVitalEntity(
                6,
                "Adultez Media",
                40,
                59,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            CicloVitalEntity(
                7,
                "Adultez Tardía",
                60,
                140,
                1,
                "SAdmin",
                "${currentdate}"
            ),

            )
        for (pos in ciclosVital.indices) {
            Log.d("Ciclo pos", "posicion ${pos} ciclo ${ciclosVital.get(pos)}")
            viewModel.fetchSaveCicloVital(ciclosVital.get(pos))
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Resource.Loading -> {
//                            Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT)
//                                .show()
                        }
                        is Resource.Success -> {
//                            Toast.makeText(
//                                requireContext(),
//                                "Save CicloVital exitoso..",
//                                Toast.LENGTH_SHORT
//                            ).show()
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

//     fun validarCargaBd(): Boolean {
//        var cargaBd: Boolean = false
//        viewModelRol.fetchCountRol().observe(viewLifecycleOwner, Observer { result ->
//            when (result) {
//                is Resource.Loading -> {
//                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
//                }
//                is Resource.Success -> {
//
//                    cargaBd = (result.data > 0)
//                    Log.d("validarCargaBd", "result.data :${result.data} / ")
//                    Log.d("validarCargaBd", "validar Carga  Bd:${cargaBd} / ")
//                }
//                is Resource.Failure -> {
//                    Log.d("Error LiveData", "${result.exception}")
//                    Toast.makeText(
//                        requireContext(),
//                        "Error: ${result.exception}",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//            }
//        })
//        Log.d("validarCargaBd", "validarCargaBd:${cargaBd} / ")
//        return cargaBd
//    }

    fun cargarRoles() {


        val roles = listOf(
            RolEntity(1, "Admin", 1, "SAdmin", "${currentdate}"),
            RolEntity(2, "Usuario", 1, "SAdmin", "${currentdate}")
        )

        for (rol in roles) {
            viewModelRol.fetchSaveRol(rol).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
//                        Toast.makeText(requireContext(), "Save Rol exitoso..", Toast.LENGTH_SHORT)
//                            .show()
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