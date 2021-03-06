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
                            if (result.data != null) {
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
                "Siento un nudo en la  garganta o presi??n en el pecho.",
                1,
                1,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                2,
                "Siento un nudo en la  garganta o presi??n en el pecho.",
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
                "Me siento cansado f??sicamente a pesar de  haber dormido bien / me cuesta mucho moverme",
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
                "Siento mucha energ??a en todo mi cuerpo",
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
                "Creo que no ser?? capaz de hacer algo.",
                2,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                10,
                "Pienso que lo que me est?? pasando es injusto.",
                2,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                11,
                "Creo que todo estar?? bien.",
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
                "Me siento peque??o o fr??gil.",
                3,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),
            PreguntasEntity(
                14,
                "Siento que lo que estoy viviendo no deber??a pasar.",
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
                "Quisiera que alguien viniera a decirme que todo estar?? bien.",
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
                "Los padres o acudientes dibujan caras que reflejen emociones como la tristeza y alegr??a (Pueden guiarse de las que ofrece SER). La idea es mostrar las caras al ni??o/a.\n" +
                        "Luego, los padres simular??n, actuar??n y gesticular??n las emociones mencionadas y cada uno le pondr?? en el rostro al otro la carita pintada correspondiente a la emoci??n. Esto pueden repetirlo un par de veces y expresar verbalmente c??mo se sienten. \n" +
                        "Luego ser?? el turno del/a ni??o/a. Se le entregan las caras y este deber?? asignar la cara triste cuando el acudiente que refleje tristeza y la cara feliz al acudiente refleje alegr??a.\n",
                "", 1, 1, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                2, "Caja Misteriosa",
                "Se realizar?? la adaptaci??n de una caja de cart??n como dep??sito de objetos que el/a ni??o/a desconozca. \n" +
                        "La caja puede pintarse de negro por dentro y por fuera para dar la sensaci??n de oscuridad y hacerle un agujero en el frente en donde el ni??o puede meter su mano con cuidado y solo usando su sentido del tacto seleccionar y sacar el objeto desconocido. \n" +
                        "Los padres pudieran simular que ellos mismos sienten un poco de miedo de meter la mano en la caja para ver qu?? hay, pero animar al/a ni??o/a que se atreva a sacar algo de la caja. En ocasiones el/la acudiente podr??a sacar primero algo de la caja para que el/la ni??o/a se sienta m??s en confianza de meter la mano en la caja",
                "", 2, 1, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                3, "Frecuencias",
                "El/la ??i??o/a tiene que realizar una acci??n (ej: aplaudir, emitir un sonido, re??r, toca un bot??n, etc) en un determinado ritmo o frecuencia que proporcionar?? la aplicaci??n SER. Esta frecuencia corresponder?? con la frecuencia cardiaca de una persona en reposo que puede ir de los 50 a 70 latidos por minuto.",
                "", 3, 1, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                4, "??Hagamos Plastilina!",
                "Elaboraci??n de plastilina casera con materiales de f??cil acceso (500g gr de harina de trigo, una taza de aceite comestible, una taza de agua, y colorantes comestibles). \n" +
                        "Solo bastar?? mezclar los primeros tres ingredientes y dividir porciones, dependiendo del n??mero de colores que se consigan. Luego agregar los colorantes. Cuando tengamos masas similares a la plastilina se pueden hacer diferentes figuras. La idea es que ni??os/as y acudientes participen en el proceso de amasado de las plastilinas, interact??en con las diferentes texturas y resultados de la experiencia.",
                "", 4, 1, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                5, "Diccionario de emociones",
                "En familia  se colorean o se buscan imagenes  (se imprimen o se recortan de revistas) de personas que expresen tristeza. Una ves identifcadas las im??genes, se dialoga sobre cuando se siente la emoci??n de tristeza, en que situaciones y como se expresa la emoci??n de trsiteza. Los padres incluyen situaciones donde se da el an??lisis y se compara con otra emoci??n. ",
                "", 1, 2, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                6, "Compartir en familia",
                "Al estar el ni??o triste, como padres compartir una ocasiones cuando se sintio triste. Qu?? pas??? cuando pas??? que hizo? se quedo triste siempre",
                "", 1, 2, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                7, "Caja de miedos",
                "Con ayuda de los padres, construir una caja donde se depositen dibujos alusivos a los miedos de los ni??os. \n" +
                        "En familia, los integrantes dibujan sus miedos y explican el contenido de ello. Al final todos depositan los dibujos en la caja, es importante decorar la caja y buscar accesorio de seguridad; para que  se simbolice el echo que los miedos estan asegurados en esa caja. Si el ni??o vuelve a tener la sensacion de miedo. Se consulta a la caja identificando que los miedos se escaparon y hay que buscar una forma de proteger y asegurar mas la caja. Padres e hijos buscan un seguro m??s eficaz.",
                "", 2, 2, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                8, "Vamos a casar un oso",
                "Leer el cuento \"Vamos a cazar un oso\" (Michael Rosen y Helen oxenbury) ",
                "", 2, 2, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                9, "T??cnica de la Tortuga",
                "Leer el cuento de la tortuga tucker y llevar estos pasos con el infante:\n" +
                        "??? Primera Etapa: Reconocer sus emociones. \n" +
                        "??? Segunda Etapa: Pensar y ???Parar???\n" +
                        "??? Tercera Etapa: Meterse adentro de su caparaz??n y tomar tres profundas respiraciones para calmarse\n" +
                        "??? Cuarto Etapa: Sacar{+}se de su caparaz??n y pensar en una solucion.  Ense??a a los ni??os pasos para como controlar sus emociones y calmarse ( pensar como una tortuga)",
                "http://earlyliteracylearning.org/TACSEI_CELL/project_files/content/level_3/pdf/3_10b_TuckerTurtleStorySpanPP.pdf",
                3, 2, 1, "SAdmin", "${currentdate}"
            ),

            ActividadesEntity(
                10,
                "Veamos un video",
                "En familia ver el video y preguntar como se siente Teo, luego de las respuestas del video; pensar ??cu??ndo me siento alegre? \n" +
                        "Cada integrante debe pensar una situaci??n y contarla y asi compartir la experiencia. ",
                "https://www.youtube.com/watch?v=3wcc_oIxX10",
                4,
                2,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            ActividadesEntity(
                11, "Cuentos para usar",
                "Leer alguno de los siguientes cuentos: lagrimas bajo la cama (Ana Meil??n)\n" +
                        "Ladr??n de Sonrisas (Susana Isern)",
                "", 1, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                12, "El bote de lagrimas",
                "Construir un bote con una hoja de papel, colocarlo en una valdesito con agua, asi mismo depositar gotas de lagrimas de papel o cartulina las situaciones que nos generan tristeza, cuando este lleno el bote, debemos vaciar el bote para que las lagrimas no lo hundan, ayudados con el dialogo idear la forma de hablar de despedir la tristeza arrojando cada gota de lagrima de papel al agua. ",
                "", 1, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                13, "La p??cima m??gica de los miedos",
                "En familia tomar un recipiente o frasco con tapa, el ni??o debera inventar una mezcla de ingredientes o materiales  y depositarlo en el tarro o recipiente, el  cual  al terminar y cerrar,  debera colocarla en un sitio especifico de su cuarto o de la casa. \n" +
                        "El  ni??o desde la fantasia identifica que la pocima m??gica no permitira que se acerquen los miedos. . ",
                "", 2, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                14, "El sem??foro",
                "Realizar un sem??foro y asignar a los colores lo siguinte:\n" +
                        "Rojo: Para, respira,  piensa antes de actuar. \n" +
                        "Amarillo:  Alerta, respira y  piensa en las  posibles consecuencias. \n" +
                        "Verde: Adelante con el buen comportamiento.",
                "", 3, 3, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                15, "Burbujas para la Ira",
                "El adulto estara atento cuando el ni??o este pasando por un momento de ira y este no logra calmarse, se le dara un burbujero el cual para funcionar necesita que el ni??o haga uso del aire de sus pulmones y asi logra respirar mas profundo y calmarse.\n" +
                        "Luego promover el dialogo para analizar la situaci??n.",
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
                "Con ayuda de los padres construir una ruleta con cartulina,  los integrantes de la familia identifican acciones o actividades que generen o expresen alegria (abrazar, dar un elogio, hacer ejercicio etc). Estas ser??n las opciones de la ruleta.\n" +
                        "Al finalizar jugar con la ruleta y el integrante de la familia debera realizar la acci??n o actividad que propueve la alegria.",
                "",
                4,
                3,
                1,
                "SAdmin",
                "${currentdate}"
            ),


            ActividadesEntity(
                18, "Coloreando lo que siento (m??ndalas)",
                "Colorear es un ejercicio creativo que contribuye a la autoreflexi??n y calma. Se puede hacer de manera individual o coloreado a dos manos",
                "", 1, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                19, "AUTOVALORACI??N. Yo me reconozco por",
                "Llevar un diario de reconocimientos y al final del d??a escribir 5 cosas por las que te reconocer haber realizado",
                "", 1, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                20, "Cosificaci??n",
                "Se trata de un ejercicio visual, donde se esculpen con materiales a libre elecci??n el miedo de la persona, la idea es convertirlo en un objeto visible y manpulable; dotar de propiedades fisicas al miedo.",
                "", 2, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                21, "Cambiar significados: Diccionario de palabras molestas",
                "En este ejercicio el joven debe identificar las palabras \"groseras\" y frases molestas que usa o que suelen usar las personas a su alrededor cuando se enojan . Colocar??n a su lado su significado y con ayuda de los padres buscar??n alternativas de palabras y frases asertivas para comunicar la molestia.",
                "", 3, 4, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                22, "Proyecci??n: Mapa de sue??os",
                "En un corcho o tablero, se seleccionan im??genes alusivas a los sue??os, metas y prop??sitos de vida a cumplir, se debe pegar en un lugar visible del cuarto",
                "", 4, 4, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                23, "Cuadros vivos collage",
                "Crear un cuadro con material y plantas muertas.\n " +
                        "Es un ejercicio que implica salir en b??squeda de material a un lugar abierto o jard??n; se puede realizar de manera grupal o individual.\n" +
                        "Se trata de dar una segunda oportunidad a aquello que se cree in??til",
                "", 1, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                24, "Meditaci??n inversa",
                "Este es un ejercicio de autorreflexi??n en donde en un espacio silencioso  y privado cerrar??s los ojos intentando focalizarte en tu propio cuerpo.\n " +
                        "Identificaras todas las im??genes y pensamientos que se te vengan a la mente, los revisaras y aceptaras y luego uno a uno ir??s dejando la mente en blanco",
                "", 2, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                25, "C??mo nos ven",
                "Este es un ejercicio grupal - familiar.\n" +
                        "Cada persona tendra una silueta con su nombre, la idea es rotar entre el grupo las siluetas y que cada integrante escriba como ve a la otra persona; al final se hace la retroalimentaci??n siempre diciendo \"yo te veo....\" \n" +
                        "Evita usar las frases: tu eres, tu siempre , tu nunca. ",
                "", 3, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                26, "Limpieza de espacios",
                "Para mantener las relaciones afectivas sanas est??s deben limpiarse tal cual se limpia la casa.\n" +
                        "En este ejercicio tendr??s dos recipientes uno llamado \"Lo que no me funciona \" y otro \"Lo que s?? me funciona \". \n" +
                        "En caso que tu rabia sea con una persona, deber??n reunirse en un espacio privado y leer??n en voz alta las cosas que no le funcionan en primer lugar, con el fin de facilitar el dialogo sobre los puntos contrarios , al final se leen las cosas que si les funcionan.\n " +
                        "Evita usar las frases : tu eres, tu siempre , tu nunca ",
                "", 3, 5, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                27, "Ruta de metas",
                "Se elabora un camino donde hay un punto de inicio y un punto final de la meta a cumplir, por ejemplo: \"Graduarme como m??dico\" . \n" +
                        "En el punto de inicio se responde a la pregunta ??d??nde estoy ahora? se debe colocar el momento de vida en el que esta actualmente (acci??n puntual desempe??ada por ejemplo \"no incrito en la universidad)\". \n" +
                        "En la ruta intermedia se colocan las acciones coherentes a desempe??ar para poder alcanzar la meta deseada. \n" +
                        "Es importante contar con la ayuda de los padres para identificar los recursos a necesitar en cada acci??n.",
                "", 4, 5, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                28, "Escribir un cuento",
                "A traves de la expresi??n verbal se puede metaforizar la experiencia que ha producido la emoci??n o bien darle a la experiencia alguna forma de resoluci??n distinta a la ocurrida. \n" +
                        "El sujeto puede simplemente narrar los hechos que le acontecieron y c??mo los vivencia o bien, crear historias fant??sticas, acorde con su estilo narrativo",
                "", 1, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                29,
                "Escuchar un cuento",
                "Escuchar el cuento: el c??rculo de los 99, Jorge Bucay",
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
                        "Evaluar las ??reas y la magnitud en las que percibe la amenaza.  Si quiere, dele a cada aspecto del esquema un color que le ayude a representar c??mo se siente a ese respecto.\n " +
                        "Plantee las posibles herramientas que necesitar?? para enfrentar la amenaza. ??no las tiene? ??es posible que alguien cerca de usted si las tenga? ??correr??a el riesgo de pedir ayuda? si no es as?? ??es mas valioso para usted onservar una imagen frente a otros o enfrentar ese miedo?",
                "", 2, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                31, "Mirarse al espejo",
                "Tome un espejo en el que pueda ver de cerca sus expresiones faciales e incluso corporales.\n " +
                        "T??mese el tiempo para ver cada parte de su rostro, lineas, colores, formas.  Dese el espacio para ver desde afuera lo que est?? ocurriendo con su cuerpo en ese momento.\n " +
                        "La expresi??n de la ira es un evento que puede llegar a tornarse complejo por la experiencia fisica que se vive; verlo desde afuera puede darle elementos para el autocontrol as?? como la posibilidad de tener un factor distractor, que no lo va a desconectar de la emoci??n pero si le va a permitir conocerse mas de cerca",
                "", 3, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                32, "Organizar",
                "Puede ser una habitaci??n desordenada, una caja de objetos varios o un juego de organizaci??n como un rompecabezas.\n " +
                        "Dar orden a los objetos materiales puede contribuir a reorganizar las ideas y dar lugar tanto a la emoci??n como a los eventos desencadenantes de la misma",
                "", 3, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                33, "Bailar",
                "Bailar musica acorde a la emoci??n del o los participantes, bailar, puede ser de manera libre o siguiendo una coreograf??a.\n  " +
                        "Utilizar el cuerpo como medio de expresi??n artistica que permita la manifestaci??n de la emoci??n y que termina por generar mayor sensaci??n de bienestar.\n " +
                        "El baile puede ser tambien utilizado como canal de las relaciones sociales y ayuda a fortalecer este tipo de v??nculos.",
                "", 4, 6, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                34, "Karaoke",
                "Canciones nuevas o conocidas, explorar nuevos idiomas o ritmos poco acostumbrados",
                "", 4, 6, 1, "SAdmin", "${currentdate}"
            ),


            ActividadesEntity(
                35, "Caminata con un prop??sito",
                "Preferiblemente en un espacio que permita reconectar con la naturaleza. \n" +
                        "Los sentimientos de tristeza pueden aparecer por una situaci??n particular o sin ninguna raz??n aparente. En ambos casos es necesario darle el espacio y la atenci??n a cada pensamiento o sensaci??n fisica o emocional que tenga lugar.\n " +
                        "Por ello, una caminata que tenga un fin b??sico, como hallar 5 tipos de flores, o 10 tipos de aves, permite a la vez que enfocarse en la tarea, darle al individuo el tiempo para estar solo con sus pensamientos y volcar la emoci??n sobre una actividad placentera que conecte la situaci??n con el aqu?? y el ahora",
                "", 1, 7, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                36, "Origami",
                "Ejercer sobre el papel el poder del que se siente desprovisto el adulto, puede ayudarle a recobrar la sensaci??n de que tiene la capacidad de transformar aquellos elementos sobre los que decida tomar decisiones, de modo que focalizar la energia que proviene de una situaci??n de amenaza en otra que genere oportunidad de aprendizaje puede resultar no solo calmante y relajante, sino tambien un entrenamiento de habilidades manuales",
                "", 2, 7, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                37, "Respiraci??n con la mano",
                "La actividad consiste en delinear la forma de la mano con los dedos abiertos. \n" +
                        "Cada  ascenso desde el abajo hasta la punta del dedo es un inspiraci??n.\n" +
                        "Desde la punta del dedo hacia la primer falange es una exhalaci??n. \n" +
                        "Repetir cuantas veces sea necesario hasta disminuir el conteo de lpm",
                "", 3, 7, 1, "SAdmin", "${currentdate}"
            ),
            ActividadesEntity(
                38, "Bolitas de colores",
                "Amazando pedaciotos de papeles de colores, crear cientos de bolitas de colores. Luego pegarlas en una cartulina. \n" +
                        "Se pueden crear paisajes, figuras o im??genes abstractas llenas de colores. \n " +
                        "Los participantes reparten su producto final con un compa??ero, que debe tratar de identificar las emociones que el otro plasm?? en su imagen\n" +
                        "Este ejercicio sirve para fortalecer los v??nculos con la familia o los pares, que utiliza la empat??a como forma de conexi??n social",
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
                "Es una emoci??n que se??ala la necesidad de afiliaci??n, siempre est?? extra??ando algo, cuando est??s triste quieres estar solo y sientes q tienes poca energ??a para hacer las cosas. En esos momentos es buscar apoyo en tu c??rculo cercano y hablar de ella.",
                1,
                "SAdmin",
                "${currentdate}"
            ),
            EmocionesEntity(
                2,
                "Miedo",
                "Siempre est?? huyendo de algo, cuando sientes miedo te sientes peque??ito y crees que no podras hacer lo que te propones. En esos momentos es bueno darnos un espacio para organizar nuestras ideas, hacer un plan. recuerda ir un paso a la vez y de lo m??s simple a lo m??s complejo.",
                1,
                "SAdmin",
                "${currentdate}"
            ),
            EmocionesEntity(
                3,
                "Ira",
                "Es una emoci??n que aparece cuando sientes que algo injusto ha pasado. Cuando sientes ira quieres que se te escuch?? y se resuelva la situaci??n... En esos momentos es clave identificar qu?? te parece injusto  y qu?? quieres  para comunicarlo asertivamente . Muchas personas necesitan espacio para transitar su ira antes de hablarla.",
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
            CicloVitalEntity(1, "2-3 A??OS", 2, 3, 1, "SAdmin", "${currentdate}"),
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
                "Adultez Tard??a",
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