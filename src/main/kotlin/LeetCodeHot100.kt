import java.util.*

class LeetCodeHot100 {
    // 思路：用HashMap作为Key，来分组，因为这些字符串的字符，在经过排序之后，都有同一个Key
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        var map = hashMapOf<String,MutableList<String>>()
        for(str in strs){
            var charArray = str.toCharArray()
            Arrays.sort(charArray)
            val key = String(charArray)
            System.out.println("key $key")
            val value = map[key]
            if(value == null){
                val list = mutableListOf(str)
                map[key] = list
            }else{
                val list = map[key]!!
                list.add(str)
                map[key] = list
            }
        }
        return map.values.toList()
    }


}

fun main() {
    val ssss = arrayOf("eat","tea","tan","ate","nat","bat")
    val result =  LeetCodeHot100().groupAnagrams(ssss)
    System.out.println("sssss $result")
}

