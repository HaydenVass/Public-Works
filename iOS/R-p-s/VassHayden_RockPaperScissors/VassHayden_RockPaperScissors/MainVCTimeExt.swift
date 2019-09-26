//
//  mainTimerExt.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/18/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit


extension ViewController{

    func countDownTimer(){
        initalTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(countDown), userInfo: nil, repeats: true)
    }
    
    @objc func countDown(){
        if startingTimer != 1 {
            startingTimer -= 1
            timerLabel.text = "\(startingTimer.description)"
            if startingTimer == 1{
                timerLabel.textColor = .red
            }
            if startingTimer == 1{
                // disable user selectionon 1 to account for async processes
                toggleButtonInteraction()
            }
        }else{
            // resets all values when timer is up
            DispatchQueue.main.async {
                self.opponetsSelectionImageView.isHidden = false
                self.opponentReadyLabel.isHidden = true
                self.playButton.setTitle("Play Again?", for: .normal)
                self.playButton.isUserInteractionEnabled = true
                self.startingTimer = 4
                self.opponetsReadyStatus = "false"
                self.readyStatus = "false"
                self.initalTimer.invalidate()
                self.decideWinner()
                self.setScore()
                self.playButton.setButton()
                self.hideButtons()
                self.disconnectButton.isEnabled = true
                self.timerLabel.textColor = .white
                self.toggleButtonInteraction()
                if self.userChoice == "-1"{
                    self.playersSelectionImageView.image = UIImage(named: "-1")
                }
                if self.opponentsChoice == "-1"{
                    self.opponetsSelectionImageView.image = UIImage(named: "-1")
                }
            }
        }
    }
    
    func toggleButtonInteraction(){
        for button in imageButtons{
            button.isUserInteractionEnabled = !button.isUserInteractionEnabled
        }
    }
}
