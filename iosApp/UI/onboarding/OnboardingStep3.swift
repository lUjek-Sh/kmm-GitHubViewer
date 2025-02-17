//
//  OnboardingStep3.swift
//  GitHubViewer
//
//  Created by Виталий Зарубин on 13.01.2022.
//

import SwiftUI

struct OnboardingStep3: View {
    var body: some View {
        VStack {
            LottieView(name: "step3").frame(width: 230, height: 150)

            Spacer().frame(height: 30)

            TextTitleLarge(
                text: L10nOnboarding.step3Title,
                alignment: .center
            )

            Spacer().frame(height: 20)

            TextBodyLarge(
                text: L10nOnboarding.step3Text,
                alignment: .center
            )
        }.padding(20)
    }
}

struct OnboardingStep3_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStep3()
    }
}
