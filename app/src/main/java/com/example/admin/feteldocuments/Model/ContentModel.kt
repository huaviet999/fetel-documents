package com.example.admin.feteldocuments.Model

class ContentModel{
    private var contentID : String = ""
    private var contentName: String = ""
    private var url  : String = ""
    private var keyId  : String = ""

    constructor()
    constructor(_objectName: String, _objecImage: String,_keyId : String,_url:String) {
        this.contentName = _objectName
        this.keyId = _keyId
        this.url = _url
    }

    fun getContentName(): String {
        return this.contentName
    }

    fun setContentName(name: String) {
        this.contentName = name
    }

    fun getContentID(): String {
        return this.contentID
    }

    fun setContentID(_contentID: String) {
        this.contentID = _contentID
    }
    fun getContentKey(): String {
        return this.keyId
    }

    fun setContentKey(_keyId: String) {
        this.keyId = _keyId
    }
    fun getContentURL() : String{
        return this.url
    }
    fun setContentURL(_url : String){
        this.url = _url
    }
}