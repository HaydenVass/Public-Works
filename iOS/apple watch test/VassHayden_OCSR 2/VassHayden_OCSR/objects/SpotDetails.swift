//
//  SpotDetails.swift
//  VassHayden_OCSR
//
//  Created by Hayden Vass on 5/28/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation

class SpotDetails: NSObject, NSCoding {
    
    var name: String?
    var spotID: String?
    var wind: String?
    var waveSize: Int?
    var tide: String?
    var swell: String?
    var waveShape: String?
    
    
    init(_name: String, _id: String, _wind: String, _waveSize: Int, _tide: String, _swell: String, _waveShape: String) {
        name = _name
        spotID = _id
        wind = _wind
        waveSize = _waveSize
        tide = _tide
        swell = _swell
        waveShape = _waveShape
        
    }
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(name, forKey: "spotName")
        aCoder.encode(spotID, forKey: "spotID")
        aCoder.encode(wind, forKey: "wind")
        aCoder.encode(waveSize, forKey: "waveSize")
        aCoder.encode(tide, forKey: "tide")
        aCoder.encode(swell, forKey: "swell")
        aCoder.encode(waveShape, forKey: "waveShape")

    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        self.init(_name: "na", _id: "na", _wind: "na", _waveSize: 0, _tide: "na", _swell: "na", _waveShape: "na")
        name = aDecoder.decodeObject(forKey: "spotName") as? String
        spotID = aDecoder.decodeObject(forKey: "spotID") as? String
        wind = aDecoder.decodeObject(forKey: "wind") as? String
        waveSize = aDecoder.decodeObject(forKey: "waveSize") as? Int
        tide = aDecoder.decodeObject(forKey: "tide") as? String
        swell = aDecoder.decodeObject(forKey: "swell") as? String
        waveShape = aDecoder.decodeObject(forKey: "waveShape") as? String
    }
}
