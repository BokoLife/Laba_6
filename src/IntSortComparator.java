import java.util.Comparator;

public class IntSortComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if ((o1 > o2) && (o1 % 2 != 0) && (o2 % 2 == 0)) {
            return 1;
        }
        else if ((o1 < o2) && (o1 % 2 == 0) && (o2 % 2 != 0)){
            return -1;
        }
        return 0;
    }
}
