import java.util.ArrayList;

public class Robot {

    //Fills a 2D array with initial probabilities
    //Index [0][j] and [7][j] are borders, as well as the first and last index in every row.
    public void fillArray(String[][] map) {

        map[0][0] = "";
        map[0][1] = "";
        map[0][2] = "";
        map[0][3] = "";
        map[0][4] = "";
        map[0][5] = "";
        map[0][6] = "";
        map[0][7] = "";
        map[0][8] = "";

        map[1][0] = "";
        map[1][1] = "5.00";
        map[1][2] = "5.00";
        map[1][3] = "####";
        map[1][4] = "####";
        map[1][5] = "####";
        map[1][6] = "####";
        map[1][7] = "5.00";
        map[1][8] = "";

        map[2][0] = "";
        map[2][1] = "5.00";
        map[2][2] = "5.00";
        map[2][3] = "5.00";
        map[2][4] = "####";
        map[2][5] = "####";
        map[2][6] = "####";
        map[2][7] = "####";
        map[2][8] = "";

        map[3][0] = "";
        map[3][1] = "####";
        map[3][2] = "5.00";
        map[3][3] = "5.00";
        map[3][4] = "5.00";
        map[3][5] = "5.00";
        map[3][6] = "####";
        map[3][7] = "####";
        map[3][8] = "";

        map[4][0] = "";
        map[4][1] = "####";
        map[4][2] = "####";
        map[4][3] = "5.00";
        map[4][4] = "5.00";
        map[4][5] = "5.00";
        map[4][6] = "####";
        map[4][7] = "####";
        map[4][8] = "";

        map[5][0] = "";
        map[5][1] = "####";
        map[5][2] = "####";
        map[5][3] = "####";
        map[5][4] = "5.00";
        map[5][5] = "5.00";
        map[5][6] = "5.00";
        map[5][7] = "####";
        map[5][8] = "";


        map[6][0] = "";
        map[6][1] = "5.00";
        map[6][2] = "####";
        map[6][3] = "####";
        map[6][4] = "####";
        map[6][5] = "5.00";
        map[6][6] = "5.00";
        map[6][7] = "5.00";
        map[6][8] = "";

        map[7][0] = "";
        map[7][1] = "";
        map[7][2] = "";
        map[7][3] = "";
        map[7][4] = "";
        map[7][5] = "";
        map[7][6] = "";
        map[7][7] = "";
        map[7][8] = "";

    }

    //Prints array with probabilities and walls
    public void printArray(String[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Calculates the probability that the robot detects the wall
    //Based on Sensor evidence and actual surroundings of the robot
    public double calculateSense(int actual, int guess, double prob) {

        if (guess == 1 && actual == 1) {
            return prob * 0.90;
        } else if (guess == 1 && actual == 0) {
            return  prob * 0.05;
        } else if (guess == 0 && actual == 1) {
            return prob * 0.10;
        } else {
            return prob * 0.95;
        }
    }

    //Senses walls in all four directions
    //Finds out if there is a wall or open space adjacent to the current cell
    public void sensing(int w, int n, int e, int s, String[][] map) {

        int west;
        int north = 0;
        int east = 0;
        int south = 0;

        ArrayList<Double> findTotal = new ArrayList<>();               //Holds the new probabilities for all open cells
        double probability;

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (!map[i][j].equals("####")) {

                    probability = (Double.parseDouble(map[i][j]) / 100) ;

                    if (map[i][j - 1].isEmpty() || map[i][j - 1].equals("####")) {
                        west = 1;
                    } else {
                        west = 0;
                    }

                    probability = calculateSense(west, w, probability);

                    if (map[i - 1][j].isEmpty() || map[i - 1][j].equals("####")) {
                        north = 1;
                    } else {
                        north = 0;
                    }

                    probability = calculateSense(north, n, probability);

                    if (map[i][j + 1].isEmpty() || map[i][j + 1].equals("####")) {
                        east = 1;
                    } else {
                        east = 0;
                    }

                    probability = calculateSense(east, e, probability);

                    if (map[i + 1][j].isEmpty() || map[i + 1][j].equals("####")) {
                        south = 1;
                    } else {
                        south = 0;
                    }

                    probability = (calculateSense(south, s, probability));
                    findTotal.add(probability);

                }
            }
        }
        updateSensor(findTotal, map);
    }

    //Updates probabilities of array after evidence
    public void updateSensor(ArrayList<Double> find, String[][] map) {
        int count = 0;
        double sum = 0.0;
        String hold;


        for (int i = 0; i < find.size(); i++) {
            sum += find.get(i);
        }


        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (!map[i][j].equals("####")) {
                    hold = String.format("%.2f", (find.get(count) / sum) * 100);
                    map[i][j] = hold;
                    count++;
                }
            }

        }
    }

    //Updates probabilities of array after an action
    public void updateMotion(ArrayList<Double> find, String[][] map) {
        int count = 0;
        double sum = 0.0;
        String hold;

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (!map[i][j].equals("####")) {
                    hold = String.format("%.2f", (find.get(count)));
                    map[i][j] = hold;
                    count++;
                }
            }

        }
    }

    //Has probability values for a move West
    public void motionWest(String[][] map) {

        double initial;
        double prob;
        ArrayList<Double> send = new ArrayList<>();

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (map[i][j] != "####") {

                    initial = Double.parseDouble(map[i][j]) / 100;
                    prob = 0.0;

                    if (map[i][j - 1].equals("") || map[i][j - 1].equals("####")) {
                        prob += (0.75 * initial);
                    }

                    if (map[i - 1][j].equals("") || map[i - 1][j].equals("####")) {
                        prob += (0.10 * initial);
                    } else {
                        prob += (0.15 * (Double.parseDouble(map[i-1][j]) / 100));
                    }

                    if (!map[i][j + 1].equals("") && !map[i][j + 1].equals("####")) {
                        prob += (0.75 * (Double.parseDouble(map[i][j + 1]) / 100));
                    }

                    if (map[i + 1][j].equals("") || map[i + 1][j].equals("####")) {
                        prob += (0.15 * initial);
                    } else {
                        prob += (0.10 * (Double.parseDouble(map[i + 1][j]) / 100));
                    }

                    send.add(prob * 100);
                }
            }
        }
        updateMotion(send, map);
    }

    //Has probability values for a move North
    public void motionNorth(String[][] map) {

        double initial;
        double prob;
        ArrayList<Double> send = new ArrayList<>();

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (map[i][j] != "####") {

                    initial = Double.parseDouble(map[i][j]) / 100;
                    prob = 0.0;

                    if (map[i][j - 1].equals("") || map[i][j - 1].equals("####")) {
                        prob = (0.15 * initial);                                                  //Bounce off West
                    } else {
                        prob = (0.10 * (Double.parseDouble(map[i][j - 1]) / 100));                //Drift from East
                    }

                    if (map[i - 1][j].equals("") || map[i - 1][j].equals("####")) {
                        prob += (0.75 * initial);                                                 //Bounce off North
                    }

                    if (map[i][j + 1].equals("") || map[i][j + 1].equals("####")) {
                        prob += (0.10 * initial);                                                 //Bounce off East
                    } else {
                        prob += (0.15 * (Double.parseDouble(map[i][j + 1]) / 100));               //Drift from West
                    }

                    if (!map[i + 1][j].equals("") && !map[i + 1][j].equals("####")) {
                        prob += (0.75 * (Double.parseDouble(map[i + 1][j]) / 100));
                    }

                    send.add(prob * 100);
                }
            }
        }
        updateMotion(send, map);
    }

    //Has probability values for a move East
    public void motionEast(String[][] map) {

        double initial;
        double prob;
        ArrayList<Double> send = new ArrayList<>();

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (map[i][j] != "####") {

                    initial = Double.parseDouble(map[i][j]) / 100;
                    prob = 0.0;

                    if (!map[i][j - 1].equals("") && !map[i][j - 1].equals("####")) {
                        prob += (0.75 * (Double.parseDouble(map[i][j - 1]) / 100));
                    }

                    if (map[i - 1][j].equals("") || map[i - 1][j].equals("####")) {
                        prob += (0.15 * initial);
                    } else {
                        prob += (0.10 * (Double.parseDouble(map[i - 1][j]) / 100));
                    }

                    if (map[i][j + 1].equals("") || map[i][j + 1].equals("####")) {
                        prob += (0.75 * initial);
                    }

                    if (map[i + 1][j].equals("") || map[i + 1][j].equals("####")) {
                        prob += (0.10 * initial);
                    } else {
                        prob += (0.15 * (Double.parseDouble(map[i + 1][j]) / 100));
                    }

                    send.add(prob * 100);
                }
            }
        }
        updateMotion(send, map);
    }

    //Has probability values for a move South
    public void motionSouth(String[][] map) {

        double initial;
        double prob;
        ArrayList<Double> send = new ArrayList<>();

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {

                if (map[i][j] != "####") {

                    initial = Double.parseDouble(map[i][j]) / 100;
                    prob = 0.0;

                    if (map[i][j - 1].equals("") || map[i][j - 1].equals("####")) {
                        prob = (0.10 * initial);                                                  //Bounce off West
                    } else {
                        prob = (0.15 * (Double.parseDouble(map[i][j - 1]) / 100));                //Drift from East
                    }

                    if (!map[i - 1][j].equals("") && !map[i - 1][j].equals("####")) {
                        prob += 0.75 * (Double.parseDouble(map[i-1][j]) / 100);                                                 //Bounce off North
                    }

                    if (map[i][j + 1].equals("") || map[i][j + 1].equals("####")) {
                        prob += (0.15 * initial);                                                 //Bounce off East
                    } else {
                        prob += (0.10 * (Double.parseDouble(map[i][j + 1]) / 100));                       //Drift from West
                    }

                    if (map[i + 1][j].equals("") || map[i + 1][j].equals("####")) {
                        prob += (0.75 * initial);
                    }

                    send.add(prob * 100);
                }
            }
        }
        updateMotion(send, map);
    }

    public static void main(String[] args) {

        String[][] map = new String[8][9];
        Robot robot = new Robot();

        robot.fillArray(map);

        System.out.print("\nInitial Location Probabilities");
        robot.printArray(map);

        System.out.print("\nFiltering after Evidence [0, 0, 0, 0]");
        robot.sensing(0, 0, 0, 0, map);
        robot.printArray(map);

        System.out.print("\nPrediction after Action N");
        robot.motionNorth(map);
        robot.printArray(map);

        System.out.print("\nFiltering after Evidence [0, 0, 1, 0]");
        robot.sensing(0, 0, 1, 0, map);
        robot.printArray(map);

        System.out.print("\nPrediction after Action N");
        robot.motionNorth(map);
        robot.printArray(map);

        System.out.print("\nFiltering after Evidence [0, 1, 1, 0]");
        robot.sensing(0, 1, 1, 0, map);
        robot.printArray(map);

        System.out.print("\nPrediction after Action W");
        robot.motionWest(map);
        robot.printArray(map);

        System.out.print("\nFiltering after Evidence [0, 1, 0, 0]");
        robot.sensing(0, 1, 0, 0, map);
        robot.printArray(map);

        System.out.print("\nPrediction after Action S");
        robot.motionSouth(map);
        robot.printArray(map);

        System.out.print("\nFiltering after Evidence [0, 0, 0, 0]");
        robot.sensing(0, 0, 0, 0, map);
        robot.printArray(map);
    }
}

