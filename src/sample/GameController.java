package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static sample.PregameController.cash;

public class GameController implements Initializable{

    public static final double SHUFFLE = 200000;
    @FXML
    TextField myBalance = new TextField();
    @FXML
    TextField myBet = new TextField();
    @FXML
    Button NewHand = new Button();
    @FXML
    Button Hit = new Button();
    @FXML
    Button Stand = new Button();
    @FXML
    TextField PDealer = new TextField();
    @FXML
    TextField PPlayer = new TextField();
    @FXML
    Button add1 = new Button();
    @FXML
    Button add5 = new Button();
    @FXML
    Button add10 = new Button();
    @FXML
    Button add25 = new Button();
    @FXML
    Button add50 = new Button();
    @FXML
    Button add100 = new Button();
    @FXML
    Button resetButton = new Button();
    @FXML
    Label Result = new Label();

    // carte del mazziere
    @FXML
    ImageView ViewDealerCard1, ViewDealerCard2, ViewDealerCard3, ViewDealerCard4, ViewDealerCard5, ViewDealerCard6, ViewDealerCard7, ViewDealerCard8, ViewDealerCard9, ViewDealerCard10, ViewDealerCard11;

    // carte del giocatore
    @FXML
    ImageView ViewPlayerCard1, ViewPlayerCard2, ViewPlayerCard3, ViewPlayerCard4, ViewPlayerCard5, ViewPlayerCard6, ViewPlayerCard7, ViewPlayerCard8, ViewPlayerCard9, ViewPlayerCard10, ViewPlayerCard11;

    int bet;
    int Turn=0, PointPlayer, PointDealer, Balance;

    String[] numberOfDeck = {"1","2"};
    String[] Value = {"A","2","3","4","5","6","7","8","9","0","J","Q","K"};
    String[] Seed = {"D","C","H","S"};
    int MAX = numberOfDeck.length* Value.length* Seed.length;
    String[] DealerCard = new String[11];
    String[] PlayerCard = new String[11];
    String[] Deck = new String[ numberOfDeck.length* Value.length* Seed.length];
    Image img ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBalance();

        NewHand.setDisable(true);
        Hit.setDisable(true);
        Stand.setDisable(true);
        Result.setVisible(true);
        Result.setText("");

        PPlayer.setText("0");
        PDealer.setText("0");
        myBalance.setText("$" + cash);

        Balance = cash;
        plusToBet(0);
    }
    // mazzo ordinato
    public void restoreDeck(String[] D){
        int i = 0;
        for(int nDeck=0; nDeck < numberOfDeck.length; nDeck++){
            for(int typeSeed=0; typeSeed < Seed.length; typeSeed++)
                for(int kindValue=0 ; kindValue < Value.length ; kindValue++ ){
                    D[i] = Value[kindValue] + Seed[typeSeed];
                    i++;
                }
        }
    }

    // mazzo mescolatp
    public void shuffleDeck(String[] D){
        for(int i=0; i< SHUFFLE; i++){
            Random rnd =  new Random();
            int firstCard = rnd.nextInt(MAX);
            int secondCard = rnd.nextInt(MAX);
            String a = Deck[firstCard];
            String b = Deck[secondCard];
            D[firstCard] = b;
            D[secondCard] = a;
        }
    }

    // controllo dei valori delle carte
    public int ValueCard( String[] Card, int T){
        int A = 0;
        int sum=0;
        for(int i=0; i< T+1;i++){
            switch (Card[i].charAt(0)){
                case 'A':
                    A++;
                    break;
                case '2':
                    sum+=2;
                    break;
                case '3':
                    sum+=3;
                    break;
                case '4':
                    sum+=4;
                    break;
                case '5':
                    sum+=5;
                    break;
                case '6':
                    sum+=6;
                    break;
                case '7':
                    sum+=7;
                    break;
                case '8':
                    sum+=8;
                    break;
                case '9':
                    sum+=9;
                    break;
                default:
                    sum+=10;
                    break;
            }
        }

        while(A > 0){
            if(sum+11>21){
            sum+=1;
            }else sum+=11;
            A--;
        }

        return sum;
    }

    // fine del game
    public void NewHand(javafx.event.ActionEvent actionEvent) throws IOException {

        img = new Image(getClass().getResourceAsStream("FotoCarte/Outline.png"));
        ViewPlayerCard1.setImage(img);
        ViewPlayerCard2.setImage(img);
        ViewPlayerCard3.setImage(img);
        ViewPlayerCard4.setImage(img);
        ViewPlayerCard5.setImage(img);
        ViewPlayerCard6.setImage(img);
        ViewPlayerCard7.setImage(img);
        ViewPlayerCard8.setImage(img);
        ViewPlayerCard9.setImage(img);
        ViewPlayerCard10.setImage(img);
        ViewPlayerCard11.setImage(img);

        ViewDealerCard1.setImage(img);
        ViewDealerCard2.setImage(img);
        ViewDealerCard3.setImage(img);
        ViewDealerCard4.setImage(img);
        ViewDealerCard5.setImage(img);
        ViewDealerCard6.setImage(img);
        ViewDealerCard7.setImage(img);
        ViewDealerCard8.setImage(img);
        ViewDealerCard9.setImage(img);
        ViewDealerCard10.setImage(img);
        ViewDealerCard11.setImage(img);

        NewHand.setDisable(true);
        Stand.setDisable(true);
        Hit.setDisable(true);
        add1.setDisable(false);
        add5.setDisable(false);
        add10.setDisable(false);
        add25.setDisable(false);
        add50.setDisable(false);
        add100.setDisable(false);
        resetButton.setDisable(false);
        Result.setText("");

        PointDealer = 0;
        PointPlayer = 0;

        myBet.setText("Bet: $" + bet);
        ChangePointDealer();
        ChangePointPlayer();

        Turn = 0;
        bet=0;
        plusToBet(0);
        Balance = cash;

        if(Balance == 0){
            Parent rootGameOver = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
            Stage stageGameOver = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene sceneGameOver = new Scene(rootGameOver);
            stageGameOver.setScene(sceneGameOver);
            stageGameOver.show();
            }



    }

    // cambia i punti del giocatore
    public void ChangePointPlayer(){
        PPlayer.setText(String.valueOf(PointPlayer));
    }

    // cambia i punti del mazziere
    public void ChangePointDealer(){
        PDealer.setText(String.valueOf(PointDealer));
    }

    // pesca
    public void hit(){

        if(Turn==0){

            add1.setDisable(true);
            add5.setDisable(true);
            add10.setDisable(true);
            add25.setDisable(true);
            add50.setDisable(true);
            add100.setDisable(true);
            resetButton.setDisable(true);
            Stand.setDisable(false);

            restoreDeck(Deck);
            shuffleDeck(Deck);

            for(int j=0, i=0; j < 2*DealerCard.length; j++, i++){
                DealerCard[i] = Deck[j];
                j +=1;
                PlayerCard[i] = Deck[j];
            }


            PointPlayer = ValueCard(PlayerCard, Turn);
            img = new Image(getClass().getResourceAsStream("FotoCarte/"+PlayerCard[Turn]+".png"));
            ViewPlayerCard1.setImage(img);

            img = new Image(getClass().getResourceAsStream("FotoCarte/"+DealerCard[Turn]+".png"));
            ViewDealerCard1.setImage(img);
            PointDealer = ValueCard(DealerCard, Turn);
            Turn++;

            img = new Image(getClass().getResourceAsStream("FotoCarte/"+PlayerCard[Turn]+".png"));
            ViewPlayerCard2.setImage(img);
            PointPlayer = ValueCard(PlayerCard, Turn);

            img = new Image(getClass().getResourceAsStream("FotoCarte/BlueCard.png"));
            ViewDealerCard2.setImage(img);


            }else{

                img = new Image(getClass().getResourceAsStream("FotoCarte/"+PlayerCard[Turn]+".png"));
                if(Turn==2) ViewPlayerCard3.setImage(img);
                if(Turn==3) ViewPlayerCard4.setImage(img);
                if(Turn==4) ViewPlayerCard5.setImage(img);
                if(Turn==5) ViewPlayerCard6.setImage(img);
                if(Turn==6) ViewPlayerCard7.setImage(img);
                if(Turn==7) ViewPlayerCard8.setImage(img);
                if(Turn==8) ViewPlayerCard9.setImage(img);
                if(Turn==9) ViewPlayerCard10.setImage(img);
                if(Turn==10) ViewPlayerCard11.setImage(img);

            }
            PointPlayer = ValueCard(PlayerCard, Turn);
            Turn++;
            ChangePointPlayer();


            if(PointPlayer >= 21){

                img = new Image(getClass().getResourceAsStream("FotoCarte/"+DealerCard[1]+".png"));
                ViewDealerCard2.setImage(img);
                PointDealer = ValueCard(DealerCard, 1);
                ChangePointDealer();


                Stand.setDisable(true);
                NewHand.setDisable(false);
                Hit.setDisable(true);

                if(PointPlayer == 21 ){

                        if(PointDealer==PointPlayer){
                            Result.setText("Draw");
                            cash += bet;
                        }else {

                            if(Turn == 1){
                                Result.setText("BlackJack! Player wins the match");
                                cash += bet*3;
                            }
                            else{
                                Result.setText("21! Player wins the match");
                                cash += bet*2;
                            }
                        }
                        myBalance.setText("$" + cash);
                }

                else if(PointPlayer > 21){

                    Result.setText("Dealer wins the match");
                    myBalance.setText("$" + cash);

                }


            }
        }
    // "sto" ( comando solo accessibile se la somma dei punti del giocatore è < 21)
    // il mazziere scopre la seconda carta e inizia a pescare fino a che non sballa o non fa una somma maggiore di quella del giocatore

    public void stand(){

        Hit.setDisable(true);
        Stand.setDisable(true);

        int DealerTurn=1;
        img = new Image(getClass().getResourceAsStream("FotoCarte/"+DealerCard[DealerTurn]+".png"));
        ViewDealerCard2.setImage(img);
        PointDealer = ValueCard(DealerCard, DealerTurn);
        ChangePointDealer();
        DealerTurn++;

        //stampa carte
        if( PointDealer <= 16 && PointDealer <= PointPlayer ) {
            img = new Image(getClass().getResourceAsStream("FotoCarte/"+DealerCard[DealerTurn]+".png"));
            ViewDealerCard3.setImage(img);
            PointDealer = ValueCard(DealerCard, DealerTurn);
            ChangePointDealer();

            DealerTurn++;
            if( PointDealer <= 16 && PointDealer < PointPlayer ) {
                img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                ViewDealerCard4.setImage(img);
                PointDealer = ValueCard(DealerCard, DealerTurn);
                ChangePointDealer();
                DealerTurn++;

                if (PointDealer <= 16 && PointDealer < PointPlayer) {
                    img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                    ViewDealerCard5.setImage(img);
                    PointDealer = ValueCard(DealerCard, DealerTurn);
                    ChangePointDealer();
                    DealerTurn++;
                    if (PointDealer <= 16 && PointDealer < PointPlayer) {
                        img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                        ViewDealerCard6.setImage(img);
                        PointDealer = ValueCard(DealerCard, DealerTurn);
                        ChangePointDealer();
                        DealerTurn++;
                        if (PointDealer <= 16 && PointDealer < PointPlayer) {
                            img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                            ViewDealerCard7.setImage(img);
                            PointDealer = ValueCard(DealerCard, DealerTurn);
                            ChangePointDealer();
                            DealerTurn++;
                            if (PointDealer < 16 && PointDealer < PointPlayer) {
                                img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                                ViewDealerCard8.setImage(img);
                                PointDealer = ValueCard(DealerCard, DealerTurn);
                                ChangePointDealer();
                                DealerTurn++;
                                if (PointDealer <= 16 && PointDealer < PointPlayer) {
                                    img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                                    ViewDealerCard9.setImage(img);
                                    PointDealer = ValueCard(DealerCard, DealerTurn);
                                    ChangePointDealer();
                                    DealerTurn++;
                                    if (PointDealer <= 16 && PointDealer < PointPlayer) {
                                        img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                                        ViewDealerCard10.setImage(img);
                                        PointDealer = ValueCard(DealerCard, DealerTurn);
                                        ChangePointDealer();
                                        DealerTurn++;
                                        if (PointDealer <= 16 && PointDealer < PointPlayer) {
                                            img = new Image(getClass().getResourceAsStream("FotoCarte/" + DealerCard[DealerTurn] + ".png"));
                                            ViewDealerCard11.setImage(img);
                                            PointDealer = ValueCard(DealerCard, DealerTurn);
                                            ChangePointDealer();
                                            DealerTurn++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (PointDealer > 21 ||PointDealer < PointPlayer){
            Result.setText("Player wins the match");
            cash+=bet*2;
        }else if (PointDealer > PointPlayer && PointDealer < 22){
            Result.setText("Dealer wins the match");
        }
        else if (PointPlayer == PointDealer) {
            Result.setText("Draw");
            cash += bet;
        }
        myBalance.setText("$" + cash);
        NewHand.setDisable(false);
    }

    // carica i soldi dello slider della pagina precedente
    @FXML
    public void loadBalance() {
        myBalance.setText("Balance: $" + cash);
    }

    // funzione usata per le scommesse
    public void plusToBet(int x){
        if(bet+x <= Balance){

            cash += bet;

            bet += x;
            myBet.setText("Bet: $" + bet);

            cash -= bet;

            myBalance.setText("$" + cash);
            if(x!=0) Hit.setDisable(false);

        }

    }

    // aggiunge $1
    public void plus1(){
        plusToBet(1);
    }

    // aggiunge $5
    public void plus5(){
        plusToBet(5);
    }

    // aggiunge $10
    public void plus10(){
        plusToBet(10);
    }

    // aggiunge $25
    public void plus25(){
        plusToBet(25);
    }

    // aggiunge $50
    public void plus50(){
        plusToBet(50);
    }

    // aggiunge $100
    public void plus100(){
        plusToBet(100);
    }

    // bottone reset
    public void reset() {

        Hit.setDisable(true);

        myBet.setText("");
        cash += bet;
        myBalance.setText("$" + cash);
        bet = 0;
        myBet.setText("Bet: $" + bet);
    }

    // torna all'entrata del casinò
    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
