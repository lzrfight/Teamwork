import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TSPPro extends Observable implements Runnable, Observer {
    private Thread t;
    private ArrayList<Route> routeList;
    private List<City> cityList;
    private Double shortestDistance = Double.POSITIVE_INFINITY;
    TSPPro(){

    }

    private void tspPro(){
        if (cityList.size() == 1){
            routeList = new ArrayList<>();
        }else {
            arrange(cityList, 1, cityList.size());
        }
        System.out.println("shortest distance: " + shortestDistance);
        //output
        routeList = new ArrayList<>();
    }

    private void arrange(List<City> cityList, int start, int length){
        if (start == length - 1){ // DummyRoute
            calculateDummyRoute();
            routeList = new ArrayList<>();
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

    public void calculateDummyRoute() {
        double totalDistance = 0;
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
           if (shortestDistance >= totalDistance){
               shortestDistance = totalDistance;
           }
        }
    }
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        cityList = ((WorkSpace)o).getCityList();
        if (cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                System.out.println(cityList.get(i).getLabel());
            }
            tspPro();
        }
        else routeList = new ArrayList<>();
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
        while (routeList == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Route route : routeList) {
            System.out.println(route.getSrc().getLabel() + "----->" + route.getDest().getLabel());
        }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          System.out.println("new routelist");
    }

    public void start(){
        System.out.println("start thread");
            t = new Thread(this);
            t.start();
    }

    public ArrayList<Route> getRouteList() {
        return routeList;
    }
}
