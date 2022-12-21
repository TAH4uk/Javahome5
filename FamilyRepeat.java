import java.util.*;

public class FamilyRepeat {
    public static class Main {
        public static void main(String[] args) {
            Map<String, Integer> dataNames = new HashMap<>();
            Map<Integer, List<String>> resultMap = new TreeMap<>(Collections.reverseOrder());
            List<String> list = List.of("Иван Иванов",
                    "Светлана Петрова",
                    "Кристина Белова",
                    "Анна Мусина",
                    "Анна Крутова",
                    "Иван Юрин",
                    "Петр Лыков",
                    "Павел Чернов",
                    "Петр Чернышов",
                    "Мария Федорова",
                    "Марина Светлова",
                    "Мария Савина",
                    "Мария Рыкова",
                    "Марина Лугова",
                    "Анна Владимирова",
                    "Иван Мечников",
                    "Петр Пети",
                    "Иван Ежов");

            for (String s : list) {
                String name = s.substring(0, s.indexOf(" "));
                if (dataNames.containsKey(name))
                    dataNames.put(name, dataNames.get(name) + 1);
                else
                    dataNames.put(name, 1);
            }

            for (Map.Entry<String, Integer> stringIntegerEntry : dataNames.entrySet()) {
                if (resultMap.containsKey(stringIntegerEntry.getValue())) {
                    resultMap.get(stringIntegerEntry.getValue()).add(stringIntegerEntry.getKey());
                } else {
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(stringIntegerEntry.getKey());
                    resultMap.put(stringIntegerEntry.getValue(), temp);
                }
            }

            for (Map.Entry<Integer, List<String>> stringIntegerEntry : resultMap.entrySet()) {
                System.out.println(stringIntegerEntry);
            }
        }
    }
}
