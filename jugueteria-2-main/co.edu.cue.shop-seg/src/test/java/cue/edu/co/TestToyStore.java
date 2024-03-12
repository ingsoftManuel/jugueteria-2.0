package cue.edu.co;

import cue.edu.co.dtos.ToysDTO;
import cue.edu.co.model.Category;
import cue.edu.co.model.Toys;
import org.junit.jupiter.api.*;

import static cue.edu.co.model.Category.Female;
import static cue.edu.co.model.Category.Unisex;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestToyStore {
    @BeforeAll
    static void init() {
        System.out.println("Inicializando pruebas...");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Preparando para ejecutar prueba...");
    }

    @Test
    @DisplayName("Prueba de tipo de juguete")
    void addTest() {
        Toys toys = new Toys();
        toys.setType(Female); // Establecer el tipo de juguete en el objeto toys
        assertEquals(Female, toys.getType(), "El tipo de juguete debería ser FEMALE");
    }



    @Test
    public void addToy_unisex() throws Exception {
        String name = "Ball";
        Category type = Unisex;
        double price = 200000;
        double amount = 19;
        ToysDTO toyToAdd = new ToysDTO(name, type, price, amount);
        System.out.println("Toy to add: " + toyToAdd);
        System.out.println("Name:"+name);

    }

    @Test
    public void search_female() throws Exception {
        String name = "Barbie";
        Category type = Female;
        Integer price = 200000;
        Integer amount = 3;
        ToysDTO toyToAdd = new ToysDTO(name,type,price,amount);
        System.out.println("Toy to add: " + toyToAdd);
        System.out.println("Name:"+name);
    }


    @AfterEach
    void tearDown() {
        System.out.println("Limpiando después de la prueba...");
    }

    @AfterAll
    static void finish() {
        System.out.println("Finalizando pruebas...");
    }
}

