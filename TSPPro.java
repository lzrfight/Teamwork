import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;


public class TSPPro implements Runnable {
    private Thread t;
    private ArrayList<Route> routeList = new ArrayList<Route>();
    private List<City> cityList;
    private Double shortestDistance = Double.POSITIVE_INFINITY;

    TSPPro(String name){
    }

    public void start(){
        System.out.println("Starting" );
        if (t == null) {
            t = new Thread (this);
            t.start ();
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (cityList == null){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (cityList.size() == 1){
            routeList = new ArrayList<>();
        }else {
            try {
                arrange(cityList, 1, cityList.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    private void arrange(List<City> cityList, int start, int length) throws InterruptedException {
        if (start == length - 1){
            calculateDistance();
        }else
        {
            for (int i = start; i < length; i++){
                swap(cityList, start, i);
                arrange(cityList, start + 1, cityList.size());
                swap(cityList,start, i);
            }
        }
    }


    private void swap(List<City> cityList, int i, int j){
        City temp;
        temp = cityList.get(i);
        cityList.set(i, cityList.get(j));
        cityList.set(j, temp);

    }


    private double getEuclideanDistance(City src, City dest) {
        double x1 = src.getX(), y1 = src.getY(), x2 = dest.getX(), y2 = dest.getY();
        return Math.sqrt((x1 + x2) * Math.abs(x1 - x2) + (y1 + y2) * Math.abs(y1 - y2));
    }



    public void calculateDistance() {
       /* double totalDistance = 0;
        routeList = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            City src = cityList.get(i);
            City dest;
            if (i == cityList.size() - 1)
                dest = cityList.get(0);
            else dest = cityList.get(i + 1);

            double distance = getEuclideanDistance(src, dest);
            Route route = new Route();
            route.setSrc(src);
            route.setDest(dest);
            route.setDist(distance);
            routeList.add(route);
        }
        for (Route value : routeList) {
            System.out.println(value.getSrc().getLabel() + "----->" + value.getDest().getLabel());
        }
        for (int i = 0; i < routeList.size(); i++){
            totalDistance = totalDistance + routeList.get(i).getDist();
        }
        if (this.shortestDistance >= totalDistance){
            this.shortestDistance = totalDistance;
        }
        System.out.println("totaldistance:" + totalDistance);
        System.out.println("shortest:" + shortestDistance);*/
        double totalDistance = 0;
        for (int i = 0; i < cityList.size(); i++) {
            City src = cityList.get(i);
            City dest;
            if (i == cityList.size() - 1)
                dest = cityList.get(0);
            else dest = cityList.get(i + 1);

            double distance = getEuclideanDistance(src, dest);
            totalDistance = distance + totalDistance;
        }
        if (totalDistance <= shortestDistance) {
            shortestDistance = totalDistance;
            routeList = new ArrayList<>();
            for (int i = 0; i < cityList.size(); i++) {
                City src = cityList.get(i);
                City dest;
                if (i == cityList.size() - 1)
                    dest = cityList.get(0);
                else dest = cityList.get(i + 1);

                double distance = getEuclideanDistance(src, dest);
                Route route = new Route();
                route.setSrc(src);
                route.setDest(dest);
                route.setDist(distance);
                routeList.add(route);
            }
        }
    }



    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    public void setShortestDistance(Double shortestDistance){
        this.shortestDistance = Double.POSITIVE_INFINITY;
    }
}
