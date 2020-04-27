package com.github.fluffycop.seenallwhitelist;

import java.util.List;

public class SelectionSort {
    public static void sort(List<SeenInfo> arr)
    {
        int n = arr.size();

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr.get(j).getLastSeen() < arr.get(min_idx).getLastSeen())
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            SeenInfo temp = arr.get(min_idx);
            arr.set(min_idx, arr.get(i));
            arr.set(i, temp);
        }
    }
}
