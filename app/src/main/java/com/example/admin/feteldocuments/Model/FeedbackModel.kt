package com.example.admin.feteldocuments.Model

class FeedbackModel{
    private var studentCode : String = ""
    private var name : String = ""
    private var content : String = ""
    constructor()
    constructor(_studentCode : String,_name : String,_content:String){
        this.studentCode = _studentCode
        this.name = _name
        this.content = _content
    }
    fun getName(): String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }
    fun getStudentCode(): String {
        return this.studentCode
    }

    fun setStudentCode(_studentCode: String) {
        this.studentCode = _studentCode
    }
    fun getContent(): String {
        return this.content
    }

    fun setContent(_content: String) {
        this.content = _content
    }
}