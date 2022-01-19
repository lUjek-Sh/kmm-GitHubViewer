//
//  GitHubViewerApp.swift
//  GitHubViewer
//
//  Created by Виталий Зарубин on 05.10.2021.
//

import SwiftUI

@main
struct AppGitHubViewer: App {
    var body: some Scene {
        WindowGroup {
            if UserDefaults.standard.bool(forKey: "currentLevel") {
                NavGraphUser()
            } else {
                NavGraphGuest()
            }
        }
    }
}
