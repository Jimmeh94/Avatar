package avatar.utilities.misc;

import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationUtils {

    //Should be a 1 block thick ring around the outside of the circle
    /*
    Given a radius length r and an angle t in radians and a circle's center (h,k),
    you can calculate the coordinates of a point on the circumference as follows
     */
    public static List<Location> getCircleOutline(Location center, double radius, boolean includeDuplicates){
        List<Location> threshold = new ArrayList<>();
        Location copy = center.copy();

        for(int i = 0; i < 360; i++){
            //2 radians = 360 degrees
            double t = i * (Math.PI / 180);
            double x = radius * Math.cos(t) + copy.getX();
            double z = radius * Math.sin(t) + copy.getZ();
            Location add = new Location(copy.getExtent(),
                    x,
                    copy.getBlockY(),
                    z);
            if(!includeDuplicates && check(add, threshold)){
                threshold.add(add);
            }
        }
        return threshold;
    }

    private static boolean check(Location add, List<Location> threshold) {
        for(Location location: threshold){
            if(location.getBlockX() == add.getBlockX() && location.getBlockZ() == add.getBlockZ())
                return false;
        }
        return true;
    }

    public static List<Location> getSquareOutline(Location first, Location second){
        List<Location> threshold = new ArrayList<>();

        int xCoefficient = first.getBlockX() > second.getBlockX() ? -1 : 1, zCoefficient = first.getBlockZ() > second.getBlockZ() ? -1 : 1;
        int deltaX = Math.max(first.getBlockX(), second.getBlockX()) - Math.min(first.getBlockX(), second.getBlockX());
        int deltaZ = Math.max(first.getBlockZ(), second.getBlockZ()) - Math.min(first.getBlockZ(), second.getBlockZ());

        Location current = first.copy();
        threshold.add(current);

        for(int i = 0; i < deltaX; i++){
            current = current.add(xCoefficient, 0, 0);
            threshold.add(current);
        }
        for(int i = 0; i < deltaZ; i++){
            current = current.add(0, 0, zCoefficient);
            threshold.add(current);
        }

        xCoefficient *= -1;
        zCoefficient *= -1;
        for(int i = 0; i < deltaX; i++){
            current = current.add(xCoefficient, 0, 0);
            threshold.add(current);
        }
        for(int i = 0; i < deltaZ; i++){
            current = current.add(0, 0, zCoefficient);
            threshold.add(current);
        }

        threshold.add(second);

        return threshold;
    }

    public static Location getMidPointLocation(Location start, Location end){
        Double[] delta = new Double[]{Math.abs(start.getX() - end.getX()),
                Math.abs(start.getY() - end.getY()),
                Math.abs(start.getZ() - end.getZ())};
        Double[] xyz = new Double[3];
        Double[] startXYZ = new Double[]{start.getX(), start.getY(), start.getZ()};
        Double[] endXYZ = new Double[]{end.getX(), end.getY(), end.getZ()};

        //3 just to cycle through the x, y and z coord
        for(int i = 0; i < 3; i++){
            if(startXYZ[i] > endXYZ[i]){
                xyz[i] = startXYZ[i] - delta[i]/2;
            } else if(startXYZ[i] < endXYZ[i]){
                xyz[i] = startXYZ[i] + delta[i]/2;
            } else xyz[i] = startXYZ[i];
        }

        return new Location(start.getExtent(), xyz[0], xyz[1], xyz[2]);
    }

    /**
     * Trace a "trail" from one block start point to another, also applying noise to that trail
     * Useful for things like lightning
     * @param start
     * @param end
     * @param noise
     * @return
     */
    public static List<Location> traceInBlock(Location start, Location end, Noise noise){

        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double dz = end.getZ() - start.getZ();
        List<Location> list = new ArrayList<>();
        list.add(start);

        int midAmount = (int) (noise.getRandomScale() * 10);
        boolean getMidClosetToStart = true;

        //want noise on both axes that aren't biggest
        //get midpoint of both of those, the midpoint between those, then apply noise

        if(dx == dy && dy == dz){
            //need to alter 2 of these values to apply noise
            Random random = new Random();
            int choice1 = random.nextInt(3), choice2;
            Double[] temp = new Double[]{dx, dy, dz};

            temp[choice1] *= 0;

            choice2 = random.nextInt(3);
            while(choice2 == choice1){
                choice2 = random.nextInt(3);
            }

            temp[choice2] /= 2;
        }

        if(dx > dy && dx > dz){
            //noise to be on y and z axes
            //how many midpoints, theoretically the more midpoints the more noise
            for(int i = 0; i < midAmount; i++){ //0 - 4 at max
                //first midpoint in the direct center
                if(i == 0){
                    list.add(getMidPointLocation(start, end).add(0, noise.getRandomScale() * getRandomNegOrPos(), noise.getRandomScale() * getRandomNegOrPos()));
                } else {
                    //next midpoint needs to be closer to the start
                    if(getMidClosetToStart){
                        list.add(getMidPointLocation(start, list.get(list.size() - 1))
                                .add(0, noise.getRandomScale() * getRandomNegOrPos(), noise.getRandomScale() * getRandomNegOrPos()));
                        getMidClosetToStart = false;
                    } else {
                        //next midpoint needs to be closer to the end
                        list.add(getMidPointLocation(list.get(list.size() - 1), end)
                                .add(0, noise.getRandomScale() * getRandomNegOrPos(), noise.getRandomScale() * getRandomNegOrPos()));
                        getMidClosetToStart = true;
                    }
                }
            }

        } else if(dy > dx && dy > dz){
            //noise to be on x and z axes
            //how many midpoints, theoretically the more midpoints the more noise
            for(int i = 0; i < midAmount; i++){ //0 - 4 at max
                //first midpoint in the direct center
                if(i == 0){
                    list.add(getMidPointLocation(start, end).add(noise.getRandomScale() * getRandomNegOrPos(), 0, noise.getRandomScale() * getRandomNegOrPos()));
                } else {
                    //next midpoint needs to be closer to the start
                    if(getMidClosetToStart){
                        list.add(getMidPointLocation(start, list.get(list.size() - 1))
                                .add(noise.getRandomScale() * getRandomNegOrPos(), 0, noise.getRandomScale() * getRandomNegOrPos()));
                        getMidClosetToStart = false;
                    } else {
                        //next midpoint needs to be closer to the end
                        list.add(getMidPointLocation(list.get(list.size() - 1), end)
                                .add(noise.getRandomScale() * getRandomNegOrPos(), 0, noise.getRandomScale() * getRandomNegOrPos()));
                        getMidClosetToStart = true;
                    }
                }
            }
        } else if(dz > dx && dz > dy){
            //noise to be on x and y axes
            //how many midpoints, theoretically the more midpoints the more noise
            for(int i = 0; i < midAmount; i++){ //0 - 4 at max
                //first midpoint in the direct center
                if(i == 0){
                    list.add(getMidPointLocation(start, end).add(noise.getRandomScale() * getRandomNegOrPos(), noise.getRandomScale() * getRandomNegOrPos(), 0));
                } else {
                    //next midpoint needs to be closer to the start
                    if(getMidClosetToStart){
                        list.add(getMidPointLocation(start, list.get(list.size() - 1))
                                .add(noise.getRandomScale() * getRandomNegOrPos(), noise.getRandomScale() * getRandomNegOrPos(), 0));
                        getMidClosetToStart = false;
                    } else {
                        //next midpoint needs to be closer to the end
                        list.add(getMidPointLocation(list.get(list.size() - 1), end)
                                .add(noise.getRandomScale() * getRandomNegOrPos(), noise.getRandomScale() * getRandomNegOrPos(), 0));
                        getMidClosetToStart = true;
                    }
                }
            }
        }

        return list;
    }

    public static int getRandomNegOrPos(){
        int give = (new Random()).nextInt(2) == 0 ? -1 : 1;
        return give;
    }

    public enum Noise{

        HIGH(0.5),
        MEDIUM(0.3),
        NONE(0.0);

        private double scale;

        Noise(double scale){this.scale = scale;}

        public double getMaxScale() {
            return scale;
        }

        public double getRandomScale(){
            Random random = new Random();
            double temp = (temp = random.nextInt(3) / 10) + (temp == 0 ? 0.1 : 0);
            switch (this){
                case HIGH: return temp + MEDIUM.getMaxScale();
                case MEDIUM: return temp + NONE.getMaxScale();
                default: return 0.0;
            }
        }
    }

}
