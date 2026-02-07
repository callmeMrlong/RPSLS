//imports
import javax.swing.*;
import java.awt.*;
import java.util.List;

//vars to use
private static JLabel botPickLabel;
private static JLabel playerScoreLabel;
private static JLabel computerScoreLabel;
private static int playerScore = 0;
private static int computerScore = 0;
private static final String[] choices = {"rock", "paper", "scissor", "lizard", "spock"};
private static final Random random = new Random();

void main() {
    //frame
    JFrame frame = new JFrame("Rock / Paper / Scissor / Lizard / Spock"); //title
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when the user hits (X) close the project and stop it
    frame.setSize(600, 200);
    frame.setBackground(Color.red);
    frame.setResizable(false); //this makes sure the window is one size cuz it's not made to get bigger or smaller
    frame.setLayout(null); //this allows me to position everything in the frame manually

    JPanel buttonPanel = new JPanel();
    JPanel botPickPanel = new JPanel();

    //bot object
    botPickLabel = new JLabel("Make your move!");
    botPickPanel.add(botPickLabel);

    // score objects
    playerScoreLabel = new JLabel("Player Score: " + playerScore);
    computerScoreLabel = new JLabel("Computer Score: " + computerScore);

    // def of Buttons
    JButton rock = new JButton("\uD83E\uDEA8Rock\uD83E\uDEA8");
    JButton paper = new JButton("\uD83D\uDCC4Paper\uD83D\uDCC4");
    JButton scissor = new JButton("✂️Scissor✂️");
    JButton lizard = new JButton("\uD83E\uDD8ELizard\uD83E\uDD8E");
    JButton spock = new JButton("\uD83D\uDD96Spock\uD83D\uDD96");

    //buttons do stuff if clicked
    rock.addActionListener(_ -> game("rock"));
    paper.addActionListener(_ -> game("paper"));
    scissor.addActionListener(_ -> game("scissor"));
    lizard.addActionListener(_ -> game("lizard"));
    spock.addActionListener(_ -> game("spock"));

    //buttons
    buttonPanel.add(rock);
    buttonPanel.add(paper);
    buttonPanel.add(scissor);
    buttonPanel.add(lizard);
    buttonPanel.add(spock);

    // pos of stuff
    computerScoreLabel.setBounds(350, 100, 150, 50);
    playerScoreLabel.setBounds(100, 100, 150, 50);

    botPickLabel.setHorizontalAlignment(SwingConstants.CENTER);
    botPickLabel.setVerticalAlignment(SwingConstants.CENTER);

    computerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    computerScoreLabel.setVerticalAlignment(SwingConstants.CENTER);

    playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    playerScoreLabel.setVerticalAlignment(SwingConstants.CENTER);

    buttonPanel.setBounds(0, 0, 600, 50);
    botPickPanel.setBounds(0, 50, 600, 150);

    //color stuff frfr
    //panels
    botPickPanel.setBackground(Color.pink);
    buttonPanel.setBackground(Color.pink);

    //labels
    playerScoreLabel.setBorder(BorderFactory.createRaisedBevelBorder());//3d border
    computerScoreLabel.setBorder(BorderFactory.createRaisedBevelBorder());//3d border
    botPickLabel.setBorder(BorderFactory.createRaisedBevelBorder()); //3d border

    //player score label config
    playerScoreLabel.setOpaque(true);
    playerScoreLabel.setBackground(new Color(0, 0, 255, 100));
    playerScoreLabel.setForeground(Color.white);

    //computer score label config
    computerScoreLabel.setOpaque(true);
    computerScoreLabel.setBackground(new Color(0, 0, 255, 100));
    computerScoreLabel.setForeground(Color.white);

    //bot pick label config
    botPickLabel.setOpaque(true);
    botPickLabel.setBackground(new Color(0, 50,205, 100));
    botPickLabel.setForeground(Color.white);

    //add everything to the frame so it will be there
    frame.add(playerScoreLabel);
    frame.add(computerScoreLabel);
    frame.add(botPickPanel);
    frame.add(buttonPanel);

    frame.setVisible(true);
}
//logic
public static void animation(String message,  Runnable onComplete){
    String[] animationSteps = {"Rock", "Paper", "Scissors", "Lizard", "Spock"};

    Timer timer = new Timer(1000, null); //need to use timer because sleep() freezes the whole window so it only shows the last thing, this fires every second
    final int[] index = {0}; //array cuz lambdas need something about "effectively final" vars, this keeps track of what step it is on

    timer.addActionListener(e -> { //lambda expression --- this tell the timer what to run everytime it goes off
        if (index[0] < animationSteps.length) { //is it done?
            botPickLabel.setText(animationSteps[index[0]]);
            index[0]++;
        } else {
            botPickLabel.setText(message);
            timer.stop(); // stop the timer
            if (onComplete != null) {
                onComplete.run(); //run the code after animation finishes
            }
        }
        System.out.println(e);
    });

    timer.start();

}

public static void game(String playerChoice) {
    //list of wins for each option
    // ( I made the lists like this cuz it was the first result when I googled "how to make lists java" )
    List<String> rock = new ArrayList<>();
    rock.add("scissor");
    rock.add("lizard");

    List<String> paper = new ArrayList<>();
    paper.add("spock");
    paper.add("rock");

    List<String> scissor = new ArrayList<>();
    scissor.add("paper");
    scissor.add("lizard");

    List<String> lizard = new ArrayList<>();
    lizard.add("spock");
    lizard.add("paper");

    List<String> spock = new ArrayList<>();
    spock.add("rock");
    spock.add("scissor");

    String computerChoice = choices[random.nextInt(choices.length)]; //get a random item from the choices list

    String result; //declare result
    if (playerChoice.equals(computerChoice)) {
        result = "Tie";
    } else {
        boolean winOrLose = switch (playerChoice) { //match case
            case "rock" -> rock.contains(computerChoice);
            case "paper" -> paper.contains(computerChoice);
            case "scissor" -> scissor.contains(computerChoice);
            case "lizard" -> lizard.contains(computerChoice);
            case "spock" -> spock.contains(computerChoice);
            default -> false;
        };
        if (winOrLose) {
            result = "Player Wins";
            playerScore++;
        } else {
            result = "Computer Wins";
            computerScore++;
        }
    }

    SwingUtilities.invokeLater(() -> //update the GUI with GUI threading, with a lambda expression so the project isn't buggy
    {
        HashMap<String, String> winMessages = new HashMap<>(); //thanks stack overflow for explaining how these work

        //rock wins
        winMessages.put("rock-scissor", "🪨 Rock crushes ✂️ Scissors");
        winMessages.put("rock-lizard", "🪨 Rock crushes 🦎 Lizard");
        //rock losses
        winMessages.put("rock-paper", "📄 Paper covers 🪨 Rock");
        winMessages.put("rock-spock", "🖖 Spock vaporizes 🪨 Rock");
        //rock tie
        winMessages.put("rock-rock", "🪨 Rock crashes into 🪨 Rock, both shatter equally");

        //paper wins
        winMessages.put("paper-rock", "📄 Paper covers 🪨 Rock");
        winMessages.put("paper-spock", "📄 Paper disproves 🖖 Spock");
        //paper losses
        winMessages.put("paper-scissor", "✂️ Scissors cuts 📄 Paper");
        winMessages.put("paper-lizard", "🦎 Lizard eats 📄 Paper");
        //paper tie
        winMessages.put("paper-paper", "📄 Paper recognizes 📄 Paper and tie");

        //scissor wins
        winMessages.put("scissor-paper", "✂️ Scissors cuts 📄 Paper");
        winMessages.put("scissor-lizard", "✂️ Scissors decapitates 🦎 Lizard");
        //scissor losses
        winMessages.put("scissor-spock", "🖖 Spock smashes ✂️ Scissors");
        winMessages.put("scissor-rock", "🪨 Rock crushes ✂️ Scissors");
        //scissor tie
        winMessages.put("scissor-scissor", "✂️✂️ The TWO Scissors attempt to fight one another but can not cut metal");

        //lizard wins
        winMessages.put("lizard-spock", "🦎 Lizard poisons 🖖 Spock");
        winMessages.put("lizard-paper", "🦎 Lizard eats 📄 Paper");
        //lizard losses
        winMessages.put("lizard-scissor", "✂️ Scissors decapitates 🦎 Lizard");
        winMessages.put("lizard-rock", "🪨 Rock crushes 🦎 Lizard");
        //lizard tie
        winMessages.put("lizard-lizard", "🦎 Lizard befriends 🦎 Lizard");

        //spock wins
        winMessages.put("spock-scissor", "🖖 Spock smashes ✂️ Scissors");
        winMessages.put("spock-rock", "🖖 Spock vaporizes 🪨 Rock");
        //spock losses
        winMessages.put("spock-lizard", "🦎 Lizard poisons 🖖 Spock");
        winMessages.put("spock-paper", "📄 Paper disproves 🖖 Spock");
        //spock tie
        winMessages.put("spock-spock", "🖖 Spock loves 🖖 Spock and refuses to fight");

        //im gonna explain this if condition cuz the other ones are pretty much the same
        if (result.equals("Player Wins")) {
            String key = playerChoice + "-" + computerChoice;//makes the key
            String message = winMessages.get(key);//finds the right win message using the key
            animation("Computer pick: "+ computerChoice + " | " + message + " | " + result, //I had to add the updating the player score to the animation callback
                    () -> {//update the botPickLabel to show the results
                playerScoreLabel.setText("Player Score: " + playerScore); //update the player score
                computerScoreLabel.setText("Computer Score: " + computerScore); //update the computer score
            });
        }
        if (result.equals("Computer Wins") || result.equals("Tie")) {
            String key = computerChoice + "-" + playerChoice;
            String message = winMessages.get(key);
            animation("Computer picks: "+ computerChoice + " | " + message + " | " + result,
                    () -> {
                playerScoreLabel.setText("Player Score: " + playerScore);
                computerScoreLabel.setText("Computer Score: " + computerScore);
            });
        }
    });
}