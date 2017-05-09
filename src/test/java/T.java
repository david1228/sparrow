import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by liudewei1228 on 17/3/10.
 */
public class T {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        List<Integer> allList = new ArrayList<>();
        int size = list.size();
        int step = 4; // 98 , 0-10 11-20 21-30... 81-90 91-98
        for (int index = 0; index < size;) {
            int end = Math.min(index + step, size);
            allList.addAll(list.subList(index, end));
            index = Math.min(index + step, size);
        }

        allList.forEach(System.out::println);

        allList.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("i:"+integer);
            }
        });

        allList.forEach(i -> {
            System.out.println("lam:"+i);
        });
    }
}
