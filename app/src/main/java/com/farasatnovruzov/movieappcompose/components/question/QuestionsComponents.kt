package com.farasatnovruzov.movieappcompose.components.question

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.model.question.QuestionItem
import com.farasatnovruzov.movieappcompose.screens.questions.QuestionsViewModel
import com.farasatnovruzov.movieappcompose.utils.AppColors

@Composable
fun Questions(
    viewModel: QuestionsViewModel
) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.loading == true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = AppColors.mLightGray,
                )
            }
        }
    } else {
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
            ) {
                // Increment question index
                questionIndex.value = questionIndex.value + 1
            }
        }
    }
}

@Composable
fun QuestionDisplay(
    question: QuestionItem,
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
            // Pass the current question number (index + 1) and total questions
            ShowProgress(
                score = questionIndex.value + 1,
                totalQuestions = viewModel.getTotalQuestionCount()
            )

            QuestionTracker(
                counter = questionIndex.value + 1, // Display 1-based index
                outOf = viewModel.getTotalQuestionCount()
            )
            DrawDottedLine(pathEffect)

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
                        .fillMaxHeight(0.3f)
                )

                //choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .background(Color.Transparent)
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
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
                Button(
                    onClick = { onNextClicked(questionIndex.value) },
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

@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = Bold,
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
            .height(3.dp),
        onDraw = {
            drawLine(
                color = AppColors.mLightGray,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        })
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShowProgress(score: Int = 7, totalQuestions: Int = 10) { // Add totalQuestions parameter
    val gradient = Brush.linearGradient(
        listOf(
            Color(0xfffab075),
            Color(0xffec67a7),
            Color(0xffae50d6),
            Color(0xff8040df),
            Color(0xff4459d6),

        )
    )

    // Calculate progressFactor as a float between 0.0f and 1.0f
    val progressFactor = remember(score, totalQuestions) {
        if (totalQuestions > 0) {
            (score.toFloat() / totalQuestions.toFloat())
        } else {
            0f // Avoid division by zero
        }
    }

    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple,
                        AppColors.mLightPurple
                    )
                ),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
    ) {

        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(progressFactor) // UNCOMMENT THIS LINE
                .background(brush = gradient),
            enabled = false,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            // Display percentage or current score/total

        }
        Text(
            text = "${(progressFactor * 100).toDouble()}%", // Display percentage
            modifier = Modifier
//                    .clip(shape = RoundedCornerShape(23.dp))
//                    .fillMaxHeight(0.87f)
                .fillMaxWidth()
//                    .padding(6.dp)
            ,
            color = AppColors.mOffWhite,
            textAlign = TextAlign.Center
        )
    }
}