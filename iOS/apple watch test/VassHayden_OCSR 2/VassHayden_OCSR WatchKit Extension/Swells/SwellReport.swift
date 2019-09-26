//
//  SwellReport.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/20/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation

class SwellReport{
    var hour: String
    var minutes: String
    var dir: Int?
    var hs: Double?
    var tp: Double?
    
    init(_hour:String, _min: String, _dir: Int?, _hs: Double, _tp: Double) {
        hour = _hour
        minutes = _min
        dir = _dir
        hs = _hs
        tp = _tp
    }
    
    
}
