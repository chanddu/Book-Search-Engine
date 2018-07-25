import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.GraphLoader;
import org.apache.spark.graphx.VertexRDD;
import org.apache.spark.graphx.lib.PageRank;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

public class LinkAnalysis {

    private final static String GRAPH_PATH = "/Users/chandu/Desktop/graph.txt";
    private final static String URLS_PATH = "/Users/chandu/Desktop/urls.txt";

    public static void main(String[] args) {
        JavaSparkContext jsc = new JavaSparkContext("local", "Page Rank");

        Graph<Object, Object> graph = GraphLoader.edgeListFile(jsc.sc(), GRAPH_PATH, false,
                -1, StorageLevel.MEMORY_ONLY(), StorageLevel.MEMORY_ONLY());
        VertexRDD<Object> vertices = graph.vertices();

        Graph<Object, Object> pageRank = PageRank.run(graph, 10, 0.0001, vertices.vdTag(), vertices.vdTag());

        VertexRDD<Object> vertexRDD = pageRank.vertices();
        JavaPairRDD<Long, String> rank = vertexRDD.toJavaRDD().mapToPair(arg ->
                new Tuple2<>((Long)arg._1(), arg._2() + ""));

        JavaPairRDD<Long, String> urlRDD = jsc.textFile(URLS_PATH).mapToPair(line -> {
            String[] split = line.split(" ");
            return new Tuple2<>(Long.parseLong(split[0]), split[1]);
        });

        JavaPairRDD<String, String> ranksByURL = urlRDD.join(rank).mapToPair(arg ->
                new Tuple2<>(arg._2()._1(), arg._2()._2()));

        ranksByURL.take(5).forEach(tuple -> System.out.println(Double.parseDouble(tuple._2())));

    }

}
