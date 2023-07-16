package com.example.payment

import android.util.Log
import com.paytm.pg.merchant.PaytmChecksum
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class SampleServer {
    private val TAG = this::class.simpleName

    fun test() {
        val body = JSONObject()
        body.put("requestType", "Payment")
        body.put("mid", "YOUR_MID_HERE")
        body.put("websiteName", "YOUR_WEBSITE_NAME")
        body.put("orderId", "ORDERID_98765")
        body.put("callbackUrl", "https://<callback URL to be used by merchant>")

        val txnAmount = JSONObject()
        txnAmount.put("value", "1.00")
        txnAmount.put("currency", "INR")

        val userInfo = JSONObject()
        userInfo.put("custId", "CUST_001")
        body.put("txnAmount", txnAmount)
        body.put("userInfo", userInfo)

        /**
         * Generate checksum by parameters we have in body
         * You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
         * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
         */


        /**
         * Generate checksum by parameters we have in body
         * You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
         * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
         */
        val checksum: String = PaytmChecksum.generateSignature(body.toString(), "YOUR_MERCHANT_KEY")

        val head = JSONObject()
        head.put("signature", checksum)

        val paytmParams = JSONObject()
        paytmParams.put("body", body)
        paytmParams.put("head", head)

        val post_data = paytmParams.toString()
        Log.d(TAG, "post data = $post_data")

        /* for Staging */

        /* for Staging */
        val url =
            URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765")

        /* for Production */
        // URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");


        /* for Production */
        // URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            val requestWriter = DataOutputStream(connection.outputStream)
            requestWriter.writeBytes(post_data)
            requestWriter.close()
            var responseData = ""
            val `is` = connection.inputStream
            val responseReader = BufferedReader(InputStreamReader(`is`))
            if (responseReader.readLine().also { responseData = it } != null) {
                System.out.append("Response: $responseData")
            }
            responseReader.close()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}