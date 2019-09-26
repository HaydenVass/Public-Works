//
//  TableViewDetailsInterfaceController.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/28/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import WatchKit
import Foundation

// favorited details
class TableViewDetailsInterfaceController: WKInterfaceController {

    @IBOutlet weak var spotNameLabel: WKInterfaceLabel!
    @IBOutlet weak var shapeLabel: WKInterfaceLabel!
    @IBOutlet weak var heightLabel: WKInterfaceLabel!
    @IBOutlet weak var swellLabel: WKInterfaceLabel!
    
    
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        if let details = context as? (String, String, String, Int){
            spotNameLabel.setText(details.0)
            shapeLabel.setText(details.1)
            swellLabel.setText(details.2)
            heightLabel.setText(String(details.3))
        }
    }
}
