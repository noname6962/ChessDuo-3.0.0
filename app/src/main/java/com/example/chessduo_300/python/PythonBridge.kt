package com.example.chessduo_300.python

import android.content.Context
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

fun validateMove(
    context: Context,
    white: List<List<Any>>,
    black: List<List<Any>>,
    x: Int,
    y: Int,
    vecX: Int,
    vecY: Int,
    turn: Int,
    moves: List<List<Any>>
): Boolean {
    if (!Python.isStarted()) {
        Python.start(AndroidPlatform(context))
    }

    val py = Python.getInstance()
    val gameModel = py.getModule("game_model")
    val builtins = py.getBuiltins()

    fun toPyList2D(data: List<List<Any>>): PyObject {
        val pyOuterList = builtins.callAttr("list") // outer []
        for (innerList in data) {
            val pyInnerList = builtins.callAttr("list") // inner []
            for (item in innerList) {
                pyInnerList.callAttr("append", item)   // add item one-by-one
            }
            pyOuterList.callAttr("append", pyInnerList)
        }
        return pyOuterList
    }

    val pyWhite = toPyList2D(white)
    val pyBlack = toPyList2D(black)
    val pyMoves = toPyList2D(moves)

    val result = gameModel.callAttr("ruch", pyWhite, pyBlack, x, y, vecX, vecY, turn, pyMoves)
    return result.toInt() == 1
}

fun checkMate(
    context: Context,
    white: List<List<Any>>,
    black: List<List<Any>>,
    turn: Int,
    moves: List<List<Any>>
): Boolean {
    if (!Python.isStarted()) {
        Python.start(AndroidPlatform(context))
    }

    val py = Python.getInstance()
    val gameModel = py.getModule("game_model")

    val builtins = py.getBuiltins()
    fun toPyList2D(data: List<List<Any>>): PyObject {
        val pyOuterList = builtins.callAttr("list")
        for (inner in data) {
            val pyInner = builtins.callAttr("list")
            for (item in inner) pyInner.callAttr("append", item)
            pyOuterList.callAttr("append", pyInner)
        }
        return pyOuterList
    }

    val pyWhite = toPyList2D(white)
    val pyBlack = toPyList2D(black)
    val pyMoves = toPyList2D(moves)

    val result = gameModel.callAttr("mat", pyWhite, pyBlack, turn, pyMoves)
    return result?.toInt() == 1
}

