package snake.denisamaris.com.snake;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context context, GestureListenerCallback callback) {
        gestureDetector = new GestureDetector(context, new GestureListener(callback));
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private GestureListenerCallback callback;

        public GestureListener(GestureListenerCallback callback) {
            this.callback = callback;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    callback.onSwipeRight();
                else
                    callback.onSwipeLeft();
                return true;
            } else if(Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceY < 0)
                    callback.onSwipeUp();
                else
                    callback.onSwipeDown();
                return true;
            }
            return false;
        }
    }
}
