//
//  getSpotForecastAsync.swift
//  VassHayden_OCSR
//
//  Created by Hayden Vass on 5/27/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation

extension ViewController{
    func configureFavSpotForecast(urlString: String){
        var newFavorite: SpotDetails? = nil
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
                                let hour = secondLevel["hour"] as? String
                                let currentHour = self.getCurrentHour()
                                if hour == currentHour{
                                    let shape = secondLevel["shape"] as? String
                                    let name = secondLevel["spot_name"] as? String
                                    let sizer = secondLevel["size"] as? Int
                                    let id = secondLevel["spot_id"] as? Int
                                    if let shapeDetails = secondLevel["shape_detail"] as? [String: Any]{
                                        let swell = shapeDetails["swell"] as? String
                                        let tide = shapeDetails["tide"] as? String
                                        let wind = shapeDetails["wind"] as? String
                                        let idStr = String(id ?? 0)
                                        
                                        newFavorite = SpotDetails(_name: name ?? "na", _id: idStr, _wind: wind ?? "na", _waveSize: sizer ?? 0, _tide: tide ?? "na", _swell: swell ?? "na", _waveShape: shape ?? "na")
                                    }
                                }
                            }
                        }
                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                DispatchQueue.main.async {
                    self.allFavoriteSpotDetails.append(newFavorite!)
                    self.sendToWatch()
                }
            }
            task.resume()
        }
    }
    
}
