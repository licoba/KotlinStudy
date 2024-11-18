fun quickSort(arr: IntArray, low: Int, high: Int) {
    if (low < high) {
        val pivotIndex = partition(arr, low, high)
        quickSort(arr, low, pivotIndex - 1)
        quickSort(arr, pivotIndex + 1, high)
    }
}

fun partition(array: IntArray, low: Int, high: Int): Int {
    val pivot = array[high] // 取最后一个元素作为中心元素
    var pointer = low  // 定义指向比中心元素大的指针，首先指向第一个元素
    // 遍历数组中的所有元素，将比中心元素大的放在右边，比中心元素小的放在左边
    for (i in low until high) {
        // 将比中心元素小的元素和指针指向的元素交换位置
        // 如果第一个元素比中心元素小，这里就是自己和自己交换位置，指针和索引都向下一位移动
        // 如果元素比中心元素大，索引向下移动，指针指向这个较大的元素，直到找到比中心元素小的元素，并交换位置，指针向下移动
        if (array[i] <= pivot) {
            swap(array,i,pointer)
            pointer++
        }
        println(array.contentToString())
    }
    // 将中心元素和指针指向的元素交换位置
    swap(array,high,pointer)
    return pointer
}

fun swap(arr: IntArray, i: Int, j: Int) {
    println("交换 ${arr[i]} ${arr[j]}")
    val temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}

fun main() {
    val arr = intArrayOf(5, 4, 6, 8, 7, 9, 3, 1, 0, 2)
    quickSort(arr, 0, arr.size - 1)
    println("Sorted array: ${arr.joinToString()}")
}
