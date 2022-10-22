import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //чтение из файла
        FileReader fin = new FileReader("input.txt");
        Scanner read = new Scanner(fin);
        ArrayList<String> mList = new ArrayList<>();
        ArrayList<String> nList = new ArrayList<>();
        int n = read.nextInt();
        read.nextLine();
        for (int i = 0; i < n; i++){
            nList.add(read.nextLine());
        }
        int m = read.nextInt();
        read.nextLine();
        for (int i = 0; i < m; i++){
            mList.add(read.nextLine());
        }
        read.close();

        //сравнение
        List<String> result = comparison(nList, mList);

        //запись в файл
        FileWriter fr = new FileWriter("output.txt");
        for (String str : result){
            fr.write(str + "\n");
        }
        fr.close();
    }

    //сравнение двух листов с данными из файла
    //файла брался без пропусков между строками
    public static List<String> comparison(List<String> n, List<String> m){

        Map<String, Map<String, Integer>> result = new HashMap<>();
        List<String> finale = new ArrayList<>();
        for (int i = 0; i < n.size(); i++) finale.add("");
        int tmp;
        List<String> copyn = List.copyOf(n);//для дальнейшей сортировки вывода

        for (String strn : n){
            Map<String, Integer> submap = new HashMap<>();
            for (String strm: m){
                tmp = substrings(strn, strm, 1);
                submap.put(strm, tmp);
            }
            result.put(strn, submap);
        }

        //ища наибольшее совпадение, добавляем эту пару в вывод и убираем значения из оригинальных листов
        while ((n.size() >0) && (m.size() > 0)){
            String key = "";
            String subkey = "";
            int max = -1;
            for (String value : n) {
                Map<String, Integer> submap = result.get(value);
                for (String s : m) {
                    int c = submap.get(s);
                    if (c > max) {
                        max = c;
                        key = value;
                        subkey = s;
                    }
                }
            }
            finale.set(copyn.indexOf(key), (key + ":" + subkey));
            n.remove(key);
            m.remove(subkey);
            result.remove(key);
        }

        //добавляем знаки вопросов для элементов без пары
        if (n.size() > 0) {
            for (String str: n){
                if (!str.contains(":"))
                    finale.set(n.indexOf(str), (str + ":" + "?"));
            }
        } else {
            if (m.size() > 0) {
                for (String str: m){
                    finale.add(str + ":" + "?");
                }
            }
        }
        return finale;
    }

    //поиск наибольшего совпадения путем просмотра длины наибольшей общей строки с использованием рекурсии
    public static int substrings(String first, String second, int indent){
        int result = 0;
        int i = 0;
        while ((i + indent) < second.length()){
            String sub = second.substring(i, i + indent);
            if (first.contains(sub)) {
                result = substrings(first, second, indent + 1);
                break;
            }
            i += 1;
        }
        if (result == 0) result = indent;
        return result;
    }
}
