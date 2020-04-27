package com.github.fluffycop.seenallwhitelist;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class QuickSort {
    /* This function takes last element as pivot,
   places the pivot element at its correct
   position in sorted array, and places all
   smaller (smaller than pivot) to left of
   pivot and all greater elements to right
   of pivot */
    private static int partition(List<SeenInfo> arr, int low, int high) {
        long pivot = arr.get(high).getLastSeen();
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr.get(j).getLastSeen() < pivot) {
                i++;

                // swap arr[i] and arr[j]
                SeenInfo temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        SeenInfo temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private static List<SeenInfo> sort(List<SeenInfo> arr, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
        throw new IllegalArgumentException("Low cannot be higher than low: "+high+"<"+low);
    }

    public static List<SeenInfo> sort(List<SeenInfo> arr) {
        return sort(arr, 0, arr.size() - 1);
    }
}
