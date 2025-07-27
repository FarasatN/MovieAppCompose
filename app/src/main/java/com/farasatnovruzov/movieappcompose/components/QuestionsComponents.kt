package com.farasatnovruzov.movieappcompose.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.model.QuestionItem
import com.farasatnovruzov.movieappcompose.screens.questions.QuestionsViewModel
import com.farasatnovruzov.movieappcompose.util.AppColors

@Composable
fun Questions(
    viewModel: QuestionsViewModel
) {
    val questions = viewModel.data.value.data?.toMutableList()
//    Log.d("size", "Questions: ${questions?.size}")
    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.loading == true) {
//        Log.d("loading", "Loading:  ${viewModel.data.value.loading}")
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
//        QuestionDisplay()
    } else {
//        questions?.forEach { questionItem ->
//            Log.d("result", "Questions: ${questionItem.question}")
//        }
        val question = try {
            questions?.get(questionIndex.value)
        } catch (ex: Exception) {
            null
        }
        if (questions != null) {
            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                viewModel = viewModel
            ){
                questionIndex.value = questionIndex.value + 1
            }
        }
    }
}

//@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
//            = QuestionItem(answer ="4", category ="math", choices = listOf("3", "4", "5"), "2x2=?"),
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit = {}
) {
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {

        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("DEBUG: TOP OF COLUMN", color = Color.White) // Debug text
            QuestionTracker( // Use hardcoded values for extreme simplicity first
                counter = 1,
                outOf = 5
            )
            DrawDottedLine(pathEffect)

            Text("DEBUG: TOP OF COLUMN", color = Color.White) // Debug text

            Column {
                Text(
                    text = question.question,
                    fontSize = 17.sp,
                    fontWeight = Bold,
                    lineHeight = 22.sp,
                    color = AppColors.mOffWhite,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(alignment = Alignment.Start)
//                        .fillMaxHeight(0.3f)
                )


                //choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .background(Color.Transparent)
//                            .background(Color.DarkGray)
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mLightPurple,
                                        AppColors.mLightPurple
                                    )
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
                                    Color.Green.copy(alpha = 0.7f)
                                } else {
                                    Color.Red.copy(alpha = 0.7f)
                                }
                            )
                        )
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green.copy(alpha = 0.7f)
                                    } else if (correctAnswerState.value == false && index == answerState.value) {
                                        Color.Red.copy(alpha = 0.7f)
                                    } else {
                                        AppColors.mOffWhite
                                    },
                                    fontSize = 17.sp
                                )
                            ) {
                                append(answerText)
                            }
                        }
                        Text(
                            text = annotatedString,
//                            color = AppColors.mOffWhite,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
                Button(
                    onClick = {onNextClicked(questionIndex.value)},
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ) {
                    append("Question ${counter}/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightPurple,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$outOf")
                    }
                }
            }
        },
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        onDraw = {
            drawLine(
                color = AppColors.mLightGray,
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        })
}
