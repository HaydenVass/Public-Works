//
//  ViewController.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/9/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import CoreData
// todo - create functions to save and load scores - triggers when time is up and second modal is presented. Perform actions in second view(?)
// add text boxes for user input
// create a date time


class ViewController: UIViewController {
    
    @IBOutlet var iPhoneAllImages: [UIImageView]!
    @IBOutlet var iPadAllImageViews: [UIImageView]!
    
    var primaryDataArray = [UIImageView]()
    
    //buttons
    @IBOutlet var redoButton: UIButton!
    @IBOutlet var timerLabel: UILabel!
    @IBOutlet var playButton: UIButton!
    @IBOutlet var leaderBoardButton: UIButton!
    @IBOutlet var profileButton: UIButton!
    
    // arrays
    var imageViewTags = [Int]()
    var gameImages = [UIImage]()
    // timer properties
    var totalTime = 0
    var isCounting: Bool = false
    var timer = Timer()
    // count down timer properties
    var initalTimer = Timer()
    var startingTimer = 6
    // checked properties
    var checkedView = UIImageView()
    var isChecking: Bool = false
    var isPlaying: Bool = true
    var gameIsInSession: Bool = false
    var winCounter: Int = 0
    var turnCounter: Int = 0


    
    override func viewDidLoad() {
        super.viewDidLoad()
        definesPresentationContext = true
        // check if iphone or ipad then assign that array to a main array that runs the app
        // Do any additional setup after loading the view, typically from a nib.
        gameImages += [UIImage(named: "001-rose")!, UIImage(named: "008-cactus")!, UIImage(named: "003-iris")!, UIImage(named: "007-seed")!, UIImage(named: "009-palm-tree")!, UIImage(named: "011-harebell")!, UIImage(named: "020-fruit-tree")!, UIImage(named: "043-carnation")!, UIImage(named: "036-orchid")!, UIImage(named: "041-tulip")!]
        roundUiElements()
        
        if UIDevice.current.userInterfaceIdiom == .pad{
            // adds aditional images to iPad verson
           gameImages += [UIImage(named: "034-poppy")!, UIImage(named: "025-peony")!, UIImage(named: "016-hibiscus")!, UIImage(named: "005-sunflower")!, UIImage(named: "014-fern")!]
            primaryDataArray = iPadAllImageViews
        }else{
            primaryDataArray = iPhoneAllImages
        }
        roundUiElements()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        // resets all switches when coming back from victory modal so
        // the user can play a new game
        navigationController?.navigationBar.isHidden = true
        resetGame()
    }
    
    // assigns images and tags to image views
    func assignImages(){
        for (index, view) in primaryDataArray.enumerated(){
            if index % 2 == 0{
                if index != 0 {
                    view.image = gameImages[index/2]
                    view.tag = index / 2
                }else{
                    view.image = gameImages[0]
                    view.tag = 0
                }
            }else{
                if index != 1{
                    view.image = gameImages[index / 2]
                    view.tag = index / 2
                }else{
                    view.image = gameImages[0]
                    view.tag = 0
                }
            }
        }
    }
    
    // tapped images
    @objc func imageTapped(tapGestureRecognizer: UITapGestureRecognizer)
    {
        let tappedImage = tapGestureRecognizer.view as! UIImageView
        //
        // first image tapped.
        tappedImage.flash()
        if !isChecking{
            // false
            // assigns image view into a variable to check against
            checkedView = tappedImage
            isChecking = true
            tappedImage.isUserInteractionEnabled = false
            tappedImage.image = gameImages[tappedImage.tag]
        }else if isChecking{
            // true
            // second image tapped - matching tags will hide the image view while an
            // incorrect match will flip the cards back over
            if tappedImage.tag == checkedView.tag{
                turnCounter += 1
                tappedImage.image = self.gameImages[tappedImage.tag]
                deactiveUserInteraction()
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                    tappedImage.isHidden = true
                    self.checkedView.isHidden = true
                    self.checkedView = UIImageView()
                    self.isChecking = false
                    self.winCounter += 1
                    self.activateUserInteraction()
                    if UIDevice.current.userInterfaceIdiom == .pad{
                        self.winSegueSequence(numberNeeded: 15)
                    }else{
                        self.winSegueSequence(numberNeeded: 10)
                    }
                }
            }else{
                turnCounter += 1
                tappedImage.image = gameImages[tappedImage.tag]
                checkedView = UIImageView()
                isChecking = false
                deactiveUserInteraction()
                addDelayOnGameBoard()
            }
        }
    }
    
    func roundUiElements(){
        playButton.layer.cornerRadius = 10
        
        leaderBoardButton.layer.cornerRadius = 10
        for imageView in primaryDataArray{
            imageView.layer.cornerRadius = 10
        }
    }
    func addTapRecognition(){
        for view in primaryDataArray{
            let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(imageTapped(tapGestureRecognizer:)))
            view.isUserInteractionEnabled = true
            view.addGestureRecognizer(tapGestureRecognizer)
        }
    }
    
    @IBAction func pressedPlay(_ sender: Any) {
        if !gameIsInSession{
            // if a game has not been started then pressing play will start a new game, else the game will
            // alternate between pause and play
            playButton.isUserInteractionEnabled = false
            redoButton.isUserInteractionEnabled = false
            playButton.setTitle("Pause", for: .normal)
            isPlaying = true
            gameIsInSession = true
            newGame()
            leaderBoardButton.isHidden = true
        }else{
            if isPlaying{
                // pause timer - switches on / off the users ability to interact with the game board
                playButton.setTitle("Play", for: .normal)
                deactiveUserInteraction()
                timer.invalidate()
                isPlaying = false
            }else if !isPlaying{
                playButton.setTitle("Pause", for: .normal)
                activateUserInteraction()
                timeIncrementer()
                isPlaying = true

            }
        }
    }
    
    func newGame(){
        primaryDataArray.shuffle()
        assignImages()
        countDownTimer()
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "winSegue"{
            if let destination = segue.destination as? WinConditionViewController{
                destination.playersTime = totalTime
                destination.turns = turnCounter
            }
        }
    }
    // adds a second delay between wrong choices
    func addDelayOnGameBoard(){
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1), execute: {
            self.activateUserInteraction()
            for view in self.primaryDataArray{
                view.image = nil
            }
        })
    }
    
    func deactiveUserInteraction(){
        for view in primaryDataArray{
            view.isUserInteractionEnabled = false
        }
    }
    func activateUserInteraction(){
        for view in primaryDataArray{
            view.isUserInteractionEnabled = true
        }
    }
    // function to determine win condtion between ipad and iphone
    func winSegueSequence(numberNeeded: Int){
        if self.winCounter == numberNeeded{
            self.timer.invalidate()
            self.performSegue(withIdentifier: "winSegue", sender: self)
        }
    }
    // if the game has already started, pressing reset will stop the clock and warn the user they are about to reset the game.
    // pressing okay will reset, pressing cancel will start the timer again.
    @IBAction func redoButtonPressed(){
        if gameIsInSession{
            timer.invalidate()
            let alert: UIAlertController = UIAlertController(title: "Alert", message: "Are you sure you want to restart the game?", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { action in
                self.timer.invalidate()
                self.resetGame()
            }))
            alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: { action in
                self.timeIncrementer()
            }))
            present(alert, animated: true, completion: nil )
        }
    }
    
    func resetGame(){
        for view in primaryDataArray{
            view.image = nil
            view.isHidden = false
            view.gestureRecognizers?.removeAll()
        }
        playButton.setTitle("Play", for: .normal)
        isChecking = false
        isPlaying = true
        gameIsInSession = false
        isCounting = false
        totalTime = 0
        winCounter = 0
        startingTimer = 6
        timerLabel.text = " "
        turnCounter = 0
        leaderBoardButton.isHidden = false
    }
}

