
package com.example.admin.feteldocuments.Model

import com.example.admin.feteldocuments.R

class CategoryModel {
    private var objectName: String = ""
    private var objectImage: String = ""
    private var keyId  : String = ""

    constructor()
    constructor(_objectName: String, _objecImage: String,_keyId : String) {
        this.objectName = _objectName
        this.objectImage = _objecImage
        this.keyId = _keyId
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
    fun getKey(): String {
        return this.keyId
    }

    fun setKey(_keyId: String) {
        this.keyId = _keyId
    }
}