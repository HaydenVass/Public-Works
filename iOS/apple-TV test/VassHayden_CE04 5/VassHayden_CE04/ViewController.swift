//
//  ViewController.swift
//  VassHayden_CE04
//
//  Created by Hayden Vass on 5/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tableView: UITableView!
    var allObjs: [[Musician]] = []
    var artistToSend: Musician? = nil
    let sections: [String] = ["Country", "Blues", "Rock"]
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        allObjs = makeArtist()
    }
    //table view methods
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return sections[section]
    }
    func tableView(_ tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        view.tintColor = UIColor.gray
        let header = view as! UITableViewHeaderFooterView
        header.textLabel?.textColor = UIColor.white
    }
    //table view section methods

    func numberOfSections(in tableView: UITableView) -> Int {
        return sections.count
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allObjs[section].count
    }

    //contructs table view
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell1", for: indexPath) as! tvCell
        cell.nameLabel.text = allObjs[indexPath.section][indexPath.row].name
        cell.categoryLabel.text = allObjs[indexPath.section][indexPath.row].category
        cell.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tap(_:))))
        return cell
    }
    
    //takes tapped object and populates it into the artist to send variable, then performs the
    //segue to the details view
    @objc func tap(_ sender: UITapGestureRecognizer) {
        let location = sender.location(in: tableView)
        let indexPath = tableView.indexPathForRow(at: location)
        artistToSend = allObjs[indexPath!.section][indexPath!.row]
        performSegue(withIdentifier: "detailsView", sender: nil)
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destination = segue.destination as? DetailsViewController{
            if artistToSend != nil{
                destination.receicvedArtist = artistToSend
            }
        }
    }
}

