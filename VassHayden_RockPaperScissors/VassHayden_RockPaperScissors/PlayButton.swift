//
//  PlayButton.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/15/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit

class PlayButton: UIButton {
    
    public var isOn = false
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        initButton()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        initButton()
    }
    
    func initButton(){
        layer.cornerRadius = frame.size.height / 2
        layer.borderWidth = 2
        layer.backgroundColor = Colors.chinaRose.withAlphaComponent(0.5).cgColor
        layer.borderColor = Colors.white.cgColor
        setTitleColor(.white, for: .normal)
        addTarget(self, action: #selector (PlayButton.playButtonPressed), for: .touchUpInside)
    }
    
    @objc func playButtonPressed(){
        activateButton(bool: !isOn)
    }
    // alternates states pending on button press
    func activateButton(bool: Bool){
        isOn = bool
        
        let color = bool ? .clear : Colors.chinaRose.withAlphaComponent(0.5)
        let title = bool ? "Waiting..." : "Play"
        let titleColor = bool ? .white : Colors.white
        
        setTitle(title, for: .normal)
        setTitleColor(titleColor, for: .normal)
        backgroundColor = color
    }
    
    func setButton(){
        setTitleColor(Colors.white, for: .normal)
        backgroundColor = Colors.chinaRose
    }
}
