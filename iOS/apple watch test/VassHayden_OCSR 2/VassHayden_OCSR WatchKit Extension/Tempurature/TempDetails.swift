//
//  TempDetails.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/20/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import WatchKit

class TempDetails: WKInterfaceController {
    @IBOutlet var fahrLabel: WKInterfaceLabel!
    @IBOutlet var celcLabel: WKInterfaceLabel!
    @IBOutlet var wetSuitLabel: WKInterfaceLabel!
    
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        if let details = context as? (String, Double, Double){
            print(details)
            wetSuitLabel.setText(details.0)
            fahrLabel.setText("\(details.2)")
            celcLabel.setText("\(details.1)")

        }
    }
}
