//
//  imageViewextension.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/11/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit

extension UIImageView{
    // pulsates image views
    func pulsate(){
        let pulse = CASpringAnimation(keyPath: "transform.scale")
        pulse.duration = 0.5
        pulse.fromValue = 0.95
        pulse.toValue = 1.0
        pulse.autoreverses = true
        pulse.repeatCount = 3
        pulse.initialVelocity = 0.5
        pulse.damping = 1.0
        layer.add(pulse, forKey: nil)
    }
    // flashes image views
    func flash(){
        let flash = CABasicAnimation(keyPath: "opacity")
        flash.duration = 0.5
        flash.fromValue = 1
        flash.toValue = 0.1
        flash.autoreverses = true
        flash.repeatCount = 1
        layer.add(flash, forKey: nil)
    }
}


