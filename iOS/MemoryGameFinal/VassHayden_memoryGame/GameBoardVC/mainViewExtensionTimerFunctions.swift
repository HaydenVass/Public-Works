//
//  mainViewExtensionTimerFunctions.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/11/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit

extension ViewController{
    
    func countDownTimer(){
        initalTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(countDown), userInfo: nil, repeats: true)
    }
    
    // sets a timer that counts down, after the timer ends user interaction is allowed with the game board and
    // the timer that tracks the game time begins
    @objc func countDown(){
        if startingTimer != 0 {
            startingTimer -= 1
            timerLabel.text = "\(startingTimer.description) seconds until the game starts!"
        }else{
            for view in primaryDataArray{
                view.image = nil
            }
            initalTimer.invalidate()
            timeIncrementer()
            addTapRecognition()
            playButton.isUserInteractionEnabled = true
            redoButton.isUserInteractionEnabled = true
        }
    }
    
    // uses modulous to format time and minutes.
    func timeFormatted(totalTime: Int) -> String {
        let seconds: Int = totalTime % 60
        let minutes: Int = (totalTime / 60) % 60
        if seconds < 10{
            return "\(minutes):0\(seconds)"
        }else{
            return "\(minutes):\(seconds)"
        }
    }
    
    // incremeting time after the inital 5 seconds stops
    func timeIncrementer(){
        timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(handleTime), userInfo: nil, repeats: true)
    }
    @objc func handleTime(){
        totalTime += 1
        timerLabel.text = "Your current game time: \(timeFormatted(totalTime: totalTime))"
    }
}
