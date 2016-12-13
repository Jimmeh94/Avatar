package avatar.utilities.misc;

import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationUtils {

    public static Location getMidPointLocation(Location start, Location end){
        Double[] delta = new Double[]{Math.abs(start.getX() - end.getX()),
                Math.abs(start.getY() - end.getY()),
                Math.abs(start.getZ() - end.getZ())};
        Double[] xyz = new Double[3];
        Double[] startXYZ = new Double[]{start.getX(), start.getY(), start.getZ()};
        Double[] endXYZ = new Double[]{end.getX(), end.getY(), end.getZ()};

        for(int i = 0; i < 3; i++){
            if(startXYZ[i] > endXYZ[i]){
                xyz[i] = startXYZ[i] - delta[i]/2;
            } else if(startXYZ[i] < endXYZ[i]){
                xyz[i] = startXYZ[i] + delta[i]/2;
            }
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

    private static int getRandomNegOrPos(){
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
