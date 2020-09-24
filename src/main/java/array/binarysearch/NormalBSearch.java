package array.binarysearch;

public class NormalBSearch {

    public int binarySearch(int[] nums, int target) {

        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length -1;

        while (left <= right) {
            int mid = left + (right - 1) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }

        return -1;

    }

    public int leftBound(int[] nums, int target) {

        if (nums == null || nums.length == 0) return -1;

        int left = 0, right = nums.length -1;
        while (left <= right) {
            int mid = left + (right - left) /2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid -1;
            } else {
                right = mid -1;
            }
        }

        if (left >= nums.length || nums[left] != target) {
            return -1;
        }

        return left;
    }

    public int rightBound(int[] nums, int target) {

        if (nums == null || nums.length == 0) return -1;

        int left = 0, right = nums.length -1;
        while (left <= right) {
            int mid = left + (right - left) /2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid -1;
            } else {
                left = mid + 1;
            }
        }

        if (right < 0 || nums[right] != target) {
            return -1;
        }

        return right;
    }

}
