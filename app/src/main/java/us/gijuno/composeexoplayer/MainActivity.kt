package us.gijuno.composeexoplayer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

class MainActivity : ComponentActivity() {
    private val videoUris = arrayListOf(
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/6/2fd43dfd79ab4821a56fb2a9be4ea583-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/3/5891904c8f3b441494681bf5d4ef18ce-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/3/3a60d88a98454b20b1ba5ca3061bf0e8-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/3/661a5df61a3d4bb88260347828a7750f-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/4/eff2f3aac0594ed8b7384e629bf78111-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/4/226a19bf7a85479096337969a1e47a3d-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/4/815b57ca460143b7bc2817cc295f74c1-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/5/d89302140f124885b9577fc896a44bda-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/5/a486426a67b84e0b84b9651a5f4cb4e4-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/5/4eb06742b7df422c98f2fb018a9fdde4-original-184-3s-cropped.mp4",
        "https://thumbnail.vreview.tv/review/videos/production/2022/12/5/0d2f0096798a48e98cac431257282c5d-original-184-3s-cropped.mp4"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollState = rememberScrollState()
            
            Row(
                Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0 until videoUris.size) {
                        Log.d("delay", i.toString())
                        Box(Modifier
                            .size(200.dp)
                            .padding(10.dp)) {
                            VideoView(uri = videoUris[i])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VideoView(
    uri: String
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        }
    DisposableEffect(
        AndroidView(factory = {
            StyledPlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}