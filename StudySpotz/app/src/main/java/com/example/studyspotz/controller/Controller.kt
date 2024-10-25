package com.example.studyspotz.controller

//import model.Model
////import entities.Task
//
//// types of actions that our controller understands
//enum class ViewEvent { Add, Del, Update, Save, Exit }
//
//class Controller(private val model: Model) {
//    fun invoke(event: ViewEvent, obj: Any) {
//        when(event) {
//            ViewEvent.Add -> model.add(obj as Task)
//            ViewEvent.Del -> model.del(obj as Task)
//            ViewEvent.Update -> model.list[(obj as Task).index].content = obj.content
//            ViewEvent.Save -> model.save()
//            ViewEvent.Exit -> return
//        }
//    }
//}