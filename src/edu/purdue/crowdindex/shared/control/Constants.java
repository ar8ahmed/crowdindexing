package edu.purdue.crowdindex.shared.control;

public   class Constants {
    //AMT tasks Constants
    public static String amtUserName = "amt";
    public static String amtPassword = "amt";
    public static String  ASSIGNMENT_ID_NOT_AVAILABLE= "ASSIGNMENT_ID_NOT_AVAILABLE";
    public static String USER_PROCESSED_A_SIMILAR_TASK = "This hit is not avaible to your user profile, you may have solved an identical hit. Please click 'Return HIT' to avoid any impact on your approval rating.";

    //Task constants
    public static String taskStatusNew = "New";
    public static String taskStatusOpen = "Open";
    public static String taskStatusSolved = "Solve";
    public static String taskStatusCanceled = "Cancel";
    public static String levelBased = "levelbased" ;
    public static String pathBased = "pathbased" ;
    public static int defaultReplications =10;
    public static int numberOfTestQueries=4;
    //Test tasks paramters for selection of expected distance error and hence the number of replications
    public static int minimumNumberReplicationsForProbablisticQueries =3;
    public static int maxNumberReplicationsForProbablisticQueries =15;
    public static int testOrdertForProbalisticQueries =3;
    public static int numberOfProbalisticQueries =4;
    public static int dataSet1TestTreeForProbalisticQueries = 5;
    public static int dataSet2TestTreeForProbalisticQueries = 6;
    //Task status constanst
    public static int task_available =0;
    public static int task_assgined =1;
    public static int task_solved =2;
    public static int task_assginedpostponed =3; //task delayed but it has been assgined to user
    public static int task_unassginedpostponed =4;//task delayed but it has not been assgined to user

    //Task Type constanst
    public static String astar = "astar";
    public static String breadth = "breadth";
    public static String depth = "depth";
    public static String informed = "informed";
    public static String insert = "insert";
    public static String test = "test";
    public static int equality_true =1;  //this means that the task viewed by the user is allowed to have the equality shortcut
    public static int equality_false =0;  //this means that the task viewed by the user is NOT allowed to have the equality shortcut

    //A* Backtracking constants
    public static String GREATER_THAN_SUBTREE = "1000000" ;
    public static String LESS_THAN_SUBTREE = "-2" ;
    public static String MAX_SUBTREE_KEY = "100000" ;
    public static String MIN_SUBTREE_KEY = "-1" ;
    public static String SQUARES_DATA_SET_ID = "1" ;
    public static int UPDATE_FACTOR = 2 ;
    public static int DIRECTION_HIGHER = 2 ;
    public static int DIRECTION_LOWER = 1 ;

    //Tree insertion constants
    public static int MIN_KEY_VALUE = 1 ;
    public static int MAX_KEY_VALUE = 10000 ;
    public static int KEY_RELATION_LESS =-1;
    public static int KEY_RELATION_Between =0;
    public static int KEY_RELATION_Greater =1;

    public static String Unselected_item = "unseleceted" ;
    public  static  int MAX_NODE_RANGE= 20000;

    //UI constants
    public static int MaxUserTasks=100;
    public static int MaxAMTUserTasks=10;

    //DataSetCOnstants
    public static int minSquaresDataSet=1;
    public static int minCarsDataSet=1;
    public static int maxSquaresDataSet=149;
    public static int maxCarsDataSet=1080;
    public static int itemsToQueryInTheCarsDataset=50;//any item key that is a multiple of this number will not be added to the index to and this will be the set of query items

    //TODO Obsoloete core to be removed
    public  static  int M = 4;    // max children per B-tree node = M-1
    public  static  int imageRectangleSize = 200;    // max children per B-tree node = M-1
    public static String path= "squareimages";
    //   public static String queryModel = pathBased;  //node based or level based
    public static String queryModel = levelBased;  //node based or level based
    public static int assignmentDurationInSeconds=120;

    static final int MaxReplications = 3;




}
