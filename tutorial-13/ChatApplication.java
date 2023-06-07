import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class ChatApplication extends Application {
  private String author1 = "Romeo";
  private String author2 = "Juliet";
  private TextArea romeoTextArea;
  private TextArea julietTextArea;
  private TextField romeoTextField;
  private TextField julietTextField;

  @Override
  public void start (Stage primaryStage) {
    romeoTextArea = createTextArea();
    julietTextArea = createTextArea();

    romeoTextField = createTextField(romeoTextArea, author1);
    julietTextField = createTextField(julietTextArea, author2);

    VBox leftPane = createChatPane(romeoTextArea, romeoTextField, author1);
    VBox rightPane = createChatPane(julietTextArea, julietTextField, author2);

    SplitPane splitPane = new SplitPane(leftPane, rightPane);
    splitPane.setDividerPositions(0.5);

    VBox root = new VBox(splitPane);
    root.setPadding(new Insets(10));
    root.setSpacing(10);
    VBox.setVgrow(splitPane, Priority.ALWAYS);

    Scene scene = new Scene(root, 900, 500);

    romeoTextField.setOnKeyPressed(this::handleTextFieldKeyPress);
    julietTextField.setOnKeyPressed(this::handleTextFieldKeyPress);

    primaryStage.setTitle("Chat appliation");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private TextArea createTextArea () {
    TextArea ta = new TextArea();
    ta.setEditable(false);
    ta.setWrapText(true);

    return ta;
  }

  private TextField createTextField (TextArea outputTA, String author) {
    TextField tf = new TextField();
    tf.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        outputTA.clear();
      }
    });

    tf.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.ENTER) {
        e.consume();
        sendMessage(tf, author);
      }
    });

    return tf;
  }

  private VBox createChatPane (TextArea ta, TextField tf, String author) {
    tf.setPromptText("Enter message as " + author);

    Button sendButton = new Button("Send");
    sendButton.setOnAction(e -> sendMessage(tf, author));

    HBox.setHgrow(tf, Priority.ALWAYS);
    HBox inputBox = new HBox(tf, sendButton);
    inputBox.setPadding(new Insets(10));
    inputBox.setAlignment(Pos.CENTER_RIGHT);
    inputBox.setSpacing(10);

    VBox chatPane = new VBox(ta);
    chatPane.setPadding(new Insets(10));
    chatPane.setSpacing(10);

    BorderPane pane = new BorderPane();
    pane.setCenter(chatPane);
    pane.setBottom(inputBox);

    VBox.setVgrow(pane, Priority.ALWAYS);
    VBox.setVgrow(ta, Priority.ALWAYS);


    return new VBox(pane);
  }

  private void handleTextFieldKeyPress (KeyEvent e) {
    if (e.getCode() == KeyCode.ESCAPE) {
      TextField tf = (TextField)e.getSource();
      tf.clear();
      if (tf == romeoTextField) {
        romeoTextArea.clear();
      } else if (tf == julietTextField) {
        julietTextArea.clear();
      }
    }
  }

  private void sendMessage (TextField tf, String author) {
    String msg = tf.getText().trim();

    if (!msg.isEmpty()) {
      TextArea sender = author.matches(author1) ? romeoTextArea : julietTextArea;
      TextArea recipient = author.matches(author2) ? romeoTextArea : julietTextArea;
      sender.appendText("You: " + msg + "\n");
      recipient.appendText(author + ": " + msg + "\n");
      tf.clear();
    }
  }

  public static void main (String[] args) {
    launch(args);
  }
}