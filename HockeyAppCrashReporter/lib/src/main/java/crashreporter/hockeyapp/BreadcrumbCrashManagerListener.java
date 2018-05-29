package crashreporter.hockeyapp;

import net.hockeyapp.android.CrashManagerListener;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
class BreadcrumbCrashManagerListener extends CrashManagerListener {

	private final int mMaxNumBreadCrumbs;
	private String mUserId;
	private final Queue<String> mBreadCrumbs;

	BreadcrumbCrashManagerListener(final int maxNumBreadCrumbs) {
		mMaxNumBreadCrumbs = maxNumBreadCrumbs;
		mBreadCrumbs = new LinkedList<>();
	}

	void addBreadcrumb(final String crumb) {
		synchronized (mBreadCrumbs) {
			mBreadCrumbs.add(crumb);
			if (mBreadCrumbs.size() > mMaxNumBreadCrumbs) {
				mBreadCrumbs.poll();
			}
		}
	}

	@Override
	public String getDescription() {
		StringBuilder stringBuilder = new StringBuilder();
		synchronized (mBreadCrumbs) {
			for (String breadcrumb : mBreadCrumbs) {
				stringBuilder.append(breadcrumb)
						.append('\n');
			}
		}
		return stringBuilder.toString();
	}

	@Override
	public String getUserID() {
		return mUserId == null ? super.getUserID() : mUserId;
	}

	public void setUserId(final String userId) {
		mUserId = userId;
	}
}
