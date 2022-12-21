import java.util.*;

public class PhoneBook {
    public static Map<Integer, String> menu = new TreeMap<>(Map.of(1, "Показать книгу",
            2, "Добавить",
            3, "Изменить",
            4, "Удалить",
            5, "Закончить работу"));
    public static Map<String, List<String>> startBook = Map.of("Иванов Иван", List.of("+7(911)325-56-98", "234-43-21"),
            "Даша Дашина", List.of("+7(954)385-88-98"),
            "Тимур Тимуров", List.of("554-43-33"));

    public static void main(String[] args) {
        Map<String, List<String>> phoneBook = new TreeMap<>(startBook);
        Scanner scn = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println();
            for (Map.Entry<Integer, String> field : menu.entrySet()) {
                System.out.printf("%d: %s\n", field.getKey(), field.getValue());
            }
            System.out.print("Выберете действие: ");
            int move = scn.nextInt();
            switch (move) {
                case 1:
                    printBook(phoneBook);
                    break;
                case 2:
                    addNew(phoneBook);
                    break;
                case 3:
                    changeContact(phoneBook);
                    break;
                case 4:
                    deleteContact(phoneBook);
                    break;
                case 5:
                    flag = false;
            }
        }
    }

    public static void addNew(Map<String, List<String>> phoneBook) {
        System.out.println("\n---РЕЖИМ ДОБАВЛЕНИЯ---\n");
        System.out.print("""
                1. Добавить новую запись?
                2. Добавить телефон к существующей записи?
                Выбор: \s""");
        Scanner scn = new Scanner(System.in);
        int choice = scn.nextInt();
        if (choice == 1) {
            System.out.print("Введите имя: ");
            scn.nextLine(); // без этой строчки сканер работает не корректно
            String name = scn.nextLine();
            String phone = isValidPhone(getPhoneNumber());
            if (!phoneBook.containsKey(name)) {
                phoneBook.put(name, List.of(phone));
            } else {
                System.out.print("""
                        Запись с таким именем уже существует!
                        1. Заменить запись?
                        2. Добавить новый номер телефона к существующей записи?
                        Выбор: \s""");
                choice = scn.nextInt();
                if (choice == 1) {
                    phoneBook.put(name, List.of(phone));
                } else {
                    List<String> phoneList = new ArrayList<>(phoneBook.get(name));
                    phoneList.add(phone);
                    phoneBook.put(name, phoneList);
                }
            }
        } else {
            Map<Integer, String> namesMap = new TreeMap<>();
            int count = 1;
            for (String s : phoneBook.keySet()) {
                namesMap.put(count, s);
                System.out.printf("%d: %s\n", count, s);
                count++;
            }
            System.out.print("Выберите имя для добавления телефона: ");
            int countName = scn.nextInt();
            List<String> phoneList = new ArrayList<>(phoneBook.get(namesMap.get(countName)));
            phoneList.add(isValidPhone(getPhoneNumber()));
            phoneBook.put(namesMap.get(countName), phoneList);
        }
    }

    public static void changeContact(Map<String, List<String>> phoneBook) {
        System.out.println("\n---РЕЖИМ ИЗМЕНЕНИЯ---\n");
        Scanner scn = new Scanner(System.in);
        int count = 1;
        Map<Integer, Map.Entry<String, List<String>>> listBook = new TreeMap<>();
        for (Map.Entry<String, List<String>> stringListEntry : phoneBook.entrySet()) {
            listBook.put(count, stringListEntry);
            System.out.printf("%d: %s %s\n", count, listBook.get(count).getKey(), listBook.get(count).getValue());
            count++;
        }
        System.out.print("Выберете запись для изменения: ");
        int choice = scn.nextInt();
        System.out.print("\nВыберете поле для изменения: \n1. Имя\n2. Телефон\nВыбор: ");
        int pole = scn.nextInt();
        switch (pole) {
            case 1: {
                System.out.print("Введите новое имя: ");
                String oldName = listBook.get(choice).getKey();
                scn.nextLine(); // без этой строчки сканер работает не корректно
                String name = scn.nextLine();
                phoneBook.remove(oldName);
                phoneBook.put(name, listBook.get(choice).getValue());
                break;
            }
            case 2: {
                if (listBook.get(choice).getValue().size() > 1) {
                    int phoneCount = 1;
                    for (String s : listBook.get(choice).getValue()) {
                        System.out.printf("%d: %s\n", phoneCount, s);
                        phoneCount++;
                    }
                    System.out.print("Выберете телефон для изменения: ");
                    int choicePhone = scn.nextInt();
                    List<String> phonesList = new ArrayList<>(listBook.get(choice).getValue());
                    phonesList.set(choicePhone - 1, isValidPhone(getPhoneNumber()));
                    phoneBook.put(listBook.get(choice).getKey(), phonesList);
                } else {
                    List<String> phonesList = new ArrayList<>(List.of(isValidPhone(getPhoneNumber())));
                    phoneBook.put(listBook.get(choice).getKey(), phonesList);
                }
                break;
            }
        }
    }

    public static void deleteContact(Map<String, List<String>> phoneBook) {
        System.out.println("\n---РЕЖИМ УДАЛЕНИЯ---\n");
        Scanner scn = new Scanner(System.in);
        int count = 1;
        Map<Integer, Map.Entry<String, List<String>>> listBook = new TreeMap<>();
        for (Map.Entry<String, List<String>> stringListEntry : phoneBook.entrySet()) {
            listBook.put(count, stringListEntry);
            System.out.printf("%d: %s %s\n", count, listBook.get(count).getKey(), listBook.get(count).getValue());
            count++;
        }
        System.out.print("Выберете запись для удаления: ");
        int choice = scn.nextInt();
        System.out.print("\n1. Удалить телефон в записи?\n2. Удалить всю запись?\nВыбор: ");
        int deleteVar = scn.nextInt();
        if (deleteVar == 1) {
            if (listBook.get(choice).getValue().size() != 1) {
                int phoneCount = 1;
                for (String s : listBook.get(choice).getValue()) {
                    System.out.printf("%d: %s\n", phoneCount, s);
                    phoneCount++;
                }
                System.out.print("Выберете телефон для удаления: ");
                int choicePhone = scn.nextInt();
                List<String> phonesList = new ArrayList<>(listBook.get(choice).getValue());
                phonesList.remove(choicePhone - 1);
                phoneBook.put(listBook.get(choice).getKey(), phonesList);
            } else {
                System.out.println("В выбранной записи только один номер телефона. Удалена вся запись!");
                phoneBook.remove(listBook.get(choice).getKey());
            }
        } else {
            phoneBook.remove(listBook.get(choice).getKey());
        }
    }

    public static void printBook(Map<String, List<String>> phoneBook) {
        System.out.println("\n---ТЕЛЕФОННАЯ КНИГА---");
        Map<Integer, Map.Entry<String, List<String>>> listBook = new TreeMap<>();
        int count = 1;
        for (Map.Entry<String, List<String>> stringListEntry : phoneBook.entrySet()) {
            listBook.put(count, stringListEntry);
            System.out.printf("%d: %s %s\n", count, listBook.get(count).getKey(), listBook.get(count).getValue());
            count++;
        }
    }

    public static String getPhoneNumber() {
        Scanner scn = new Scanner(System.in);
        System.out.print("""
                Введите городской или мобильный номер телефона:
                Пример ввода мобильного телефона: 9001234567 (10 цифр без 8-ки)
                Пример ввода городского телефона: 7534216 (7 цифр)\s""");
        String phone = scn.nextLine();
        while (!((phone.length() == 7 || phone.length() == 10) && tryLong(phone))) {
            System.out.print("Неверный ввод, попробуйте ещё раз: ");
            phone = scn.nextLine();
        }
        return phone;
    }

    public static String isValidPhone(String phone) {
        StringBuilder sb = new StringBuilder();
        if (phone.length() == 7) {
            sb.append(phone, 0, 3);
            sb.append("-");
            sb.append(phone, 3, 5);
            sb.append("-");
            sb.append(phone, 5, 7);
        } else {
            sb.append("+7(");
            sb.append(phone, 0, 3);
            sb.append(")");
            sb.append(phone, 3, 6);
            sb.append("-");
            sb.append(phone, 6, 8);
            sb.append("-");
            sb.append(phone, 8, 10);
        }
        return sb.toString();
    }

    public static boolean tryLong(String str) throws NumberFormatException {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int readInt(Scanner s, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = s.nextLine();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.err.println("Error: wrong integer");
            }
        }
    }
}