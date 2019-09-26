//
//  BeachSpot.swift
//  VassHayden_OCSR
//
//  Created by Hayden Vass on 5/21/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import WatchKit

class BeachSpot: MKPointAnnotation {
    var name: String?
    var spotID: Int?
    var latitude: Double?
    var longitude: Double?
    
    
    init(_name: String, _id: Int, lat: Double, long: Double) {
        name = _name
        spotID = _id
        latitude = lat
        longitude = long
    }
}
