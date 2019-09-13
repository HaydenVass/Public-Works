//
//  File.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/20/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation

extension ViewController{
    
    func decideWinner(){
        switch opponentsChoice {
        // rock
        case "0":
            if opponentsChoice == userChoice{
                // tie
                tieScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "It's a tie!"
                }
            }else if userChoice == "1"{
                // win
                userScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Winner."
                }
            }else if userChoice == "2"{
                // lose
                opponentScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "You. Lost."
                }
            }else{
                ifPickedNothing()
            }
        // paper
        case "1":
            if opponentsChoice == userChoice{
                //tie
                tieScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "We have a tie on our hands!"
                }
            } else if userChoice == "2"{
                // win
                userScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Dang! Winner!"
                }
            }else if userChoice == "0"{
                //lose
                opponentScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Lame. You lost."
                }
            }else{
                ifPickedNothing()
            }
        case "2":
            if opponentsChoice == userChoice{
                // tie
                tieScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Tie!"
                }
            }else if userChoice == "0"{
                // win
                userScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Win Win Win"
                }
            }else if userChoice == "1"{
                // loss
                opponentScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Loser"
                }
            }else{
                ifPickedNothing()
            }
        // case for -1
        case "-1":
            if opponentsChoice == userChoice{
                tieScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Lame. Tie."
                }
            }else{
                userScore += 1
                DispatchQueue.main.async {
                    self.timerLabel.text = "Nothing Picked by Opponent. Win!"
                }
            }
        default:
            print ("critical failure or choice was not picked")
        }
    }
    
    func ifPickedNothing(){
        if userChoice == "-1"{
            timerLabel.text = "Try picking something next time. This is a loss."
            opponentScore += 1
        }
    }
    
}
