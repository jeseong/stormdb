package Air;

import AirMap.AirMap;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by honey on 17. 10. 22.
 */
public class AirTopology {
    static public MWNumericArray n = null;
    static public MWNumericArray region_n = null;
    static public Object[] result_step1_1 = null;
    static public Object[] result_step1_2 = null;
    static public Object[] bld3d = null;
    private static AirMap airMap = null;


    public static void main(String[] args) throws Exception {
        double beginTime = System.currentTimeMillis();

        System.out.println("@@@ Create Topology Builder @@@");
        //make Topology
        TopologyBuilder builder = new TopologyBuilder();
        //make Spout
        builder.setSpout("spout", new AirSpout(),1);
        //make No2Bolt
        builder.setBolt("no2", new No2Bolt() ,1)
                .shuffleGrouping("spout");
        //make So2Bolt
//        builder.setBolt("so2", new So2Bolt(), 1)
//                .shuffleGrouping("spout");


        //Local mode
        LocalCluster cluster = new LocalCluster();
        Config config = new Config();
        cluster.submitTopology("AirTopology", config, builder.createTopology());

        try{
            Thread.sleep(1000 * 660);
        }
        catch (InterruptedException e){

        }
        cluster.killTopology("AirTopology");
        cluster.shutdown();
    }
    double endTime = System.currentTimeMillis();

}
