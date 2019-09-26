//
//  SwellDetails.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import WatchKit

class SwellDetails: WKInterfaceController {
    

    @IBOutlet var dirLabel: WKInterfaceLabel!
    @IBOutlet var hsLabel: WKInterfaceLabel!
    @IBOutlet var tpLabel: WKInterfaceLabel!
    
    
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        if let details = context as? SwellReport{
            dirLabel.setText("\(details.dir ?? 0) Degrees")
            hsLabel.setText("\(details.hs ?? 0.0) meters")
            tpLabel.setText("\(details.tp ?? 0.0) seconds")
        }
    }
    
    override func willActivate() {
        super.willActivate()
    }
    
    override func didDeactivate() {
        super.didDeactivate()
    }

}
