package com.example.ciphersafe

import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.google.zxing.BarcodeFormat

object QrCodeReader {
    private lateinit var barcodeView: DecoratedBarcodeView

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            barcodeView.resume()
        }
    }

    override fun onResume() {
        super.onResume()
        requestPermission.launch(Manifest.permission.CAMERA)
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Initialize ZXing BarcodeView
    barcodeView = findViewById(R.id.barcode_scanner)
    val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
    barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
    barcodeView.initializeFromIntent(intent)

    // Set up barcode callback
    val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // Handle barcode result
            // You can customize this part based on your app's requirements
        }
    }
    barcodeView.decodeContinuous(callback)

    // Set Jetpack Compose content
    setContent {
        val state = text.observeAsState()
        state.value?.let { ZxingDemo(root, it) }
    }
}
