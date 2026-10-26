import java.util.*;

public class WordCountMapReduce {

    // ---------------- MAP PHASE ----------------
    static class Mapper {

        public Map<String, List<Integer>> map(String document) {

            Map<String, List<Integer>> mappedData = new HashMap<>();

            String[] words = document.toLowerCase()
                    .replaceAll("[^a-zA-Z0-9 ]", "")
                    .split("\\s+");

            for (String word : words) {

                if (!word.isEmpty()) {

                    mappedData
                            .computeIfAbsent(word, k -> new ArrayList<>())
                            .add(1);
                }
            }

            return mappedData;
        }
    }

    // ---------------- REDUCE PHASE ----------------
    static class Reducer {

        public Map<String, Integer> reduce(
                Map<String, List<Integer>> mappedData) {

            Map<String, Integer> result = new TreeMap<>();

            for (Map.Entry<String, List<Integer>> entry : mappedData.entrySet()) {

                int sum = 0;

                for (int value : entry.getValue()) {
                    sum += value;
                }

                result.put(entry.getKey(), sum);
            }

            return result;
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        // Collection of documents
        String[] documents = {
                "Hello world hello",
                "Hadoop is powerful",
                "Hello Hadoop world",
                "MapReduce is powerful",
                "Hadoop makes big data processing easy"
        };

        Mapper mapper = new Mapper();
        Reducer reducer = new Reducer();

        // This represents the intermediate MapReduce data
        Map<String, List<Integer>> intermediate = new HashMap<>();

        // ---------------- MAP ----------------
        System.out.println("===== MAP PHASE =====");

        for (String document : documents) {

            Map<String, List<Integer>> mapped = mapper.map(document);

            for (Map.Entry<String, List<Integer>> entry : mapped.entrySet()) {

                System.out.println(
                        entry.getKey() + " -> " + entry.getValue());

                intermediate
                        .computeIfAbsent(
                                entry.getKey(),
                                k -> new ArrayList<>())
                        .addAll(entry.getValue());
            }
        }

        // ---------------- SHUFFLE & SORT ----------------
        System.out.println("\n===== SHUFFLE AND SORT =====");

        Map<String, List<Integer>> sortedIntermediate = new TreeMap<>(intermediate);

        for (Map.Entry<String, List<Integer>> entry : sortedIntermediate.entrySet()) {

            System.out.println(
                    entry.getKey() + " -> " + entry.getValue());
        }

        // ---------------- REDUCE ----------------
        System.out.println("\n===== REDUCE PHASE =====");

        Map<String, Integer> result = reducer.reduce(sortedIntermediate);

        for (Map.Entry<String, Integer> entry : result.entrySet()) {

            System.out.println(
                    entry.getKey() + " -> " + entry.getValue());
        }
    }
}