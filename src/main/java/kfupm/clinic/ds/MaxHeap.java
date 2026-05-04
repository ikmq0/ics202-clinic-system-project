package kfupm.clinic.ds;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class MaxHeap<T> {
    private T[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private Comparator<T> comparator;

    @SuppressWarnings("unchecked")
    public MaxHeap(Comparator<T> comparator) {
        heap = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    public MaxHeap(int capacity, Comparator<T> comparator) {
        heap = (T[]) new Object[capacity];
        size = 0;
        this.comparator = comparator;
    }

    public void insert(T item) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = item;
        size++;
        heapifyUp(size - 1);
    }

    public T extractMax() {
        if (size == 0) {
            return null;
        }
        T max = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        heapifyDown(0);
        return max;
    }

    public T peekMax() {
        if (size == 0) {
            return null;
        }
        return heap[0];
    }
    
    public T peek() {
        return peekMax();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<T> toListSnapshot() {
        List<T> list = new ArrayList<>();
        // Note: returning a shallow snapshot in arbitrary heap order, 
        // to strictly return sorted order we would need to clone the heap.
        for (int i = 0; i < size; i++) {
            list.add(heap[i]);
        }
        // Basic sort to snapshot correctly visually
        list.sort(comparator.reversed());
        return list;
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && comparator.compare(heap[index], heap[parentIndex]) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            int largestIndex = index;

            if (leftChildIndex < size && comparator.compare(heap[leftChildIndex], heap[largestIndex]) > 0) {
                largestIndex = leftChildIndex;
            }

            if (rightChildIndex < size && comparator.compare(heap[rightChildIndex], heap[largestIndex]) > 0) {
                largestIndex = rightChildIndex;
            }

            if (largestIndex != index) {
                swap(index, largestIndex);
                index = largestIndex;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newHeap = (T[]) new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }
}
