//
//  apiCall.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
//api call for swells

var dataToPass: (String?, Double?, Double?)?

extension InterfaceController{
    func configureTempURL(urlString: String){
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        if let validURL = URL (string: urlString){
            let task = session.dataTask(with: validURL) { (data, response, error) in
                if error != nil {return}
                guard let response = response as? HTTPURLResponse,
                    response.statusCode == 200,
                    let data = data
                    else {return}
                do{
                    if let json = try JSONSerialization.jsonObject(with: data) as? [String: Any]
                    {
                        let celcius = json["celcius"] as? Double
                        let fahrenheit = json["fahrenheit"] as? Double
                        let recSuit = json["wetsuit"] as? String
                        
                        dataToPass = (recSuit, celcius, fahrenheit)

                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                // get back on the main thread - perform segue -
                //checks against most recent minute (by 10s) to present most relevant ata
                DispatchQueue.main.async {
                    if dataToPass != nil{
                        self.pushController(withName: "TempDetails", context: dataToPass)

                    }
                }
            }
            task.resume()
        }
    }
    
}


