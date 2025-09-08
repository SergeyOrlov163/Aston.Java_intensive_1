public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        System.out.println(map.get("two"));     // 2
        System.out.println(map.remove("one"));  // 1
        System.out.println(map);                // {three=3, two=2}
    }
}
