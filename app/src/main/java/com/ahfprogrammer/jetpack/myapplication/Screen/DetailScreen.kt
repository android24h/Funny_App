package com.ahfprogrammer.jetpack.myapplication.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun DetailScreen(name: String?, url: String?) {

    Column(
        modifier = Modifier
            .background(Color(0xffffc107))
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (url!=null){
            AsyncImage(
                model = url,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))

            )
        }
        Spacer(modifier = Modifier.height(20.dp))
      if (name!=null){
          Text(text = name,
              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentSize(),
              fontSize = 30.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
              lineHeight = 45.sp


          )
      }

    }

}