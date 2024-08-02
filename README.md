# Pong-Game-Console-Application
My final project for Intro to Client / Server Computing

Design Document  

Section 1 – Project Description  

1.1 The Project 

Pong Game – Client / Server Implementation 

1.2 Description 

    The pong game is a simple single player game with an AI opponent. The player controls a paddle, 
    and the goal is to hit a ball back and forth with the AI opponent and try to score. 
    The score will increase every time the player hits the ball back.  
    

1.3 Revision History 

        Date     |           Comment             |            Author
    -------------|-------------------------------|------------------------
    27/07/2024   | Made the design Doc           | Travis Schellenberg 
             

Section 2 – Overview 

2.1 Purpose 

    The purpose of this project is to make a game in Java to hone my programming skills. 
    Then implement a client / server side to it to handle multithreading. 
    So many users can play their own games at once. 

2.2 Scope 

    The scope includes the core mechanics of Pong, including player controls, 
    basic AI behavior, game physics, user interface, and client / server handling.   
 

2.3 Requirements  

2.3.1 Functional Requirements 

    R1: The game will open with a main menu where users can
        select to start a game or see past records (up to 3 games) 
    R2: The game will implement an AI to control one paddle  
    R3: The game shall display a game over message when completed and score 
    R4: Chance to store score to records after game then restart 

2.3.2 Non-Functional Requirements 

    Performance: The game should run at 60 FPS 
    Reliability: The game should handle unexpected inputs without crashing 
    Usability: The game should have a simple and intuitive interface 

2.3.3 Technical Requirements  

    Hardware: The game should run on any PC with basic graphic capabilities 
    Software: The game will be developed in Java using Swing for the GUI
              and run on the local host port 4000.  

2.3.4 Security Requirements 

    The game does not handle sensitive information, 
    so minimal security measures are implemented. 

2.3.5 Estimates  

    Task Number     |                    Description                  |         Estimated Hours
    ----------------|-------------------------------------------------|--------------------------
        1           |   Set up project structure and GitHub           |               1
        2           |   Implement game panel and paddles              |               2
        3           |   Implement ball movement and collision         |               4
        4           |   Implement player movement                     |               2
        5           |   Implement AI movement                         |               4
        6           |   Implement scoring and game over logic         |               4
        7           |   User interface and menu                       |               2
        8           |   Testing / Debugging                           |               4
        9           |   Client / Sever Implementation                 |               6
        10          |   Testing / Debugging                           |               4
    ----------------|-------------------------------------------------|--------------------------
                    |                                         Total:  |               33

Section 3 – System Architecture  

3.1 Overview  

    The system consists of a game loop that updates the positions of the ball and paddles, 
    checks for collisions, and renders the game state on the screen 

Section 4 – Data Dictionary  

    Field (Variable)    |                Note               |        Data Type
    --------------------|-----------------------------------|------------------------
    paddleX             |   X - coordinate of the paddle    |        Integer
    paddleY             |   Y - coordinate of the paddle    |        Integer
    ballX               |   X - coordinate of the ball      |        Integer
    ballY               |   Y - coordinate of the ball      |        Integer
    xSpeed              |   Speed of the ball X - axis      |        Integer
    ySpeed              |   Speed of the ball Y - axis      |        Integer
    playerScore         |   The player's points             |        Integer
    aiScore             |   The AI's points                 |        Integer


Section 5 – Data Design 

5.1 Persistent/Static Data 

    The only persistent data is the player records. The player can store their top 3 scores from games played
    The relationship between players and score is 1:1  
    All other data is volatile and resets after each game.  

Section 6 – User Interface Design  

6.1 User Interface Design Overview 

    Simple graphical representation of paddles and ball using Java Swing. 

6.2 User Interface Navigation Flow 

    Menu -> Start Game -> Game Screen -> Menu -> Records -> Menu  

6.3 Use Cases / User Function Description 

    UC1: Player starts the game 
    UC2: Player controls paddle using keyboard 
    UC3: AI moves paddle automatically 
    UC4: Game displays score and handles game over 
    UC5: Player adds record / check records 

 

 

 

 
