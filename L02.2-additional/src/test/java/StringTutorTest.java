import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTutorTest {
    public static void log(String s) {
        System.out.println(s);
    }

    /**
     * Замените все null в assertEquals на true или false
     */
    @Test
    public void testStringEquals() {
        String s1 = "aaa";
        String s2 = "aaa";
        s1.equals(s2);
        String s3 = new String("aaa");
        log("адрес объекта s1: "+System.identityHashCode(s1));
        log("адрес объекта s2: "+System.identityHashCode(s2));
        assertEquals(s1==s2, true);
        assertEquals(s1.equals(s2), true);
        log("адрес объекта s3: "+System.identityHashCode(s3));
        assertEquals(s1==s3, false);
        // метод intern() позволяет получить строку из пула строк
        String s4 = s3.intern();
        log("адрес объекта s4: "+System.identityHashCode(s4));
        assertEquals(s1==s4, true);
        // тестируем пересоздание объекта каждый раз при изменении
        s3 = s3+"bbb";
        log("адрес измененного объекта s3: "+System.identityHashCode(s3));
        s3 = s3.substring(0, 3); // s3 снова "aaa"
        assertEquals(s3==s1, false);
        assertEquals(s3.equals(s1), true);
        assertEquals(s3.intern()==s1, true);
    }

    /**
     *  Убедитесь, что приветствие greeting имеет вид
     *  Привет, Иван Иванов!
     *  или
     *  Привет,Петр Первый!
     *  т.е. начинается на Привет, заканчивается на !
     *  и содержит 2 слова для имени и фамилии,
     *  причем имя и фамилия не короче 3 букв
     *  и начинаются с большой буквы
     */
    public boolean checkGreeting(String greeting) {
        String rusName = "[А-ЯЁ][а-яё]{2,}";
        return greeting.matches("Привет,\\s?"+rusName+"\\s"+rusName+"\\s?!");
    }

    @Test
    void testCheckGreeting() {
        assertTrue(checkGreeting("Привет, Иван Иванов!"));
        assertTrue(checkGreeting("Привет,Петр Первый!"));
        assertTrue(checkGreeting("Привет, Петр Первый!"));
        assertTrue(checkGreeting("Привет, Пйёъь Первый !"));

        assertFalse(checkGreeting("Здравствуйте, Петр Первый!"),
                "В начале должно быть слово Привет и запятая");
        assertFalse(checkGreeting("Привет, Петр Первый"),
                "В конце должен быть восклицательный знак");
        assertFalse(checkGreeting("Привет, Ли Сунь!"),
                "Имя слишком короткое");
        assertFalse(checkGreeting("Привет, Куй Ли!"),
                "Фамилия слишком короткая");
        assertFalse(checkGreeting("Привет, Петр!"),
                "Должны быть указаны и имя, и фамилия");
        assertFalse(checkGreeting("Привет, петр Первый!"),
                "Первая буква имени должна быть заглавной");
        assertFalse(checkGreeting("Привет, Петр первый!"),
                "Первая буква фамилии должна быть заглавной");
    }
}
