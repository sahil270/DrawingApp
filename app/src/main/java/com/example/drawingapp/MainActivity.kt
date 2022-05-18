package com.example.drawingapp

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private var drawingView: DrawingView? = null
    private var mImageButtonCurrentPaint: ImageButton? = null

    var customProgressDialog: Dialog? = null

    val openGalleryLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == RESULT_OK && result.data != null){
            val imageBackground:ImageView = findViewById(R.id.iv_background)
            imageBackground.setImageURI(result.data?.data)
        }
    }

    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val perMissionName = it.key
                val isGranted = it.value
                if (isGranted ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted now you can read the storage files.",
                        Toast.LENGTH_LONG
                    ).show()
                    val pickIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    openGalleryLauncher.launch(pickIntent)
                } else {

                    if (perMissionName == Manifest.permission.READ_EXTERNAL_STORAGE)
                        Toast.makeText(
                            this@MainActivity,
                            "Oops you just denied the permission.",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        val ibBrush: ImageButton = findViewById(R.id.ib_brush)
        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_paint_colors)
        val skin: ImageButton = findViewById(R.id.iv_skin)
        val black: ImageButton = findViewById(R.id.iv_black)
        val red: ImageButton = findViewById(R.id.iv_red)
        val green: ImageButton = findViewById(R.id.iv_green)
        val blue: ImageButton = findViewById(R.id.iv_blue)
        val yellow: ImageButton = findViewById(R.id.iv_yellow)
        val lollipop: ImageButton = findViewById(R.id.iv_lollipop)
        val random: ImageButton = findViewById(R.id.iv_random)

        skin.setOnClickListener{
            if (skin !== mImageButtonCurrentPaint) {
                val colorTag = skin.tag.toString()
                drawingView?.setColor(colorTag)
                skin.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = skin
            }
        }
        black.setOnClickListener{
            if (black !== mImageButtonCurrentPaint) {
                val colorTag = black.tag.toString()
                drawingView?.setColor(colorTag)
                black.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = black
            } }
        red.setOnClickListener{
            if (red !== mImageButtonCurrentPaint) {
                val colorTag = red.tag.toString()
                drawingView?.setColor(colorTag)
                red.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = red
            }}
        green.setOnClickListener{
            if (green !== mImageButtonCurrentPaint) {
                val colorTag = green.tag.toString()
                drawingView?.setColor(colorTag)
                green.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = green
            }
        }
        blue.setOnClickListener{
            if (blue !== mImageButtonCurrentPaint) {
                val colorTag = blue.tag.toString()
                drawingView?.setColor(colorTag)
                blue.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = blue
            }
        }
        yellow.setOnClickListener{
            if (yellow !== mImageButtonCurrentPaint) {
                val colorTag = yellow.tag.toString()
                drawingView?.setColor(colorTag)
                yellow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = yellow
            } }
        lollipop.setOnClickListener{
            if (lollipop !== mImageButtonCurrentPaint) {
                val colorTag = lollipop.tag.toString()
                drawingView?.setColor(colorTag)
                lollipop.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_pressed
                    )
                )
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.pallet_normal
                    )
                )
                mImageButtonCurrentPaint = lollipop
            } }
        random.setOnClickListener{
            if (random !== mImageButtonCurrentPaint) {
                val colorTag = random.tag.toString()
                drawingView?.setColor(colorTag)
                random.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
                mImageButtonCurrentPaint?.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.pallet_normal))
                mImageButtonCurrentPaint = random
            }
        }

        drawingView?.setSizeForBrush(20.toFloat())
        mImageButtonCurrentPaint = linearLayoutPaintColors[1] as ImageButton
        mImageButtonCurrentPaint?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.pallet_pressed
            )
        )

        ibBrush.setOnClickListener {
            showBrushSizeChooserDialog()
        }
        val ibGallery: ImageButton = findViewById(R.id.ib_gallery)
        ibGallery.setOnClickListener {
            requestStoragePermission()
        }
        val ibUndo: ImageButton = findViewById(R.id.ib_undo)
        ibUndo.setOnClickListener {
            // This is for undo recent stroke.
            drawingView?.onClickUndo()
        }
        //reference the save button from the layout
        val ibSave:ImageButton = findViewById(R.id.ib_save)
        //set onclick listener
        ibSave.setOnClickListener{
            //check if permission is allowed
            if (isReadStorageAllowed()){
                showProgressDialog()
                //launch a coroutine block
                lifecycleScope.launch{
                    //reference the frame layout
                    val flDrawingView:FrameLayout = findViewById(R.id.fl_drawing_view_container)
                    saveBitmapFile(getBitmapFromView(flDrawingView))
                }
            }
        }
    }


    private fun showBrushSizeChooserDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size :")
        val smallBtn: ImageButton = brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener{
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumBtn: ImageButton = brushDialog.findViewById(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener{
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        val largeBtn: ImageButton = brushDialog.findViewById(R.id.ib_large_brush)
        largeBtn.setOnClickListener{
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
    }

    private fun isReadStorageAllowed(): Boolean {

        val result = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission(){

        if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ){

            showRationaleDialog("Kids Drawing App","Kids Drawing App " +
                    "needs to Access Your External Storage")
        }
        else {

            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }

    }

    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    /**
     * Create bitmap from view and returns it
     */
    private fun getBitmapFromView(view: View): Bitmap {

        //Define a bitmap with the same size as the view.
        // CreateBitmap : Returns a mutable bitmap with the specified width and height
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        // draw the view on the canvas
        view.draw(canvas)
        //return the bitmap
        return returnedBitmap
    }

    private suspend fun saveBitmapFile(mBitmap: Bitmap?):String{
        var result = ""
        withContext(Dispatchers.IO) {
            if (mBitmap != null) {

                try {
                    val bytes = ByteArrayOutputStream() // Creates a new byte array output stream.
                    // The buffer capacity is initially 32 bytes, though its size increases if necessary.

                    mBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)


                    val f = File(
                        externalCacheDir?.absoluteFile.toString()
                                + File.separator + "KidDrawingApp_" + System.currentTimeMillis() / 1000 + ".jpg"
                    )

                    val fo = FileOutputStream(f) // Creates a file output stream to write to the file represented by the specified object.
                    fo.write(bytes.toByteArray()) // Writes bytes from the specified byte array to this file output stream.
                    fo.close() // Closes this file output stream and releases any system resources associated with this stream. This file output stream may no longer be used for writing bytes.
                    result = f.absolutePath // The file absolute path is return as a result.
                    //We switch from io to ui thread to show a toast
                    runOnUiThread {
                        cancelProgressDialog()
                        if (result.isNotEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                "File saved successfully :$result",
                                Toast.LENGTH_SHORT
                            ).show()
                            shareImage(result)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Something went wrong while saving the file.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    result = ""
                    e.printStackTrace()
                }
            }
        }
        return result
    }

    // TODO (Step 1 - Sharing the downloaded Image file)
    private fun shareImage(result:String){

        /*MediaScannerConnection provides a way for applications to pass a
        newly created or downloaded media file to the media scanner service.
        The media scanner service will read metadata from the file and add
        the file to the media content provider.
        The MediaScannerConnectionClient provides an interface for the
        media scanner service to return the Uri for a newly scanned file
        to the client of the MediaScannerConnection class.*/

        /*scanFile is used to scan the file when the connection is established with MediaScanner.*/
        MediaScannerConnection.scanFile(
            this@MainActivity, arrayOf(result), null
        ) { _, uri ->
            // This is used for sharing the image after it has being stored in the storage.
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                uri
            ) // A content: URI holding a stream of data associated with the Intent, used to supply the data being sent.
            shareIntent.type =
                "image/png" // The MIME type of the data being handled by this intent.
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    "Share"
                )
            )// Activity Action: Display an activity chooser,
            // allowing the user to pick what they want to before proceeding.
            // This can be used as an alternative to the standard activity picker
            // that is displayed by the system when you try to start an activity with multiple possible matches,
            // with these differences in behavior:
        }
        // END
    }
    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog?.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
}