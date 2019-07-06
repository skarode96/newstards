package com.news.aggregator.newstard.ui.views.iconbarview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.news.aggregator.newstard.R
import com.news.aggregator.newstard.repositories.services.NewsService

class IconBarView(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {

    // Class level extension function
    private fun Float.toDP(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics).toInt()

    // Layout for View
    val layout: ConstraintLayout = inflate(context, R.layout.view_icon_bar_layout, this) as ConstraintLayout

    // Data binding Variables
    var  services: List<NewsService>? = null
    set(value){
        field = value

        value?.let { renderServicesAsImageIcons(it) }
    }

    // Private variables
    private val _iconButtonWidth = 30f.toDP()
    private val _iconButtonHeight = 30f.toDP()
    private val _activeIconButtonWidth = 50f.toDP()
    private val _activeIconButtonHeight = 50f.toDP()

    /**
     * Add services as child view in linear layout
     */
    private fun renderServicesAsImageIcons(services: List<NewsService>) {
        val container: LinearLayout = findViewById(R.id.viewIconBarImageIconLayout)
        services.forEach { container.addView(getViewForService(it)) }
    }

    /**
     * Convert service instance to ImageButtonView instance
     */
    private fun getViewForService(service: NewsService): ImageButton {
        val imageButton = ImageButton(context)

        with(imageButton) {
            id = service.id
            layoutParams = LinearLayout.LayoutParams(30f.toDP(), 30f.toDP())
            scaleType = ImageView.ScaleType.FIT_XY
            adjustViewBounds = true

            setBackgroundResource(R.drawable.view_icon_bar_icon_button_background)
            setImageResource(service.icon)

            setOnClickListener {
                handleImageButtonClicked(imageButton, service)
            }
        }

        return imageButton
    }

    private fun handleImageButtonClicked(view: ImageButton, service: NewsService) {
        Log.e("Sunit", "Service Clicked: "+service.toString())
    }

    /**
     * Scale up animation fot Image Button
     * Note: Can use ValueAnimator to show animation instead of instantaneous value change
     */
    private fun scaleUpImageButton(view: ImageButton){
        val layoutParams = view.layoutParams
        layoutParams.width = _activeIconButtonWidth
        layoutParams.height = _activeIconButtonHeight
        view.layoutParams = layoutParams
    }

    /**
     * Scale down animation fot Image Button
     */
    private fun scaleUpDownButton(view: ImageButton){
        val layoutParams = view.layoutParams
        layoutParams.width = _iconButtonWidth
        layoutParams.height = _iconButtonHeight
        view.layoutParams = layoutParams
    }

}
