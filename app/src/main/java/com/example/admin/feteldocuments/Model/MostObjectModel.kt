package com.example.admin.feteldocuments.Model

import com.example.admin.feteldocuments.R

class MostObjectModel {
    private var objectName: String = ""
    private var objectImage: Int = 0

    constructor()
    constructor(_objectName: String, _objecImage: Int) {
        this.objectName = _objectName
        this.objectImage = _objecImage
    }

    fun getName(): String {
        return this.objectName
    }

    fun setName(name: String) {
        this.objectName = name
    }

    fun getImage(): Int {
        return this.objectImage
    }

    fun setImage(image: Int) {
        this.objectImage = image
    }

}