//
//  OnboardingStep4.swift
//  GitHubViewer
//
//  Created by Виталий Зарубин on 13.01.2022.
//

import SwiftUI

struct OnboardingStep4: View {
    var body: some View {
        VStack {
            LottieView(name: "step4").frame(width: 150, height: 130)

            Spacer().frame(height: 30)

            TextTitleLarge(
                text: L10nOnboarding.step4Title,
                alignment: .center
            )

            Spacer().frame(height: 20)

            TextBodyLarge(
                text: L10nOnboarding.step4Text,
                alignment: .center
            )
        }.padding(20)
    }
}

struct OnboardingStep4_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStep4()
    }
}
