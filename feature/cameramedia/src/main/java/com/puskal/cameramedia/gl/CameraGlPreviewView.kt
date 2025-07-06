package com.puskal.cameramedia.gl

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.view.Surface
import android.view.TextureView
import androidx.camera.core.Preview
import com.daasuu.mp4compose.filter.GlFilter
import com.daasuu.mp4compose.gl.GlPreviewFilter
import com.daasuu.mp4compose.gl.GlSurfaceTexture
import java.util.concurrent.Executor

/**
 * Simple TextureView based preview that attaches a CameraX [Preview.SurfaceProvider]
 * to a [GlSurfaceTexture] and renders frames using a [GlPreviewFilter] and
 * arbitrary [GlFilter]. This is a very lightweight wrapper intended for demo
 * purposes and does not cover all edge cases.
 */
class CameraGlPreviewView(context: Context) : TextureView(context), TextureView.SurfaceTextureListener {

    private var surfaceTextureWrapper: GlSurfaceTexture? = null
    private var previewFilter: GlPreviewFilter? = null
    private var glFilter: GlFilter = GlFilter()

    init {
        surfaceTextureListener = this
    }

    fun setGlFilter(filter: GlFilter) {
        glFilter.release()
        glFilter = filter
        glFilter.setup()
    }

    fun surfaceProvider(executor: Executor): Preview.SurfaceProvider = Preview.SurfaceProvider { request ->
        val texture = surfaceTextureWrapper?.surfaceTexture
        if (texture != null) {
            request.provideSurface(Surface(texture), executor) { }
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        // Initialize GL wrapper objects when the TextureView becomes available
        val tex = IntArray(1)
        GLES20.glGenTextures(1, tex, 0)
        surfaceTextureWrapper = GlSurfaceTexture(tex[0])
        previewFilter = GlPreviewFilter(surfaceTextureWrapper!!.textureTarget)
        previewFilter?.setup()
        glFilter.setup()
        surfaceTextureWrapper!!.setOnFrameAvailableListener {
            this@CameraGlPreviewView.invalidate()
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        surfaceTextureWrapper?.release()
        previewFilter?.release()
        glFilter.release()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
}
