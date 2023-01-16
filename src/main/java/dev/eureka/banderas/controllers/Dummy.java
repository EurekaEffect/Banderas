package dev.eureka.banderas.controllers;

public class Dummy {}
/*implements Initializable {
    @FXML private ImageView flagView;

    @FXML private TextField inputField;
    @FXML private Text countryText;

    private final FlagParser parser = new FlagParser();
    private Flag currentFlag;

    private Thread animationThread = new Thread("Animation");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.EVENT_BUS.register(this);
        parser.collectFlags();
        nextFlag();

        inputField.setFont(getFont(32));
        countryText.setFont(getFont(42));

        inputField.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                App.EVENT_BUS.post(new EnterTextEvent(inputField.getText()));
                inputField.clear();
            }
        });
    }

    @Subscribe
    public void onEnter(EnterTextEvent event) {
        boolean validName = event.getText().equals(currentFlag.getName());

        if (!validName) {
            animateCountryName(currentFlag.getName());
        }

        nextFlag();
    }

    @SneakyThrows
    private void animateCountryName(String countryName) {
        countryText.setText(countryName);
        countryText.setOpacity(1.0);

        animationThread.stop();
        animationThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1500);

                for (double i = 1.0; i > 0.0; i -= 0.02) {
                    Thread.sleep(17);

                    double opacity = i;
                    Platform.runLater(() ->  countryText.setOpacity(opacity));
                }
            }
        });

        animationThread.start();
    }

    public void nextFlag() {
        currentFlag = parser.getAny();
        flagView.setImage(currentFlag.getFlag());
    }

    @SneakyThrows
    private static Font getFont(int px) {
        return Font.loadFont(Objects.requireNonNull(App.class.getResource("DaxLine.otf")).openStream(), px);
    }
}*/