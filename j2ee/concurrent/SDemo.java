package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SDemo {

    public static void main(String[] args) {
        List<String> ls = new ArrayList<String>();
        ls.add("x");
        ls.add("°²Åô³Â");
        ls.add("°²ÆßìÅ");
        ls.add("ºú´øÎ°");
        ls.add("e");
        ls.add("d");
        ls.add("f");
        ls.add("f");

        Optional<String> max = ls.stream().max(String::compareTo);
        System.out.println("max:"+max.get());

        ls.stream().sorted().forEach(e -> System.out.println(e));

        System.out.println(ls.stream().distinct().count());
    }

}
