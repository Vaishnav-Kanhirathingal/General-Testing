package com.example.payment

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.payment.databinding.ActivityMainBinding

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding= ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        applyBinding()
//    }
//
//    private fun applyBinding() {
//        binding.testButton.setOnClickListener {
//            SampleServer().test()
//        }
//    }
//}

class MainActivity : AppCompatActivity() {
    val TAG = this::class.simpleName

    private val REQUEST_CODE = 123
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyBinding()
    }

    private fun applyBinding() {
        val PAYTM = "net.one97.paytm"
        val GOOGLE_PAY = "com.google.android.apps.nbu.paisa.user"
//        val PHONE_PE = "com.phonepe.app"
//        val BHIM_UPI = "in.org.npci.upiapp"

        val upiApps = listOf(PAYTM, GOOGLE_PAY /*PHONE_PE, BHIM_UPI*/)

        val upiAppButtons = listOf(
            binding.paytmButton,
            binding.googlePayButton,
//            binding.phonepe,
//            binding.bhim,
        )

        val uri = getUpiUrl(
            amount = 1,
            paytmPhoneNumber = "7219648837",
            transactionNote = "Some Note containing a transaction ID"
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.data = Uri.parse(uri)

        binding.upiButton.setOnClickListener {
            val chooser = Intent.createChooser(intent, "Pay with...")
            startActivityForResult(chooser, REQUEST_CODE)
        }

        for (i in upiApps.indices) {
            val b = upiAppButtons[i]
            val p = upiApps[i]
            Log.d(TAG, p + " | ${isAppInstalled(p)} | ${isAppUpiReady(p)}")
            if (isAppInstalled(p) && isAppUpiReady(p)) {
                b.visibility = View.VISIBLE
                b.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.data = Uri.parse(uri)
                    intent.setPackage(p)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            } else {
                b.visibility = View.GONE
            }
        }
    }

    private fun getUpiUrl(
        amount: Int,
        paytmPhoneNumber: String,
        transactionNote: String
    ): String {
        return "upi://pay" +
                "?" + "pa=$paytmPhoneNumber@paytm" +
                "&" + "pn=Paytm%20Merchant" +
                "&" + "tn=$transactionNote" +
                "&" + "am=$amount"
    }

    private fun isAppInstalled(packageName: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return false
    }

    fun isAppUpiReady(packageName: String): Boolean {
        var appUpiReady = false
        val upiIntent = Intent(Intent.ACTION_VIEW, Uri.parse("upi://pay"))
        val pm = packageManager
        val upiActivities: List<ResolveInfo> = pm.queryIntentActivities(upiIntent, 0)
        for (a in upiActivities) {
            if (a.activityInfo.packageName == packageName) appUpiReady = true
        }
        return appUpiReady
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            // Process based on the data in response.
            Log.d(TAG, data.toString())
            data?.getStringExtra("Status")?.let { Log.d("result", it) };
            data?.getStringExtra("Status")
                ?.let { Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show() };
        }
    }
}