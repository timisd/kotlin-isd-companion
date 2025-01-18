package de.hshl.isd.companion.features.shared

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import de.hshl.isd.companion.features.shared.BackgroundDark
import de.hshl.isd.companion.features.shared.BackgroundLight
import de.hshl.isd.companion.features.shared.BlueTertiaryContainerDark
import de.hshl.isd.companion.features.shared.BlueTertiaryContainerLight
import de.hshl.isd.companion.features.shared.BlueTertiaryDark
import de.hshl.isd.companion.features.shared.BlueTertiaryLight
import de.hshl.isd.companion.features.shared.ErrorContainerDark
import de.hshl.isd.companion.features.shared.ErrorContainerLight
import de.hshl.isd.companion.features.shared.ErrorDark
import de.hshl.isd.companion.features.shared.ErrorLight
import de.hshl.isd.companion.features.shared.GreenSecondaryContainerDark
import de.hshl.isd.companion.features.shared.GreenSecondaryContainerLight
import de.hshl.isd.companion.features.shared.GreenSecondaryDark
import de.hshl.isd.companion.features.shared.GreenSecondaryLight
import de.hshl.isd.companion.features.shared.OnBackgroundDark
import de.hshl.isd.companion.features.shared.OnBackgroundLight
import de.hshl.isd.companion.features.shared.OnBlueTertiaryContainerDark
import de.hshl.isd.companion.features.shared.OnBlueTertiaryContainerLight
import de.hshl.isd.companion.features.shared.OnBlueTertiaryDark
import de.hshl.isd.companion.features.shared.OnBlueTertiaryLight
import de.hshl.isd.companion.features.shared.OnErrorContainerDark
import de.hshl.isd.companion.features.shared.OnErrorContainerLight
import de.hshl.isd.companion.features.shared.OnErrorDark
import de.hshl.isd.companion.features.shared.OnErrorLight
import de.hshl.isd.companion.features.shared.OnGreenSecondaryContainerDark
import de.hshl.isd.companion.features.shared.OnGreenSecondaryContainerLight
import de.hshl.isd.companion.features.shared.OnGreenSecondaryDark
import de.hshl.isd.companion.features.shared.OnGreenSecondaryLight
import de.hshl.isd.companion.features.shared.OnOrangeContainerDark
import de.hshl.isd.companion.features.shared.OnOrangeContainerLight
import de.hshl.isd.companion.features.shared.OnOrangePrimaryDark
import de.hshl.isd.companion.features.shared.OnOrangePrimaryLight
import de.hshl.isd.companion.features.shared.OnSurfaceDark
import de.hshl.isd.companion.features.shared.OnSurfaceLight
import de.hshl.isd.companion.features.shared.OnSurfaceVariantDark
import de.hshl.isd.companion.features.shared.OnSurfaceVariantLight
import de.hshl.isd.companion.features.shared.OrangeContainerDark
import de.hshl.isd.companion.features.shared.OrangeContainerLight
import de.hshl.isd.companion.features.shared.OrangePrimaryDark
import de.hshl.isd.companion.features.shared.OrangePrimaryLight
import de.hshl.isd.companion.features.shared.OutlineDark
import de.hshl.isd.companion.features.shared.OutlineLight
import de.hshl.isd.companion.features.shared.SurfaceDark
import de.hshl.isd.companion.features.shared.SurfaceLight
import de.hshl.isd.companion.features.shared.SurfaceVariantDark
import de.hshl.isd.companion.features.shared.SurfaceVariantLight

val DarkColorScheme = darkColorScheme(
    primary = OrangePrimaryDark,
    secondary = GreenSecondaryDark,
    tertiary = BlueTertiaryDark,
    onPrimary = OnOrangePrimaryDark,
    primaryContainer = OrangeContainerDark,
    onPrimaryContainer = OnOrangeContainerDark,
    onSecondary = OnGreenSecondaryDark,
    secondaryContainer = GreenSecondaryContainerDark,
    onSecondaryContainer = OnGreenSecondaryContainerDark,
    onTertiary = OnBlueTertiaryDark,
    onTertiaryContainer = OnBlueTertiaryContainerDark,
    tertiaryContainer = BlueTertiaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    outline = OutlineDark,
)

val LightColorScheme = lightColorScheme(
    primary = OrangePrimaryLight,
    secondary = GreenSecondaryLight,
    tertiary = BlueTertiaryLight,
    onPrimary = OnOrangePrimaryLight,
    primaryContainer = OrangeContainerLight,
    onPrimaryContainer = OnOrangeContainerLight,
    onSecondary = OnGreenSecondaryLight,
    secondaryContainer = GreenSecondaryContainerLight,
    onSecondaryContainer = OnGreenSecondaryContainerLight,
    onTertiary = OnBlueTertiaryLight,
    onTertiaryContainer = OnBlueTertiaryContainerLight,
    tertiaryContainer = BlueTertiaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    outline = OutlineLight,
)