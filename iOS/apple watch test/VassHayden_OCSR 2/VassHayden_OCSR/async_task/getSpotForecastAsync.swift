//
//  getSpotForecastAsync.swift
//  VassHayden_OCSR
//
//  Created by Hayden Vass on 5/27/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
//gets current forecast for details view when user clicks on map
extension ViewController{
    func configureForecast(urlString: String){
        var dataToUpdate: (String, String, String, String, String, Int)? = nil
        
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
                                    if let shapeDetails = secondLevel["shape_detail"] as? [String: Any]{
                                        let swell = shapeDetails["swell"] as? String
                                        let tide = shapeDetails["tide"] as? String
                                        let wind = shapeDetails["wind"] as? String
                                        dataToUpdate = (shape, swell, tide, wind, name, sizer)
                                            as? (String, String, String, String, String, Int)
                                    }
                                }
                            }
                        }
                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                DispatchQueue.main.async {
                    let size = dataToUpdate?.5 ?? 0
                    let sizeStr = String(size)
                    
                    self.spotNameLabel.text = dataToUpdate?.4
                    self.shapeLabel.text = dataToUpdate?.0
                    self.sizeLabel.text = sizeStr
                    self.swellLabel.text = dataToUpdate?.1
                    self.tideLabel.text = dataToUpdate?.2
                    self.windLabel.text = dataToUpdate?.3
                }
            }
            task.resume()
        }
    }
    
}
