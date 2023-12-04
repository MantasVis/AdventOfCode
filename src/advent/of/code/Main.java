package advent.of.code;

import java.lang.reflect.InvocationTargetException;

import advent.of.code.days.Day;

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 25; i++) {
            try {
                String className = "advent.of.code.days.day" + i + ".Day" + i;
                Class<?> dayClass = Class.forName(className);

                if (Day.class.isAssignableFrom(dayClass)) {
                    Day dayInstance = (Day) dayClass.getDeclaredConstructor().newInstance();
                    dayInstance.run();
                }

            } catch (ClassNotFoundException e) {
                break;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
