//
//  Modals.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit
extension ViewController{
    
    func noConnectionModal(){
        let alert: UIAlertController = UIAlertController(title: "Connection Alert", message: "You are not connected to any other users. Please connect before playing.", preferredStyle: .alert)
        let escapeButton: UIAlertAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(escapeButton)
        present(alert, animated: true, completion: nil)
    }
    
    func disconnectedModal(){
        let alert: UIAlertController = UIAlertController(title: "Connection Alert", message: "Connection lost.", preferredStyle: .alert)
        let escapeButton: UIAlertAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(escapeButton)
        present(alert, animated: true, completion: nil)
    }
    
    
}
