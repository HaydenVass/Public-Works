//
//  TableViewDetailsAsync.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/28/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
extension TableInterfaceController{
    // get forecast for favorited spots
    func getForecast(urlString: String){
        var dataToUpdate: (String, String, String, Int)? = nil
        
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
                                let currentHour = InterfaceController.getCurrentHour()
                                if hour == currentHour{
                                    let shape = secondLevel["shape"] as? String
                                    let name = secondLevel["spot_name"] as? String
                                    let sizer = secondLevel["size"] as? Int
                                    if let shapeDetails = secondLevel["shape_detail"] as? [String: Any]{
                                        let swell = shapeDetails["swell"] as? String
                                        dataToUpdate = ((name, shape, swell, sizer)
                                            as! (String, String, String, Int))
                                    }
                                }
                            }
                        }
                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                DispatchQueue.main.async {
                    //pass touple to details
                self.pushController(withName: "tableViewCellDetail", context: dataToUpdate)
                }
            }
            task.resume()
        }
    }
    
}
