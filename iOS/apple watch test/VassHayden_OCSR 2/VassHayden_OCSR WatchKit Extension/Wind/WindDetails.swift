//
//  WindDetails.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/20/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import WatchKit

class WindDetails: WKInterfaceController {
    
    @IBOutlet var knotsLabel: WKInterfaceLabel!
    @IBOutlet var mphLabel: WKInterfaceLabel!
    @IBOutlet var directionLabel: WKInterfaceLabel!
    
    
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        if let details = context as? WindReport{
            knotsLabel.setText("\(details.knots ?? 0.0)")
            let mph = String(format: "%.2f", details.mph ?? 0.0)
            mphLabel.setText(mph)
            directionLabel.setText("\(details.direcrtion ?? 0.0) \(details.directionStr ?? "na")")
        }
    }
    
    override func willActivate() {
        super.willActivate()
    }
    
    override func didDeactivate() {
        super.didDeactivate()
    }
    
}
