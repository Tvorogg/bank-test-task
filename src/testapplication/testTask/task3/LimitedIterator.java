package testapplication.testTask.task3;

import java.util.*;


public class LimitedIterator implements Iterator<Integer> {
    private List<Integer> filteredList;
    private Iterator<Integer> iterator;
    private int currentIndex;

    public LimitedIterator(Iterator<Integer> iterator, Integer number) {
        this.iterator = iterator;
        this.filteredList = new ArrayList<>();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next < number) {
                filteredList.add(next);
            }
        }
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < filteredList.size();
    }


    @Override
    public Integer next() {
        return filteredList.get(currentIndex++);
    }
}