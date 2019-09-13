//
//  ViewController.swift
//  VassHayden_RockPaperScissors
//
//  Created by Hayden Vass on 2/15/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import MultipeerConnectivity


class ViewController: UIViewController, MCSessionDelegate, MCBrowserViewControllerDelegate {
    
    
    @IBOutlet var imageButtons: [UIImageView]!
    @IBOutlet var paperImageView: UIImageView!
    @IBOutlet var playersSelectionImageView: UIImageView!
    @IBOutlet var opponetsSelectionImageView: UIImageView!
    @IBOutlet var rockImageView: UIImageView!
    
    // buttons
    @IBOutlet var playButton: PlayButton!
    @IBOutlet var connectButton: UIBarButtonItem!
    @IBOutlet var disconnectButton: UIBarButtonItem!
    
    // counters - labels
    @IBOutlet var winCounter: UILabel!
    @IBOutlet var tieCounter: UILabel!
    @IBOutlet var lossCounter: UILabel!
    @IBOutlet var timerLabel: UILabel!
    @IBOutlet var opponentReadyLabel: UILabel!
    @IBOutlet var playerChoiceLabel: UILabel!
    @IBOutlet var opponentChoiceLabel: UILabel!
    @IBOutlet var winsTiesLossesLabels: [UILabel]!
    
    // p2p variables
    var peerID: MCPeerID!
    var session: MCSession!
    var browser: MCBrowserViewController!
    var advertiser: MCAdvertiserAssistant!
    let serviceID = "rpsToTheMax"
    
    // timer
    var initalTimer = Timer()
    var startingTimer: Int = 4
    
    // game choices
    var userChoice: String = "-1"
    var opponentsChoice: String = "-1"
    var readyStatus: String = "false"
    var opponetsReadyStatus: String = "false"
    var userScore: Int = 0
    var opponentScore: Int = 0
    var tieScore: Int = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        addTapRecognition()
        for label in [winCounter, tieCounter, lossCounter]{
            label?.text = 0.description
        }
        peerID = MCPeerID(displayName: UIDevice.current.name)
        session = MCSession(peer: peerID)
        session.delegate = self
        advertiser = MCAdvertiserAssistant(serviceType: serviceID, discoveryInfo: nil, session: session)
        advertiser.start()
        timerLabel.text = "Not connected. Press connect to select an opponent."
        opponetsSelectionImageView.isHidden = true
        opponentReadyLabel.isHidden = true
        navigationItem.title = "Not connected"
        playButton.setButton()
        hideButtons()
        for label in [playerChoiceLabel, opponentChoiceLabel]{
            label?.isHidden = true
        }
        disconnectButton.isEnabled = false
        hideScores()
        
    }
    
    // logic for pressing view
    // takes tapped image and transmits data in the form of a string that will assign the image for the opponent
    @objc func imageTapped(tapGestureRecognizer: UITapGestureRecognizer)
    {
        let tappedImage = tapGestureRecognizer.view as! UIImageView
        tappedImage.shake()
        playersSelectionImageView.image = tappedImage.image
        userChoice = String (tappedImage.tag)
        
        if let playerChoice: Data = userChoice.data(using: String.Encoding.utf8) {
            do {
                try session.send(playerChoice, toPeers: session.connectedPeers, with: MCSessionSendDataMode.reliable)
            }
            catch {
                print("Message Error - check p2p connection - event triggered image tap")
            }
        }
    }
    
    // adds tap recoginition to all image views
    func addTapRecognition(){
        for view in imageButtons{
            let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(imageTapped(tapGestureRecognizer:)))
            view.isUserInteractionEnabled = true
            view.addGestureRecognizer(tapGestureRecognizer)
        }
    }
    // disconnect / connect methods
    @IBAction func disconnectTapped(_ sender: Any) {
        session.disconnect()
    }
    @IBAction func connectTapped(_ sender: UIBarButtonItem) {
        browser = MCBrowserViewController(serviceType: serviceID, session: session)
        browser.delegate = self
        present(browser, animated: true, completion: nil)
    }
    
    // checks again to make sure theres only one user. Once one user starts the peer connect, the UI will change to reflect the
    // game board.
    func session(_ session: MCSession, peer peerID: MCPeerID, didChange state: MCSessionState) {
        if state == MCSessionState.connected{
            // connected
            if session.connectedPeers.count == 1{
                advertiser.stop()
                DispatchQueue.main.async {
                    self.navigationItem.title = "Playing"
                    self.connectButton.title = "Connected"
                    self.connectButton.isEnabled = false
                    self.disconnectButton.isEnabled = true
                    self.timerLabel.text = "Connected! Press play to begin."
                    self.showScores()
                }
            }else{
                // discconects the session if the count is over 2
                DispatchQueue.main.async {
                    self.browser.tooManyConnectedModal()
                }
                session.disconnect()
            }
        }else if state == MCSessionState.connecting{
            DispatchQueue.main.async {
                self.navigationItem.title = "Connecting..."
            }
        }else if state == MCSessionState.notConnected{
            DispatchQueue.main.async {
                self.navigationItem.title = "Disconnected"
                self.connectButton.title = "Connect"
                self.connectButton.isEnabled = true
                self.disconnectButton.isEnabled = false
                self.disconnectedModal()
                self.disconnectReset()
                self.timerLabel.text = "Not connected. Press connect to select an opponent."
                self.hideScores()
            }
            advertiser.start()
        }
    }
    
    // Recieving Data
    // the first conditional checks to see if the other player is ready, the other checks to see what
    // choice the opponent made
    func session(_ session: MCSession, didReceive data: Data, fromPeer peerID: MCPeerID) {
        // get players ready status
        if let data: String = String(data: data, encoding: .utf8){
            if data == "true"{
                DispatchQueue.main.async {
                    self.opponetsReadyStatus = "true"
                    self.opponentReadyLabel.isHidden = false
                    self.opponentReadyLabel.text = "Opponet Ready"
                    self.bothReady()
                }
            }else if data == "0" || data == "1" || data == "2" || data == "-1"{
                opponentsChoice = data
                DispatchQueue.main.async {
                    self.opponetsSelectionImageView.image = UIImage(named: self.opponentsChoice)
                }
            }
        }
    }
    // initally sets defaults incase the user doesnt press an option during the round
    // once play is pressed, the countdown starts and the game begins.
    // once the coutdown is finished the winner is revealed and scores are tallied
    @IBAction func playButtonPressed(_ sender: Any) {
        if session.connectedPeers.count == 1
        {
            opponentsChoice = "-1"
            userChoice = "-1"
            timerLabel.text = ""
            readyStatus = "true"
            if let message: Data = readyStatus.data(using: String.Encoding.utf8) {
                do {
                    for playerChoices in [opponetsSelectionImageView, playersSelectionImageView]{
                        playerChoices?.image = nil
                    }
                    try session.send(message, toPeers: session.connectedPeers, with: MCSessionSendDataMode.reliable)
                    DispatchQueue.main.async {
                        self.disconnectButton.isEnabled = false
                        self.playButton.isOn = false
                        self.playButton.isUserInteractionEnabled = false
                        self.bothReady()
                        self.opponentReadyLabel.text = "Choose your weapon! Quick!"
                        for label in [self.playerChoiceLabel, self.opponentChoiceLabel]{
                            label?.isHidden = false
                        }
                        self.playersSelectionImageView.isHidden = false
                        self.opponetsSelectionImageView.isHidden = true

                    }
                }
                catch {
                    print("Message Error - check p2p connection - event triggered from play button")
                }
            }
        }else{
            // if user presses play with no connection a modal will pop up
            noConnectionModal()
            playButtonInitalState()
        }
    }
    
    // Received a byte stream from remote peer.
    func session(_ session: MCSession, didReceive stream: InputStream, withName streamName: String, fromPeer peerID: MCPeerID) {}
    
    // Start receiving a resource from remote peer.
    func session(_ session: MCSession, didStartReceivingResourceWithName resourceName: String, fromPeer peerID: MCPeerID, with progress: Progress) {}
    
    func session(_ session: MCSession, didFinishReceivingResourceWithName resourceName: String, fromPeer peerID: MCPeerID, at localURL: URL?, withError error: Error?) {}
    
    func browserViewControllerDidFinish(_ browserViewController: MCBrowserViewController) {
        browser.dismiss(animated: true, completion: nil)
    }
    
    func browserViewControllerWasCancelled(_ browserViewController: MCBrowserViewController) {
        browser.dismiss(animated: true, completion: nil)
    }
    //sets the conditions for when both players hit play
    func bothReady(){
        if self.opponetsReadyStatus == "true" && self.readyStatus == "true"{
            showButtons()
            self.timerLabel.isHidden = false
            self.countDownTimer()
            self.opponentReadyLabel.text = "Choose your weapon! Quick!"
            playButton.setTitle("In Sesh!", for: .normal)
            for label in [playerChoiceLabel, opponentChoiceLabel]{
                label?.isHidden = false
            }
        }
    }
    
    // sets all values back to their starting values when a disconnect occurs
    func disconnectReset(){
        userChoice = "-1"
        opponentsChoice = "-1"
        readyStatus = "false"
        opponetsReadyStatus = "false"
        userScore = 0
        opponentScore = 0
        tieScore = 0
        startingTimer = 4

        opponentReadyLabel.isHidden = true
        playerChoiceLabel.isHidden = true
        opponentChoiceLabel.isHidden = true
        playersSelectionImageView.isHidden = true
        opponetsSelectionImageView.isHidden = true
        timerLabel.isHidden = true
        playButtonInitalState()
        playButton.setButton()
        setScore()
        playButton.isUserInteractionEnabled = true
        timerLabel.isHidden = false
    }
}

