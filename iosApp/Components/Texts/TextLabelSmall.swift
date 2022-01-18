//
//  TextLabelSmall.swift
//  GitHubViewer
//
//  Created by Виталий Зарубин on 16.01.2022.
//

import SwiftUI

struct TextLabelSmall: View {
    var text: String
    var maxLines = Int.max
    var color = Color.onBackground
    var alignment: TextAlignment = .leading

    var body: some View {
        Text(text.getAttributedString(
            font: FontFamily.Poppins.medium,
            color: color,
            size: 11
        ))
        .fontWeight(.medium)
        .lineSpacing(0.5)
        .lineLimit(maxLines)
        .multilineTextAlignment(alignment)
    }
}
