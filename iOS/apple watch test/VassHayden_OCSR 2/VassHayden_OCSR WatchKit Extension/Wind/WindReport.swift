//
//  WindReport.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/20/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation

class WindReport{
    var knots: Double?
    var mph: Double?
    var direcrtion: Double?
    var directionStr: String?
    
    init(_knots: Double, _mph: Double, _direction: Double, dStr: String) {
        knots = _knots
        mph = _mph
        direcrtion = _direction
        directionStr = dStr
    }
}
