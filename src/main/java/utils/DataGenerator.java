package utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en-US"));
    private static final Random random = new Random();

    public static class UsuarioData {
        public String nombre;
        public String apellido;
        public String email;
        public String celular;
        public String direccion;

        public String genero;
        public List<String> hobbies;
        public List<String> materias;
        public String fechaNacimiento;
        public String estado;
        public String ciudad;
    }
    private static final Map<String, List<String>> ESTADOS_Y_CIUDADES = Map.of(
            "NCR", List.of("Delhi", "Gurgaon", "Noida"),
            "Uttar Pradesh", List.of("Agra", "Lucknow", "Merrut"),
            "Haryana", List.of("Karnal", "Panipat"),
            "Rajasthan", List.of("Jaipur", "Jaiselmer")
    );

    public static UsuarioData generarUsuarioValido() {
        UsuarioData usuario = new UsuarioData();

        // Datos básicos
        usuario.nombre = faker.name().firstName();
        usuario.apellido = faker.name().lastName();
        usuario.email = faker.internet().emailAddress();
        usuario.celular = faker.number().digits(10);
        usuario.direccion = faker.address().fullAddress();

        // Datos combinatorios
        usuario.genero = generarGenero();
        usuario.hobbies = generarHobbies();
        usuario.materias = generarMaterias();
        usuario.fechaNacimiento = generarFecha();
        //asegura la relacion entre el estado y la ciudad aunque s escojan al azar
        asignarEstadoYCiudad(usuario);

        return usuario;
    }

    private static String generarGenero() {
        String[] generos = {"Male", "Female", "Other"};
        return generos[random.nextInt(generos.length)];
    }

    private static List<String> generarHobbies() {
        List<String> posibles = Arrays.asList("Sports", "Reading", "Music");
        Collections.shuffle(posibles);
        int cantidad = random.nextInt(posibles.size() + 1);
        return posibles.subList(0, cantidad);
    }

    private static List<String> generarMaterias() {
        List<String> posibles = Arrays.asList("Maths", "Physics", "Chemistry", "English",
                "Computer Science", "Commerce", "Economics");
        Collections.shuffle(posibles);
        int cantidad = random.nextInt(posibles.size() + 1);
        return posibles.subList(0, cantidad);
    }

    private static String generarFecha() {
        LocalDate fecha = LocalDate.of(
                faker.number().numberBetween(1985, 2004),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28)
        );

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        return fecha.format(formatter);
    }

    private static String generarEstado() {
        String[] estados = {"NCR", "Uttar Pradesh", "Haryana"};
        return estados[random.nextInt(estados.length)];
    }

    private static void asignarEstadoYCiudad(UsuarioData usuario) {

        // Selección del estado
        List<String> estados = new ArrayList<>(ESTADOS_Y_CIUDADES.keySet());
        usuario.estado = estados.get(random.nextInt(estados.size()));

        // Selección de ciudad DEPENDIENTE del estado
        List<String> ciudades = ESTADOS_Y_CIUDADES.get(usuario.estado);
        usuario.ciudad = ciudades.get(random.nextInt(ciudades.size()));
    }
}
