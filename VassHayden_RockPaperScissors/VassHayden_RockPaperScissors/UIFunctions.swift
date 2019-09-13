//
//  UIFunctions.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/21/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit

extension ViewController{
    // show / hide the rock paper scissor buttons
    func hideButtons(){
        for button in imageButtons{
            button.isHidden = true
        }
    }
    func showButtons(){
        for button in imageButtons{
            button.isHidden = false
        }
    }
    // sets play button to inital state incase user presses play when not connected
    func playButtonInitalState(){
        playButton.isOn = false
        playButton.setButton()
        playButton.setTitle("Play", for: .normal)
    }
    // shows - hides scores view
    func hideScores(){
        for label in winsTiesLossesLabels{
            label.isHidden = true
        }
        for label in [winCounter, lossCounter, tieCounter]{
            label?.isHidden = true
        }
    }
    
    func showScores(){
        for label in winsTiesLossesLabels{
            label.isHidden = false
        }
        for label in [winCounter, lossCounter, tieCounter]{
            label?.isHidden = false
        }
    }
    // sets the values of the scores
    func setScore(){
        tieCounter.text = tieScore.description
        winCounter.text = userScore.description
        lossCounter.text = opponentScore.description
    }

}
