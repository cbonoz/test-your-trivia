package trivia.test.model

class State {
    var name: String? = null
    var abbreviation: String? = null
    var capital: String? = null
    var statehoodYear: String? = null
    var statehoodOrder: String? = null

    constructor() {}
    constructor(name: String?, abbreviation: String?, capital: String?, statehoodYear: String?, statehoodOrder: String?) {
        this.name = name
        this.abbreviation = abbreviation
        this.capital = capital
        this.statehoodYear = statehoodYear
        this.statehoodOrder = statehoodOrder
    }

}