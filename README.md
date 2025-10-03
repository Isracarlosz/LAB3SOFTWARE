java-mvc-productos/
│
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/demo/productos/
│   │   │       ├── controller/
│   │   │       │   └── UsuarioController.java
│   │   │       ├── model/
│   │   │       │   └── Usuario.java
│   │   │       ├── repository/
│   │   │       │   └── UsuarioRepository.java
│   │   │       ├── service/
│   │   │       │   ├── UsuarioService.java
│   │   │       │   └── UsuarioServiceImpl.java
│   │   │       └── MvcProductosApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       │   └── laboTres/
│   │       │       ├── login.html
│   │       │       ├── listar-usuarios.html
│   │       │       ├── crear-usuario.html
│   │       │       └── editar-usuario.html
│   │       └── application.properties
│   │
│   └── test/
│
├── db.sql                      # Script para crear y poblar la base de datos
├── pom.xml                     # Dependencias y configuración de Maven
└── README.md                   # Este archivo
