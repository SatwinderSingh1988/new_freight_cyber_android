package com.app.freightCyber.presentation.home_dashboard


import android.content.pm.ActivityInfo
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityHomeBinding
import com.app.freightCyber.databinding.LogoutDialogDesignBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.dialog.BaseCustomDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeActivityVM by viewModels()
    private lateinit var navController: NavController

    override fun getLayoutResource(): Int {
        return R.layout.activity_home
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        setUpNavController()
        initOnClick()
        val navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)
        val imgBack = headerView.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener{
            binding.drawerLayout.close()
        }
        showLogoutDialog()
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeContainer) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    homeFragmentOperation()
                }

                R.id.jobFragment -> {
                    jobFragmentOperation()
                }

                R.id.complianceAuthFragment, R.id.complianceDataFragment -> {
                   complianceFragmentOperation()
                }

                R.id.inboxFragment -> {
                   inboxFragmentOpearation()
                }

                R.id.ewdFragment ->{
                  ewdFragmentOperation()
                }


                R.id.investigationFragment  , R.id.workRestChangesFragment , R.id.addAnnotationTwoFragment , R.id.workDiarySummaryFragment , R.id.driverTimeSheetFragment->{
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    hideBothHeaderAndBottomNavigation()
                }

                else -> {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    hideBothHeaderAndBottomNavigation()
                }
            }
        }
    }

    private fun inboxFragmentOpearation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.imgNotification.setImageResource(R.drawable.icon_add_2)
        binding.imgNotification.tag = "ADD"
        binding.tvHeader.text = getString(R.string.inbox)
        showBothHeaderAndBottomNavigation()
    }

    private fun ewdFragmentOperation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.imgNotification.setImageResource(R.drawable.profile_icon)
        binding.imgNotification.tag = "PROFILE"
        showBothHeaderAndBottomNavigation()
        binding.tvHeader.text = getString(R.string.ework_diary)
    }

    private fun complianceFragmentOperation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.imgNotification.setImageResource(R.drawable.notification_icon)
        binding.imgNotification.tag = "NOTIFICATION"
        binding.tvHeader.text = getString(R.string.compliance)
        showBothHeaderAndBottomNavigation()
    }

    private fun jobFragmentOperation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.imgNotification.setImageResource(R.drawable.notification_icon)
        binding.imgNotification.tag = "NOTIFICATION"
        binding.tvHeader.text = getString(R.string.my_jobs)
        showBothHeaderAndBottomNavigation()
    }

    private fun homeFragmentOperation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.imgNotification.setImageResource(R.drawable.notification_icon)
        binding.imgNotification.tag = "NOTIFICATION"
        binding.tvHeader.text = "Welcome Robert!"
        showBothHeaderAndBottomNavigation()
    }

    private fun hideHeader() {
        binding.imgHam.visibility = View.GONE
        binding.tvHeader.visibility = View.GONE
        binding.imgNotification.visibility = View.GONE
    }

    private fun showHeader() {
        binding.imgHam.visibility = View.VISIBLE
        binding.tvHeader.visibility = View.VISIBLE
        binding.imgNotification.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        binding.bottomNav.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    private fun showBothHeaderAndBottomNavigation() {
        binding.bottomNav.visibility = View.VISIBLE
        showHeader()
    }

    private fun hideBothHeaderAndBottomNavigation() {
        binding.bottomNav.visibility = View.GONE
        hideHeader()
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    navController.popBackStack()
                }

                R.id.imgHam -> {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }

                R.id.tvLogout -> {
                    logoutDialog?.show()
                }

                R.id.imgNotification -> {
                    when (binding.imgNotification.tag) {
                        "NOTIFICATION" -> {
                            navController.navigate(R.id.notificationFragment)
                        }
                        "ADD" -> {
                            navController.navigate(R.id.inboxCreateNewFragment)
                        }
                        "PROFILE" -> {
                            navController.navigate(R.id.profileSettingsFragment)
                        }
                    }
                }
            }
        })
    }

    private var logoutDialog: BaseCustomDialog<LogoutDialogDesignBinding>? = null
    private fun showLogoutDialog() {
        logoutDialog =
            BaseCustomDialog(
                this,
                R.layout.logout_dialog_design,
                BaseCustomDialog.Listener {
                    when (it.id) {
                        R.id.tvYes -> {
                            logoutDialog?.dismiss()
                        }

                        R.id.tvNo -> {
                            logoutDialog?.dismiss()
                        }
                    }
                })
        logoutDialog?.setCancelable(false)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    /* override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
         if (currentFocus != null) {
             val imm: InputMethodManager =
                 getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
             imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
         }
         return super.dispatchTouchEvent(ev)
     }*/


}