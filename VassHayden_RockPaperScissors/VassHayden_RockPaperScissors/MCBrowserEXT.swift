//
//  test.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/20/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit
import MultipeerConnectivity

extension MCBrowserViewController{
    
    func tooManyConnectedModal(){
        let alert: UIAlertController = UIAlertController(title: "Connection Alert", message: "Too many users are connected. Please  connect with one user at a time.", preferredStyle: .alert)
        let escapeButton: UIAlertAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(escapeButton)
        present(alert, animated: true, completion: nil)
    }
    
}
