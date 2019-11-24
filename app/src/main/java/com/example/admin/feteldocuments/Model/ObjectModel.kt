package com.example.admin.feteldocuments.Model

import com.example.admin.feteldocuments.R

class ObjectModel {
    private var objectName: String = ""
    private var objectImage: String = ""
    private var categoryID: String = ""
    private var keyID : String = ""

    constructor()
    constructor(_objectName: String, _objecImage: String, _categoryID: String,_keyID :String) {
        this.objectName = _objectName
        this.objectImage = _objecImage
        this.categoryID = _categoryID
        this.keyID = _keyID
    }

    fun getName(): String {
        return this.objectName
    }

    fun setName(name: String) {
        this.objectName = name
    }

    fun getImage(): String {
        return this.objectImage
    }

    fun setImage(image: String) {
        this.objectImage = image
    }

    fun getCategoryID(): String {
        return this.categoryID
    }

    fun setCategoryID(_categoryID: String) {
        this.categoryID = _categoryID
    }
    fun getKeyID(): String {
        return this.keyID
    }

    fun setKeyID(_keyID: String) {
        this.keyID = _keyID
    }
}

