//bot @Test2312313_bot
import java.io.*;
import java.net.*;
import java.util.*;

public class App {
    static String TOKEN = "8922649983:AAFj59h0BB-h3k2P8CJxgKroBaeDbEY38hI";
    static Map<Long, Integer> orders = new HashMap<>();
    static Map<Long, Integer> selectedPrice = new HashMap<>();
    static Map<Long, String> selectedName = new HashMap<>();
    private static final String FILE_NAME = "orders.json";

    public static void main(String[] args) throws Exception {
        loadOrdersJson();
        int lastId = 0;
        System.out.println("Бот запущений...");

        while (true) {
            try {
                String resp = send("getUpdates?offset=" + (lastId + 1));
                if (resp.contains("\"text\":\"")) {
                    long id = Long.parseLong(val(resp, "\"chat\":{\"id\":", ","));
                    String txt = val(resp, "\"text\":\"", "\"");
                    String user = val(resp, "\"first_name\":\"", "\"");
                    lastId = Integer.parseInt(val(resp, "\"update_id\":", ","));

                    if (txt.equals("/start") || txt.equalsIgnoreCase("скасувати")) {
                        selectedPrice.remove(id);
                        send("sendMessage?chat_id=" + id + "&text=Привіт, " + user + "!\nОберіть:\n1 - Піца(200)\n2 - Бургер(150)\n3 - Кола(50)");
                    }
                    else if (txt.equals("1") || txt.equals("2") || txt.equals("3")) {
                        if(txt.equals("1")) { selectedName.put(id, "Піца"); selectedPrice.put(id, 200); }
                        if(txt.equals("2")) { selectedName.put(id, "Бургер"); selectedPrice.put(id, 150); }
                        if(txt.equals("3")) { selectedName.put(id, "Кола"); selectedPrice.put(id, 50); }

                        send("sendMessage?chat_id=" + id + "&text=Ви обрали " + selectedName.get(id) + ". Скільки штук?\n(Напишіть 'скасувати' для виходу)");
                    }
                    else {
                        try {
                            int count = Integer.parseInt(txt);
                            if (!selectedPrice.containsKey(id)) {
                                send("sendMessage?chat_id=" + id + "&text=Спочатку оберіть товар!");
                                continue;
                            }

                            int price = selectedPrice.get(id) * count;
                            int uOrders = orders.getOrDefault(id, 0);

                            String msg = "";
                            if (uOrders >= 3) {
                                price *= 0.9;
                                msg = "Персональна знижка 10%!";
                            }

                            orders.put(id, uOrders + 1);
                            saveOrdersJson();

                            send("sendMessage?chat_id=" + id + "&text=" + msg + "\nТовар: " + selectedName.get(id) + "\nДо сплати: " + price + " грн.\nЗамовлень у базі: " + (uOrders + 1));
                            selectedPrice.remove(id);
                        } catch (Exception e) {
                            send("sendMessage?chat_id=" + id + "&text=Будь ласка, введіть число або /start");
                        }
                    }
                }
            } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
            Thread.sleep(1000);
        }
    }

    static String send(String cmd) throws Exception {
        String safeCmd = cmd.replace(" ", "%20").replace("\n", "%0A");
        URL url = new URL("https://api.telegram.org/bot" + TOKEN + "/" + safeCmd);
        Scanner s = new Scanner(url.openStream());
        return s.hasNext() ? s.useDelimiter("\\A").next() : "";
    }

    static String val(String src, String key, String del) {
        if (!src.contains(key)) return "Користувач";
        int s = src.indexOf(key) + key.length();
        return src.substring(s, src.indexOf(del, s));
    }

    private static void saveOrdersJson() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            out.print("{");
            int size = orders.size();
            int count = 0;
            for (Map.Entry<Long, Integer> entry : orders.entrySet()) {
                out.print("\"" + entry.getKey() + "\":" + entry.getValue());
                count++;
                if (count < size) {
                    out.print(",");
                }
            }
            out.print("}");
        } catch (IOException e) {
            System.out.println("Помилка збереження файлу: " + e.getMessage());
        }
    }

    private static void loadOrdersJson() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNext()) return;
            String content = scanner.useDelimiter("\\A").next();
            content = content.trim().replace("{", "").replace("}", "");

            if (content.isEmpty()) return;

            String[] pairs = content.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                long id = Long.parseLong(keyValue[0].replace("\"", "").trim());
                int count = Integer.parseInt(keyValue[1].trim());
                orders.put(id, count);
            }
        } catch (Exception e) {
            System.out.println("Помилка зчитування файлу: " + e.getMessage());
        }
    }
}