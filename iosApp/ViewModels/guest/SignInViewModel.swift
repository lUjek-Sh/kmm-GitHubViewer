//
//  SignInViewModel.swift
//  GitHubViewer
//
//  Created by Виталий Зарубин on 13.01.2022.
//

import Combine
import Foundation
import shared

class SignInViewModel: ObservableObject, Identifiable {
    
    var serviceNetwork = AuthNetwork()
    
    @Published var isShowProgressView = false
    @Published var error: ResponseError?

    func authUser(url: URL, action: (() -> Void)?) {
        if url.path == "/oauth" {
            let code = url.valueOf("code")
            if code != nil {
                isShowProgressView = true
                Task {
                    await authUser(code: code!, action: action)
                }
            }
        } else {
            // @todo error
        }
    }

    func authUser(code: String, action: (() -> Void)?) async {
        DispatchQueue.main.async {
            self.error = nil
        }
        do {
            let response = try await serviceNetwork.oauth(code: code)
            ConstantsKMM.STORAGE.authToken = response.accessToken
            ConstantsKMM.CLIENT.setToken(token: ConstantsKMM.STORAGE.authToken)
            DispatchQueue.main.async {
                action?()
            }
        } catch let error as ResponseError {
            self.error = error
        } catch {
            print("Unexpected error: \(error).")
        }
    }

    func clear() {
        isShowProgressView = false
        error = nil
    }
}
