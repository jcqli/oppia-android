package org.oppia.android.app.home.promotedlist

import android.content.res.Configuration
import android.content.res.Resources
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import org.oppia.android.R
import org.oppia.android.app.home.HomeItemViewModel
import org.oppia.android.app.home.RouteToRecentlyPlayedListener
import org.oppia.android.app.shim.IntentFactoryShim

/** [ViewModel] for the promoted story list displayed in [HomeFragment]. */
class PromotedStoryListViewModel(
  private val activity: AppCompatActivity,
  private val internalProfileId: Int,
  private val intentFactoryShim: IntentFactoryShim,
  val promotedStoryList: List<PromotedStoryViewModel>
) : HomeItemViewModel() {
  // TODO(#2297): Update this span count and move to values/integers.xml once behavior is clarified
  private val promotedStoriesTabletSpanCount: Int =
    if (Resources.getSystem().configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
    else 3

    /** Returns the padding placed at the end of the promoted stories list based on the number of promoted stories. */
  fun getEndPadding(): Int {
    return if (promotedStoryList.size > 1) {
      activity.resources.getDimensionPixelSize(R.dimen.home_padding_end)
    } else {
      activity.resources.getDimensionPixelSize(R.dimen.home_padding_start)
    }
  }

  /** Determines and returns the visibility for the "View All" button. */
  fun getButtonVisibility(): Int {
    if (activity.resources.getBoolean(R.bool.isTablet)) {
      when (Resources.getSystem().configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
          return if (promotedStoryList.size > promotedStoriesTabletSpanCount)View.VISIBLE
          else View.INVISIBLE
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
          return if (promotedStoryList.size > promotedStoriesTabletSpanCount) View.VISIBLE
          else View.INVISIBLE
        }
        else -> View.VISIBLE
      }
    }
    return View.VISIBLE
  }

  fun routeToRecentlyPlayed() {
    val intent = intentFactoryShim.createRecentlyPlayedActivityIntent(
      activity.applicationContext,
      internalProfileId
    )
    activity.startActivity(intent)
  }
}