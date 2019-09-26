//
//  apiCall.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
extension ViewController{
    func configureSpotUrl(urlString: String){
        //gets all spots to present on map view
        var spotArray: [BeachSpot] = []
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        if let validURL = URL (string: urlString){
            print (validURL)
            let task = session.dataTask(with: validURL) { (data, response, error) in
                if error != nil {return}
                guard let response = response as? HTTPURLResponse,
                    response.statusCode == 200,
                    let data = data
                    else {return}
                do{
                    if let json = try JSONSerialization.jsonObject(with: data) as? [Any]
                    {
                        for data in json{
                            if let secondLevel = data as? [String: Any]{
                                let name = secondLevel["spot_name"] as? String
                                let id = secondLevel["spot_id"] as? Int
                                let latitude = secondLevel["latitude"] as? Double
                                let longitutde = secondLevel["longitude"] as? Double

                                spotArray.append(BeachSpot(_name: name ?? "n/a", _id: id ?? 0, lat: latitude ?? 0.0, long: longitutde ?? 0.0))
                            }
                        }
                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                DispatchQueue.main.async {
                    self.allOCSpots = spotArray
     
                    self.pointSpotPlots()
                }
            }
            task.resume()
        }
    }
    
}

