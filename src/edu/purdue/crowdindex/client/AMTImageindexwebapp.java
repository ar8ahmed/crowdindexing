package edu.purdue.crowdindex.client;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.widgetideas.client.SliderBar;

import edu.purdue.crowdindex.shared.control.Constants;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AMTImageindexwebapp implements EntryPoint {
	// Init. Logger
	
	Logger logger = Logger.getLogger("AMTPageLogger");	
    
	
    private static String buttonWidth = "10em";
    private static String longseperator = "40em";
    private static String smallButtonWidth = "3em";
    private static int MAX_DISPLAY_ITEM = 6;
    String taskId;
    String queryId;
    String queryItem;
    String taskType;
    String equality;
    String level;
    String dataItems;
    String dataset;
    String dataItemsDetails;
    String dataInformedFrequenceies;
    Integer maxDepthTasks;

    String assignmentId;
    String hitId;
    String amtWorkerId;
    String passedTaskId ;
    String passedGroupId ;

    FlowPanel fPanelLeft;
    FlowPanel answerWrapper;
    FlowPanel selector;

    HorizontalPanel fPanelMiddle;
    FlowPanel fPanelRight;
    //    HorizontalPanel center;
    RootPanel center;
    String selectedResult;
    Label textToServerLabel;
    boolean resultSend;
    DialogBox dialogBox;
    DialogBox confirmSubmitBox;

    Button accept;
    Button cancel;
    HTML confirmMessage;

    DialogBox tasksDoneBox;
    Button done;
    Button retake;
    HTML taskDoneMessage;

    Button closeButton;
    HTML serverResponseLabel;
    TextBox nameField;
    PasswordTextBox passwordField;
    Button registerUserButton;
    Button getTaskButton;
    Button resetButton;
    Button skipButton;
    Button signUpButton;
    Button test;
    String userId;
    String userName= "Login Error!!!";
    Label errorLabel;
    Label taskInfoLabel;
    Label taskAvailableLable;



    ProgressBar bar;

    Label warninglabel;
    Label instructionslabel;
    Label userNameLabel;
    Label passwordLable;
    ClickHandler userSelectionHandler;
    int progress;

    /**
     * 
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);

    public static native void js_slideLeftQueryContent() /*-{
		$wnd.slideLeftQueryContent();
	}-*/;

    public static native void js_slideRightQueryContent() /*-{
		$wnd.slideRightQueryContent();
	}-*/;

    public static native void js_slideLeftAnswerContent() /*-{
		$wnd.slideLeftAnswerContent();
	}-*/;

    public static native void js_slideRightAnswerContent() /*-{
		$wnd.slideRightAnswerContent();
	}-*/;

    public static native void js_toggleMoreImgs(String container) /*-{
		$wnd.toggleMoreImgs(container);
	}-*/;

    private void getTask() {
    	
    	
        errorLabel.setText("");
        String textToServer = userId + ";" + taskId + ";" + taskType + ";"
                + queryId;
        // Then, we send the input to the server.
        // getTaskButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        if (resultSend) {
            greetingService.getTask(textToServer, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    errorLabel.setText(SERVER_ERROR);
                }

                @Override
                public void onSuccess(String result) {
                    if (!result.equals("No  tasks for now")) {
                        selectedResult = Constants.Unselected_item;
                        resultSend = false;
                        String[] s = result.split(";");
                        taskId = s[0];
                        queryId = s[1];
                        queryItem = s[2];
                        equality = s[3];
                        level = s[4];
                        dataItems = s[5];
                        taskType = s[6];
                        dataset = s[7];
                        if ("2".equals(dataset)) {
                            dataItemsDetails = s[8];
                            logger.info(dataItemsDetails);
                        }
                        else
                            dataItemsDetails=null;
                        if ("informed".equals(taskType)) {
                            if(s.length>=10)
                                dataInformedFrequenceies = s[9];
                            else
                                dataInformedFrequenceies=null;
                        }
                        else
                            dataInformedFrequenceies = null;
                        if(Constants.depth.equals(taskType)){
                            int  currentTaskCount= maxDepthTasks-Integer.parseInt(level);
                            // taskInfoLabel.setText("Performing comparison task "+currentTaskCount+" out of "+maxDepthTasks+" overall comparison tasks in this HIT ");
                        }
                        // taskInfoLabel.setText("Task id:"+taskId+" Task type:"+taskType+" Equality:"+equality);
                        addVerticalFlowPanelText();
                        addVerticalFlowPanelMain();
                    } else {
                        errorLabel.setText(result);
                        taskInfoLabel.setText("");
                        //  submitForm();
                    }
                }
            });
        } else {
            errorLabel
            .setText("Please submit the result of the previous task first");
            taskInfoLabel.setText("");
        }
    }
    private void getTaskByID() {
        errorLabel.setText("");
        if(assignmentId==null||Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId)){
            assignmentId=Constants.ASSIGNMENT_ID_NOT_AVAILABLE;
            hitId="";
            amtWorkerId="";
        }

        String textToServer = userId + ";" + taskId + ";" + taskType + ";"
                + queryId+ ";"+assignmentId+";"+hitId+";"+amtWorkerId;
        // Then, we send the input to the server.
        // getTaskButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");

        greetingService.getTaskById(textToServer, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(SERVER_ERROR);
            }

            @Override
            public void onSuccess(String result) {
                if (!Constants.USER_PROCESSED_A_SIMILAR_TASK.equals(result)) {
                    selectedResult = Constants.Unselected_item;
                    resultSend = false;
                    String[] s = result.split(";");
                    taskId = s[0];
                    queryId = s[1];
                    queryItem = s[2];
                    equality = s[3];
                    level = s[4];
                    dataItems = s[5];
                    taskType = s[6];
                    dataset = s[7];
                    if ("2".equals(dataset)) {
                        dataItemsDetails = s[8];
                    }
                    else
                        dataItemsDetails=null;
                    if ("informed".equals(taskType)) {
                        if(s.length>=10)
                            dataInformedFrequenceies = s[9];
                        else
                            dataInformedFrequenceies=null;
                    }
                    else
                        dataInformedFrequenceies = null;

                    // taskInfoLabel.setText("Task id:"+taskId+" Task type:"+taskType+" Equality:"+equality);
                    if(Constants.depth.equals(taskType)){
                        maxDepthTasks = Integer.parseInt(level)+1;
                        //  taskInfoLabel.setText("Performing comparison task 1 out of "+maxDepthTasks+" overall comparison tasks in this HIT ");
                    }
                    else{
                        ;
                        //  taskInfoLabel.setText("Performing comparison task 1 out of 1 overall comparison tasks in this HIT ");
                    }

                    addVerticalFlowPanelText();
                    addVerticalFlowPanelMain();
                } else {
                    errorLabel.setText(result);
                    taskInfoLabel.setText("");
                }
            }
        });

    }
    private void getTaskByTaskGroupID() {

        errorLabel.setText("");
        if(assignmentId==null||Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId)){
            assignmentId=Constants.ASSIGNMENT_ID_NOT_AVAILABLE;
            hitId="";
            amtWorkerId="";
        }

        String textToServer = userId + ";" + passedGroupId + ";" + taskType + ";"
                + queryId+ ";"+assignmentId+";"+hitId+";"+amtWorkerId;
        // Then, we send the input to the server.
        // getTaskButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");

        greetingService.getTaskByTaskGroupId(textToServer, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(SERVER_ERROR);
            }

            @Override
            public void onSuccess(String result) {
                if (!Constants.USER_PROCESSED_A_SIMILAR_TASK.equals(result)) {
                    selectedResult = Constants.Unselected_item;
                    resultSend = false;
                    String[] s = result.split(";");
                    taskId = s[0];
                    queryId = s[1];
                    queryItem = s[2];
                    equality = s[3];
                    level = s[4];
                    dataItems = s[5];
                    taskType = s[6];
                    dataset = s[7];
                    if ("2".equals(dataset)) {
                    	
                        dataItemsDetails = s[8];

                    }
                    else
                        dataItemsDetails=null;
                    if ("informed".equals(taskType)) {
                        if(s.length>=10)
                            dataInformedFrequenceies = s[9];
                        else
                            dataInformedFrequenceies=null;
                    }
                    else
                        dataInformedFrequenceies = null;

                    // taskInfoLabel.setText("Task id:"+taskId+" Task type:"+taskType+" Equality:"+equality);
                    if(Constants.depth.equals(taskType)){
                        maxDepthTasks = Integer.parseInt(level)+1;
                        //  taskInfoLabel.setText("Performing comparison task 1 out of "+maxDepthTasks+" overall comparison tasks in this HIT ");
                    }
                    else{
                        ;
                        //   taskInfoLabel.setText("Performing comparison task 1 out of 1 overall comparison tasks in this HIT ");
                    }

                    addVerticalFlowPanelText();
                    addVerticalFlowPanelMain();
                } else {
                    errorLabel.setText(result);
                    taskInfoLabel.setText("");
                }
            }
        });

    }
    private void skipCurrentTask() {
        errorLabel.setText("");
        String textToServer = taskId;
        // Then, we send the input to the server.
        // getTaskButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");

        greetingService.skipTask(textToServer, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(SERVER_ERROR);
            }

            @Override
            public void onSuccess(String result) {
                if (result.equals("ok")) {
                    resultSend=true;
                    getTask();

                } else {
                    errorLabel.setText(result);
                    taskInfoLabel.setText("");
                }
            }
        });

    }

    void showPanels() {
        DOM.getElementById("right").getStyle().setDisplay(Display.BLOCK);
        DOM.getElementById("left").getStyle().setDisplay(Display.BLOCK);
        DOM.getElementById("container").getStyle().setDisplay(Display.BLOCK);
        DOM.getElementById("clear").getStyle().setDisplay(Display.BLOCK);
    }

    void hidePanels() {
        DOM.getElementById("right").getStyle().setDisplay(Display.NONE);
        DOM.getElementById("left").getStyle().setDisplay(Display.NONE);
        DOM.getElementById("container").getStyle().setDisplay(Display.NONE);
        DOM.getElementById("clear").getStyle().setDisplay(Display.NONE);
    }

    // addTaskIndexImage
    private void addVerticalFlowPanelMain() {
        HashMap<String, String> frequenceies = new HashMap<String, String>();
        if (Constants.informed.equals(taskType)
                && dataInformedFrequenceies != null
                && !"".equals(dataInformedFrequenceies)
                && dataInformedFrequenceies.contains(",")) {
            String[] itemsandFrequencies = dataInformedFrequenceies.split(",");
            for (String s : itemsandFrequencies) {
                if (s != null && s.contains("@")) {
                    String[] valueandFreq = s.split("@");
                    frequenceies.put(valueandFreq[0], valueandFreq[1]);
                }
            }
        }
        fPanelLeft.clear();

        answerWrapper = new FlowPanel();
        answerWrapper.getElement().setId("answer-wrapper");
        answerWrapper.getElement().setAttribute("slidevalue", "0");

        String[] dataiTemslist = dataItems.split(",");
        addLessThanPart(frequenceies);

        int i = 2;
        if (Constants.astar.equals(taskType) || "0".equals(level)) {// this is
            // to
            // dispaly
            // the first
            // item in
            // the list
            i = 1;
        }
        for (; i < dataiTemslist.length; i++) {

            if (dataset.equals("1")) {
                addDataItemsForDataSet1(frequenceies, i, dataiTemslist);
            } else {
                addDataItemsForDataSet2(frequenceies, i, dataiTemslist);
            }
            addSeperatorOrGreaterThanItem(frequenceies, i, dataiTemslist);
        }

        FlowPanel answerWrapperView = new FlowPanel();
        answerWrapperView.getElement().setId("answer-wrapper-view");
        answerWrapperView.add(answerWrapper);
        int w = dataiTemslist.length;
        //        int w = (dataiTemslist.length-1)/2;
        //        Window.alert("dataitemlist length = "+dataiTemslist.length+", w = "+w);
        if (w > MAX_DISPLAY_ITEM) {
            // create arrow buttons
            PushButton scrollLeft = new PushButton(" ");
            scrollLeft.getElement().setId("tn-scroll-left");
            scrollLeft.setHTML("<span class=\"fa fa-chevron-left\"></span>");
            scrollLeft.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    js_slideLeftAnswerContent();
                }
            });

            PushButton scrollRight = new PushButton(" ");
            scrollRight.getElement().setId("tn-scroll-right");
            scrollRight.setHTML("<span class=\"fa fa-chevron-right\"></span>");
            scrollRight.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    js_slideRightAnswerContent();
                }
            });

            fPanelLeft.getElement().setId("asnwer-container");
            fPanelLeft.add(scrollLeft);
            fPanelLeft.add(answerWrapperView);
            fPanelLeft.add(scrollRight);

        }
        else{
            fPanelLeft.getElement().setId("asnwer-container");
            fPanelLeft.add(answerWrapperView);
        }

        center.add(fPanelLeft);

        // center.clear();
        // center.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        // center.add(fPanelLeft);
    }

    /**
     * This method adds the less than component of the selection
     */
    void addLessThanPart(HashMap<String, String> frequenceies) {
        RadioButton b;

        FlowPanel wrapper2 = new FlowPanel();
        wrapper2.addStyleName("first-answer-item");

        b = new RadioButton("radioGroup");
        b.setHeight(smallButtonWidth);
        b.setWidth(buttonWidth);
        if (taskType.equals(Constants.astar) || "0".equals(level))
            b.setTitle("" + Constants.LESS_THAN_SUBTREE);
        else
            b.setTitle("" + 0);
        b.addStyleName("my-button");
        b.addClickHandler(userSelectionHandler);

        FlowPanel selector = new FlowPanel();
        selector.addStyleName("select-option");
        selector.add(b);

        // wrapper2.add(b);
        Label l;
        if (Constants.SQUARES_DATA_SET_ID.equals(dataset))
            l = new Label("Smaller size.");
        else
            l = new Label("Less expensive.");

        l.setWidth(buttonWidth);

        if ("informed".equals(taskType) && dataInformedFrequenceies != null
                && !"".equals(dataInformedFrequenceies)
                && dataInformedFrequenceies.contains(",")) {
            String value = frequenceies.get("" + 0);
            if (value != null && !"0".equals(value) && !"".equals(value)) {
                Label informed_count = new Label("(" + value + ")");
                selector.add(informed_count);
                // add border
                // selector.getElement().getStyle()
                // .setBorderWidth(new Double(value), Unit.PX);
            }

        }

        wrapper2.add(selector);
        wrapper2.add(l);

        // non zero levels are allowed to have less than
        // zero levels for insetion and backtracking are allowed to have less
        // than in leaf level
        if (!"0".equals(level)
                || ("0".equals(level) && (Constants.insert.equals(taskType) || Constants.astar
                        .equals(taskType)))) {
            answerWrapper.add(wrapper2);
            // fPanelLeft.add(answerWrapper);
        }
    }

    /**
     * This method adds the images of items of dataset1
     */
    void addDataItemsForDataSet1(HashMap<String, String> frequenceies, int i,
            String[] dataiTemslist) {
        RadioButton b = new RadioButton("radioGroup");
        b.setHeight(smallButtonWidth);
        b.setWidth(buttonWidth);
        b.setTitle("" + (2 * (i - 1) - 1));
        if (i == dataiTemslist.length - 1 && taskType.equals(Constants.astar)) {
            b.setTitle("" + Constants.MAX_SUBTREE_KEY);
        }
        b.addStyleName("my-button");
        if ((("" + Constants.equality_true).equals(equality) || "0"
                .equals(level)) && !Constants.insert.equals(taskType))
            b.addClickHandler(userSelectionHandler);

        Image image = new Image("squareimages/(" + dataiTemslist[i] + ").jpg");
        image.setHeight(buttonWidth);
        image.setWidth(buttonWidth);
        Image blankImage = new Image();
        // blankImage.setHeight(smallButtonWidth);
        // blankImage.setWidth(buttonWidth);
        blankImage.setStyleName("blankImage");
        PushButton button = new PushButton(image);
        button.setTitle("" + (2 * (i - 1) - 1));
        if (i == dataiTemslist.length - 1 && taskType.equals(Constants.astar)) {
            button.setTitle("" + Constants.MAX_SUBTREE_KEY);
        }
        // System.out.println("test title is " + button.getTitle());
        button.setHeight(buttonWidth);
        button.setWidth(buttonWidth);
        if ((("" + Constants.equality_true).equals(equality) || "0"
                .equals(level)) && !Constants.insert.equals(taskType))
            button.addClickHandler(userSelectionHandler);
        button.addStyleName("my-button");

        selector = new FlowPanel();
        selector.addStyleName("select-option");
        //selector.add(b);

        // VerticalPanel wrapper2 = new VerticalPanel();
        // wrapper2.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        FlowPanel wrapper2 = new FlowPanel();

        if ((("" + Constants.equality_true).equals(equality) || "0"
                .equals(level)) && !Constants.insert.equals(taskType))
            selector.add(b);
        else
            selector.add(blankImage);

        if ("informed".equals(taskType) && dataInformedFrequenceies != null
                && !"".equals(dataInformedFrequenceies)
                && dataInformedFrequenceies.contains(",")) {
            String value = frequenceies.get("" + button.getTitle());
            if (value != null && !"0".equals(value) && !"".equals(value)) {
                Label informed_count = new Label("(" + value + ")");
                selector.add(informed_count);
                // add border
                // selector.getElement().getStyle()
                // .setBorderWidth(new Double(value), Unit.PX);
            }

        }
        wrapper2.add(selector);
        wrapper2.add(button);
        wrapper2.addStyleName("image-answer-item");
        answerWrapper.add(wrapper2);
        // fPanelLeft.add(selector);
    }

    /**
     * This method adds the images of items of dataset2
     */
    void addDataItemsForDataSet2(HashMap<String, String> frequenceies, int i,
            String[] dataiTemslist) {
        String[] dataItemsDetailsList = dataItemsDetails.split("@");
        String[] ss = dataItemsDetailsList[i].split("'");
        String itemstrdetails = ss[2];
       logger.info("item-details["+i+"]: \n"+itemstrdetails);
      
        final FlowPanel hPanel = new FlowPanel();
        hPanel.addStyleName("image-answer-item");
        final String itemId = dataiTemslist[i];
        hPanel.getElement().setId(itemId);

        Image blankImage = new Image();
        // blankImage.setHeight(smallButtonWidth);
        // blankImage.setWidth(buttonWidth);
        blankImage.setStyleName("blankImage");

        // VerticalPanel hPanel = new VerticalPanel();
        Label l = new Label();
        l.setText("     ");
        Label l2 = new Label();
        l2.setWidth(buttonWidth);
        l2.setText(ss[2]);
        RadioButton b = new RadioButton("radioGroup", "         ");
        b.setHeight(smallButtonWidth);
        b.setWidth(buttonWidth);
        b.setTitle("" + (2 * (i - 1) - 1));
        if (i == dataiTemslist.length - 1 && taskType.equals(Constants.astar)) {
            b.setTitle("" + Constants.MAX_SUBTREE_KEY);
        }
        b.addStyleName("my-button");
        b.addClickHandler(userSelectionHandler);
        // hPanel.add(new HTML(""));
        // hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);

        selector = new FlowPanel();
        selector.addStyleName("select-option");

        if ((("" + Constants.equality_true).equals(equality) || "0"
                .equals(level)) && !Constants.insert.equals(taskType))
            selector.add(b);
        else
            selector.add(blankImage);

        if ("informed".equals(taskType) && dataInformedFrequenceies != null
                && !"".equals(dataInformedFrequenceies)
                && dataInformedFrequenceies.contains(",")) {
            String value = frequenceies.get("" + (2 * (i - 1) - 1));
            if (value != null && !"0".equals(value) && !"".equals(value)) {
                Label informed_count = new Label("(" + value + ")");
                selector.add(informed_count);

                // add border
                // selector.getElement().getStyle()
                // .setBorderWidth(new Double(value), Unit.PX);
            }

        }

        hPanel.add(selector);

        int imgCount = 0;
        for (int j = 0; j < new Integer(ss[0]) && j < 4; j++) {
            ++imgCount;
            Image image = new Image("data/Image_" + dataiTemslist[i] + "/("
                    + dataiTemslist[i] + "_" + j + ").jpg");
            image.setHeight(buttonWidth);
            image.setWidth(buttonWidth);
            PushButton b2 = new PushButton(image);
            b2.getElement().setAttribute("idata", itemstrdetails);
            b2.addStyleName("img-PushButton");
            b2.setTitle("" + (2 * (i - 1) - 1));
            if (i == dataiTemslist.length - 1
                    && taskType.equals(Constants.astar)) {
                b2.setTitle("" + Constants.MAX_SUBTREE_KEY);
            }
            b2.setHeight(buttonWidth);
            b2.setWidth(buttonWidth);
            if ((("" + Constants.equality_true).equals(equality) || "0"
                    .equals(level)) && !Constants.insert.equals(taskType))
                b2.addClickHandler(userSelectionHandler);
            if (j > 0)
                b2.addStyleName("hidden");
            hPanel.add(b2);

        }

        if (imgCount > 1) {
            PushButton moreImgsToggle = new PushButton(" ");
            moreImgsToggle.getElement().setId("tn-toggle-imgs-" + itemId);
            moreImgsToggle.addStyleName("tn-toggle-imgs");

            moreImgsToggle
            .setHTML("<span class=\"fa fa-chevron-down\"></span>");
            moreImgsToggle.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    js_toggleMoreImgs(itemId);
                }
            });
            hPanel.add(moreImgsToggle);
        }

        answerWrapper.add(hPanel);
        // fPanelLeft.add(hPanel);
    }

    /**
     * This method adds the seperator or the last item in the main window
     * 
     * @param frequenceies
     * @param i
     * @param dataiTemslist
     */
    void addSeperatorOrGreaterThanItem(HashMap<String, String> frequenceies,
            int i, String[] dataiTemslist) {
        Image blankImage = new Image();
        //  blankImage.setHeight(smallButtonWidth);
        // blankImage.setWidth(buttonWidth);
        blankImage.setStyleName("blankImageSmall");

        // VerticalPanel wrapper2 = new VerticalPanel();
        FlowPanel wrapper2 = new FlowPanel();

        RadioButton b = new RadioButton("radioGroup");
        b.setTitle("" + (2 * (i - 1)));
        if (i == dataiTemslist.length - 1 && taskType.equals(Constants.astar)) {
            b.setTitle("" + Constants.GREATER_THAN_SUBTREE);
        }
        // System.out.println("test title is " + b.getTitle());
        b.addStyleName("my-button");
        b.addClickHandler(userSelectionHandler);
        Image im = new Image("title/seperator.jpg");
        // wrapper2.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        Label l = new Label();

        selector = new FlowPanel();
        selector.addStyleName("select-option");

        // add separator, otherwise add last item
        if (i != dataiTemslist.length - 1) {
            // at non zero level the seperator exist
            // at level zero only insertions are allowed to have a selection for
            // below

            if (!"0".equals(level)
                    || ("0".equals(level) && (Constants.insert.equals(taskType))))
                selector.add(b);
            //  else
            //     selector.add(blankImage);
            l = new Label();
            l.setWidth(smallButtonWidth);

            // if ("1".equals(dataset))
            // im.setHeight(buttonWidth);
            // else
            // im.setHeight(longseperator);
            // im.setWidth(smallButtonWidth);

            if ("informed".equals(taskType) && dataInformedFrequenceies != null
                    && !"".equals(dataInformedFrequenceies)
                    && dataInformedFrequenceies.contains(",")) {
                String value = frequenceies.get("" + (2 * (i - 1)));
                if (value != null && !"0".equals(value) && !"".equals(value)) {
                    Label informed_count = new Label("(" + value + ")");
                    selector.add(informed_count);
                    // add border
                    // selector.getElement().getStyle()
                    // .setBorderWidth(new Double(value), Unit.PX);
                }

            }

            wrapper2.addStyleName("separator-answer-item");
            wrapper2.add(selector);
            wrapper2.add(im);
            // fPanelLeft.add(wrapper2);
            answerWrapper.add(wrapper2);
        }
        // non zero levels are allowed to have greater than
        // zero levels for insetion and backtracking are allowed to have greater
        // than in leaf level
        else if (!"0".equals(level)
                || ("0".equals(level) && (Constants.insert.equals(taskType) || Constants.astar
                        .equals(taskType)))) {

            selector.add(b);
            if ("1".equals(dataset))
                l = new Label("Larger size.");
            else
                l = new Label("More Expensive.");
            l.setWidth(buttonWidth);

            wrapper2.add(selector);
            wrapper2.addStyleName("last-answer-item");
            wrapper2.add(l);
            if ("informed".equals(taskType) && dataInformedFrequenceies != null
                    && !"".equals(dataInformedFrequenceies)
                    && dataInformedFrequenceies.contains(",")) {
                String value = frequenceies.get("" + (2 * (i - 1)));
                if (value != null && !"0".equals(value) && !"".equals(value)) {
                    Label informed_count = new Label("(" + value + ")");
                    selector.add(informed_count);
                    // add border
                    // selector.getElement().getStyle()
                    // .setBorderWidth(new Double(value), Unit.PX);
                }
            }
            answerWrapper.add(wrapper2);
            // fPanelLeft.add(wrapper2);

        }

    }

    void displayImagesOfAnItem(String radioButtonTitle, String dataitemKey,
            HashMap<String, String> frequenceies, String dataItemDetails) {
    	
        VerticalPanel wrapper2 = new VerticalPanel();
        RadioButton b;
        Image image;
        if (dataset.equals("1")) {
            b = new RadioButton("radioGroup");
            b.setHeight(smallButtonWidth);
            b.setWidth(buttonWidth);
            b.setTitle("" + radioButtonTitle);
            b.addStyleName("my-button");
            b.addClickHandler(userSelectionHandler);

            image = new Image("squareimages/(" + dataitemKey + ").jpg");
            image.setHeight(buttonWidth);
            image.setWidth(buttonWidth);
            PushButton button = new PushButton(image);
            button.setTitle("" + radioButtonTitle);
            //System.out.println("test title is " + button.getTitle());
            button.setHeight(buttonWidth);
            button.setWidth(buttonWidth);
            button.addClickHandler(userSelectionHandler);
            button.addStyleName("my-button");
            wrapper2.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
            wrapper2.add(b);
            wrapper2.add(button);
            if ("informed".equals(taskType) && dataInformedFrequenceies != null
                    && !"".equals(dataInformedFrequenceies)
                    && dataInformedFrequenceies.contains(",")) {
                String value = frequenceies.get("" + button.getTitle());
                if (value != null && !"0".equals(value) && !"".equals(value))
                    wrapper2.setBorderWidth(new Integer(value));

            }
            fPanelLeft.add(wrapper2);
        } else {
            String[] ss = dataItemDetails.split("'");
            VerticalPanel hPanel = new VerticalPanel();
            Label l = new Label();
            l.setText("     ");
            Label l2 = new Label();
            l2.setWidth(buttonWidth);
            l2.setText(ss[2]);
            b = new RadioButton("radioGroup", "         ");
            b.setHeight(smallButtonWidth);
            b.setWidth(buttonWidth);
            b.setTitle("" + radioButtonTitle);
            b.addStyleName("my-button");
            b.addClickHandler(userSelectionHandler);
            hPanel.add(new HTML(""));
            hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
            hPanel.add(b);
            for (int j = 0; j < new Integer(ss[0]) && j < 4; j++) {
                image = new Image("data/Image_" + dataitemKey + "/("
                        + dataitemKey + "_" + j + ").jpg");
                image.setHeight(buttonWidth);
                image.setWidth(buttonWidth);
                PushButton b2 = new PushButton(image);
                b2.setTitle("" + radioButtonTitle);
                b2.setHeight(buttonWidth);
                b2.setWidth(buttonWidth);
                b2.addClickHandler(userSelectionHandler);
                hPanel.add(b2);
            }
            if ("informed".equals(taskType) && dataInformedFrequenceies != null
                    && !"".equals(dataInformedFrequenceies)
                    && dataInformedFrequenceies.contains(",")) {
                String value = frequenceies.get("" + radioButtonTitle);
                if (value != null && !"0".equals(value) && !"".equals(value))
                    hPanel.setBorderWidth(new Integer(value));

            }
            fPanelLeft.add(hPanel);
        }
    }

    void showMessageBox(String text) {
        serverResponseLabel.setHTML(new SafeHtmlBuilder().appendEscapedLines(
                text).toSafeHtml());
        dialogBox.center();
        closeButton.setFocus(true);
    }

    void showTasksDoneBox() {

        taskDoneMessage.setText("Would you like to retake the survey?\n\n");
        tasksDoneBox.center();
        done.setFocus(true);
    }

    void showConfirmMessageBox() {

        confirmMessage.setText("Are you sure you selected the corret answer?");
        confirmSubmitBox.center();
        accept.setVisible(true);
        cancel.setVisible(true);
        accept.setFocus(true);
    }

    private PushButton createImagePushButton(String queryItem,
            String buttonWidth, String buttonHeight, String imgUrl) {

        // image = new
        // Image("data/Image_"+queryItem+"/("+queryItem+"_"+j+").jpg");

        Image image = new Image(imgUrl);
        image.setHeight(buttonWidth);
        image.setWidth(buttonWidth);

        PushButton b2 = new PushButton(image);

        b2.addStyleName("query-image");
        b2.setTitle("" + queryItem);

        b2.setHeight(buttonWidth);
        b2.setWidth(buttonWidth);
        //        b2.addClickHandler(new ClickHandler() {
        //            @Override
        //            public void onClick(ClickEvent event) {

        //                // selectedResult = ( (PushButton)
        //                // event.getSource()).getTitle();
        //                // System.out.println("Result selected as button " +(
        //                // (PushButton) event.getSource()).getTitle());
        //            }
        //        });
        return b2;
    }

    // add query and insructions
    private void addVerticalFlowPanelText() {
        // fPanelRight = new FlowPanel();
        fPanelRight.clear();
        SimplePanel wrapper = new SimplePanel();

        Image image;
        // errorLabel.setText(dataset);
        Label l;
        String queryText = "";
        if (dataset.equals("1")) {

            l = new HTML(
                    new SafeHtmlBuilder()
                    .appendEscapedLines(
                            "You are given the following image of the square under question:\r\n\r\n")
                            .toSafeHtml());
            l.setStyleName("instructionlabel");
            fPanelRight.add(l);
            queryText = "\r\n\r\nPlease compare the square side length of the image ABOVE to that of squares BELOW based on square side length.\r\n"
                    + "If you see scroll buttons use them  see all images below .\r\n";
            if(!"0".equals(level)){
                queryText =queryText  + "If you think the side length of the square ABOVE is LESS than all images BELOW, choose the SMALLER SIZE radio button .\r\n";
                queryText =queryText  + "If you think the side length of the square ABOVE is GREATER than all images BELOW, choose the LARGER SIZE radio button .\r\n";
                queryText =queryText  + "If you think the side length of the square ABOVE falls BETWEEN two of the images BELOW, choose the radio button BETWEEN those two images.\r\n";
            }
            if( "0".equals(level)||(equality!=null&&("" + Constants.equality_true).equals(equality)==true))
                queryText =queryText +  "Choose the radio button on top the square image BELOW that is CLOSEST in size to the sqaure image ABOVE .\r\n";
            if( "informed".equals(taskType))
                queryText =queryText +  "The number appearing on top of a radio button represnts how many other workers choose this answer.\r\n";


            // image = new
            // Image("http://storage.googleapis.com/crowdindex/data/squareimages/("+
            // queryItem + ").jpg");
            image = new Image("squareimages/(" + queryItem + ").jpg");
            image.setHeight(buttonWidth);
            image.setWidth(buttonWidth);

            PushButton b = new PushButton(image);
            b.setHeight(buttonWidth);
            b.setWidth(buttonWidth);
            b.addStyleName("my-button");
            b.setTitle(queryItem);

            wrapper.add(b);

            fPanelRight.add(l);
            wrapper.setStyleName("centerplacement");
            fPanelRight.add(wrapper);
            l = new HTML(new SafeHtmlBuilder().appendEscapedLines(queryText)
                    .toSafeHtml());
            l.setStyleName("instructionlabel");
            fPanelRight.add(l);
        } else {
            l = new HTML(
                    new SafeHtmlBuilder()
                    .appendEscapedLines(
                            "You are given the following image(s) of the car under question:\r\n\r\n")
                            .toSafeHtml());
            l.setStyleName("instructionlabel");
            fPanelRight.add(l);
            queryText = "\r\n\r\nPlease compare the car ABOVE to that of cars BELOW based on your estimation of the selling price of the cars.\r\n"
                    + "If you see scroll buttons use them  see all images below .\r\n";
            if(!"0".equals(level)){
                queryText =queryText  + "If you think the selling price of the car ABOVE is LESS than all cars BELOW, choose the LESS EXPENSIVE radio button .\r\n";
                queryText =queryText  + "If you think the selling price of the car ABOVE is GREATER than all cars BELOW, choose the MORE EXPENSIVE radio button .\r\n";
                queryText =queryText  + "If you think the selling price of the car ABOVE falls BETWEEN two of the cars BELOW, choose the radio button BETWEEN those two cars.\r\n";
            }
            if( "0".equals(level)||(equality!=null&&("" + Constants.equality_true).equals(equality)==true))
                queryText =queryText +  "If you think the selling price of the car ABOVE is Equal to one of the cars BELOW Choose the radio button on top the car image BELOW that is CLOSEST in size to the car images ABOVE .\r\n";
            if( "informed".equals(taskType))
                queryText =queryText +  "The number appearing on top of a radio button represnts how many other workers choose this answer.\r\n";



            image = new Image("data/Image_" + queryItem + "/(" + queryItem
                    + "_0).jpg");
            String[] dataItemsDetailsList = dataItemsDetails.split("@");

            String[] ss = dataItemsDetailsList[0].split("'");
            String itemstrdetails = ss[2];
            logger.info("query-item-details : \n"+itemstrdetails);
            
            // showMessageBox("hi"+dataItemsDetails);
            // HorizontalPanel hPanel = new HorizontalPanel();
            // hPanel.setSpacing(5);
            // hPanel.add(new HTML(""));
            // errorLabel.setText(dataItemsDetailsList[i-1]);

            FlowPanel wrapHPanel = new FlowPanel();
            wrapHPanel.getElement().setId("query-wrapper");

            FlowPanel hPanelview = new FlowPanel();
            hPanelview.getElement().setId("query-content-view");

            final FlowPanel hPanel = new FlowPanel();
            hPanel.getElement().setId("query-content");            
            hPanel.getElement().setAttribute("slidevalue", "0");

            Label l2 = new Label();
            l2.setText(ss[2]);
            l2.setStyleName("centerplacement");

            // add query images
            for (int j = 0; j < new Integer(ss[0]) && j < 4; j++) {

                // String imgUrl =
                // "http://storage.googleapis.com/crowdindex/data/Image_"
                // + queryItem + "/(" + queryItem + "_" + j + ").jpg";
                String imgUrl = "data/Image_" + queryItem + "/(" + queryItem
                        + "_" + j + ").jpg";
                PushButton b2 = createImagePushButton(queryItem, buttonWidth,
                        buttonWidth, imgUrl);
                b2.getElement().setAttribute("idata", itemstrdetails);
                hPanel.add(b2);

            }

            // // create arrow buttons
            // PushButton scrollLeft = new PushButton(" ");
            // scrollLeft.getElement().setId("query-scroll-left");
            // scrollLeft.setHTML("<span class=\"fa fa-chevron-left\"></span>");
            // scrollLeft.setWidth("2em");
            // scrollLeft.setHeight(buttonWidth);
            //
            // scrollLeft.addClickHandler(new ClickHandler() {
            //
            // @Override
            // public void onClick(ClickEvent event) {
            // js_slideLeftQueryContent();
            // }
            // });
            //
            // PushButton scrollRight = new PushButton(" ");
            // scrollRight.getElement().setId("query-scroll-right");
            // scrollRight.setHTML("<span class=\"fa fa-chevron-right\"></span>");
            // scrollRight.setWidth("2em");
            // scrollRight.setHeight(buttonWidth);
            //
            // scrollRight.addClickHandler(new ClickHandler() {
            //
            // @Override
            // public void onClick(ClickEvent event) {
            // js_slideRightQueryContent();
            // }
            // });

            //			hPanelview.add(hPanel);
            // wrapHPanel.add(scrollLeft);
            //			wrapHPanel.add(hPanelview);
            // wrapHPanel.add(scrollRight);
            //			fPanelRight.add(wrapHPanel);

            // hPanel.setStyleName("centerplacement");
            // hPanel.setBorderWidth(1);
            fPanelRight.add(hPanel);
            l = new HTML(new SafeHtmlBuilder().appendEscapedLines(queryText)
                    .toSafeHtml());
            l.setStyleName("instructionlabel");

            fPanelRight.add(l);
            // fPanelRight.add(l2);
            /*
             * int i =4,j=3; Grid grid = new Grid(i, j);
             * 
             * int numRows = grid.getRowCount(); int numColumns =
             * grid.getColumnCount(); for (int row = 0; row < numRows; row++) {
             * 
             * for (int col = 0; col < numColumns; col++) { image = new
             * Image("data/Image_"+queryItem+"/("+queryItem+"_"+j+").jpg");
             * image.setHeight(buttonWidth); image.setWidth(buttonWidth);
             * grid.setWidget(row, col, image);
             * 
             * } } fPanelRight.add(grid);
             */

        }

        // System.out.println(dataset);

        SimplePanel wrapper2 = new SimplePanel();
        PushButton b2 = new PushButton("Submit Result..");
        b2.setHeight(smallButtonWidth);
        b2.setWidth(buttonWidth);
        b2.addStyleName("my-button");
        b2.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                sendResultToServer(selectedResult);
                //System.out.println("Sending results to the server");
            }
        });
        b2.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {

                if (event.getNativeKeyCode() == 32) {
                    sendResultToServer(selectedResult);
                    //System.out.println("Sending results to the server");
                }

            }
        });
        wrapper2.add(b2);

        // fPanelRight.add(wrapper2);

        RootPanel.get("right").add(fPanelRight);
    }

    private void sendResultToServer(String result) {
        // First, we validate the input.
        //submitForm();

        if (selectedResult.equals(Constants.Unselected_item)) {
            errorLabel.setText("Please select an answer first");
        } else if (resultSend) {
            errorLabel
            .setText("Result already send to server please get another task");
        } else if (!resultSend) {
            String toSend = taskId + ";"
                    + userId + ";"
                    + result + ";"
                    + taskType+ ";"
                    + assignmentId+ ";"
                    + hitId+ ";"
                    + amtWorkerId;
            greetingService.returnResultAMT(toSend, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {

                    errorLabel.setText("Error in sending result to server");
                }

                @Override
                public void onSuccess(String result) {
                    errorLabel.setText(result);
                    if("Result send successfully".equals(result)){
                        resultSend = true;
                        getTaskButton.setEnabled(true);

                        fPanelLeft.clear();
                        fPanelRight.clear();
                        bar.setProgress(++progress);
                        if((Constants.depth.equals(  taskType) &&!"0".equals(level)))
                            getTask();
                        else
                            submitForm();
                    }


                }
            });
        }

    }

    void submitForm(){
        Element assignmentIdField = DOM.getElementById("assignmentIdField");
        ((InputElement)assignmentIdField).setValue(assignmentId);

        Element workerIdField = DOM.getElementById("workerIdField");
        ((InputElement)workerIdField).setValue(amtWorkerId);

        Element hitIdField = DOM.getElementById("hitIdField");
        ((InputElement)hitIdField).setValue(hitId);



        Element submitIdField = DOM.getElementById("submitIdField");
        //   e.setInnerText(assignmentId);
        ((InputElement)submitIdField).click();


    }
    /**
     * This is the entry point method.
     */

    void readGetRequestArguments(){
        passedTaskId = com.google.gwt.user.client.Window.Location.getParameter("taskId");
        passedGroupId = com.google.gwt.user.client.Window.Location.getParameter("groupId");
        assignmentId = com.google.gwt.user.client.Window.Location.getParameter("assignmentId");
        hitId = com.google.gwt.user.client.Window.Location.getParameter("hitId");
        amtWorkerId =  com.google.gwt.user.client.Window.Location.getParameter("workerId");
    }

    void createUserSelectionHnadler(){
        userSelectionHandler = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                selectedResult = ((RadioButton) event.getSource()).getTitle();
                //                System.out.println("hi i was clicked and i am button "
                //                        + ((RadioButton) event.getSource()).getTitle());
                if(assignmentId!=null&&!Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId))
                    showConfirmMessageBox();
                else{
                    errorLabel.setText("Cannot operate on hit in task preview mode");
                }
                // sendResultToServer(selectedResult);
            }
        };
    }
    void initPageComponets(){
        progress = 0;
        bar = new ProgressBar(0, Constants.MaxAMTUserTasks, 0);
        FlowPanel p = new FlowPanel();
        p.add(bar);
        RootPanel.get("statusDiv").add(p);
        bar.setVisible(false);
        // bar.setWidth("50em");

        taskId = " ";
        queryId = " ";
        queryItem = " ";
        taskType = " ";

        dialogBox = new DialogBox();
        dialogBox.setText("test code status");

        resultSend = true;
        selectedResult = "-1";
        fPanelLeft = new FlowPanel();

        fPanelMiddle = new HorizontalPanel();
        fPanelMiddle
        .setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        RootPanel.get("middleslider").add(fPanelMiddle);

        SliderBar slider = new SliderBar(0.0, 100.0);
        slider.setWidth("50em");
        slider.setStepSize(1.0);
        slider.setCurrentValue(50.0);
        slider.setNumTicks(10);
        slider.setNumLabels(0);
        SimplePanel wrapper = new SimplePanel();
        wrapper.setStyleName("centerplacement");
        // wrapper.add(slider);
        fPanelMiddle.add(wrapper);

        fPanelRight = new FlowPanel();
        //        center = new HorizontalPanel();
        //        center = new FlowPanel();
        center = RootPanel.get("left");
        //        RootPanel.get("left").add(center);
        registerUserButton = new Button("Log in");
        registerUserButton.setVisible(false);
        getTaskButton = new Button(
                "<i class=\"fa fa-step-forward fa-lg\"></i> <span>Get task</span>");
        getTaskButton.setVisible(false);
        skipButton = new Button("Skip task");
        skipButton.setVisible(false);
        nameField = new TextBox();
        nameField.setText("");
        nameField.setVisible(false);
        passwordField = new PasswordTextBox();
        passwordField.setVisible(false);
        passwordField.setText("");
        passwordField.setWidth("10em");
        resetButton = new Button("<i class=\"fa fa-times fa-lg\"></i> <span>Reset</span>");
        resetButton.setVisible(false);
        signUpButton = new Button();
        signUpButton.setVisible(false);
        test = new Button("test");
        test.setVisible(false);
        errorLabel = new Label();
        errorLabel.setVisible(true);
        taskInfoLabel = new Label();
        taskInfoLabel.setVisible(true);
        taskAvailableLable = new Label();
        taskAvailableLable.setVisible(false);
        resetButton.setVisible(false);
        // resetButton.setText("Reset");
        signUpButton.setText("Sign up");

        getTaskButton.addStyleName("getTaskButton");
        getTaskButton.setEnabled(false);
        getTaskButton.setVisible(false);

        skipButton.addStyleName("getTaskButton");
        skipButton.setEnabled(false);
        skipButton.setVisible(false);

        warninglabel = new Label("");
        warninglabel.setVisible(false);
        warninglabel.setStyleName("warningLabel");
        instructionslabel = new Label("Please sign up or log in.");
        instructionslabel.setVisible(false);
        instructionslabel.setStyleName("instLabel");
        instructionslabel.setVisible(false);
        userNameLabel = new Label("User name");
        userNameLabel.setVisible(false);
        passwordLable = new Label("Password");
        passwordLable.setVisible(false);


        textToServerLabel = new Label();
        textToServerLabel.setVisible(false);
        serverResponseLabel = new HTML();
        serverResponseLabel.setVisible(false);
        // Add the nameField and getTaskButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("errorLabelContainer").add(errorLabel);
        RootPanel.get("taskInfoContainer").add(taskInfoLabel);
        RootPanel.get("taskAvailabilityLabelContainer").add(taskAvailableLable);

        RootPanel.get("nameFieldContainer").add(nameField);
        RootPanel.get("passwordFieldContainer").add(passwordField);
        RootPanel.get("registerUserContainer").add(registerUserButton);
        RootPanel.get("getTaskButtonContainer").add(getTaskButton);
        RootPanel.get("resetButtonContainer").add(resetButton);
        RootPanel.get("skipButtonContainer").add(skipButton);
        // RootPanel.get("resetButtonContainer").add(bar1);
        RootPanel.get("signUpButtonContainer").add(signUpButton);

        RootPanel.get("signinInstContanier").add(instructionslabel);
        RootPanel.get("warningInstContanier").add(warninglabel);
        RootPanel.get("userLabelContanier").add(userNameLabel);
        RootPanel.get("passwordLabelContanier").add(passwordLable);


        Element assignmentIdField = DOM.getElementById("assignmentIdField");
        ((InputElement)assignmentIdField).getStyle().setDisplay(Display.NONE);

        Element workerIdField = DOM.getElementById("workerIdField");
        ((InputElement)workerIdField).getStyle().setDisplay(Display.NONE);

        Element hitIDField = DOM.getElementById("hitIdField");
        ((InputElement)hitIDField).getStyle().setDisplay(Display.NONE);


        Element submitIdField = DOM.getElementById("submitIdField");
        ((InputElement)submitIdField).getStyle().setDisplay(Display.NONE);



    }
    void addWindwCloseingEvent(){
        Window.addWindowClosingHandler(new Window.ClosingHandler() {

            @Override
            public void onWindowClosing(ClosingEvent event) {
                if(taskId!=null&&!"".equals(taskId)&&!resultSend &&assignmentId!=null&&!Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId)){
                    //event.setMessage("You have not properly chosen an answer. Are you sure you want to leave");
                    greetingService.cleanUserData(taskId,
                            new AsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            errorLabel.setText(SERVER_ERROR);

                        }

                        @Override
                        public void onSuccess(String result) {
                            ;//Do nothing
                        }

                    });
                }

                //  event.setMessage("Really?");

            }
        });
    }
    @Override
    public void onModuleLoad() {

        readGetRequestArguments();
        createUserSelectionHnadler();
        initPageComponets();
        //  addWindwCloseingEvent();
        // RootPanel.get("signUpButtonContainer").add(test);
        // hidePanels();
        //  addTimerToRefreshState();
        // addSignUpHnadler();
        addTestButtonAndService();
        //  addResetButtonHandler();
        //  addRegisterUserHandler();
        // Focus the cursor on the name field when the app loads
        // nameField.setFocus(true);
        //  nameField.selectAll();

        // buildConcentInfo();
        buildConfirmMessageSubmissionBox();
        // buildTaskDoneMessageBox();
        if(passedGroupId!=null && !"".equals(passedGroupId)){

            if(assignmentId!=null&&!"".equals(assignmentId)&&!Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId)){
                signUpForTheCurrentAMTWorker();
                //submitForm();
            } else{

                logInAndGetTaskByTaskGroupId();
            }
        }
        else if(passedTaskId!=null && !"".equals(passedTaskId)){
            taskId= passedTaskId;
            if(assignmentId!=null&&!"".equals(assignmentId)&&!Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId)){
                signUpForTheCurrentAMTWorker();
                //submitForm();
            } else{

                logInAndGetTaskById();
            }
        }
        else {
            taskId ="";
            errorLabel.setText("Error in the hit and cannot dispaly the task");;
            errorLabel.setVisible(true);
        }


    }

    void addTimerToRefreshState() {
        // This may be no longer needed
        Timer t = new Timer() {
            @Override
            public void run() {
                if (!(userId == null || "".equals(userId))) {
                    greetingService.getAvailTask(userId,
                            new AsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            errorLabel.setText(SERVER_ERROR);

                        }

                        @Override
                        public void onSuccess(String result) {
                            String[] s = result.split(",");
                            updateTaskAvailableLabel(s);
                        }

                    });
                }
            }
        };
        t.scheduleRepeating(3000);
    }
    void signUpForTheCurrentAMTWorker(){
        greetingService.signUp(amtWorkerId + ";"
                + amtWorkerId,
                new AsyncCallback<String>() {

            @Override
            public void onSuccess(String result) {

                errorLabel.setText(result);
                errorLabel.setVisible(true);
                if(passedGroupId!=null && !"".equals(passedGroupId)){
                    logInAndGetTaskByTaskGroupId();
                }else
                    logInAndGetTaskById();
            }

            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText("Error in sign up");
                errorLabel.setVisible(true);

            }
        });
    }
    void addSignUpHnadler() {
        signUpButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!("".equals(nameField.getText()) || "".equals(passwordField
                        .getText()))) {
                    greetingService.signUp(nameField.getText() + ";"
                            + passwordField.getText(),
                            new AsyncCallback<String>() {

                        @Override
                        public void onSuccess(String result) {

                            errorLabel.setText(result);

                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            errorLabel.setText("Error in sign up");

                        }
                    });
                } else {
                    errorLabel.setText("Please enter user name and password");
                }

            }
        });
    }

    void addRegisterUserHandler() {
        registerUserButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // errorLabel.setText("login clicked");
                if (!("".equals(nameField.getText()) || "".equals(passwordField
                        .getText()))) {
                    greetingService.registerUser(nameField.getText() + ";"
                            + passwordField.getText(),
                            new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            errorLabel.setText(SERVER_ERROR);
                        }

                        @Override
                        public void onSuccess(String result) {
                            if (result.contains("Login successfull")) {
                                // showPanels();
                                String[] s = result.split(":");
                                getTaskButton.setEnabled(true);
                                getTaskButton.setVisible(true);
                                skipButton.setEnabled(true);
                                skipButton.setVisible(true);
                                registerUserButton.setVisible(false);
                                nameField.setVisible(false);
                                passwordField.setVisible(false);
                                userName = nameField.getText();
                                userId = s[1];
                                errorLabel.addStyleName("error-label");
                                //                                errorLabel.setText(result);
                                if ("1".equals(userId)) {
                                    resetButton.setVisible(true);
                                } else {
                                    resetButton.setVisible(false);
                                }
                                signUpButton.setVisible(false);
                                passwordLable.setVisible(false);
                                userNameLabel.setVisible(false);
                                instructionslabel.setVisible(false);
                                warninglabel.setVisible(false);
                                bar.setVisible(true);

                                greetingService.getAvailTask(userId,
                                        new AsyncCallback<String>() {

                                    @Override
                                    public void onFailure(
                                            Throwable caught) {
                                        errorLabel.setText("Error in getting the number of avaible tasks ");

                                    }

                                    @Override
                                    public void onSuccess(
                                            String result) {
                                        String[] s = result.split(",");
                                        // Set label for avaiable tasks
                                        setTaskAvailableLabel(s);
                                        // bar1.setText(result+" tasks avaiable");
                                        bar.setProgress(Integer
                                                .parseInt(s[1]));
                                        progress = Integer
                                                .parseInt(s[1]);
                                        if (progress >= Constants.MaxUserTasks) {
                                            showTasksDoneBox();
                                        }

                                    }

                                });

                            } else {
                                getTaskButton.setEnabled(false);
                                getTaskButton.setVisible(false);
                                skipButton.setEnabled(false);
                                skipButton.setVisible(false);
                                registerUserButton.setVisible(true);
                                nameField.setVisible(true);
                                passwordField.setVisible(true);
                                errorLabel.setText(result);
                                signUpButton.setVisible(true);
                                passwordLable.setVisible(true);
                                userNameLabel.setVisible(true);
                                instructionslabel.setVisible(true);
                                warninglabel.setVisible(true);
                                bar.setVisible(false);
                            }
                        }
                    });

                } else {
                    errorLabel.setText("Please enter user name and password");
                }
            }

        });
    }
    void logInAndGetTaskById(){
        String userName = Constants.amtUserName;
        if(assignmentId!=null&&!"".equals(assignmentId)&&!Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId))
            userName = amtWorkerId;
        greetingService.registerUser(userName + ";"
                + userName ,
                new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(SERVER_ERROR);
            }

            @Override
            public void onSuccess(String result) {
                if (result.contains("Login successfull")) {
                    // showPanels();
                    String[] s = result.split(":");
                    userId = s[1];
                    if(taskId!=null){
                        getTaskByID();
                    }else{
                        errorLabel.setText("Error in the taskId paramter and cannot dispaly the task");
                        errorLabel.setVisible(true);
                    }

                } else {
                    errorLabel.setText("Error in the app and cannot login now ");
                    errorLabel.setVisible(true);
                }
            }
        });
    }
    void logInAndGetTaskByTaskGroupId(){
        String userName = Constants.amtUserName;
        if(assignmentId!=null&&!"".equals(assignmentId)&&!Constants.ASSIGNMENT_ID_NOT_AVAILABLE.equals(assignmentId))
            userName = amtWorkerId;
        greetingService.registerUser(userName + ";"
                + userName ,
                new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(SERVER_ERROR);
            }

            @Override
            public void onSuccess(String result) {
                if (result.contains("Login successfull")) {
                    // showPanels();
                    String[] s = result.split(":");
                    userId = s[1];
                    if(passedGroupId!=null){
                        getTaskByTaskGroupID();
                    }else{
                        errorLabel.setText("Error in the task group paramter and cannot dispaly the task");
                        errorLabel.setVisible(true);
                    }

                } else {
                    errorLabel.setText("Error in the app and cannot login now ");
                    errorLabel.setVisible(true);
                }
            }
        });
    }

    void addResetButtonHandler() {
        resetButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                greetingService.reset("", new AsyncCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        errorLabel.setText("System reset sucessfully");

                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        errorLabel.setText("Error reseting the system");

                    }
                });

            }
        });

    }

    void addTestButtonAndService() {
        test.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                greetingService.test("", new AsyncCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        errorLabel.setText(result);

                    }

                    @Override
                    public void onFailure(Throwable caught) {


                    }
                });

            }
        });

    }

    void buildConcentInfo() {
        // Create the popup dialog box
        dialogBox = new DialogBox();
        dialogBox.setText("Consent infromation");
        dialogBox.setAnimationEnabled(true);
        closeButton = new Button("Accept");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");

        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        // Create a handler for the getTaskButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {
            /**
             * Fired when the user clicks on the getTaskButton.
             */
            @Override
            public void onClick(ClickEvent event) {
                sendNameToServer();
            }

            /**
             * Fired when the user types in the nameField.
             */
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendNameToServer();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a
             * response.
             */
            private void sendNameToServer() {

                getTask();

            }
        }

        // Create a handler for the getTaskButton and nameField
        class SkipTaskHandler implements ClickHandler, KeyUpHandler {
            /**
             * Fired when the user clicks on the getTaskButton.
             */
            @Override
            public void onClick(ClickEvent event) {
                skipTask();
            }

            /**
             * Fired when the user types in the nameField.
             */
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    skipTask();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a
             * response.
             */
            private void skipTask() {
                skipCurrentTask();


            }
        }
        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        getTaskButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);

        SkipTaskHandler skipTaskHandler = new SkipTaskHandler();
        skipButton.addClickHandler(skipTaskHandler);
        /* Preparing message boxes */
        showMessageBox("1) This website and tasks shown are for research purposes. "
                + "The purpose of this research is to study how humans can make decisions in a crowd-sourced environment. "
                + "The experiment aims at building and querying a tree-like index.\r\n\r\n"
                + "2) No personal information will be stored in the process.\r\n\r\n"
                + "3) No risks are involved in the process.\r\n\r\n"
                + "4) It would take about 5 minutes to finish the survey.\r\n\r\n"
                + "5) Participation is voluntary. Refusal to participate will not incur any penalty. "
                + "The user can stop and quit the experiment at any point in time.\r\n\r\n"
                + "6) Please contact Ahmed Mahmood (amahamoo@purdue.edu) for any questions.\r\n\r\n");
    }

    void buildConfirmMessageSubmissionBox() {
        confirmSubmitBox = new DialogBox();
        confirmSubmitBox.setText("Confirm result submission.");
        confirmSubmitBox.setAnimationEnabled(true);
        accept = new Button("Ok");
        accept.setWidth("8em");
        cancel = new Button("Cancel");
        cancel.setWidth("8em");
        confirmMessage = new HTML();
        VerticalPanel confirmVPanel = new VerticalPanel();
        confirmVPanel.addStyleName("dialogVPanel");

        confirmVPanel.add(confirmMessage);
        confirmVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        HorizontalPanel hPanel2 = new HorizontalPanel();
        hPanel2.add(accept);
        hPanel2.add(cancel);
        confirmVPanel.add(hPanel2);
        confirmSubmitBox.setWidget(confirmVPanel);
        // Add a handler to close the DialogBox
        accept.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                confirmSubmitBox.hide();
                getTaskButton.setEnabled(true);
                getTaskButton.setFocus(true);
                sendResultToServer(selectedResult);
            }
        });
        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                confirmSubmitBox.hide();
                getTaskButton.setEnabled(true);
                getTaskButton.setFocus(true);
            }
        });
    }

    void buildTaskDoneMessageBox() {
        tasksDoneBox = new DialogBox();
        tasksDoneBox.setText("Survery done.");
        tasksDoneBox.setAnimationEnabled(true);
        done = new Button("No");
        done.setWidth("8em");
        retake = new Button("Yes");
        retake.setWidth("8em");
        taskDoneMessage = new HTML();
        VerticalPanel tasksDoneVPanel = new VerticalPanel();
        tasksDoneVPanel.addStyleName("dialogVPanel");

        tasksDoneVPanel.add(taskDoneMessage);
        tasksDoneVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        HorizontalPanel hPanel3 = new HorizontalPanel();
        hPanel3.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        hPanel3.add(done);
        hPanel3.add(retake);
        tasksDoneVPanel.add(hPanel3);
        tasksDoneBox.setWidget(tasksDoneVPanel);

        // Add a handler to close the DialogBox
        done.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                // DO NOTHING
                tasksDoneBox.hide();
                getTaskButton.setEnabled(false);
            }
        });
        retake.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                greetingService.resetUserTasks(userId,
                        new AsyncCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        errorLabel.setText(result);
                        bar.setProgress(0.0);
                        progress = 0;

                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        errorLabel.setText(caught.getMessage());

                    }
                });
                tasksDoneBox.hide();

            }
        });

    }

    /**
     * @param s
     */
    private void setTaskAvailableLabel(String[] s) {
        taskAvailableLable.getElement().setInnerHTML("<p> Welcome <span class=\"user-name\" >"
                + userName
                + "</span>, there are <span id=\"task-count\">"+s[0]+"</span> tasks avaiable</p>");
    }

    /**
     * @param s
     */
    private void updateTaskAvailableLabel(String[] s) {
        DOM.getElementById("task-count").setInnerHTML(s[0]);
    }

}
