package co.icristea.yuga.presentation.onboarding

import androidx.annotation.DrawableRes
import co.icristea.yuga.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val text: String,
    val title: String,
    val message: String,
) {
    object First : OnBoardingPage(
        image = R.drawable.onboarding_1,
        text = "Yoga",
        title = "Daily Yoga",
        message = "Do your practice of physical exercise and relaxation make healthy",
    )

    object Second : OnBoardingPage(
        image = R.drawable.onboarding_2,
        text = "Meditation",
        title = "Yoga classes",
        message = "Meditation is the key to Productivity, Happiness & Longevity",
    )

    object Third : OnBoardingPage(
        image = R.drawable.onboarding_3,
        text = "Meets",
        title = "Community",
        message = "Do Your practice of physical exercise and relaxation make healthy",
    )
}
